/*******************************************************************************
 * Copyright (c) 1999, 2016 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 */
package com.rit.tcs;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.rit.tcs.util.TCSPreference;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;

public class PahoMqttActivity extends AppCompatActivity {

    MqttAndroidClient mqttAndroidClient;
    //    final String macId = "lck/b8:27:eb:b2:2e:4f";
    String macId = "lck/";

    final String serverUri = "tcp://letsfado.com:8883";//tcs
    //    final String serverUri = "tcp://broker.data.letsfado.com:1883"; //office
//    final String macId = "lck/b8:27:eb:b2:2e:4f/";
    String clientId = "HC-";
    //    final String subscriptionTopic = "eegrab/macid/stat";
//    final String subscriptionTopic = "lck/b8:27:eb:e2:97:d1/stat"; //tcs
    String subscriptionTopic = "/stat";  //office
    //    final String subscriptionTopic = "lck/b8:27:eb:b7:c2:84/stat";
//    final String subscriptionTopic = "lck/b8:27:eb:d0:a7:76/stat";
    //    final String subscriptionTopic = "eegrab/#";
//    final String publishTopic = "eegrab/macid/oppo";
//    final String publishTopic = "lck/b8:27:eb:e2:97:d1/oppo"; //tcs
    String publishTopic = "/oppo"; //office
    //    final String publishTopic = "lck/b8:27:eb:b7:c2:84/oppo";
//    final String publishTopic = "lck/b8:27:eb:d0:a7:76/oppo";
    String publishMessage = "get,4";// get to get staus
    private boolean isToSubscribe = true;
    String resultMessage = "";// set to open
    TCSPreference preference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        preference = new TCSPreference(this);

        publishMessage = getIntent().getExtras().getString("Id");
        isToSubscribe = getIntent().getExtras().getBoolean("IsToSubscribe");
        clientId = clientId + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (preference.getPreference("String", "MacId").equals("")) {
            macId = macId + "b8:27:eb:b2:2e:4f";
        }
        subscriptionTopic = macId + preference.getPreference("String", "MacId") + subscriptionTopic;
        publishTopic = macId + preference.getPreference("String", "MacId") + publishTopic;
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    addToHistory("Reconnected to : " + serverURI);
                        subscribeToTopic();
                } else {
                    addToHistory("Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                addToHistory("The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                addToHistory("Incoming message: " + new String(message.getPayload()));
                resultMessage = message.toString();
                if (!isToSubscribe || (resultMessage != null && resultMessage.startsWith("02"))) {
                    Intent intent = new Intent();
                    intent.putExtra("IsToSubscribe", isToSubscribe);
                    intent.putExtra("RESULTS", resultMessage);
                    setResult(RESULT_OK, intent);
                    mqttAndroidClient.unregisterResources();
                    mqttAndroidClient.close();
                    finish();
                    return;
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                addToHistory("Incoming message: " + "Delivery Complete");
               /* if (!isToSubscribe || (resultMessage != null && resultMessage.startsWith("02"))) {
                    Intent intent = new Intent();
                    intent.putExtra("IsToSubscribe", isToSubscribe);
                    intent.putExtra("RESULTS", resultMessage);
                    setResult(RESULT_OK, intent);
                    finish();
                }*/
            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName("eegrab"); //tcs
        mqttConnectOptions.setPassword("eegrab@123!".toCharArray());
//        mqttConnectOptions.setUserName("root"); //office
//        mqttConnectOptions.setPassword("private".toCharArray());
        try {
            //addToHistory("Connecting to " + serverUri);
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to connect to: " + serverUri);
                    Intent intent = new Intent();
                    intent.putExtra("IsToSubscribe", isToSubscribe);
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            });


        } catch (MqttException ex) {
            ex.printStackTrace();
            Intent intent = new Intent();
            intent.putExtra("IsToSubscribe", isToSubscribe);
            setResult(RESULT_CANCELED, intent);
            finish();
        }


    }


    private void addToHistory(String mainText) {
        System.out.println("LOG: " + mainText);
       /* Snackbar.make(findViewById(android.R.id.content), mainText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/

    }

    public void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 1, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    addToHistory("Subscribed!");
                    publishMessage();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to subscribe");
                    Intent intent = new Intent();
                    intent.putExtra("IsToSubscribe", isToSubscribe);
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            });


//            mqttAndroidClient.subscribe(subscriptionTopic, 1);
            // THIS DOES NOT WORK!
           /* mqttAndroidClient.subscribe(subscriptionTopic, 1, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // message Arrived!
                    resultMessage = message.toString();
                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
                    if(resultMessage!=null && resultMessage.startsWith("02"))
                    unSubscribe(mqttAndroidClient, subscriptionTopic);
                }
            });
*/
        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
            Intent intent = new Intent();
            intent.putExtra("IsToSubscribe", isToSubscribe);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    public void unSubscribe(@NonNull MqttAndroidClient client, @NonNull final String topic) throws MqttException {

        IMqttToken token = client.unsubscribe(topic);

        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                Log.d("Message: ", "UnSubscribe Successfully " + topic);
                Intent intent = new Intent();
                intent.putExtra("IsToSubscribe", isToSubscribe);
                intent.putExtra("RESULTS", resultMessage);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                Log.e("Message: ", "UnSubscribe Failed " + topic);
            }
        });
    }

    public void publishMessage() {

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
//            message.setRetained(false);
            mqttAndroidClient.publish(publishTopic, message);
            addToHistory("Message Published");
            if (!mqttAndroidClient.isConnected()) {
                addToHistory(mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
                Intent intent = new Intent();
                intent.putExtra("IsToSubscribe", isToSubscribe);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
            Intent intent = new Intent();
            intent.putExtra("IsToSubscribe", isToSubscribe);
            setResult(RESULT_CANCELED, intent);
            finish();

        }
    }

    public void doNothing(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (subscriptionTopic != null)
                unSubscribe(mqttAndroidClient, subscriptionTopic);
            if (mqttAndroidClient != null) {
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient.close();
                disconnect(mqttAndroidClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void disconnect(@NonNull MqttAndroidClient client) throws MqttException {
        IMqttToken mqttToken = client.disconnect();
        mqttToken.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                Log.d("disconnected", "Successfully disconnected");

            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                Log.d("disconnected", "Failed to disconnected " + throwable.toString());
            }
        });
        client.disconnectForcibly();
    }

}
