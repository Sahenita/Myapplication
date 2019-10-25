package com.rit.tcs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.rit.tcs.bussinessobject.RFIDDataModel;
import com.rit.tcs.interfaces.confirmClick;
import com.rit.tcs.retrofit.NetworkUtility;
import com.rit.tcs.retrofit.RetrofitClient;
import com.rit.tcs.util.ConfigPreference;
import com.rit.tcs.util.TCSPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.InetAddress;

import static com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_1;
import static com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_3;

public class TabMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_OPEN_LOCKER = 205;
    public TextView img_internet;
    MyKeyboard keyboard;
    LinearLayout rl_main;
    int length;
    boolean mStopHandler = false;
    android.support.constraint.ConstraintLayout coordinatorLayout;
    // to check if we are connected to Network
 // to check if we are connected to Network
    boolean isConnected = true;
    ConnectivityManager.NetworkCallback connectivityCallback;
    Handler handler1 = new Handler();
    Runnable runnable;
    int delay = 1000;
    Handler handler;
    Handler mhandler;
    Runnable r;
    private EditText mEditText1, mEditText2, mEditText3, mEditText4;
    private String inputStr = "";
    private TCSPreference prefManager;
    private ConfigPreference configpref;
    private RelativeLayout hoverLyt;
    /*newly added*/
    private TextView img1;
    private TextView img2;
    private TextView img3;
    private TextView img4;
    private EditText edit1;
    private EditText edit2;
    private EditText edit3;

    /*newly added*/
    private EditText edit4;
    private String finalpin = "";
    // to check if we are monitoring Network
    private boolean monitoringConnectivity = false;

    private BroadcastReceiver broadcastRFIDReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent dialogIntent = new Intent(TabMainActivity.this, MainActivity.class);
            dialogIntent.putExtra("IsFromRFID", true);
            dialogIntent.putExtra("RFID", intent.getExtras().getString("RFID"));
            startActivity(dialogIntent);
//            finish();
        }
    };

    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
        stopHandler();//stop first and then start
        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 30 * 30 * 1000); //for 5 minutes
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_passcode_new);

        Intent intent = new Intent(BROADCAST_ACTION_3);
        sendBroadcast(intent);
        prefManager = new TCSPreference(this);
        configpref = new ConfigPreference(this);
        RetrofitClient.setBaseurl(configpref.getPreference("String", "BASE_ENDPOINT"));
NetworkUtility.BASE=configpref.getPreference("String", "BASE_ENDPOINT");

        /*newly added*/
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img_internet = findViewById(R.id.img_internet);
        rl_main = findViewById(R.id.rl_main);
        edit1.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edit1.setTextIsSelectable(true);
        edit2.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edit2.setTextIsSelectable(true);
        edit3.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edit3.setTextIsSelectable(true);
        edit4.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edit4.setTextIsSelectable(true);
        edit1.setFocusable(true);
        edit1.setFocusableInTouchMode(true);
        edit2.setFocusable(false);
        edit2.setFocusableInTouchMode(false);
        edit3.setFocusable(false);
        edit3.setFocusableInTouchMode(false);
        edit4.setFocusable(false);
        edit4.setFocusableInTouchMode(false);
        keyboard = findViewById(R.id.keyboard);
        keyboard.setVisibility(View.GONE);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboard.setVisibility(View.VISIBLE);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboard.setVisibility(View.VISIBLE);

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboard.setVisibility(View.VISIBLE);

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboard.setVisibility(View.VISIBLE);

            }
        });


        edit1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboard.setVisibility(View.VISIBLE);
                return false;
            }
        });
        edit2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboard.setVisibility(View.VISIBLE);
                return false;
            }
        });
        edit3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboard.setVisibility(View.VISIBLE);
                return false;
            }
        });
        edit4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboard.setVisibility(View.VISIBLE);
                return false;
            }
        });
        rl_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboard.setVisibility(View.GONE);
                hoverLyt.setVisibility(View.GONE);
                return false;
            }
        });

        edit1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                if (edit1.getText().toString().length() == 1) {
                    String field1 = edit1.getText().toString().trim();
                    setImage(field1, img1);
                    //setImage(field1,tv_verification);
                    edit2.setFocusable(true);
                    edit2.setFocusableInTouchMode(true);
                    edit2.requestFocus();
                } else if (Constant.blank1 == 0) {
                    desetImage(img1);
                    // desetImage(edit1);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        edit2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                if (edit2.getText().toString().length() == 1) {
                    String field2 = edit2.getText().toString().trim();
                    setImage(field2, img2);
                    //setImage(field2,edit2);

                    edit3.setFocusable(true);
                    edit3.setFocusableInTouchMode(true);
                    edit3.requestFocus();
                } else if (Constant.blank2 == 0) {
                    desetImage(img2);
                    //  desetImage(edit2);
                    edit1.setFocusable(true);
                    edit1.setFocusableInTouchMode(true);
                    edit1.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        edit3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                if (edit3.getText().toString().length() == 1) {
                    String field3 = edit3.getText().toString().trim();
                    setImage(field3, img3);
                    // setImage(field3, edit3);
                    edit4.setFocusable(true);
                    edit4.setFocusableInTouchMode(true);
                    edit4.requestFocus();
                } else if (Constant.blank3 == 0) {
                    desetImage(img3);
                    // desetImage(edit3);
                    edit2.setFocusable(true);
                    edit2.setFocusableInTouchMode(true);
                    edit2.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        edit4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                if (edit4.getText().toString().length() == 1) {
                    String field4 = edit4.getText().toString().trim();
                    setImage(field4, img4);
                    // setImage(field4, edit4);
                    edit4.setFocusable(true);
                    edit4.setFocusableInTouchMode(true);
                    edit4.requestFocus();
                } else if (Constant.blank4 == 0) {
                    desetImage(img4);
                    // desetImage(edit4);
                    edit3.setFocusable(true);
                    edit3.setFocusableInTouchMode(true);
                    edit3.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                finalpin = (edit1.getText().toString().trim()).concat(edit2.getText().toString().trim()).concat(edit3.getText().toString().trim()).concat(edit4.getText().toString().trim());
                length = finalpin.length();
                if (edit1.getText().toString().length() == 1 && edit2.getText().toString().length() == 1 && edit3.getText().toString().length() == 1 && edit4.getText().toString().length() == 1) {
                    // loginPinCheckApiCall();
                    requestPostData(finalpin);

                    keyboard.setVisibility(View.GONE);

                }
            }

        });
        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = edit1.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
        InputConnection ic1 = edit2.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection1(ic1);
        InputConnection ic2 = edit3.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection2(ic2);
        InputConnection ic3 = edit4.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection3(ic3);



        /*newly added*/
        mEditText1 = (EditText) findViewById(R.id.edtTxt1);
        mEditText2 = (EditText) findViewById(R.id.edtTxt2);
        mEditText3 = (EditText) findViewById(R.id.edtTxt3);
        mEditText4 = (EditText) findViewById(R.id.edtTxt4);
        mEditText1.setEnabled(true);
        mEditText2.setEnabled(false);
        mEditText3.setEnabled(false);
        mEditText4.setEnabled(false);
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabMainActivity.this, TutorialActivity.class);
                stopHandler();
                startActivity(intent);
                finish();
            }
        });
        hoverLyt = (RelativeLayout) findViewById(R.id.hoverLyt);
        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("0") || s.toString().equals("1") || s.toString().equals("2") || s.toString().equals("3") || s.toString().equals("4") || s.toString().equals("5") || s.toString().equals("6") || s.toString().equals("7") || s.toString().equals("8") || s.toString().equals("9")) {
                    inputStr = mEditText1.getText().toString();
//                    mEditText1.setBackgroundResource(R.drawable.passcode_bg_progess_solid);
                    mEditText1.setEnabled(false);
                    mEditText2.setEnabled(true);
                    mEditText3.setEnabled(false);
                    mEditText4.setEnabled(false);
                    mEditText2.requestFocus();
                    // setKeyBoardUp(mEditText2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("0") || s.toString().equals("1") || s.toString().equals("2") || s.toString().equals("3") || s.toString().equals("4") || s.toString().equals("5") || s.toString().equals("6") || s.toString().equals("7") || s.toString().equals("8") || s.toString().equals("9")) {
                    inputStr = inputStr + mEditText2.getText().toString();
//                    mEditText2.setBackgroundResource(R.drawable.passcode_bg_progess_solid);
                    mEditText1.setEnabled(false);
                    mEditText2.setEnabled(false);
                    mEditText3.setEnabled(true);
                    mEditText4.setEnabled(false);
                    mEditText3.requestFocus();
                    //setKeyBoardUp(mEditText3);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("0") || s.toString().equals("1") || s.toString().equals("2") || s.toString().equals("3") || s.toString().equals("4") || s.toString().equals("5") || s.toString().equals("6") || s.toString().equals("7") || s.toString().equals("8") || s.toString().equals("9")) {
                    inputStr = inputStr + mEditText1.getText().toString();
//                    mEditText3.setBackgroundResource(R.drawable.passcode_bg_progess_solid);
                    mEditText1.setEnabled(false);
                    mEditText2.setEnabled(false);
                    mEditText3.setEnabled(false);
                    mEditText4.setEnabled(true);
                    mEditText4.requestFocus();
                    //setKeyBoardUp(mEditText4);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              /*  mEditText4.setText("");
                //mEditText4.setBackgroundResource(R.drawable.passcode_bg_progess);*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("0") || s.toString().equals("1") || s.toString().equals("2") || s.toString().equals("3") || s.toString().equals("4") || s.toString().equals("5") || s.toString().equals("6") || s.toString().equals("7") || s.toString().equals("8") || s.toString().equals("9")) {
                    inputStr = inputStr + mEditText4.getText().toString();
//                    mEditText4.setBackgroundResource(R.drawable.passcode_bg_progess_solid);
                    mEditText1.setEnabled(false);
                    mEditText2.setEnabled(false);
                    mEditText3.setEnabled(false);
                    mEditText4.setEnabled(false);
                    // requestPostData(inputStr);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(TabMainActivity.this, TutorialActivity.class);
                stopHandler();
                startActivity(intent);
                finish();
            }
        };
        startHandler();
    }

    public void open() {

        String shareBody = prefManager.getPreference("String", "FCMTokenKey");
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    @Override
    public void onClick(View v) {
        inputStr = mEditText1.getText().toString() + mEditText2.getText().toString() + mEditText3.getText().toString() + mEditText4.getText().toString();

    }



    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        showSnack(netInfo != null && netInfo.isConnectedOrConnecting());
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {

//            message = "Good! Connected to Internet";
//            color = Color.WHITE;
        } else {


        }


    }

    public void openLockerMqttByPasscode(String id) {
        try {
            if (!TextUtils.isEmpty(id)) {
                String text = "set," + String.valueOf(Integer.parseInt(id.substring(id.lastIndexOf("_") + 1)) - 1);
//                Intent intent = new Intent(TabMainActivity.this, PahoMqttActivity.class);
//                intent.putExtra("Id", text);
//                intent.putExtra("IsToSubscribe", false);
//                startActivityForResult(intent, REQUEST_OPEN_LOCKER);
            } else {
                Toast.makeText(TabMainActivity.this, "Wrong Passcode!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }

    }

    public void setImage(String s, TextView editText) {
        if ("1".equalsIgnoreCase(s)) {
            editText.setText("1");
        } else if ("2".equalsIgnoreCase(s)) {
            editText.setText("2");
        } else if ("3".equalsIgnoreCase(s)) {
            editText.setText("3");
        } else if ("4".equalsIgnoreCase(s)) {
            editText.setText("4");
        } else if ("5".equalsIgnoreCase(s)) {
            editText.setText("5");
        } else if ("6".equalsIgnoreCase(s)) {
            editText.setText("6");
        } else if ("7".equalsIgnoreCase(s)) {
            editText.setText("7");
        } else if ("8".equalsIgnoreCase(s)) {
            editText.setText("8");
        } else if ("9".equalsIgnoreCase(s)) {
            editText.setText("9");
        } else if ("0".equalsIgnoreCase(s)) {
            editText.setText("0");
        }
    }

    public void desetImage(TextView editText) {
        editText.setText("");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OPEN_LOCKER) {
            if (resultCode == RESULT_OK) {
                inputStr = "";
                Toast.makeText(TabMainActivity.this, "Locker Opened!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TabMainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestPostData(final String passcode) {

        Call<RFIDDataModel> callLogin = RetrofitClient.getInstance().getApi().checkPasscode(passcode);
//        Call<PasscodeModel> callLogin = RetrofitClient.getInstance("89c6a10524686ea15b775dba37ba81b9").getApi().createlogin(userName, pwd, deviceId, "ANDROID");
        hoverLyt.setVisibility(View.VISIBLE);
        callLogin.enqueue(new Callback<RFIDDataModel>() {
            @Override
            public void onResponse(Call<RFIDDataModel> call, Response<RFIDDataModel> response) {
                try {
                    RFIDDataModel s = response.body();
                    boolean success = false;
                    success = s.getStatus().equalsIgnoreCase("success");
                    if (success) {
                        prefManager.startToSavePreference();
                        prefManager.savePreference("String", "UserId", s.getProfile()[0].getId());
                        prefManager.savePreference("String", "name", s.getProfile()[0].getName());
                        prefManager.savePreference("String", "email", s.getProfile()[0].getEmail());
                        prefManager.savePreference("String", "phone", s.getProfile()[0].getPhone());
                        prefManager.stopToSavePreference();
//                        openLockerMqttByPasscode(s.getLocker());
                        mEditText1.setText("");
                        //mEditText1.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText2.setText("");
                        //mEditText2.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText3.setText("");
                        //mEditText3.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText4.setText("");
                        //mEditText4.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText1.setEnabled(false);
                        mEditText2.setEnabled(false);
                        mEditText3.setEnabled(false);
                        mEditText4.setEnabled(false);
                        mEditText1.requestFocus();
                        // setKeyBoardUp(mEditText1);
                        Intent dialogIntent = new Intent(TabMainActivity.this, MainActivity.class);
                        dialogIntent.putExtra("IsFromRFID", false);
                        dialogIntent.putExtra("RFID", passcode);
                        startActivity(dialogIntent);
//                        finish();
                    } else {
//                        startActivity(new Intent(TabMainActivity.this,MainActivity.class));
                        new CustomPopUp2().popup_confirm(TabMainActivity.this, s.getMessage().toString(), new confirmClick() {


                            @Override
                            public void onClick() {
                                keyboard.setVisibility(View.VISIBLE);

                            }
                        });

                        // Toast.makeText(TabMainActivity.this, s.getMessage().toString(), Toast.LENGTH_LONG).show();
                        mEditText1.setText("");
                        //mEditText1.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText2.setText("");
                        //mEditText2.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText3.setText("");
                        //mEditText3.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText4.setText("");
                        //mEditText4.setBackgroundResource(R.drawable.passcode_bg_progess);
                        mEditText1.setEnabled(false);
                        mEditText2.setEnabled(false);
                        mEditText3.setEnabled(false);
                        mEditText4.setEnabled(false);
                        mEditText1.requestFocus();
                        //setKeyBoardUp(mEditText1);
                    }
                    hoverLyt.setVisibility(View.GONE);

                } catch (Exception e) {
                    hoverLyt.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<RFIDDataModel> call, Throwable t) {
                hoverLyt.setVisibility(View.GONE);
                mEditText1.setText("");
                //mEditText1.setBackgroundResource(R.drawable.passcode_bg_progess);
                mEditText2.setText("");
                //mEditText2.setBackgroundResource(R.drawable.passcode_bg_progess);
                mEditText3.setText("");
                //mEditText3.setBackgroundResource(R.drawable.passcode_bg_progess);
                mEditText4.setText("");
                //mEditText4.setBackgroundResource(R.drawable.passcode_bg_progess);
                mEditText1.setEnabled(false);
                mEditText2.setEnabled(false);
                mEditText3.setEnabled(false);
                mEditText4.setEnabled(false);
                // mEditText1.requestFocus();
                //setKeyBoardUp(mEditText1);
                Toast.makeText(TabMainActivity.this, "Network error. Please try again", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void doNothing(View v) {
// do nothing
    }

    @Override
    public void onBackPressed() {

    }

    private void setKeyBoardUp(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    public void isOnline() {
        String status = NetWorkUtils1.getConnectivityStatusString(getApplicationContext());
        if("Not_connected_to_Internet".equalsIgnoreCase(status)){

            img_internet.setText("offline");
            img_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0);
        }else{

            img_internet.setText("online");
            img_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle, 0, 0, 0);
        }
        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            img_internet.setText("online");
            img_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle, 0, 0, 0);


        } else {
            img_internet.setText("offline");
            img_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0);

        }*/
    }


    public boolean isInternetAvailable1() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        handler1.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                isOnline();
                handler1.postDelayed(runnable, delay);
            }
        }, delay);


        try {
            Intent intent = new Intent(BROADCAST_ACTION_3);
            sendBroadcast(intent);
            registerReceiver(broadcastRFIDReceiver, new IntentFilter(
                    BROADCAST_ACTION_1));


        } catch (Exception e) {

        }

    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            handler1.removeCallbacks(runnable); //stop handler when activity not visible

//            registerReceiver(broadcastRFIDReceiver, new IntentFilter(
//                BROADCAST_ACTION_1));
            unregisterReceiver(broadcastRFIDReceiver);

        } catch (Exception e) {

        }

    }

}
