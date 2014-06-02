package edu.ucsd.fccr.telemetry;

/**
 * Created by failingtofocus on 4/13/14.
 */

import android.util.Log;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.Messages.ardupilotmega.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client implements Runnable {

    //Joystick params
    /* Tilt -10000 <-> 10000*/
    public short chan1;

    /* Pan -10000 <-> 10000*/
    public short chan2;

    @Override
    public void run() {
        try {
            // Retrieve the ServerName
            InetAddress clientAddr;

            clientAddr = InetAddress.getByName(Server.CLIENTIP);

            Log.d("UDP", "C: Connecting...");
            /* Create new UDP-Socket */
            DatagramSocket socket = new DatagramSocket();

            /* Prepare Mavlink packet to be sent. */
            msg_rc_channels_scaled msg = new msg_rc_channels_scaled();
            msg.time_boot_ms = (int)System.currentTimeMillis();
            msg.chan3_scaled = chan1;
            msg.chan4_scaled = chan2;
            MAVLinkPacket txPacket = msg.pack();

            byte[] buf = txPacket.encodePacket();

                        /* Create UDP-packet with
                         * data & destination(url+port) */
            DatagramPacket packet = new DatagramPacket(buf, buf.length, clientAddr, 14551);
            Log.d("UDP", "C: Sending: '" + txPacket.unpack() + "'");

                        /* Send out the packet */
            socket.send(packet);
            Log.d("UDP", "C: Sent.");
            Log.d("UDP", "C: Done.");
        } catch (Exception e) {
            Log.e("UDP", "C: Error", e);
        }
    }
}
