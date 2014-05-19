package edu.ucsd.fccr.telemetry;

/**
 * Created by CTogashi on 5/19/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Colin on 5/9/2014.
 */
public class ChangeWindowActivity extends Activity {

    TelemetryApp tApp = TelemetryApp.getInstance();

    TextView variableNameTextView;
    TextView variableMaxTextView;
    TextView variableMinTextView;
    EditText variableValueEditText;
    Button okayButton;
    Button cancelButton;

    private int index;
    private String name;
    private String value;
    private String newValue;
    private String min;
    private String max;
    private String[] Values;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_window);

        Intent fromMain = getIntent();

        index = tApp.getIndex();
        name = tApp.getVarNames()[index];
        value = tApp.getVarValues()[index];
        min = tApp.getVarMins()[index];
        max = tApp.getVarMaxs()[index];
        newValue = value;

        variableNameTextView = (TextView) findViewById(R.id.variableNameTextView);
        variableMaxTextView = (TextView) findViewById(R.id.variableMaxTextView);
        variableMinTextView = (TextView) findViewById(R.id.variableMinTextView);
        variableValueEditText = (EditText) findViewById(R.id.variableValueEditText);
        okayButton = (Button) findViewById(R.id.okayButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        variableNameTextView.setText(name);
        variableMaxTextView.setText("Max Value : " + max);
        variableMinTextView.setText("Min Value : " + min);
        variableValueEditText.setText(newValue);

        variableValueEditText.addTextChangedListener(variableValueEditTextListener);
        okayButton.setOnClickListener(okayButtonOnClickListener);
        cancelButton.setOnClickListener(cancelButtonOnClickListener);
    }

    private TextWatcher variableValueEditTextListener = new TextWatcher(){

        @Override
        public void afterTextChanged(Editable arg0) {


        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

            try {

                if (Double.parseDouble(arg0.toString()) > Double.parseDouble(max)) {

                    Toast maxError = Toast.makeText(getApplicationContext(), "Too High", Toast.LENGTH_SHORT);
                    maxError.show();
                    variableValueEditText.setText(newValue);

                } else if (Double.parseDouble(arg0.toString()) < Double.parseDouble(min)) {

                    Toast minError = Toast.makeText(getApplicationContext(), "Too Low", Toast.LENGTH_SHORT);
                    minError.show();
                    variableValueEditText.setText(newValue);
                } else {

                    newValue = arg0.toString();

                }
            } catch (NumberFormatException e) {

                Toast notANumber = Toast.makeText(getApplicationContext(), "Not a Number", Toast.LENGTH_SHORT);
                notANumber.show();
                variableValueEditText.setText(newValue);

            }

        }

    };

    private OnClickListener okayButtonOnClickListener = new OnClickListener(){

        @Override
        public void onClick(View arg0) {
            Intent returnIntent = new Intent();
            Values = tApp.getVarValues();
            Values[index] = newValue;
            tApp.setVarValues(Values);
            setResult(RESULT_FIRST_USER, returnIntent);
            finish();
        }

    };

    private OnClickListener cancelButtonOnClickListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            setResult(RESULT_FIRST_USER, returnIntent);
            finish();

        }

    };

}
