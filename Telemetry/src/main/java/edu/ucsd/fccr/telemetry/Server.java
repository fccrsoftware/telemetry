package edu.ucsd.fccr.telemetry;

/**
 * Created by failingtofocus on 4/13/14.
 */

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.Parser;

public class Server implements Runnable {

    public static final String SERVERIP = "192.168.43.217";
    public static final int SERVERPORT = 14551;

    @Override
    public void run() {
        try {
                        /* Retrieve the ServerName */
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.d("UDP", "S: Connecting...");
                        /* Create new UDP-Socket */
            DatagramSocket socket = new DatagramSocket(SERVERPORT, serverAddr);

                        /* We know, how much data will be waiting for us, since
                        the min to max amount of data that can be sent via MAVLink is 8-263 bytes */
            byte[] buf = new byte[264];
                        /* Prepare a UDP-Packet that can
                         * contain the data we want to receive */
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            Log.d("UDP", "S: Receiving...");
                        /* Parser Constructor*/
            Parser parser = new Parser();
                        /* Receive the UDP-Packet */
            socket.receive(packet);
                        /* Access the data in the packet */
            byte[] receivedData = packet.getData();

            MAVLinkPacket mlPacket = null;

            for(byte currentByte : receivedData){
                mlPacket = parser.mavlink_parse_char(currentByte);
            }
            if(mlPacket == null){
                Log.d("UDP", "S: Null.");
            }

            Log.d("UDP", "S: Received - " + mlPacket + "'");
            Log.d("UDP", "S: Done.");


        } catch (Exception e) {
            Log.e("UDP", "S: Error", e);
        }
    }
}
