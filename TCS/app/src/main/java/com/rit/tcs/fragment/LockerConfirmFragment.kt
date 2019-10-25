package com.rit.tcs.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rit.tcs.*
import com.rit.tcs.bussinessobject.Allassets
import com.rit.tcs.bussinessobject.Assets
import com.rit.tcs.bussinessobject.ConfirmModel.ConfirmModel
import com.rit.tcs.bussinessobject.GenericModel
import com.rit.tcs.interfaces.confirmClick
import com.rit.tcs.interfaces.confirmOtpClick
import com.rit.tcs.retrofit.NetworkUtility
import com.rit.tcs.retrofit.RetrofitClient
import com.rit.tcs.util.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_booking_items.view.*
import kotlinx.android.synthetic.main.layout_confirm_booking.view.*
import kotlinx.android.synthetic.main.layout_confirm_booking.view.clear_data_btn
import kotlinx.android.synthetic.main.layout_confirm_booking.view.home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class LockerConfirmFragment : Fragment() {




    lateinit var asset: Assets
    lateinit var allAsset: Allassets
    lateinit var preference: TCSPreference
    private var hoverLyts: RelativeLayout? = null
    private var confirm: TextView? = null
    private var bookingDuration: TextView? = null
    private var startTimes: TextView? = null
    private var endTimes: TextView? = null
    private var assetPathId: TextView? = null
    var home: ImageView? = null
    private var mLastClickTime: Long = 0
    var final_locker: String? = null
    private var clear_data_btn: LinearLayout? = null
    var i = 0
    lateinit var config_preference: ConfigPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.layout_confirm_booking, container, false);
        preference = TCSPreference(context)
        config_preference = ConfigPreference(context)
        hoverLyts = view.hoverLogin81
        confirm = view.confirm
        clear_data_btn = view.clear_data_btn
        bookingDuration = view.bookingDuration
        startTimes = view.startTimes
        endTimes = view.endTimes
        assetPathId = view.assetPathId
        home = view.home

      /*  clear_data_btn!!.setOnClickListener {

            i++
            if (i === 5) {
                i = 0
                CustomPopUp2().popup_admin(activity, "Provide admin otp and verify..", object : confirmOtpClick {
                    override fun onClick(otp: String?) {
                        if (otp.equals("1234")) {
                            preference.clearPreference()
                            config_preference.clearPreference()
                            val intent = Intent(activity, ConfigActivity::class.java)
                            startActivity(intent)
                            activity!!.finish()
                        }

                    }

                })

            }

        }*/


        view.home.setSafeOnClickListener{
            CustomPopUp.getInstance(activity).popup_logout("Do you want to loggout?",object : confirmClick {
                override fun onClick() {

                    preference.clearPreference()
//            val intent = Intent(activity, TabMainActivity::class.java)
                    val intent = Intent(activity, TutorialActivity::class.java)

                    startActivity(intent)
                    activity!!.finishAffinity()

                }
            })
        }

        allAsset=getArguments()?.getSerializable("Allassets") as Allassets
        asset=getArguments()?.getSerializable("Assets") as Assets
        assetPathId?.text=allAsset.cat_name+" >> "+asset.name
        startTimes?.text=getArguments()?.getString("StartTime")
        endTimes?.text=getArguments()?.getString("EndTime")
       // bookingDuration?.text = getDateDiffString_forconformation(getArguments()?.getString("StartTime"),getArguments()?.getString("EndTime"))
        val time = getDateDiffString1_confirm(getArguments()?.getString("StartTime"), getArguments()?.getString("EndTime"));

        val str1 = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );

        bookingDuration?.text = str1

        Picasso.with(context).load(NetworkUtility.BASE +"/"+allAsset.cat_image).fit()
            .placeholder(R.drawable.tcs_logo)
            .error(R.drawable.tcs_logo)
            .into(view.assetImg);
        confirm?.setSafeOnClickListener {

          /*  if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
            {
                return@OnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()*/
            createBooking(preference.getPreference("String", "UserId"),allAsset?.catid,asset?.id,changeDateFormat(startTimes?.text.toString()),changeDateFormat(endTimes?.text.toString()))
        }
        return view
    }
    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }
    private fun createBooking(userid: String,catId: String,assetid: String,startDate: String,endDate: String) {
//        val callLogin = RetrofitClient.getInstance().api.createBooking(preference.getPreference("String", "UserId"),catId,assetid,startDate,endDate)

        val callLogin = RetrofitClient.getInstance(preference.getPreference("String","token"))!!.getApi().createBooking(preference.getPreference("String", "UserId"),catId,assetid,startDate,endDate)
        hoverLyts?.setVisibility(View.VISIBLE)
        callLogin.enqueue(object : Callback<ConfirmModel> {
            override fun onResponse(call: Call<ConfirmModel>, response: Response<ConfirmModel>) {
                try {
                    val s = response.body()
                    var success = false
                    success = s!!.getStatus().equals("success", ignoreCase = true)


//                    val s = response.body()
//                    var success = false
//                    success = s!!.getStatus().equals("success", ignoreCase = true)

                    if (success) {
                        val  locker = s?.asset!![0].lockerNumber
                        final_locker = (Integer.parseInt(locker.substring(locker.lastIndexOf("_") + 1)) - 1).toString()
                      //  CustomPopUp().popup(activity,"No assets avaiable for this category",false);
                        CustomPopUp.getInstance(activity).popup_confirm(s!!.getMessage().toString(), object: confirmClick {
                            override fun onClick() {
                                openlocker(final_locker)
                                val bundle = Bundle()
                                (activity as MainActivity).supportFragmentManager.popBackStack()
                                (activity as MainActivity).supportFragmentManager.popBackStack()
                                (activity as MainActivity).supportFragmentManager.popBackStack()
                                (activity as MainActivity).supportFragmentManager.popBackStack()
                                (activity as MainActivity).supportFragmentManager.popBackStack()


                                (activity as MainActivity).addFragment(3, bundle)
                            }

                        })

                    } else {
                        CustomPopUp.getInstance(activity).popup(s!!.getMessage().toString(),false)
                      //  Toast.makeText(context, s!!.getMessage().toString(), Toast.LENGTH_LONG).show()
                    }
                    hoverLyts?.setVisibility(View.GONE)
                } catch (e: Exception) {
                    hoverLyts?.setVisibility(View.GONE)
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ConfirmModel>, t: Throwable) {
                hoverLyts?.setVisibility(View.GONE)
                Toast.makeText(context, "Network error. Please try again", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun doNothing(view:View){
        // do nothing
    }


    fun openlocker(final_locker: String?) {

        val text =
            "set," + final_locker
        val intent1: Intent = Intent(NetworkUtility.BROADCAST_CHECK_INITIAL_STATUS)
//        intent1.putExtra("RFID", RFID)
        intent1.putExtra("Id", text)
        intent1.putExtra("IsToSubscribe", true)
        activity!!.sendBroadcast(intent1)
    }


}