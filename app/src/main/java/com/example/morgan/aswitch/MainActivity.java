package com.example.morgan.aswitch;

import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DatagramSocket socket = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(7777);
                    while (true) {
                        //byte data[] = new byte[1024];
                        byte data[] = new byte[32];
                        DatagramPacket packet = new DatagramPacket(data, data.length);
                        try {
                            socket.receive(packet);
                            final String result = new String(packet.getData(), packet.getOffset(), packet.getLength());
                            //
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable(){
                                        @Override
                                        public void run() {
                                            //更新UI
                                            ((EditText)findViewById(R.id.msg)).setText(result);
                                        }
                                    });
                                }
                            }).start();
                            //
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {


                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(7777);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                WifiManager wifiManager=(WifiManager)getSystemService(getBaseContext().WIFI_SERVICE);
                WifiManager.MulticastLock multicastLock=wifiManager.createMulticastLock("multicast.test");
                multicastLock.acquire();

                InetAddress addr= null;
                try {
                    addr = InetAddress.getByName("192.168.1.108");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                MulticastSocket socket= null;
                try {
                    socket = new MulticastSocket(7777);
                    socket.joinGroup(addr);
                    //socket.setNetworkInterface(NetworkInterface.getByName("eth0"));
                    //socket.setLoopbackMode(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();*/



        //

        //显示IP地址



        final String[] ipArray=(new GetIP().getAllIp());//本机ip段的全部ip地址
        ((TextView)findViewById(R.id.ip)).setText("本机IP："+(new GetIP()).getLocalIP());
        //final String[] ipArray=(new GetIP()).getAllIp();

        //添加点击事件
        findViewById(R.id.switch_desk_lamp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String msg="on";
                        if(((Switch)findViewById(R.id.switch_desk_lamp)).isChecked()){

                        }else{
                            msg="off";
                        }


                        for (int i = 0; i < ipArray.length; i++) {
                            (new UdpClient()).sendUdpMsg(ipArray[i],8888,msg);
                        }

                    }
                }).start();
            }
        });

        //
        //设置一个 UDP 服务器，用来监听 灯 传递的消息
        //

        //
    }

}
