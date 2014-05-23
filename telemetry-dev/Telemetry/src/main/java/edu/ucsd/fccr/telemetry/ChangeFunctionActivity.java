package edu.ucsd.fccr.telemetry;

/**
 Created by FCCR Telemetry Group (5/22/2014)

 Creates a new activity which allows the user to modify the value of a variable
 or a function parameter. It then updates the singleton TelemetryApp instance
 and resumes back to the main activity.


 Known Bugs:

 Notes:
 - The switch statement was left in the case that the FunctionFragment will use
 numerical values to be changed. Currently, it accepts any string to replace
 the parameters line.

 */

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

public class ChangeFunctionActivity extends Activity {

    // Calls the singleton instance of the TelemetryApp
    TelemetryApp tApp = TelemetryApp.getInstance();

    // Declare Views
    TextView functionNameTextView;
    TextView functionMaxTextView;
    TextView functionMinTextView;
    EditText functionValueEditText;
    Button okayButton;
    Button cancelButton;

    // Declare variables
    private int index;
    private String name;
    private String value;
    private String newValue;
    private String min;
    private String max;
    private String[] Values;

    // Declare a int which will track whether the function parameters stay within bounds
    private int FunCheck = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_window);

        // Sets the variables from the singleton TelemetryApp instance
        index = tApp.getIndex();
        name = tApp.getFunNames()[index];
        value = tApp.getFunValues()[index];
        min = tApp.getFunMins()[index];
        max = tApp.getFunMaxs()[index];
        newValue = value;

        // Finds the views by the id's from R.config
        functionNameTextView = (TextView) findViewById(R.id.nameTextView);
        functionMaxTextView = (TextView) findViewById(R.id.maxTextView);
        functionMinTextView = (TextView) findViewById(R.id.minTextView);
        functionValueEditText = (EditText) findViewById(R.id.valueEditText);
        okayButton = (Button) findViewById(R.id.okayButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        // Sets the view values to those specific to each item
        functionNameTextView.setText(name);
        functionMaxTextView.setText("Max Value : " + max);
        functionMinTextView.setText("Min Value : " + min);
        functionValueEditText.setText(newValue);

        // Add listeners to watch for clicks/changes
        functionValueEditText.addTextChangedListener(functionValueEditTextListener);
        okayButton.setOnClickListener(okayButtonOnClickListener);
        cancelButton.setOnClickListener(cancelButtonOnClickListener);
    }

    protected void onResume(){
        super.onResume();

        // Sets the variables from the singleton TelemetryApp instance
        index = tApp.getIndex();
        name = tApp.getFunNames()[index];
        value = tApp.getFunValues()[index];
        min = tApp.getFunMins()[index];
        max = tApp.getFunMaxs()[index];
        newValue = value;

        // Sets the view values to those specific to each item
        functionNameTextView.setText(name);
        functionMaxTextView.setText("Max Value : " + max);
        functionMinTextView.setText("Min Value : " + min);
        functionValueEditText.setText(newValue);

    }

    // Listens for the EditText to change values. Also, sets the intermediate variable
    // to the input value.
    private TextWatcher functionValueEditTextListener = new TextWatcher(){

        @Override
        public void afterTextChanged(Editable arg0) {

            // Sets the check value since any string will be accepted
            FunCheck = 0;

            // Sets the intermediate value to the input
            newValue = arg0.toString();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

        }

    };

    // Listens for the okay button to be clicked
    private View.OnClickListener okayButtonOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View arg0) {

            // Opens a switch case on whether the parameter values are within the range (min,max)
            switch (FunCheck) {

                // Case 0 is if the value is within (min,max) and a number
                case 0:

                    // Creates an intent to switch back to the main activity
                    Intent returnIntent = new Intent();

                    // Sets the values in the TelemetryApp class to the new input values
                    Values = tApp.getFunValues();
                    Values[index] = newValue;
                    tApp.setFunValues(Values);

                    // Closes the activity and sets the resultCode to return a "Good Result"
                    setResult(RESULT_FIRST_USER, returnIntent);
                    finish();
                    break;

                // Case 1 is if the value > max (Unused)
                case 1:

                    // Notify the user of the maxError and reset the EditText
                    Toast maxError = Toast.makeText(getApplicationContext(), "Too High", Toast.LENGTH_SHORT);
                    maxError.show();
                    functionValueEditText.setText(value);
                    break;

                // Case 2 is if the value < min (Unused)
                case 2:

                    // Notify the user of the minError and reset the EditText
                    Toast minError = Toast.makeText(getApplicationContext(), "Too Low", Toast.LENGTH_SHORT);
                    minError.show();
                    functionValueEditText.setText(value);
                    break;

                // Case 3 is if the value is not a number (Unused)
                case 3:

                    // Notify the user of the notANumber and reset the EditText
                    Toast notANumber = Toast.makeText(getApplicationContext(), "Not a Number", Toast.LENGTH_SHORT);
                    notANumber.show();
                    functionValueEditText.setText(value);
                    break;
            }
            finish();
        }

    };

    // Listens for the cancel button to be pressed
    private View.OnClickListener cancelButtonOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            // Closes the activity without saving any of the changes to the values
            Intent returnIntent = new Intent();
            setResult(RESULT_FIRST_USER, returnIntent);
            finish();

        }

    };


}
