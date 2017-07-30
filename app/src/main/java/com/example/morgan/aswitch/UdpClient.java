package com.example.morgan.aswitch;

import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by morgan on 2017/7/30.
 */

public class UdpClient {
    public void sendUdpMsg(String ip,int port,String msg) {
        DatagramSocket socket = null;
        InetAddress serverAddress = null;
        byte data[] = msg.getBytes();
        try {
            serverAddress = InetAddress.getByName(ip);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 8888);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendMsg(int port, String msg)throws IOException{
        String destAddressStr = "224.0.0.1";
        int destPort = port;
        int TTL = 4;
        InetAddress destAddress = null;
        destAddress = InetAddress.getByName(destAddressStr);
        MulticastSocket multiSocket = null;
        multiSocket = new MulticastSocket();
        multiSocket.setTimeToLive(TTL);
        byte[] sendMSG = msg.getBytes();
        DatagramPacket dp = new DatagramPacket(sendMSG, sendMSG.length, destAddress  , destPort);
        multiSocket.send(dp);
        multiSocket.close();
    }
}
