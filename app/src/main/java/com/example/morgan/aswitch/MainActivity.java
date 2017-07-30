package com.example.morgan.aswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    DatagramSocket socket = null;
    InetAddress serverAddress = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.switch_desk_lamp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Looper.prepare();
                        //sendDataByUDP();
                        String msg="on";
                        if(((Switch)findViewById(R.id.switch_desk_lamp)).isChecked()){

                        }else{
                            msg="off";
                        }
                        //((Switch)findViewById(R.id.switch_desk_lamp)).setText("ok");

                        try {
                            socket = new DatagramSocket(8888);
                            serverAddress = InetAddress.getByName("192.168.1.107");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String sendData = msg;
                            byte data[] = sendData.getBytes();
                            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 8888);
                            socket.send(packet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
