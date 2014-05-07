package edu.ucsd.fccr.telemetry;

/**
 * Created by failingtofocus on 4/13/14.
 */

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import edu.ucsd.fccr.telemetry.Parser;

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
            Parser parser = new Parser();
                        /* By magic we know, how much data will be waiting for us */
            byte[] buf = new byte[17];
                        /* Prepare a UDP-Packet that can
                         * contain the data we want to receive */
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            Log.d("UDP", "S: Receiving...");

                        /* Receive the UDP-Packet */
            socket.receive(packet);
            byte[] recievedData = packet.getData();
            Log.d("UDP", "S: Received: '" + parser.mavlink_parse_char(recievedData[0]) + "'");
            Log.d("UDP", "S: Done.");
        } catch (Exception e) {
            Log.e("UDP", "S: Error", e);
        }
    }
}
