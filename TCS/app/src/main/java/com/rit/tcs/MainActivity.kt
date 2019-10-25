package com.rit.tcs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.rit.tcs.bussinessobject.Myassets
import com.rit.tcs.bussinessobject.RFIDDataModel
import com.rit.tcs.fragment.LockerBookingFragment
import com.rit.tcs.fragment.LockerConfirmFragment
import com.rit.tcs.fragment.LockerManagementFragment
import com.rit.tcs.fragment.LockerTimeFragment
import com.rit.tcs.interfaces.confirmClick
import com.rit.tcs.retrofit.NetworkUtility
import com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_1
import com.rit.tcs.retrofit.RetrofitClient
import com.rit.tcs.util.TCSPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var myassetList: List<Myassets>
    lateinit var preference: TCSPreference
    var backfinishtrue_for_lockermanagement:Int=0
    var blank:Int=0

    override fun onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction()
        stopHandler()//stop first and then start
        startHandler()
    }

    fun stopHandler() {
        handler.removeCallbacks(r)
    }

    fun startHandler() {
        handler.postDelayed(r, (30000 ).toLong()) //for 5 minutes
    }

    lateinit var handler: Handler
    lateinit var r: Runnable

    override fun onStop() {
        super.onStop()
        stopHandler()
    }

    var isFromRFID:Boolean =false
    var RFID:String =""
    var is_firsttime_open_show_single_item:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        TCSJobService.getTcsJobService().setToSubscribe(true)
        preference = TCSPreference(this)

        isFromRFID= intent.extras.getBoolean("IsFromRFID")
        RFID= intent.extras.getString("RFID")

        if(isFromRFID){
            if(!RFID.equals("") && RFID.length>4){
                getProfileLocker(RFID)
            }else{

            }

        }else{
            is_firsttime_open_show_single_item="1"
            backfinishtrue_for_lockermanagement=1
            blank=0
            var fragment: Fragment? = LockerManagementFragment()
            if (fragment != null) {
                val fragmentManager = supportFragmentManager
                val ft = fragmentManager.beginTransaction()
                ft.addToBackStack("1")
                ft.add(R.id.content_main, fragment)
                ft.commit()
            }
        }
        val intent:Intent= Intent(NetworkUtility.BROADCAST_ACTION_2)
        intent.putExtra("RFID", RFID)
        sendBroadcast(intent)



        handler = Handler()
        r = Runnable {
            val intent = Intent(this@MainActivity, TutorialActivity::class.java)
            stopHandler()
            startActivity(intent)
            finishAffinity()
        }
        startHandler()



    }

    fun addFragment(index: Int, bundle: Bundle) {

        when (index) {
            1 -> {
                backfinishtrue_for_lockermanagement=0
                blank=0
                var fragment: Fragment? = LockerTimeFragment()
                if (fragment != null) {
                    val fragmentManager = supportFragmentManager
                    val ft = fragmentManager.beginTransaction()
                    fragment.arguments = bundle
//                    ft.addToBackStack("3")
                                        ft.addToBackStack(null)

                    ft.replace(R.id.content_main, fragment)
                    ft.commit()
                }
            }

            2 -> {
                backfinishtrue_for_lockermanagement=0
                blank=0
                var fragment: Fragment? = LockerConfirmFragment()
                if (fragment != null) {
                    val fragmentManager = supportFragmentManager
                    val ft = fragmentManager.beginTransaction()
                    fragment.arguments = bundle

//                    ft.addToBackStack("4")
//                    ft.replace(R.id.content_main, fragment)

                    ft.addToBackStack(null)
                    ft.replace(R.id.content_main, fragment)
                    ft.commit()
                }
            }
            3 -> {
                 backfinishtrue_for_lockermanagement=1
                blank=0
                var fragment: Fragment? = LockerManagementFragment()
                if (fragment != null) {
                    val fragmentManager = supportFragmentManager
                    val ft = fragmentManager.beginTransaction()
                    fragment.arguments = bundle
//                    ft.addToBackStack("5")
                    ft.addToBackStack(null)
                    ft.replace(R.id.content_main, fragment)
                    ft.commit()
                }
            }
            4 -> {
                backfinishtrue_for_lockermanagement=3
                blank=0
                var fragment: Fragment? = LockerBookingFragment()
                if (fragment != null) {
                    val fragmentManager = supportFragmentManager
                    val ft = fragmentManager.beginTransaction()
//                    ft.addToBackStack("1")
                    ft.addToBackStack(null)
                   // var fragment1: Fragment? = LockerManagementFragment()
                    ft.remove(fragment!!)
                    ft.replace(R.id.content_main, fragment)
                    ft.commit()
                }
            } 5 -> {
            backfinishtrue_for_lockermanagement=3
            blank=0
            var fragment: Fragment? = LockerBookingFragment()
            if (fragment != null) {
                val fragmentManager = supportFragmentManager
                val ft = fragmentManager.beginTransaction()
//                    ft.addToBackStack("1")
                ft.addToBackStack(null)
                // var fragment1: Fragment? = LockerManagementFragment()
                ft.remove(fragment!!)
                ft.add(R.id.content_main, fragment)
                ft.commit()
            }
        }6 -> {
            backfinishtrue_for_lockermanagement=0
            blank=1
            var fragment: Fragment? = LockerBookingFragment()
            if (fragment != null) {
                val fragmentManager = supportFragmentManager
                val ft = fragmentManager.beginTransaction()
//                    ft.addToBackStack("1")
                ft.addToBackStack(null)
                // var fragment1: Fragment? = LockerManagementFragment()
                ft.remove(fragment!!)
                ft.replace(R.id.content_main, fragment)
                ft.commit()
            }
        }

        }
    }

    private fun getProfileLocker(passcode: String) {
        val callLogin = RetrofitClient.getInstance()!!.getApi().fetchDetailsByRFId(passcode)
       // hoverLyts?.setVisibility(View.VISIBLE)
        callLogin.enqueue(object : Callback<RFIDDataModel> {
            override fun onResponse(call: Call<RFIDDataModel>, response: Response<RFIDDataModel>) {
                try {
                    val s = response.body()
                    var success = false
                    success = s!!.getStatus().equals("success", ignoreCase = true)
                    if (success) {
                        preference.startToSavePreference()
                        preference.savePreference("String", "UserId", s.profile[0].id)
                        preference.savePreference("String", "token", s.secretkey)
                        preference.stopToSavePreference()
//                        locker = s?.getMyassets()!![0]
//                        profile = s?.getProfile()!![0]
                        myassetList = s?.getMyassets()!!.asList()
                        if((myassetList==null || myassetList.size<=0)){

                            backfinishtrue_for_lockermanagement=0
                            blank=1
                            if(isFromRFID){
                                var fragment: Fragment? = LockerBookingFragment()
                                if (fragment != null) {
                                    val fragmentManager = supportFragmentManager
                                    val ft = fragmentManager.beginTransaction()
                                    // ft.addToBackStack("1")
                                    ft.replace(R.id.content_main, fragment)
                                    ft.commit()
                                }
                            }else{

                            }

                        }else {
                            is_firsttime_open_show_single_item="1"
                            backfinishtrue_for_lockermanagement=1

                            var fragment: Fragment? = LockerManagementFragment()
                            if (fragment != null) {
                                val fragmentManager = supportFragmentManager
                                val ft = fragmentManager.beginTransaction()
                                ft.replace(R.id.content_main, fragment)
                                ft.commit()
                            }

                        }
                    } else {
                        is_firsttime_open_show_single_item="1"
                        CustomPopUp.getInstance(this@MainActivity).popup_locker_management1(this@MainActivity,s!!.getMessage().toString(), true, object :confirmClick{
                            override fun onClick() {

                                val intent = Intent(this@MainActivity, TutorialActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                            }
                        })
                     /*   CustomPopUp.getInstance(activity).popup_confirm(s!!.getMessage().toString(), object: confirmClick {
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

                        })*/
                    }
                    //hoverLyts?.setVisibility(View.GONE)
                } catch (e: Exception) {
                   // hoverLyts?.setVisibility(View.GONE)
                }
            }

            override fun onFailure(call: Call<RFIDDataModel>, t: Throwable) {
//                hoverLyts?.setVisibility(View.GONE)
                Toast.makeText(this@MainActivity, "Network error. Please try again", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onBackPressed() {


        if(backfinishtrue_for_lockermanagement==1){
//            preference.clearPreference()
//            startActivity(Intent(this@MainActivity, TabMainActivity::class.java))
//            finish()
        }else if(backfinishtrue_for_lockermanagement==3){
            backfinishtrue_for_lockermanagement=1
            var fragment: Fragment? = LockerManagementFragment()
            if (fragment != null) {
                val fragmentManager = supportFragmentManager
                val ft = fragmentManager.beginTransaction()
                ft.addToBackStack(null)
                ft.replace(R.id.content_main, fragment)
                ft.commit()
            }
        }else{
            super.onBackPressed()
        }
    }



}
