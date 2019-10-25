package com.rit.tcs;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;


public  class MyKeyboard extends LinearLayout implements View.OnClickListener, View.OnTouchListener {

    // constructors
    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // keyboard keys (buttons)
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButton0;
    private Button mButtonDelete;
    private Button button_clear;
    private Button mButtonEnter;

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;
    InputConnection inputConnection1;
    InputConnection inputConnection2;
    InputConnection inputConnection3;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.confirm_keyboards, this, true);
        mButton1 = (Button) findViewById(R.id.button_1);
        mButton2 = (Button) findViewById(R.id.button_2);
        mButton3 = (Button) findViewById(R.id.button_3);
        mButton4 = (Button) findViewById(R.id.button_4);
        mButton5 = (Button) findViewById(R.id.button_5);
        mButton6 = (Button) findViewById(R.id.button_6);
        mButton7 = (Button) findViewById(R.id.button_7);
        mButton8 = (Button) findViewById(R.id.button_8);
        mButton9 = (Button) findViewById(R.id.button_9);
        mButton0 = (Button) findViewById(R.id.button_0);
        mButtonDelete = (Button) findViewById(R.id.button_delete);
        button_clear=findViewById(R.id.button_clear);

       // mButtonEnter = (Button) findViewById(R.id.button_enter);

        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        button_clear.setOnClickListener(this);

        mButton1.setOnTouchListener(this);
        mButton2.setOnTouchListener(this);
        mButton3.setOnTouchListener(this);
        mButton4.setOnTouchListener(this);
        mButton5.setOnTouchListener(this);
        mButton6.setOnTouchListener(this);
        mButton7.setOnTouchListener(this);
        mButton8.setOnTouchListener(this);
        mButton9.setOnTouchListener(this);
        mButton0.setOnTouchListener(this);
        mButtonDelete.setOnTouchListener(this);
        button_clear.setOnTouchListener(this);

        //mButtonEnter.setClickable(false);

        // map buttons IDs to input strings
        keyValues.put(R.id.button_1, "1");
        keyValues.put(R.id.button_2, "2");
        keyValues.put(R.id.button_3, "3");
        keyValues.put(R.id.button_4, "4");
        keyValues.put(R.id.button_5, "5");
        keyValues.put(R.id.button_6, "6");
        keyValues.put(R.id.button_7, "7");
        keyValues.put(R.id.button_8, "8");
        keyValues.put(R.id.button_9, "9");
        keyValues.put(R.id.button_0, "0");
       /* keyValues.put(R.id.button_enter, "\n");*/
    }
    public static String removeCharAt(String s) {
        StringBuffer buf = new StringBuffer( s.length() - 1 );
       // buf.append( s.substring(0,pos) ).append( s.substring(pos+1) );
        return buf.toString();
    }

    @Override
    public void onClick(View v) {

        CharSequence selectedText = inputConnection.getTextBeforeCursor(1,0);
        CharSequence selectedText1 = inputConnection1.getTextBeforeCursor(1,0);
        CharSequence selectedText2 = inputConnection2.getTextBeforeCursor(1,0);
        CharSequence selectedText3 = inputConnection3.getTextBeforeCursor(1,0);
        String s4 = selectedText3.toString();
        String s3 = selectedText2.toString();
        String s2 = selectedText1.toString();
        String s1 = selectedText.toString();
        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;
        if (inputConnection1 == null) return;
        if (inputConnection2 == null) return;
        if (inputConnection3 == null) return;

        // Delete text or input key value
        // All communication goes through the InputConnection

        if (v.getId() == R.id.button_delete) {

            if (!s4.equalsIgnoreCase("")) {
               // removeCharAt(s);
                StringBuffer buf4 = new StringBuffer( s4 );
                buf4.deleteCharAt(0);
                String str4 = buf4.toString();
                Log.d("print", str4);
               // inputConnection3.commitText("",1);
                Constant.blank4=0;
                inputConnection3.deleteSurroundingText(1, 0);

            }
            else if(!s3.equalsIgnoreCase("") && s4.equalsIgnoreCase("")){
                StringBuffer buf3 = new StringBuffer( s3 );
                buf3.deleteCharAt(0);
                String str3 = buf3.toString();
                Log.d("print", str3);
                // inputConnection3.commitText("",1);
                Constant.blank3=0;
                inputConnection2.deleteSurroundingText(1, 0);


            } else if(!s2.equalsIgnoreCase("") && s4.equalsIgnoreCase("") && s3.equalsIgnoreCase("")){
                StringBuffer buf2 = new StringBuffer( s2 );
                buf2.deleteCharAt(0);
                String str2 = buf2.toString();
                Log.d("print", str2);
                // inputConnection3.commitText("",1);
                Constant.blank2=0;
                inputConnection1.deleteSurroundingText(1, 0);

            } else if(!s1.equalsIgnoreCase("") && s4.equalsIgnoreCase("") && s3.equalsIgnoreCase("") && s2.equalsIgnoreCase("")){
                StringBuffer buf1 = new StringBuffer( s1 );
                buf1.deleteCharAt(0);
                String str1 = buf1.toString();
                Log.d("print", str1);
                // inputConnection3.commitText("",1);
                Constant.blank1=0;
                inputConnection.deleteSurroundingText(1, 0);

            }else {
                // delete the selection
               // inputConnection.commitText("", 1);
            }
        }  else if (v.getId() == R.id.button_clear) {
            if (!s4.equalsIgnoreCase("") && !s3.equalsIgnoreCase("")&& !s2.equalsIgnoreCase("")&& !s1.equalsIgnoreCase("") ) {
                // removeCharAt(s);
                StringBuffer buf4 = new StringBuffer( s4 );
                buf4.deleteCharAt(0);
                String str4 = buf4.toString();
                Log.d("print", str4);
                // inputConnection3.commitText("",1);
                Constant.blank4=0;
                inputConnection3.deleteSurroundingText(1, 0);

                StringBuffer buf3 = new StringBuffer( s3 );
                buf3.deleteCharAt(0);
                String str3 = buf3.toString();
                Log.d("print", str3);
                // inputConnection3.commitText("",1);
                Constant.blank3=0;
                inputConnection2.deleteSurroundingText(1, 0);

                StringBuffer buf2 = new StringBuffer( s2 );
                buf2.deleteCharAt(0);
                String str2 = buf2.toString();
                Log.d("print", str2);
                // inputConnection3.commitText("",1);
                Constant.blank2=0;
                inputConnection1.deleteSurroundingText(1, 0);

                StringBuffer buf1 = new StringBuffer( s1 );
                buf1.deleteCharAt(0);
                String str1 = buf1.toString();
                Log.d("print", str1);
                // inputConnection3.commitText("",1);
                Constant.blank1=0;
                inputConnection.deleteSurroundingText(1, 0);



            }else if (!s3.equalsIgnoreCase("")&& !s2.equalsIgnoreCase("")&& !s1.equalsIgnoreCase("") ) {
                // removeCharAt(s);


                StringBuffer buf3 = new StringBuffer( s3 );
                buf3.deleteCharAt(0);
                String str3 = buf3.toString();
                Log.d("print", str3);
                // inputConnection3.commitText("",1);
                Constant.blank3=0;
                inputConnection2.deleteSurroundingText(1, 0);

                StringBuffer buf2 = new StringBuffer( s2 );
                buf2.deleteCharAt(0);
                String str2 = buf2.toString();
                Log.d("print", str2);
                // inputConnection3.commitText("",1);
                Constant.blank2=0;
                inputConnection1.deleteSurroundingText(1, 0);

                StringBuffer buf1 = new StringBuffer( s1 );
                buf1.deleteCharAt(0);
                String str1 = buf1.toString();
                Log.d("print", str1);
                // inputConnection3.commitText("",1);
                Constant.blank1=0;
                inputConnection.deleteSurroundingText(1, 0);
            }else if ( !s2.equalsIgnoreCase("")&& !s1.equalsIgnoreCase("") ) {
                // removeCharAt(s);


                StringBuffer buf2 = new StringBuffer( s2 );
                buf2.deleteCharAt(0);
                String str2 = buf2.toString();
                Log.d("print", str2);
                // inputConnection3.commitText("",1);
                Constant.blank2=0;
                inputConnection1.deleteSurroundingText(1, 0);

                StringBuffer buf1 = new StringBuffer( s1 );
                buf1.deleteCharAt(0);
                String str1 = buf1.toString();
                Log.d("print", str1);
                // inputConnection3.commitText("",1);
                Constant.blank1=0;
                inputConnection.deleteSurroundingText(1, 0);

            }else if ( !s1.equalsIgnoreCase("")) {
                // removeCharAt(s);



                StringBuffer buf1 = new StringBuffer( s1 );
                buf1.deleteCharAt(0);
                String str1 = buf1.toString();
                Log.d("print", str1);
                // inputConnection3.commitText("",1);
                Constant.blank1=0;
                inputConnection.deleteSurroundingText(1, 0);


            }
        }
        else {

            String value = keyValues.get(v.getId());
            if(s1.equalsIgnoreCase("")){
                inputConnection.commitText(value, 1);
            }else if(s2.equalsIgnoreCase("")){
                inputConnection1.commitText(value, 1);
            }else if(s3.equalsIgnoreCase("")){
                inputConnection2.commitText(value, 1);
            }else if(s4.equalsIgnoreCase("")){
                inputConnection3.commitText(value, 1);
            }

        }
    }
    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }
    public void setInputConnection1(InputConnection ic) {
        this.inputConnection1 = ic;
    }
    public void setInputConnection2(InputConnection ic) {
        this.inputConnection2 = ic;
    }
    public void setInputConnection3(InputConnection ic) {
        this.inputConnection3 = ic;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return false;

    }
}