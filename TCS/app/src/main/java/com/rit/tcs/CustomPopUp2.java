package com.rit.tcs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.rit.tcs.interfaces.confirmClick;
import com.rit.tcs.interfaces.confirmOtpClick;


public class CustomPopUp2 {
    String value;


    public void popup(final Activity activity, String msg, final boolean isFinish) {


        final Dialog dialog = new Dialog(activity);
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_yes_no);
        dialog.setCanceledOnTouchOutside(false);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(msg);
//        typeFace.setTypeFace(text,"r");
        LinearLayout lower = (LinearLayout) dialog.findViewById(R.id.lower);
        TextView ok = (TextView) dialog.findViewById(R.id.report);
        ok.setText("OK");
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setText("CANCEL");
        cancel.setVisibility(View.GONE);

        TextView tvEventDetails = (TextView) dialog.findViewById(R.id.tvEventDetails);
        tvEventDetails.setVisibility(View.GONE);
        // final EditText edit_txt=(EditText)dialog.findViewById(R.id.edit_txt);

        // typeFace.setTypeFace(ok,"b");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               /* if (isFinish)
                    activity.finish();*/
            }
        });


        /*lower.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (isFinish)
                    activity.finish();
                //activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });*/
        // Set dialog title
        dialog.setTitle("Touch ID For TBotWallet");

        dialog.show();
    }
    public void popup_service(final Context activity, String msg, final boolean isFinish) {


        final Dialog dialog = new Dialog(activity);
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_yes_no);
        dialog.setCanceledOnTouchOutside(false);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(msg);
//        typeFace.setTypeFace(text,"r");
        LinearLayout lower = (LinearLayout) dialog.findViewById(R.id.lower);
        TextView ok = (TextView) dialog.findViewById(R.id.report);
        ok.setText("OK");
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setText("CANCEL");
        cancel.setVisibility(View.GONE);

        TextView tvEventDetails = (TextView) dialog.findViewById(R.id.tvEventDetails);
        tvEventDetails.setVisibility(View.GONE);
        // final EditText edit_txt=(EditText)dialog.findViewById(R.id.edit_txt);

        // typeFace.setTypeFace(ok,"b");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               /* if (isFinish)
                    activity.finish();*/
            }
        });


        /*lower.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (isFinish)
                    activity.finish();
                //activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });*/
        // Set dialog title
        dialog.setTitle("Touch ID For TBotWallet");

        dialog.show();
    }
    public void popup_confirm(final Activity activity, String msg,final confirmClick confirmClick) {


        final Dialog dialog = new Dialog(activity);
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_yes_no);
        dialog.setCanceledOnTouchOutside(false);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(msg);
//        typeFace.setTypeFace(text,"r");
        LinearLayout lower = (LinearLayout) dialog.findViewById(R.id.lower);
        TextView ok = (TextView) dialog.findViewById(R.id.report);
        ok.setText("OK");
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setText("CANCEL");
        cancel.setVisibility(View.GONE);

        TextView tvEventDetails = (TextView) dialog.findViewById(R.id.tvEventDetails);
        tvEventDetails.setVisibility(View.GONE);
        // final EditText edit_txt=(EditText)dialog.findViewById(R.id.edit_txt);

        // typeFace.setTypeFace(ok,"b");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClick.onClick();
                dialog.dismiss();
               /* if (isFinish)
                    activity.finish();*/
            }
        });


        /*lower.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (isFinish)
                    activity.finish();
                //activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });*/
        // Set dialog title
        dialog.setTitle("Touch ID For TBotWallet");

        dialog.show();
    }



    public void popup_logout(final Activity activity, String msg,final confirmClick confirmClick) {


        final Dialog dialog = new Dialog(activity);
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_yes_no_logout);
        dialog.setCanceledOnTouchOutside(false);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(msg);
//        typeFace.setTypeFace(text,"r");
        LinearLayout lower = (LinearLayout) dialog.findViewById(R.id.lower);
        TextView ok = (TextView) dialog.findViewById(R.id.report);
        TextView no = (TextView) dialog.findViewById(R.id.report1);

        ok.setText("YES");
        no.setText("NO");
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setText("CANCEL");
        cancel.setVisibility(View.GONE);

        TextView tvEventDetails = (TextView) dialog.findViewById(R.id.tvEventDetails);
        tvEventDetails.setVisibility(View.GONE);
        // final EditText edit_txt=(EditText)dialog.findViewById(R.id.edit_txt);

        // typeFace.setTypeFace(ok,"b");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClick.onClick();
                dialog.dismiss();
               /* if (isFinish)
                    activity.finish();*/
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               /* if (isFinish)
                    activity.finish();*/
            }
        });

        /*lower.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (isFinish)
                    activity.finish();
                //activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });*/
        // Set dialog title
        dialog.setTitle("Touch ID For TBotWallet");

        dialog.show();
    }

    public void popup_admin(final Activity activity, String msg,final confirmOtpClick confirmOtpClick) {


        final Dialog dialog = new Dialog(activity);
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_otp_popup);
        dialog.setCanceledOnTouchOutside(false);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        final EditText edit_txt=dialog.findViewById(R.id.edit_txt);
        text.setText(msg);
//        typeFace.setTypeFace(text,"r");
        LinearLayout lower = (LinearLayout) dialog.findViewById(R.id.lower);
        TextView ok = (TextView) dialog.findViewById(R.id.report);
        TextView no = (TextView) dialog.findViewById(R.id.report1);

        ok.setText("YES");
        no.setText("NO");
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setText("CANCEL");
        cancel.setVisibility(View.GONE);

        TextView tvEventDetails = (TextView) dialog.findViewById(R.id.tvEventDetails);
        tvEventDetails.setVisibility(View.GONE);
        // final EditText edit_txt=(EditText)dialog.findViewById(R.id.edit_txt);

        // typeFace.setTypeFace(ok,"b");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_txt.getText().toString().trim().equalsIgnoreCase("Fado1505")){
                    edit_txt.setError("Please provide valid admin otp ");
                }else{
                    confirmOtpClick.onClick(edit_txt.getText().toString().trim());
                    dialog.dismiss();
                }

               /* if (isFinish)
                    activity.finish();*/
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               /* if (isFinish)
                    activity.finish();*/
            }
        });

        /*lower.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (isFinish)
                    activity.finish();
                //activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });*/
        // Set dialog title
        dialog.setTitle("Touch ID For TBotWallet");

        dialog.show();
    }


}
