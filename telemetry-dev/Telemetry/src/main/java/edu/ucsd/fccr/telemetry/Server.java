package edu.ucsd.fccr.telemetry;

/**
 * Created by failingtofocus on 4/13/14.
 */

import android.app.Fragment;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.Parser;

public class Server implements Runnable {

    public static final String SERVERIP = "192.168.43.1";
    public static final int SERVERPORT = 14550;
    public DatagramSocket socket = null;

    @Override
    public void run() {
        try {
                        /* Retrieve the ServerName */
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.d("UDP", "S: Connecting...");
                        /* Create UDP-Socket */
            socket = new DatagramSocket(SERVERPORT, serverAddr);


                        /* We know, how much data will be waiting for us, since
                        the min to max amount of data that can be sent via MAVLink is 8-263 bytes */
            byte[] buf = new byte[254];
                        /* Prepare a UDP-Packet that can
                         * contain the data we want to receive */
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            Log.d("UDP", "S: Receiving...");
            boolean run = true;
            while (run) {
                socket.receive(packet);
                        /* Access the data in the packet */
                byte[] receivedData = packet.getData();

                       /* Parser Constructor*/
                Parser parser = new Parser();
                        /* Receive the UDP-Packet */
                MAVLinkPacket mlPacket = null;

                for (int currentByte : receivedData) {
                    currentByte = currentByte & 0xff; // Convert currentByte into unsigned char
                    mlPacket = parser.mavlink_parse_char(currentByte);
                    // Received a mavlinkpacket, get outta here
                    if (mlPacket != null) break;
//                Log.d("UDP", ""+currentByte);
                }

                Log.d("UDP", "S: Received - " + mlPacket.unpack() + "'");
                if (mlPacket.unpack().msgid == 30) break;
            }
            Log.d("UDP", "S: Done");

        } catch (Exception e) {
            Log.e("UDP", "S: Error", e);
        }
    }
}
