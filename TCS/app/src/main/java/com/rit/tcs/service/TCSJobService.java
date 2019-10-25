package com.rit.tcs.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import com.rit.tcs.AppController;
import com.rit.tcs.ConfigActivity;
import com.rit.tcs.CustomPopUp2;
import com.rit.tcs.MainActivity;
import com.rit.tcs.retrofit.NetworkUtility;
import com.rit.tcs.retrofit.RetrofitClient;
import com.rit.tcs.util.ConfigPreference;
import com.rit.tcs.util.TCSPreference;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;

import static com.rit.tcs.retrofit.NetworkUtility.*;

public class TCSJobService extends JobService {


    //    final String serverUri = "tcp://letsfado.com:8883";//tcs
//    String serverUri = "tcp://192.168.1.22:8883";//tcs
    String serverUri = "";//tcs


    String publishTopic = "/oppo"; //tcs
    String subscriptionTopic = "/stat";
    String rfidSubscriptionTopic = "/card";

   /* final String publishTopic = "lck/b8:27:eb:a2:99:24/oppo"; //tcs
    String subscriptionTopic = "lck/b8:27:eb:a2:99:24/stat";
    String rfidSubscriptionTopic = "lck/b8:27:eb:a2:99:24/card";*/


    MqttAndroidClient mqttAndroidClient;
    MqttAsyncClient mqttAsyncClient;
    String macId = "lck/";
    String clientId = "HC-A";

    //    String publishTopic = "/oppo"; //office
    // String publishMessage = "get,4";// get to get staus
    String publishMessage = "";// get to get staus
    String resultMessage = "";// set to open
    String resultMessage_final = "";// set to open

    int issend = 0;
    TCSPreference preference = null;
    ConfigPreference preference_config = null;

    private boolean isToSubscribe = false;
    private BroadcastReceiver broadcastLockerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getExtras().getString("RFID");
            setToSubscribe(true);
        }
    };
    private BroadcastReceiver broadcastRFIDReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setToSubscribe(false);
        }
    };
    private BroadcastReceiver broadcastLockerReceiverforinitialchecking = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String publishMessage = intent.getExtras().getString("Id");
            setToSubscribe(true);
            publishMessage(publishMessage);
        }
    };

    public boolean isToSubscribe() {

        return isToSubscribe;
    }

    public void setToSubscribe(boolean toSubscribe) {
        isToSubscribe = toSubscribe;
        if (mqttAndroidClient != null) {
            if (isToSubscribe) {
                try {
                    unSubscribe(mqttAndroidClient, rfidSubscriptionTopic);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    unSubscribe(mqttAndroidClient, subscriptionTopic);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
            try {
                subscribeToTopic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("TCSJobService", "onStartJob: ");

        try {
            if (broadcastLockerReceiver != null)
                unregisterReceiver(broadcastLockerReceiver);
            if (broadcastLockerReceiverforinitialchecking != null)
                unregisterReceiver(broadcastLockerReceiverforinitialchecking);
            if (broadcastRFIDReceiver != null)
                unregisterReceiver(broadcastRFIDReceiver);
        } catch (Exception e) {

        }
        try{
            registerReceiver(broadcastLockerReceiver, new IntentFilter(
                    BROADCAST_ACTION_2));
        }catch(Exception e){

        }

        try{
            registerReceiver(broadcastLockerReceiverforinitialchecking, new IntentFilter(
                    BROADCAST_CHECK_INITIAL_STATUS));
        }catch(Exception e){

        }

        try{
            registerReceiver(broadcastRFIDReceiver, new IntentFilter(
                    BROADCAST_ACTION_3));
        }catch(Exception e){

        }




        preference = new TCSPreference(this);
        preference_config = new ConfigPreference(this);

        clientId = clientId + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        /*newly added for form*/
        String server_uri = (preference_config.getPreference("String", "BASE_MQTT"));
        if (server_uri.equalsIgnoreCase("")) {
//            serverUri = "tcp://192.168.1.22:8883";
        } else {
            serverUri = server_uri;
        }
        String mcId = (preference_config.getPreference("String", "macid"));
        if (mcId.equalsIgnoreCase("")) {
//            macId = macId + "b8:27:eb:a2:99:24";
//            publishTopic = macId + publishTopic;
//            subscriptionTopic = macId + subscriptionTopic;
//            rfidSubscriptionTopic = macId + rfidSubscriptionTopic;
        } else {
            macId = macId + mcId;
            publishTopic = macId + publishTopic;
            subscriptionTopic = macId + subscriptionTopic;
            rfidSubscriptionTopic = macId + rfidSubscriptionTopic;
            RetrofitClient.setBaseurl(preference_config.getPreference("String", "BASE_ENDPOINT"));
            BASE=preference_config.getPreference("String", "BASE_ENDPOINT");
        }
        /*newly added for form*/

       /* if (preference_config.getPreference("String", "macid").equals("")) {
            macId = macId + "b8:27:eb:a2:99:24";
        }else{

        }*/

        if (mqttAndroidClient == null) {
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

                    if (resultMessage.length() >= 6) {
                        if (!isToSubscribe && (resultMessage != null && !resultMessage.equalsIgnoreCase(""))) {
                            Intent dialogIntent = new Intent(TCSJobService.this, MainActivity.class);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            dialogIntent.putExtra("IsFromRFID", true);
                            dialogIntent.putExtra("RFID", resultMessage);
                            TCSJobService.this.startActivity(dialogIntent);
                            setToSubscribe(true);
                        } else {
                            // locker checking
//                        Intent intent = new Intent(BROADCAST_ACTION_1);
//                        intent.putExtra("RFID", resultMessage);
//                        sendBroadcast(intent);
                            if (AppController.broadcast) {
                                AppController.broadcast=false;
                                if (issend == 0) {
                                    issend = 1;
                                    String slic1 = resultMessage.substring(0, 2);
                                    String slic2 = resultMessage.substring(14, 16);
                                    if (resultMessage.length() == 18 && slic1.equalsIgnoreCase("02") && slic2.equalsIgnoreCase("03")) {
                                        Intent intent1 = new Intent(BROADCAST_CHECK_INITIAL_STATUS_RECEIVED_DATA);
                                        intent1.putExtra("DATA", resultMessage);
                                        sendBroadcast(intent1);
                                        unSubscribe(mqttAndroidClient, subscriptionTopic);

                                    } else {

                                    }
                                } else {

                                }

                                CountDownTimer timer = new CountDownTimer(200, 200) {
                                    public void onTick(long millisUntilFinished) {
                                        issend = 1;
                                    }

                                    public void onFinish() {

                                        issend = 0;

                                    }
                                };
                                timer.start();
                            }

                        }
                    }else{
                        new CustomPopUp2().popup_service(TCSJobService.this, "RFID is not valid.", false);

                    }


                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    addToHistory("Incoming message: " + "Delivery Complete");
                }
            });

            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setCleanSession(false);
            mqttConnectOptions.setUserName("eegrab"); //tcs
            mqttConnectOptions.setPassword("eegrab@123!".toCharArray());
            try {
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
                    }
                });


            } catch (MqttException ex) {
                ex.printStackTrace();
            }


        }


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("TCSJobService", "onStopJob: ");
       /* try {
            if (subscriptionTopic != null)
                unSubscribe(mqttAndroidClient, subscriptionTopic);
            if (mqttAndroidClient != null) {
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient.close();
                disconnect(mqttAndroidClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return true;

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

    private void addToHistory(String mainText) {
        System.out.println("LOG: TCSJobService " + mainText);
    }

    public void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(isToSubscribe ? subscriptionTopic : rfidSubscriptionTopic, 1, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    addToHistory("Subscribed!");
//                    publishMessage();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to subscribe");
                }
            });


        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
        }
    }

    public void unSubscribe(@NonNull MqttAndroidClient client, @NonNull final String topic) throws MqttException {

        IMqttToken token = client.unsubscribe(topic);

        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                Log.d("Message: ", "UnSubscribe Successfully " + topic);
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                Log.e("Message: ", "UnSubscribe Failed " + topic);
            }
        });
    }

    public void publishMessage(String publishMessage) {

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
//            message.setRetained(false);
            mqttAndroidClient.publish(publishTopic, message);
            addToHistory("Message Published");
            if (!mqttAndroidClient.isConnected()) {
                addToHistory(mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
        }
    }

}
