package com.rit.tcs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.rit.tcs.bussinessobject.GenericModel;
import com.rit.tcs.bussinessobject.LoginFirstModel.LoginFirstModel;
import com.rit.tcs.interfaces.confirmClick;
import com.rit.tcs.retrofit.*;
import com.rit.tcs.util.ConfigPreference;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Route;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import static com.rit.tcs.util.UtilKt.scheduleJob;

public class ConfigActivity extends AppCompatActivity {

    private static final int REQUEST_READ_PHONE_STATE = 111;
    final String serverUri = "tcp://letsfado.com:8883";//tcs
    LinearLayout ll_ip1, ll_ip2, ll_ip3, ll_ip4, ll_ip5, ll_ip6,ll_ip7,ll_ip8,ll_ip9,ll_ip10, ll_login;
    Button test_mqtt, test_base_enspoint, btn_login, btn_clear,test_proxy;
    ScrollView scroll;
    Handler handler;
    TelephonyManager tManager = null;
    String device_uid = "";
    com.rit.tcs.bussinessobject.LoginFirstModel.Profile profile = null;
    String macid = "";
    MqttAndroidClient mqttAndroidClient;
    String clientId = "HC-A";
RelativeLayout hoverLyts;
    CustomPopUp customPopUp;
    String str1="";
    String str2="";
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            startActivity(new Intent(ConfigActivity.this, TabMainActivity.class));
                        startActivity(new Intent(ConfigActivity.this, TutorialActivity.class));

            finish();
        }
    };
    private EditText et_password, et_username, et_endpoint, et_local_password, et_local_username, et_mqtt,et_proxyhost,et_proxyport,et_proxyusername,et_proxypassword;
    private ConfigPreference configPreference;
    private long mLastClickTime = 0;
    private boolean colon_exist=true;
    private String str="";
    private Authenticator proxy_authenticator=null;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.config);

        configPreference = new ConfigPreference(this);
        hoverLyts=findViewById(R.id.hoverLyts);
        ll_ip1 = findViewById(R.id.ll_ip1);
        ll_ip2 = findViewById(R.id.ll_ip2);
        ll_ip3 = findViewById(R.id.ll_ip3);
        ll_ip4 = findViewById(R.id.ll_ip4);
        ll_ip5 = findViewById(R.id.ll_ip5);
        ll_ip6 = findViewById(R.id.ll_ip6);
        ll_ip7 = findViewById(R.id.ll_ip7);
        ll_ip8 = findViewById(R.id.ll_ip8);
        ll_ip9 = findViewById(R.id.ll_ip9);
        ll_ip10 = findViewById(R.id.ll_ip10);

        ll_login = findViewById(R.id.ll_login);

        ll_login.setVisibility(View.GONE);
        ll_ip4.setVisibility(View.GONE);
        ll_ip5.setVisibility(View.GONE);
        ll_ip6.setVisibility(View.GONE);
        ll_ip7.setVisibility(View.GONE);
        ll_ip8.setVisibility(View.GONE);
        ll_ip9.setVisibility(View.GONE);
        ll_ip10.setVisibility(View.GONE);


        test_mqtt = findViewById(R.id.test_mqtt);
        test_base_enspoint = findViewById(R.id.test_base_enspoint);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setEnabled(false);
        btn_clear = findViewById(R.id.btn_clear);
        test_proxy=findViewById(R.id.test_proxy);

        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);
        et_endpoint = findViewById(R.id.et_endpoint);
        et_local_password = findViewById(R.id.et_local_password);
        et_local_username = findViewById(R.id.et_local_username);
        et_mqtt = findViewById(R.id.et_mqtt);
        et_proxyhost = findViewById(R.id.et_proxyhost);
        et_proxyport = findViewById(R.id.et_proxyport);
        et_proxyusername = findViewById(R.id.et_proxyusername);
        et_proxypassword = findViewById(R.id.et_proxypassword);


        scroll = findViewById(R.id.scroll);

//        configPreference.startToSavePreference();h
//        configPreference.savePreference("String", "BASE_ENDPOINT", "http://"+str1+"/");
//        configPreference.stopToSavePreference();
        et_mqtt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
                test_mqtt.setText("TEST");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_local_password.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
                test_mqtt.setText("TEST");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_local_username.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
                test_mqtt.setText("TEST");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });

        et_proxyhost.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_proxyport.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_proxyusername.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_proxypassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                ll_ip4.setVisibility(View.GONE);
                test_proxy.setText("TEST");
                test_proxy.setBackgroundColor(getResources().getColor(R.color.tring));
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });

        test_proxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
               /* Authenticator proxyAuthenticator = new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        String credential = Credentials.basic(username, password);
                        return response.request().newBuilder()
                                .header("Proxy-Authorization", credential)
                                .build();
                    }
                };*/
                if (et_proxyhost.getText().toString().equalsIgnoreCase("") || et_proxyport.getText().toString().equalsIgnoreCase("") || et_proxyusername.getText().toString().equalsIgnoreCase("") ||
                        et_proxypassword.getText().toString().equalsIgnoreCase("") ) {
                    new CustomPopUp2().popup(ConfigActivity.this, "PLease enter all proxy data..", false);
                }else{
                     proxy_authenticator=new Authenticator() {
                        @Override
                        public Request authenticate(Route route, okhttp3.Response response) throws IOException {
                            String credential = Credentials.basic(et_proxyusername.getText().toString().trim(), et_proxypassword.getText().toString().trim());
                            return response.request().newBuilder()
                                    .header("Proxy-Authorization", credential)
                                    .build();
                        }

                  /* @Override
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(et_proxyusername.getText().toString().trim(), et_proxypassword.getText().toString().trim().toCharArray());
                   }*/

                    };
                    if(proxy_authenticator!=null){
                        test_proxy.setText("PASS");
                        test_proxy.setBackgroundColor(getResources().getColor(R.color.green));
                        Apiclientlogin.setproxy(proxy_authenticator);
                        Apiclientlogin.sethost(et_proxyhost.getText().toString().trim());
                        Apiclientlogin.setport(et_proxyport.getText().toString().trim());

                        Apiclientmeridian.setproxy(proxy_authenticator);
                        Apiclientmeridian.sethost(et_proxyhost.getText().toString().trim());
                        Apiclientmeridian.setport(et_proxyport.getText().toString().trim());

                          ll_ip4.setVisibility(View.VISIBLE);
                          test_base_enspoint.setText("TEST");
                          test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));
                          btn_login.setEnabled(false);

                    }else{
                        test_proxy.setText("RETRY");
                        test_proxy.setBackgroundColor(getResources().getColor(R.color.pink));

                        ll_ip4.setVisibility(View.GONE);
                        ll_ip5.setVisibility(View.GONE);
                        ll_ip6.setVisibility(View.GONE);
                               /* ll_ip7.setVisibility(View.GONE);
                                ll_ip8.setVisibility(View.GONE);
                                ll_ip9.setVisibility(View.GONE);
                                ll_ip10.setVisibility(View.GONE);*/
                        ll_login.setVisibility(View.GONE);
                        btn_login.setEnabled(false);
                    }
                  /*  Apiclientlogin.setproxy(proxy_authenticator);
                    Apiclientlogin.sethost(et_proxyhost.getText().toString().trim());
                    Apiclientlogin.setport(et_proxyport.getText().toString().trim());*/
                }




            }
        });

        test_mqtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                hoverLyts.setVisibility(View.VISIBLE);
                test_mqtt.setText("TRYING...");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));
                btn_login.setEnabled(false);
                ll_ip4.setVisibility(View.GONE);
                btn_login.setEnabled(false);
                clientId = clientId + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                if (mqttAndroidClient == null) {
                    mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), et_mqtt.getText().toString().trim(), clientId);
                    mqttAndroidClient.setCallback(new MqttCallbackExtended() {
                        @Override
                        public void connectComplete(boolean reconnect, String serverURI) {
                            if (reconnect) {
                                addToHistory("Reconnected to : " + serverURI);
                            } else {
                                addToHistory("Connected to: " + serverURI);
                            }
                        }

                        @Override
                        public void connectionLost(Throwable cause) {
                            hoverLyts.setVisibility(View.GONE);
                            test_mqtt.setText("TEST");
                            test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));
                            test_base_enspoint.setText("TEST");
                            test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));
                            ll_ip4.setVisibility(View.VISIBLE);
                            btn_login.setEnabled(false);
                            ll_ip5.setVisibility(View.GONE);
                            ll_ip6.setVisibility(View.GONE);
                            ll_ip7.setVisibility(View.GONE);
                            ll_ip8.setVisibility(View.GONE);
                            ll_ip9.setVisibility(View.GONE);
                            ll_ip10.setVisibility(View.GONE);
                            ll_login.setVisibility(View.GONE);
                            btn_login.setEnabled(false);
                            mqttAndroidClient=null;
                            addToHistory("The Connection was lost.");
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            hoverLyts.setVisibility(View.GONE);
                            addToHistory("Incoming message: " + new String(message.getPayload()));

                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                            hoverLyts.setVisibility(View.GONE);
                            addToHistory("Incoming message: " + "Delivery Complete");

                        }
                    });

                    MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                    mqttConnectOptions.setAutomaticReconnect(true);
                    mqttConnectOptions.setCleanSession(false);
                    mqttConnectOptions.setUserName(et_local_username.getText().toString().trim()); //tcs
                    mqttConnectOptions.setPassword(et_local_password.getText().toString().trim().toCharArray());

                    try {
                        //addToHistory("Connecting to " + serverUri);
                        mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                if (mqttAndroidClient == null) {

                                }else{
                                    try{
                                        hoverLyts.setVisibility(View.GONE);
                                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                                        disconnectedBufferOptions.setBufferEnabled(true);
                                        disconnectedBufferOptions.setBufferSize(100);
                                        disconnectedBufferOptions.setPersistBuffer(false);
                                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                                        mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                                        test_mqtt.setText("PASS");
                                        test_mqtt.setBackgroundColor(getResources().getColor(R.color.green));
                                        /* ll_ip7.setVisibility(View.VISIBLE);
                                ll_ip8.setVisibility(View.VISIBLE);
                                ll_ip9.setVisibility(View.VISIBLE);
                                ll_ip10.setVisibility(View.VISIBLE);*/
                                         ll_ip7.setVisibility(View.GONE);
                                ll_ip8.setVisibility(View.GONE);
                                ll_ip9.setVisibility(View.GONE);
                                        ll_ip10.setVisibility(View.GONE);
                                        test_base_enspoint.setText("TEST");
                                        test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));
                                        ll_ip4.setVisibility(View.VISIBLE);
                                        btn_login.setEnabled(false);
                                        mqttAndroidClient=null;
                               /* ll_ip5.setVisibility(View.VISIBLE);
                                ll_ip6.setVisibility(View.VISIBLE);
                                ll_login.setVisibility(View.VISIBLE);
                                btn_login.setEnabled(true);*/

//                            subscribeToTopic();
                                    }catch (Exception e){

                                    }

                                }

                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                hoverLyts.setVisibility(View.GONE);
                                test_mqtt.setText("RETRY");
                                test_mqtt.setBackgroundColor(getResources().getColor(R.color.pink));
                                ll_ip4.setVisibility(View.GONE);
                                ll_ip5.setVisibility(View.GONE);
                                ll_ip6.setVisibility(View.GONE);
                                ll_ip7.setVisibility(View.GONE);
                                ll_ip8.setVisibility(View.GONE);
                                ll_ip9.setVisibility(View.GONE);
                                ll_ip10.setVisibility(View.GONE);
                                ll_login.setVisibility(View.GONE);
                                btn_login.setEnabled(false);
                                mqttAndroidClient=null;
                                addToHistory("Failed to connect to: " + serverUri);

                            }
                        });


                    } catch (MqttException ex) {
                        ex.printStackTrace();

                    }

                }


            }
        });
        test_base_enspoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1="";
                boolean popup_showed=true;
                colon_exist=true;
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                 str=et_endpoint.getText().toString()+"/";

                try {

                     str1=(new URL(str)).getAuthority();
                    if( "".equalsIgnoreCase(str1) || str1==null ){
                        if(popup_showed) {
                            popup_showed=false;
                            new CustomPopUp2().popup(ConfigActivity.this, "Please give valid port number..", false);
                        }
                    }else{
                        if(str1.contains(":")){
                            colon_exist=true;
                            if(str1!=null && !str1.equalsIgnoreCase("")){
                                str2 = (str1.substring(str1.lastIndexOf(":") + 1));

                            }else{
                                if(popup_showed) {
                                    popup_showed=false;
                                    new CustomPopUp2().popup(ConfigActivity.this, "Please give valid port number..", false);
                                }
                            }

                        }else{
                            colon_exist=false;
                            Apiclientmeridian.setBaseurl(str);
                            Apiclientmeridian.setproxy(proxy_authenticator);
                            Apiclientmeridian.sethost(et_proxyhost.getText().toString().trim());
                            Apiclientmeridian.setport(et_proxyport.getText().toString().trim());
                            apitestbaseurl();
                        }
                    }

                    /* if(str1.contains(":")){
                         colon_exist=true;
                         if(str1!=null && !str1.equalsIgnoreCase("")){
                             str2 = (str1.substring(str1.lastIndexOf(":") + 1));

                         }else{
                             if(popup_showed) {
                                 popup_showed=false;
                                 new CustomPopUp2().popup(ConfigActivity.this, "Please give valid port number..", false);
                             }
                         }

                     }else{
                         colon_exist=false;
                         Apiclientmeridian.setBaseurl(str);
                         apitestbaseurl();
                     }*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if(str2!=null && !str2.equalsIgnoreCase("") && str1!=null && !str1.equalsIgnoreCase("") ){
                    colon_exist=true;
                    if(str2.length()>4){
                        if(popup_showed) {
                            popup_showed=false;
                            new CustomPopUp2().popup(ConfigActivity.this, "Please give valid port number..", false);
                        }
                    }else{
                        Apiclientmeridian.setBaseurl("http://"+str1+"/");
                        Apiclientmeridian.setproxy(proxy_authenticator);
                        Apiclientmeridian.sethost(et_proxyhost.getText().toString().trim());
                        Apiclientmeridian.setport(et_proxyport.getText().toString().trim());
                        apitestbaseurl();
                    }
                }else{
                   // colon_exist=true;
                    if(colon_exist){
                        if(popup_showed) {
                            popup_showed=false;
                            new CustomPopUp2().popup(ConfigActivity.this, "Please give valid port number..", false);
                        }
                    }else{
                        colon_exist=false;
                        Apiclientmeridian.setBaseurl(str);
                        Apiclientmeridian.setproxy(proxy_authenticator);
                        Apiclientmeridian.sethost(et_proxyhost.getText().toString().trim());
                        Apiclientmeridian.setport(et_proxyport.getText().toString().trim());
                        apitestbaseurl();
                    }

                }

            }
        });
        et_local_username.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
               // Apiclientmeridian.setBaseurl(str);
                ll_ip4.setVisibility(View.GONE);
                ll_ip5.setVisibility(View.GONE);
                ll_ip6.setVisibility(View.GONE);
               /* ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);*/
                ll_login.setVisibility(View.GONE);
                test_mqtt.setText("TEST");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_local_password.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
               // Apiclientmeridian.setBaseurl(str);
                ll_ip4.setVisibility(View.GONE);
                ll_ip5.setVisibility(View.GONE);
                ll_ip6.setVisibility(View.GONE);
               /* ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);*/
                ll_login.setVisibility(View.GONE);
                test_mqtt.setText("TEST");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        et_mqtt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
                //Apiclientmeridian.setBaseurl(str);
                ll_ip4.setVisibility(View.GONE);
                ll_ip5.setVisibility(View.GONE);
                ll_ip6.setVisibility(View.GONE);
               /* ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);*/
                ll_login.setVisibility(View.GONE);
                test_mqtt.setText("TEST");
                test_mqtt.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });




        et_endpoint.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                String str = sb.toString();
               // Apiclientmeridian.setBaseurl(str+"/");
                ll_ip5.setVisibility(View.GONE);
                ll_ip6.setVisibility(View.GONE);
               /* ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);*/
                ll_login.setVisibility(View.GONE);
                test_base_enspoint.setText("TEST");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.tring));

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (et_local_password.getText().toString().equalsIgnoreCase("") || et_local_username.getText().toString().equalsIgnoreCase("") || et_username.getText().toString().equalsIgnoreCase("") ||
                        et_password.getText().toString().equalsIgnoreCase("") || et_mqtt.getText().toString().equalsIgnoreCase("") || et_endpoint.getText().toString().equalsIgnoreCase("")||et_proxyhost.getText().toString().equalsIgnoreCase("")||et_proxyport.getText().toString().equalsIgnoreCase("")||et_proxyusername.getText().toString().equalsIgnoreCase("")||et_proxypassword.getText().toString().equalsIgnoreCase("")) {
                    new CustomPopUp2().popup(ConfigActivity.this, "PLease enter all field..", false);
                } else {
                    int permissionCheck = ContextCompat.checkSelfPermission(ConfigActivity.this, Manifest.permission.READ_PHONE_STATE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ConfigActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                    } else {
                        //TODO
                        apicalllogin();

                    }


                }

            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // et_password.setText("");
               // et_username.setText("");
                et_endpoint.setText("");
                et_local_password.setText("");
                et_local_username.setText("");
                et_mqtt.setText("");
            }
        });
        /*newly added*/

    }

    private void addToHistory(String mainText) {
        System.out.println("LOG: TCSJobService " + mainText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void setKeyBoardUp(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(editText, InputMethodManager.RESULT_HIDDEN);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }



   /* fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }*/

    public void apitestbaseurl() {
        hoverLyts.setVisibility(View.VISIBLE);

        Apiinterface apiService =
                Apiclientmeridian.getClientanother().create(Apiinterface.class);

        Call<GenericModel> call = apiService.getTopRatedMovies();
        call.enqueue(new Callback<GenericModel>() {
            @Override
            public void onResponse(Call<GenericModel> call, Response<GenericModel> response) {
                hoverLyts.setVisibility(View.GONE);

                btn_login.setEnabled(true);
                btn_login.setVisibility(View.VISIBLE);
                ll_login.setVisibility(View.VISIBLE);
                try {
                    GenericModel s = response.body();
                    boolean success = false;
                    success = s.getStatus().equalsIgnoreCase("true");
                    if (success) {
                        if(colon_exist){
                            hoverLyts.setVisibility(View.GONE);
                            test_base_enspoint.setText("PASS");
                            test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.green));
                            ll_ip5.setVisibility(View.VISIBLE);
                            ll_ip6.setVisibility(View.VISIBLE);
                           /* ll_ip7.setVisibility(View.VISIBLE);
                            ll_ip8.setVisibility(View.VISIBLE);
                            ll_ip9.setVisibility(View.VISIBLE);
                            ll_ip10.setVisibility(View.VISIBLE);*/
                            ll_ip7.setVisibility(View.GONE);
                            ll_ip8.setVisibility(View.GONE);
                            ll_ip9.setVisibility(View.GONE);
                            ll_ip10.setVisibility(View.GONE);
                            ll_login.setVisibility(View.VISIBLE);
                            RetrofitClient.setBaseurl("http://"+str1+"/");
                            Apiclientlogin.setBaseurl("http://"+str1+"/");
                            NetworkUtility.BASE=("http://"+str1+"/");
                            btn_login.setVisibility(View.VISIBLE);
                            btn_login.setEnabled(true);
                        }else{
                            hoverLyts.setVisibility(View.GONE);
                            test_base_enspoint.setText("PASS");
                            test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.green));
                            ll_ip5.setVisibility(View.VISIBLE);
                            ll_ip6.setVisibility(View.VISIBLE);
                          /*  ll_ip7.setVisibility(View.VISIBLE);
                            ll_ip8.setVisibility(View.VISIBLE);
                            ll_ip9.setVisibility(View.VISIBLE);
                            ll_ip10.setVisibility(View.VISIBLE);*/
                            ll_login.setVisibility(View.VISIBLE);
                            ll_ip7.setVisibility(View.GONE);
                            ll_ip8.setVisibility(View.GONE);
                            ll_ip9.setVisibility(View.GONE);
                            ll_ip10.setVisibility(View.GONE);
                            RetrofitClient.setBaseurl(str);
                            Apiclientlogin.setBaseurl(str);
                            NetworkUtility.BASE=(str);
                            btn_login.setVisibility(View.VISIBLE);
                            btn_login.setEnabled(true);
                        }


                        // Toast.makeText(ConfigActivity.this, "success", Toast.LENGTH_SHORT).show();

                    } else {
                        test_base_enspoint.setText("RETRY");
                        hoverLyts.setVisibility(View.GONE);
                        test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.pink));
                        //Toast.makeText(ConfigActivity.this, "faild", Toast.LENGTH_SHORT).show();
                        ll_ip5.setVisibility(View.GONE);
                        ll_ip6.setVisibility(View.GONE);
                       /* ll_ip7.setVisibility(View.GONE);
                        ll_ip8.setVisibility(View.GONE);
                        ll_ip9.setVisibility(View.GONE);
                        ll_ip10.setVisibility(View.GONE);*/
                        ll_login.setVisibility(View.GONE);
                        btn_login.setEnabled(false);
                    }

                } catch (Exception e) {
                    hoverLyts.setVisibility(View.GONE);
                    test_base_enspoint.setText("RETRY");
                    test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.pink));
                    //Toast.makeText(ConfigActivity.this, "faild", Toast.LENGTH_SHORT).show();
                    ll_ip5.setVisibility(View.GONE);
                    ll_ip6.setVisibility(View.GONE);
                   /* ll_ip7.setVisibility(View.GONE);
                    ll_ip8.setVisibility(View.GONE);
                    ll_ip9.setVisibility(View.GONE);
                    ll_ip10.setVisibility(View.GONE);*/
                    ll_login.setVisibility(View.GONE);
                    btn_login.setEnabled(false);
                }

            }

            @Override
            public void onFailure(Call<GenericModel> call, Throwable t) {
                hoverLyts.setVisibility(View.GONE);
                // Log error here since request failed
                test_base_enspoint.setText("RETRY");
                test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.pink));
                //Toast.makeText(ConfigActivity.this, "faild", Toast.LENGTH_SHORT).show();
                ll_ip5.setVisibility(View.GONE);
                ll_ip6.setVisibility(View.GONE);
               /* ll_ip7.setVisibility(View.GONE);
                ll_ip8.setVisibility(View.GONE);
                ll_ip9.setVisibility(View.GONE);
                ll_ip10.setVisibility(View.GONE);*/
                ll_login.setVisibility(View.GONE);
                btn_login.setEnabled(false);
//                Toast.makeText(ConfigActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void apicalllogin() {
        hoverLyts.setVisibility(View.VISIBLE);
        int permissionCheck = ContextCompat.checkSelfPermission(ConfigActivity.this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ConfigActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {

            tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            device_uid = tManager.getDeviceId();

        }

        Api apiService =
                Apiclientlogin.getClientanother().create(Api.class);

        //Call<LoginFirstModel> call = apiService.createlogin1(et_username.getText().toString(), et_password.getText().toString(), device_uid, "ANDROID");
        Call<LoginFirstModel> call = apiService.checkproxy();

        call.enqueue(new Callback<LoginFirstModel>() {
            @Override
            public void onResponse(Call<LoginFirstModel> call, Response<LoginFirstModel> response) {
                btn_login.setEnabled(true);
                try {
                    LoginFirstModel s = response.body();
                    boolean success = false;
                    success = s.getStatus().equalsIgnoreCase("success");
                    if (success) {
                        if(colon_exist){
                            hoverLyts.setVisibility(View.GONE);

                            RetrofitClient.setBaseurl("http://"+str1+"/");
                            NetworkUtility.BASE=("http://"+str1+"/");
                            macid = s.getProfile().getCompanydevices().get(0).getMacid();
                            configPreference.startToSavePreference();
                            configPreference.savePreference("String", "localpassword", et_local_password.getText().toString());
                            configPreference.savePreference("String", "localusername", et_local_username.getText().toString());
                            configPreference.savePreference("String", "hrd_password", et_password.getText().toString());
                            configPreference.savePreference("String", "hrd_username", et_username.getText().toString());
                            configPreference.savePreference("String", "BASE_MQTT", et_mqtt.getText().toString());
                            configPreference.savePreference("String", "BASE_ENDPOINT", "http://"+str1+"/");
                            configPreference.savePreference("String", "macid", macid);
                            configPreference.savePreference("String", "iS_form_open", "1");
                            configPreference.stopToSavePreference();
                            new CustomPopUp2().popup_confirm(ConfigActivity.this, s.getMessage().toString(), new confirmClick() {
                                @Override
                                public void onClick() {
                                    handler = new Handler();
                                    handler.postDelayed(runnable, 1000);
                                    scheduleJob(ConfigActivity.this);
                                    // startActivity(new Intent(ConfigActivity.this, TabMainActivity.class));
                                    startActivity(new Intent(ConfigActivity.this, TutorialActivity.class));
                                    finish();
                                }
                            });

                        }else{
                            hoverLyts.setVisibility(View.GONE);

                            RetrofitClient.setBaseurl(str);
                            NetworkUtility.BASE=(str);
                            macid = s.getProfile().getCompanydevices().get(0).getMacid();
                            configPreference.startToSavePreference();
                            configPreference.savePreference("String", "localpassword", et_local_password.getText().toString());
                            configPreference.savePreference("String", "localusername", et_local_username.getText().toString());
                            configPreference.savePreference("String", "hrd_password", et_password.getText().toString());
                            configPreference.savePreference("String", "hrd_username", et_username.getText().toString());
                            configPreference.savePreference("String", "BASE_MQTT", et_mqtt.getText().toString());
                            configPreference.savePreference("String", "BASE_ENDPOINT", str);
                            configPreference.savePreference("String", "macid", macid);
                            configPreference.savePreference("String", "iS_form_open", "1");
                            configPreference.stopToSavePreference();
                            new CustomPopUp2().popup_confirm(ConfigActivity.this, s.getMessage().toString(), new confirmClick() {
                                @Override
                                public void onClick() {
                                    handler = new Handler();
                                    handler.postDelayed(runnable, 1000);
                                    scheduleJob(ConfigActivity.this);
                                    // startActivity(new Intent(ConfigActivity.this, TabMainActivity.class));
                                    startActivity(new Intent(ConfigActivity.this, TutorialActivity.class));
                                    finish();
                                }
                            });

                        }


                    } else {
                        hoverLyts.setVisibility(View.GONE);
                        new CustomPopUp2().popup(ConfigActivity.this, s.getMessage().toString(),false);
                        // btn_login.setEnabled(false);
                    }
                    hoverLyts.setVisibility(View.GONE);
                } catch (Exception e) {
                    hoverLyts.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<LoginFirstModel> call, Throwable t) {
                // Log error here since request failed
                hoverLyts.setVisibility(View.GONE);
               // new CustomPopUp2().popup(ConfigActivity.this, "failed",false);

                try{
                    String s=t.getMessage();
                    String s1=t.getLocalizedMessage();

                    if( "".equalsIgnoreCase(s) || s==null ){
                        new CustomPopUp2().popup(ConfigActivity.this, "failed", false);
                        test_base_enspoint.setText("RETRY");
                        test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.pink));

                    }else{
                        new CustomPopUp2().popup(ConfigActivity.this, s, false);
                        test_base_enspoint.setText("RETRY");
                        test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.pink));
                    }

//                    new CustomPopUp2().popup(MainActivity.this, s, false);
//
//                    test_base_enspoint.setText("RETRY");
//                    test_base_enspoint.setBackgroundColor(getResources().getColor(R.color.pink));

                }catch(Exception e){
                    e.printStackTrace();
                }

//                Toast.makeText(ConfigActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    apicalllogin();
                }
                break;

            default:
                break;
        }
    }
void apicalllogin1(){
    if(colon_exist){
        hoverLyts.setVisibility(View.GONE);

        RetrofitClient.setBaseurl("http://"+str1+"/");
        NetworkUtility.BASE=("http://"+str1+"/");
        macid = "b8:27:eb:a2:99:24";
        configPreference.startToSavePreference();
        configPreference.savePreference("String", "localpassword", et_local_password.getText().toString());
        configPreference.savePreference("String", "localusername", et_local_username.getText().toString());
        configPreference.savePreference("String", "hrd_password", et_password.getText().toString());
        configPreference.savePreference("String", "hrd_username", et_username.getText().toString());
        configPreference.savePreference("String", "BASE_MQTT", et_mqtt.getText().toString());
        configPreference.savePreference("String", "BASE_ENDPOINT", "http://"+str1+"/");
        configPreference.savePreference("String", "macid", macid);
        configPreference.savePreference("String", "iS_form_open", "1");
        configPreference.stopToSavePreference();
        new CustomPopUp2().popup_confirm(ConfigActivity.this, "You have successfully login", new confirmClick() {
            @Override
            public void onClick() {
                handler = new Handler();
                handler.postDelayed(runnable, 1000);
                scheduleJob(ConfigActivity.this);
                // startActivity(new Intent(ConfigActivity.this, TabMainActivity.class));
                startActivity(new Intent(ConfigActivity.this, TutorialActivity.class));
                finish();
            }
        });

    }else{
        hoverLyts.setVisibility(View.GONE);

        RetrofitClient.setBaseurl(str);
        NetworkUtility.BASE=(str);
        macid = "b8:27:eb:a2:99:24";
        configPreference.startToSavePreference();
        configPreference.savePreference("String", "localpassword", et_local_password.getText().toString());
        configPreference.savePreference("String", "localusername", et_local_username.getText().toString());
        configPreference.savePreference("String", "hrd_password", et_password.getText().toString());
        configPreference.savePreference("String", "hrd_username", et_username.getText().toString());
        configPreference.savePreference("String", "BASE_MQTT", et_mqtt.getText().toString());
        configPreference.savePreference("String", "BASE_ENDPOINT", str);
        configPreference.savePreference("String", "macid", macid);
        configPreference.savePreference("String", "iS_form_open", "1");
        configPreference.stopToSavePreference();
       new CustomPopUp2().popup_confirm(ConfigActivity.this, "You have successfully login", new confirmClick() {
            @Override
            public void onClick() {
                handler = new Handler();
                handler.postDelayed(runnable, 1000);
                scheduleJob(ConfigActivity.this);
                // startActivity(new Intent(ConfigActivity.this, TabMainActivity.class));
                startActivity(new Intent(ConfigActivity.this, TutorialActivity.class));
                finish();
            }
        });

    }
}

}
