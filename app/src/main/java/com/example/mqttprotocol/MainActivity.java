package com.example.mqttprotocol;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity  {
    static  String MQTTHOST ="tcp://192.168.43.79:1883";
    static String USERNAME ="nehasharma";
    static  String PASSWORD="nssharma";
    String topicStr="Dog";
    MqttAndroidClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());



        try {
             IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.e("Neha","OnSuccess");
                    Toast.makeText(MainActivity.this,"connected!!",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.e("Neha",exception.getMessage() +  "\n" + exception.getLocalizedMessage());
                    Toast.makeText(MainActivity.this," connection failed!!",Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
    public void pub(View v){

        String topic = topicStr;
        String message = "Hi neha Howz you";

        try {
               client.publish(topic, message.getBytes(),0,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }


    }

}