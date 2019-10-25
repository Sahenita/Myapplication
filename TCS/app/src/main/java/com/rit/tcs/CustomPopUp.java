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


public class CustomPopUp {
    String value;
    private static Dialog dialog;
    private static CustomPopUp customPopUp;



    public static CustomPopUp getInstance(Context context){
        if(customPopUp==null)
            customPopUp=new CustomPopUp();
        if (dialog == null || dialog.getContext() != context) {
            dialog = new Dialog(context);
            dialog.dismiss();
        }
        return customPopUp;
    }


    public void popup(String msg, final boolean isFinish) {
        dialog.dismiss();
        if (dialog != null) {
            dialog.dismiss();
        }
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
       // dialog.setCancelable(false);
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
                if (dialog != null) {
                    dialog.dismiss();
                }
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
    public void popup_locker_management1(final Activity activity, String msg, final boolean isFinish,final confirmClick confirmClick) {
        dialog.dismiss();
        if (dialog != null) {
            dialog.dismiss();
        }
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_yes_no);
//        dialog.setCanceledOnTouchOutside(false);

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
               // dialog.dismiss();
                if (dialog != null) {
                    confirmClick.onClick();
                    dialog.dismiss();

                }
               /* if (isFinish){
                    activity. finishAffinity();
                }*/

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
    public void popup_locker_management(String msg, final boolean isFinish) {
        dialog.dismiss();
        if (dialog != null) {
            dialog.dismiss();
        }
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_yes_no);
//        dialog.setCanceledOnTouchOutside(false);

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
                if (dialog != null) {
                    dialog.dismiss();
                }
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
    public void popup_confirm(String msg, final confirmClick confirmClick) {


        if (dialog != null) {
            dialog.dismiss();
        }
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


    public void popup_logout(String msg, final confirmClick confirmClick) {


        if (dialog != null) {
            dialog.dismiss();
        }
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCanceledOnTouchOutside(true);
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

    public void popup_locker_close(String msg, final confirmClick confirmClick) {


        if (dialog != null) {
            dialog.dismiss();
        }
//        final LoadingPopup loadingPopup = new LoadingPopup(activity);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_yes_no_logout);
//        dialog.setCanceledOnTouchOutside(false);

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

    public void popup1(Context context,String msg, final boolean isFinish) {
        if (dialog != null) {
            dialog.dismiss();
        }
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
    public void popup_confirm1(Context context,String msg, final confirmClick confirmClick) {


        if (dialog != null) {
            dialog.dismiss();
        }
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
}
