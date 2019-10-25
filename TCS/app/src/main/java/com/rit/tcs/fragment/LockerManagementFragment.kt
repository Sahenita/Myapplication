package com.rit.tcs.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rit.tcs.*
import com.rit.tcs.adapter.MyAssetAdapter
import com.rit.tcs.bussinessobject.GenericModel
import com.rit.tcs.bussinessobject.Myassets
import com.rit.tcs.bussinessobject.Profile
import com.rit.tcs.bussinessobject.RFIDDataModel
import com.rit.tcs.interfaces.MyAssetSelectedListener
import com.rit.tcs.interfaces.confirmClick
import com.rit.tcs.interfaces.confirmOtpClick
import com.rit.tcs.retrofit.NetworkUtility
import com.rit.tcs.retrofit.RetrofitClient
import com.rit.tcs.util.*
import kotlinx.android.synthetic.main.layout_locker.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class LockerManagementFragment : Fragment(), MyAssetSelectedListener {
    override fun setOnClicked(
        allassets: Myassets?,
        locker: String?,
        occupancyy: Boolean?,
        booking_id: Int?,
        serial_no: String?,
        position: Int?
    ) {
        lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_grey);
        pos = position!!
        clicked_releaselicker = 0
        locker_of_on_click = 0
        final_locker_forapi = locker
        final_locker = (Integer.parseInt(locker!!.substring(locker!!.lastIndexOf("_") + 1)) - 1).toString()
        // checkInitailLockerMqtt_item(final_locker)
        occupency = occupancyy!!
        bookingid = booking_id.toString()
        serialnumber = serial_no.toString()
        setDisplayForSpacefor_item(allassets)
        AppController.broadcast=true
       /* releaseBtn?.isEnabled = true
        releaseBtn?.isClickable = true
        lockerOnOffBtn?.isEnabled = true
        lockerOnOffBtn?.isClickable = true*/
        checkInitailLockerMqtt(final_locker)
    }


    var isclocelocker: String = ""
    var firstentry: String = "yes"

    var pos: Int = 0
    var isclocelocker_release: String = ""

    var clicked_releaselicker: Int = 0
    var locker_of_on_click: Int = 0

    var ocupency_true_0: Int = 0
    var occupency: Boolean = false;
    var bookingid: String = "";
    var serialnumber: String = ""
    var myadapter: MyAssetAdapter? = null

    lateinit var profile: Profile
    var locker: Myassets? = null
    lateinit var preference: TCSPreference
    lateinit var config_preference:ConfigPreference

    private var hoverLyts: RelativeLayout? = null
    private var lockerOnOffBtn: Button? = null
    private var userName: TextView? = null
    private var open: TextView? = null
    private var close: TextView? = null
    private var phoneE: TextView? = null
    private var emailL: TextView? = null
    private var rl_no_data: RelativeLayout? = null
    private var rl_data: RelativeLayout? = null
    private var lockerId: TextView? = null
    private var signOut: TextView? = null
    private var Bookmore: TextView? = null
    private var mLastClickTime: Long = 0
    private var clear_data_btn: LinearLayout? = null
    var str:String=""
    var num:Int=0
    var substr:String=""
    //    private var Bookmore: TextView?=null

    //var Bookmore: TextView? = null

    private var lockerType: TextView? = null
    private var refresh: TextView? = null
    private var img_internet: TextView? = null
    private var lockerBookedLayout: LinearLayout? = null
    private var lockerUsedTime: TextView? = null
    private var lockerEndTm: TextView? = null
    private var recylerVw: RecyclerView? = null
    val REQUEST_INITIAL_LOCKER_STATUS = 305
    val REQUEST_INITIAL_LOCKER_STATUS_FOR_OPEN = 405
    val REQUEST_PAYMENT = 55
    val REQUEST_OPEN_LOCKER = 205
    val REQUEST_RELEASE_LOCKER = 105
    private var spaceFirst: Myassets? = null
    private var lockerBookLayout: LinearLayout? = null
    var lockerPasscode: TextView? = null
    lateinit var countDownTimer: CountDownTimer
    var releaseBtn: Button? = null
    lateinit var myassetList: List<Myassets>
    var home: ImageView? = null
    var int1: Int = 0
    var id1: Int? = 0
    var id1_16: Int? = 0

    var final_locker: String? = null
    var final_locker_forapi: String? = null
    var time: Long? = 0
    var handler1 = Handler()
    var runnable: Runnable? = null
    var delay = 1000
    var i = 0
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity!!.registerReceiver(
            broadcastinitialstatusreceived, IntentFilter(
                NetworkUtility.BROADCAST_CHECK_INITIAL_STATUS_RECEIVED_DATA
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.layout_locker, container, false);
        (activity as MainActivity).backfinishtrue_for_lockermanagement=1
        preference = TCSPreference(context)
        config_preference=ConfigPreference(context)
        hoverLyts = view.hoverLogin51
        lockerOnOffBtn = view.lockerOnOffBtn
        lockerId = view.lockerId
        clear_data_btn = view.clear_data_btn
        refresh = view.refresh
        rl_data = view.rl_data
        open = view.open
        img_internet = view.img_internet
        close = view.close
        rl_no_data = view.rl_no_data
        rl_data!!.visibility = View.VISIBLE
        rl_no_data!!.visibility = View.GONE

//        Bookmore=view.Bookmore
//        Bookmore = view.findViewById<View>(R.id.Bookmore)as? TextView

        lockerType = view.lockerType
        lockerUsedTime = view.lockerUsedTime
        userName = view.username
        emailL = view.email
        phoneE = view.phone
        signOut = view.signOut
        Bookmore = view.Bookmore

        Bookmore!!.isClickable = false
        Bookmore!!.isEnabled = false

        lockerEndTm = view.lockerEndTm
        releaseBtn = view.releaseBtn
        home = view.home
        view.home.isEnabled = false
        view.home.isClickable = false
        signOut!!.isEnabled = false
        signOut!!.isClickable = false

        view.refresh.setOnClickListener(View.OnClickListener {
            clicked_releaselicker = 0
            locker_of_on_click = 0
            if ((activity as MainActivity).isFromRFID) {
//            Bookmore.visibility = View.VISIBLE
                Bookmore!!.visibility = View.VISIBLE
                getProfileLocker((activity as MainActivity).RFID)
            } else {
                getProfileLockerbyOTP((activity as MainActivity).RFID)
                Bookmore!!.visibility = View.GONE

            }

        })
        view.home.setOnClickListener(View.OnClickListener {

            CustomPopUp.getInstance(activity).popup_logout("Do you want to loggout?", object : confirmClick {
                override fun onClick() {
                    clicked_releaselicker = 0
                    locker_of_on_click = 0
                    preference.clearPreference()
                    val intent = Intent(activity, TutorialActivity::class.java)
                    startActivity(intent)
                    activity!!.finishAffinity()

                }
            })


        })
        lockerPasscode = view.lockerPasscode
        signOut!!.setSafeOnClickListener {
            clicked_releaselicker = 0
            locker_of_on_click = 0
            preference.clearPreference()
            val intent = Intent(activity, TutorialActivity::class.java)
            startActivity(intent)
            activity!!.finishAffinity()


        }
     /*   clear_data_btn!!.setOnClickListener {
            var token = preference.getPreference("String", "token")
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
        Bookmore!!.setSafeOnClickListener {
            clicked_releaselicker = 0
            locker_of_on_click = 0

            //            val intent = Intent(activity, TabMainActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
            if ((activity as MainActivity).isFromRFID && (activity as MainActivity).RFID.length > 5) {
                val bundle = Bundle()
                (activity as MainActivity).supportFragmentManager.popBackStack();
                (activity as MainActivity).supportFragmentManager.popBackStack()
                (activity as MainActivity).addFragment(4, bundle)

            } else {

            }

        }
        recylerVw = view.recylerVw
        recylerVw?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recylerVw!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override
            fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                } else {
                    // Scrolling down
                }
            }

            override
            fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    myassetList.get(pos).setSelected1("NA")

                    myadapter!!.notifyDataSetChanged()
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    myassetList.get(pos).setSelected1("NA")

                    myadapter!!.notifyDataSetChanged()

                } else {
                    myassetList.get(pos).setSelected1("NA")

                    myadapter!!.notifyDataSetChanged()

                }
            }
        })


        lockerOnOffBtn?.setSafeOnClickListener {
            AppController.broadcast=true
           // hoverLyts?.setVisibility(View.VISIBLE)
           //  Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
            lockerOnOffBtn?.isEnabled = false
            lockerOnOffBtn?.isClickable = false
           /* releaseBtn?.isEnabled = true
            releaseBtn?.isClickable = true*/
            clicked_releaselicker = 0
            locker_of_on_click = 1
            checkInitailLockerMqtt(final_locker)

            /* if (isclocelocker.equals("yes")) {

                 openlocker(final_locker)
                 lockerOnOffBtn?.setImageResource(R.drawable.power_button_green)

                *//* open!!.text = "Open Locker"
                 open!!.setBackgroundResource(R.drawable.open_circle)
                 open!!.visibility = View.GONE
                 close!!.visibility = View.GONE*//*
             } else {
                 CustomPopUp.getInstance(activity).popup("This locker is open..", false)
             }*/


        }
        releaseBtn?.setSafeOnClickListener {
            AppController.broadcast=true

           // hoverLyts?.setVisibility(View.VISIBLE)
           // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()

            firstentry = "no"
            clicked_releaselicker = 1
            clicked_releaselicker = 1
            locker_of_on_click = 0
            releaseBtn?.isEnabled = false
            releaseBtn?.isClickable = false
           /* lockerOnOffBtn?.isEnabled = true
            lockerOnOffBtn?.isClickable = true*/
            checkInitailLockerMqtt(final_locker)
        }


        if ((activity as MainActivity).isFromRFID) {
//            Bookmore.visibility = View.VISIBLE
            Bookmore!!.visibility = View.VISIBLE
            Bookmore!!.isClickable = true
            Bookmore!!.isEnabled = true
            getProfileLocker((activity as MainActivity).RFID)
        } else {
            getProfileLockerbyOTP((activity as MainActivity).RFID)
            Bookmore!!.visibility = View.GONE
            Bookmore!!.isClickable = false
            Bookmore!!.isEnabled = false
        }
        return view
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    private fun getProfileLocker(passcode: String) {
        val callLogin = RetrofitClient.getInstance()!!.getApi().fetchDetailsByRFId(passcode)
        hoverLyts?.setVisibility(View.VISIBLE)
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
                        if (s?.getMyassets().size == 0 || s?.getMyassets() == null) {
                            /* preference.clearPreference()
                             val intent = Intent(activity, TutorialActivity::class.java)
                             startActivity(intent)
                             activity!!.finish()*/
                            rl_data!!.visibility = View.GONE
                            rl_no_data!!.visibility = View.VISIBLE
                            Bookmore!!.visibility = View.VISIBLE
                            profile = s?.getProfile()!![0]
                            userName?.text = profile.name
                            emailL?.text = profile.email
                            phoneE?.text = profile.empCode
                            view!!.home.isEnabled = true
                            view!!.home.isClickable = true
                            signOut!!.isEnabled = true
                            signOut!!.isClickable = true
                        } else {
                            locker = s?.getMyassets()!![0]
                            profile = s?.getProfile()!![0]
                            myassetList = s?.getMyassets()!!.asList()
                            // myassetList[0].value = "NA"
                            myassetList.get(0).setSelected1("NA")

                            occupency = locker!!.occupency
                            bookingid = locker!!.booking_id.toString()
                            serialnumber = locker!!.serialnumber
                            final_locker_forapi = locker!!.locker_number!!

                            final_locker =
                                (Integer.parseInt(
                                    locker!!.locker_number!!.substring(
                                        locker!!.locker_number!!.lastIndexOf(
                                            "_"
                                        ) + 1
                                    )
                                ) - 1).toString()

//                            checkInitailLockerMqtt(final_locker)
//                            setDisplayForSpace(locker, profile)
                            //checkInitailLockerMqtt(locker)
                            if ((myassetList == null || myassetList.size <= 0)) {
                                val bundle = Bundle()
                                (activity as MainActivity).supportFragmentManager.popBackStack();
                                (activity as MainActivity).addFragment(4, bundle)
                                hoverLyts?.setVisibility(View.GONE)
                                Bookmore!!.visibility = View.VISIBLE
                            } else if(myassetList.size == 3){
                                Bookmore!!.visibility = View.GONE
                                myassetList.get(0).setSelected1("NA")
                                myassetList.get(0).setSelected(true)
                                setDisplayForSpace(locker, profile)
                                AppController.broadcast=true
                                checkInitailLockerMqtt(final_locker)
                            }else {
                                Bookmore!!.visibility = View.VISIBLE
                                myassetList.get(0).setSelected1("NA")
                                myassetList.get(0).setSelected(true)
                                setDisplayForSpace(locker, profile)
                                AppController.broadcast=true
                                checkInitailLockerMqtt(final_locker)

                            }
                        }

                    } else {
                        val bundle = Bundle()
                        (activity as MainActivity).supportFragmentManager.popBackStack();
                        (activity as MainActivity).addFragment(4, bundle)
                        hoverLyts?.setVisibility(View.GONE)
                        // Toast.makeText(context, s!!.getMessage().toString(), Toast.LENGTH_LONG).show()
                    }
                    hoverLyts?.setVisibility(View.GONE)
                } catch (e: Exception) {
                    hoverLyts?.setVisibility(View.GONE)
                }
            }

            override fun onFailure(call: Call<RFIDDataModel>, t: Throwable) {
                hoverLyts?.setVisibility(View.GONE)
                Toast.makeText(context, "Network error. Please try again", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getProfileLockerbyOTP(passcode: String) {
        val callLogin = RetrofitClient.getInstance()!!.getApi().checkPasscode(passcode)
        hoverLyts?.setVisibility(View.VISIBLE)
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
                        if (s?.getMyassets().size == 0 || s?.getMyassets() == null) {
                            preference.clearPreference()
                            val intent = Intent(activity, TutorialActivity::class.java)
                            startActivity(intent)
                            activity!!.finish()
                        } else {
                            locker = s?.getMyassets()!![0]
                            profile = s?.getProfile()!![0]
                            myassetList = s?.getMyassets()!!.asList()
                            myassetList[0].value = "NA"
                            myassetList.get(0).setSelected1("NA")
                            occupency = locker!!.occupency
                            bookingid = locker!!.booking_id.toString()
                            serialnumber = locker!!.serialnumber

                            final_locker_forapi = locker!!.locker_number!!

                            final_locker =
                                (Integer.parseInt(
                                    locker!!.locker_number!!.substring(
                                        locker!!.locker_number!!.lastIndexOf(
                                            "_"
                                        ) + 1
                                    )
                                ) - 1).toString()
                            AppController.broadcast=true
                            checkInitailLockerMqtt(final_locker)
                            if ((myassetList == null || myassetList.size <= 0)) {
                                preference.clearPreference()
                                val intent = Intent(activity, TutorialActivity::class.java)
                                startActivity(intent)
                                activity!!.finish()
                            } else {
                                myassetList.get(0).setSelected1("NA")
                                myassetList.get(0).setSelected(true)
                                setDisplayForSpace(locker, profile)

                            }
                        }

                    } else {
                        val bundle = Bundle()
                        (activity as MainActivity).supportFragmentManager.popBackStack();
                        (activity as MainActivity).addFragment(4, bundle)
                        hoverLyts?.setVisibility(View.GONE)
                        // Toast.makeText(context, s!!.getMessage().toString(), Toast.LENGTH_LONG).show()
                    }
                    hoverLyts?.setVisibility(View.GONE)
                } catch (e: Exception) {
                    hoverLyts?.setVisibility(View.GONE)
                }
            }

            override fun onFailure(call: Call<RFIDDataModel>, t: Throwable) {
                hoverLyts?.setVisibility(View.GONE)
                Toast.makeText(context, "Network error. Please try again", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setDisplayForSpacefor_item(myassets: Myassets?) {
        view!!.home.isEnabled = true
        view!!.home.isClickable = true
        signOut!!.isEnabled = true
        signOut!!.isClickable = true
        if (myassets == null) {
            rl_no_data!!.visibility = View.VISIBLE
            Bookmore!!.visibility = View.VISIBLE
            rl_data!!.visibility = View.GONE
        } else {
            rl_no_data!!.visibility = View.GONE
            rl_data!!.visibility = View.VISIBLE



            spaceFirst = myassets
//        userName?.text = profile.name
//        emailL?.text = profile.email
//        phoneE?.text = profile.empCode


            str = myassets!!.locker_number
             num = str.length
            if (num == 14) {
                 substr = str.substring(str.length - 1)
                lockerId?.text = "Locker No : 00" + substr

            } else if (num == 15) {
                 substr = str.substring(str.length - 2)
                lockerId?.text = "Locker No : 0" + substr

            } else if (num == 16) {
                 substr = str.substring(str.length - 3)
                lockerId?.text = "Locker No : " + substr

            }
            lockerType?.text = myassets.category_name
            lockerEndTm?.text = changeDateFormatEO(myassets.end_time)
            //  lockerUsedTime?.text = getDateDiffString(myassets.start_time, myassets.end_time)
            lockerPasscode?.text = myassets.id


            // val time = getDateDiffString1(myassets.start_time, myassets.end_time);


/*new addad for time*/

            val Str1 = getDateDiffString2(myassets.start_time, myassets.end_time);
            lockerUsedTime?.text = Str1


            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var d1: Date? = null
            var d2: Date? = null

            try {
                d1 = format.parse(myassets.start_time)
                d2 = format.parse(myassets.end_time)

                if (System.currentTimeMillis() > d1.time && System.currentTimeMillis() < d2.time) {

                    time = getDateDiffString1(System.currentTimeMillis(), myassets.end_time);

                    if (::countDownTimer.isInitialized) {
                        countDownTimer.cancel()
                    }
                    countDownTimer = object : CountDownTimer(time!!, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                            val str = String.format(
                                "%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                            );

                            lockerUsedTime?.text = str

                        }

                        override fun onFinish() {
                            // DO something when 1 minute is up
                        }
                    }.start()

                } else {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        val Str1 = getDateDiffString2(myassets.start_time, myassets.end_time);
                        lockerUsedTime?.text = Str1
                    } else {
                        val Str1 = getDateDiffString2(myassets.start_time, myassets.end_time);
                        lockerUsedTime?.text = Str1
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()

            }
            /*new addad for time*/

        }

    }

    private fun setDisplayForSpace(myassets: Myassets?, profile: Profile) {
        view!!.home.isEnabled = true
        view!!.home.isClickable = true
        signOut!!.isEnabled = true
        signOut!!.isClickable = true
        if ((activity as MainActivity).is_firsttime_open_show_single_item.equals("1")) {
            (activity as MainActivity).is_firsttime_open_show_single_item = ""
            if (myassetList.size == 1) {
                openlocker(final_locker);
            } else {

            }
        } else {
            (activity as MainActivity).is_firsttime_open_show_single_item = ""

        }

        if (myassets == null) {
            rl_no_data!!.visibility = View.VISIBLE
            Bookmore!!.visibility = View.VISIBLE
            rl_data!!.visibility = View.GONE
            userName?.text = profile.name
            emailL?.text = profile.email
            phoneE?.text = profile.empCode
        } else {
            rl_no_data!!.visibility = View.GONE
            rl_data!!.visibility = View.VISIBLE
            spaceFirst = myassets
            userName?.text = profile.name
            emailL?.text = profile.email
            phoneE?.text = profile.empCode


             str = myassets!!.locker_number
             num = str.length

            if (num == 14) {
                 substr = str.substring(str.length - 1)
                lockerId?.text = "Locker No : 00" + substr

            } else if (num == 15) {
                 substr = str.substring(str.length - 2)
                lockerId?.text = "Locker No : 0" + substr

            } else if (num == 16) {
                 substr = str.substring(str.length - 3)
                lockerId?.text = "Locker No : " + substr

            }
            lockerType?.text = myassets.category_name
            lockerEndTm?.text = changeDateFormatEO(myassets.end_time)
            //  lockerUsedTime?.text = getDateDiffString(myassets.start_time, myassets.end_time)
            lockerPasscode?.text = myassets.id
            myadapter = MyAssetAdapter(myassetList, context!!, this@LockerManagementFragment)

            recylerVw?.adapter = myadapter
            myadapter!!.notifyDataSetChanged()

            val Str1 = getDateDiffString2(myassets.start_time, myassets.end_time);
            lockerUsedTime?.text = Str1

            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var d1: Date? = null
            var d2: Date? = null

            try {
                d1 = format.parse(myassets.start_time)
                d2 = format.parse(myassets.end_time)

                if (System.currentTimeMillis() > d1.time && System.currentTimeMillis() < d2.time) {

                    time = getDateDiffString1(System.currentTimeMillis(), myassets.end_time);

                    if (::countDownTimer.isInitialized) {
                        countDownTimer.cancel()
                    }
                    countDownTimer = object : CountDownTimer(time!!, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                            val str = String.format(
                                "%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                            );

                            lockerUsedTime?.text = str

                        }

                        override fun onFinish() {
                            // DO something when 1 minute is up
                        }
                    }.start()

                } else {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        val Str1 = getDateDiffString2(myassets.start_time, myassets.end_time);
                        lockerUsedTime?.text = Str1

                    } else {
                        val Str1 = getDateDiffString2(myassets.start_time, myassets.end_time);
                        lockerUsedTime?.text = Str1
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()

            }


        }

    }

    fun checkInitailLockerMqtt(final_locker: String?) {
        try {
            scheduleJob(mContext!!)
            /* releaseBtn?.isEnabled = true
             releaseBtn?.isClickable = true*/
            val text =
                "get," + final_locker
            val intent1: Intent = Intent(NetworkUtility.BROADCAST_CHECK_INITIAL_STATUS)
//        intent1.putExtra("RFID", RFID)
            intent1.putExtra("Id", text)
            intent1.putExtra("IsToSubscribe", false)
            activity!!.sendBroadcast(intent1)
        }catch (e:java.lang.Exception){

        }


    }

    fun openlocker(final_locker: String?) {
        try {
        scheduleJob(mContext!!)
        val text =
            "set," + final_locker
        val intent1: Intent = Intent(NetworkUtility.BROADCAST_CHECK_INITIAL_STATUS)
//        intent1.putExtra("RFID", RFID)
        intent1.putExtra("Id", text)
        intent1.putExtra("IsToSubscribe", false)
        activity!!.sendBroadcast(intent1)
        }catch (e:java.lang.Exception){

        }
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }
    fun statuschange(isclose: Int) {
/*no '0' means close 'red'*/
        if (clicked_releaselicker == 0) {
            if (isclose != 0) {
//                lockerOnOffBtn?.setImageResource(R.drawable.power_button_pink)
                //lockerOnOffBtn?.setImageResource(R.drawable.power_button_green1)

                lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_green1);

                open!!.text = "Close Locker"
                open!!.setBackgroundResource(R.drawable.close_circle)
                close!!.visibility = View.GONE
                open!!.visibility = View.GONE
//                open!!.text = "Tap on this button to open this locker.."
//                open!!.setTextColor(activity!!.resources.getColor(R.color.pink))
                isclocelocker = "yes"


            } else {
/*'0' means open 'green'*/

                // lockerOnOffBtn?.setImageResource(R.drawable.power_button_green)
                lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_open1)

                open!!.text = "Open Locker"
                open!!.setBackgroundResource(R.drawable.open_circle)
                open!!.visibility = View.GONE
                close!!.visibility = View.GONE
//                open!!.text = "Locker is open.."
//                open!!.setTextColor(activity!!.resources.getColor(R.color.green))
                isclocelocker = "no"

            }
        } else {
            /*no '0' means close 'red'*/
            if (isclose != 0) {
                //  lockerOnOffBtn?.setImageResource(R.drawable.power_button_pink)
                lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_green1)

                open!!.text = "Close Locker"
                open!!.setBackgroundResource(R.drawable.close_circle)
                close!!.visibility = View.GONE
                open!!.visibility = View.GONE
//                open!!.text = "Tap on this button to open this locker.."
//                open!!.setTextColor(activity!!.resources.getColor(R.color.pink))
                isclocelocker_release = "yes"
                if (occupency) {
                    if (ocupency_true_0 == 0) {
                       // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
                       // hoverLyts?.setVisibility(View.GONE)
//                        CustomPopUp().popup(activity, "Please keep the asset in the locker and try again!", false)
                        CustomPopUp.getInstance(activity)
                            .popup_locker_management("Please keep the asset in the locker and try again!", false)

                       /* releaseBtn?.isEnabled = true
                        releaseBtn?.isClickable = true*/
                    } else {
                        if (isclose != 0) {
                            try {
                                releaseLocker()

                            } catch (e: java.lang.Exception) {
                               /* releaseBtn?.isEnabled = true
                                releaseBtn?.isClickable = true*/
                            }
                        } else {
                          //  Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
                          //  hoverLyts?.setVisibility(View.GONE)
                            CustomPopUp.getInstance(activity).popup_locker_management("First close your locker.", false)
                          /*  releaseBtn?.isEnabled = true
                            releaseBtn?.isClickable = true*/
                        }

//                    CustomPopUp().popup(activity, "This locker is occupied", false);

                    }
                } else {
                    try {
                        releaseLocker()

                    } catch (e: java.lang.Exception) {
                       /* releaseBtn?.isEnabled = true
                        releaseBtn?.isClickable = true*/
                    }
//                CustomPopUp().popup(activity, "occupency false", false);

                }

            } else {
/*'0' means open 'green'*/

                //  lockerOnOffBtn?.setImageResource(R.drawable.power_button_green)
                lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_open1)

                open!!.text = "Open Locker"
                open!!.setBackgroundResource(R.drawable.open_circle)
                open!!.visibility = View.GONE
                close!!.visibility = View.GONE
//                open!!.text = "Locker is open.."
//                open!!.setTextColor(activity!!.resources.getColor(R.color.green))
                isclocelocker_release = "no"
                CustomPopUp.getInstance(activity).popup_locker_management("This locker is open.", false)
              //  Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
              //  hoverLyts?.setVisibility(View.GONE)

               /* releaseBtn?.isEnabled = true
                releaseBtn?.isClickable = true*/

            }
        }


    }

    fun statuschange_of_on(isclose: Int) {
/*no '0' means close 'red'*/
        if (isclose != 0) {
//                lockerOnOffBtn?.setImageResource(R.drawable.power_button_pink)

            lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_green1)
           // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
           // hoverLyts?.setVisibility(View.GONE)

            CustomPopUp.getInstance(activity)
                .popup_locker_close("Locker is close now..Do you want to open this locker?", object : confirmClick {
                    override fun onClick() {
                        openlocker(final_locker)
                        lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_open1)
                    }
                })
            isclocelocker = "yes"
            /* open!!.text = "Close Locker"
             open!!.setBackgroundResource(R.drawable.close_circle)
             close!!.visibility = View.GONE
             open!!.visibility = View.GONE
             isclocelocker = "yes"*/


        } else {
/*'0' means open 'green'*/

            // lockerOnOffBtn?.setImageResource(R.drawable.power_button_green)

            lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_open1)
          // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
           // hoverLyts?.setVisibility(View.GONE)
            CustomPopUp.getInstance(activity).popup_locker_management("This locker is open.", false)

            /*open!!.text = "Open Locker"
            open!!.setBackgroundResource(R.drawable.open_circle)
            open!!.visibility = View.GONE
            close!!.visibility = View.GONE*/

            isclocelocker = "no"

        }

    }

    fun statuschange_release(isclose: Int) {
/*no '0' means close 'red'*/
        if (isclose != 0) {
            //  lockerOnOffBtn?.setImageResource(R.drawable.power_button_pink)
            lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_green1)

            open!!.text = "Close Locker"
            open!!.setBackgroundResource(R.drawable.close_circle)
            close!!.visibility = View.GONE
            open!!.visibility = View.GONE
//            open!!.text = "Tap on this button to open this locker.."
//            open!!.setTextColor(activity!!.resources.getColor(R.color.pink))
            isclocelocker_release = "yes"
            if (occupency) {
                if (ocupency_true_0 == 0) {
                   // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
                    //hoverLyts?.setVisibility(View.GONE)

                    CustomPopUp.getInstance(activity).popup_locker_management("Please keep the asset in the locker and try again!", false)
                   /* releaseBtn?.isEnabled = true
                    releaseBtn?.isClickable = true*/
                } else {
                    if (isclose != 0) {
                        try {
                            releaseLocker()

                        } catch (e: java.lang.Exception) {
                           /* releaseBtn?.isEnabled = true
                            releaseBtn?.isClickable = true*/
                        }
                    } else {
                       // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()
                       // hoverLyts?.setVisibility(View.GONE)
                        CustomPopUp.getInstance(activity).popup_locker_management("First close your locker.", false)
                       /* releaseBtn?.isEnabled = true
                        releaseBtn?.isClickable = true*/
                    }

//                    CustomPopUp().popup(activity, "This locker is occupied", false);

                }
            } else {
                try {
                    releaseLocker()

                } catch (e: java.lang.Exception) {
                   /* releaseBtn?.isEnabled = true
                    releaseBtn?.isClickable = true*/
                }
//                CustomPopUp().popup(activity, "occupency false", false);

            }

        } else {
/*'0' means open 'green'*/

            // lockerOnOffBtn?.setImageResource(R.drawable.power_button_green)
            lockerOnOffBtn!!.setBackgroundResource(R.drawable.power_button_open1)

            open!!.text = "Open Locker"
            open!!.setBackgroundResource(R.drawable.open_circle)
            open!!.visibility = View.GONE
            close!!.visibility = View.GONE
//            open!!.text = "Locker is open.."
//            open!!.setTextColor(activity!!.resources.getColor(R.color.green))
            isclocelocker_release = "no"
           // Toast.makeText(context, "locker number:"+substr, Toast.LENGTH_LONG).show()

            CustomPopUp.getInstance(activity).popup_locker_management("This locker is open.", false)
           /* releaseBtn?.isEnabled = true
            releaseBtn?.isClickable = true*/

        }

    }

    private fun releaseLocker() {
        clicked_releaselicker = 0
        locker_of_on_click = 0
        val callLogin = RetrofitClient.getInstance(preference.getPreference("String", "token"))!!.getApi()
            .releaseLocker(final_locker_forapi, preference.getPreference("String", "UserId"), bookingid!!)
        hoverLyts?.setVisibility(View.VISIBLE)
        callLogin.enqueue(object : Callback<GenericModel> {
            override fun onResponse(call: Call<GenericModel>, response: Response<GenericModel>) {
                try {
                    val s = response.body()
                    var success = false
                    success = s!!.status.equals("success")
                    if (success) {

                        if (myassetList.size > 1) {

                            if ((activity as MainActivity).isFromRFID) {
                                Bookmore!!.visibility = View.VISIBLE
                                Bookmore!!.isClickable = true
                                Bookmore!!.isEnabled = true
                                getProfileLocker((activity as MainActivity).RFID)
                            } else {
                                getProfileLockerbyOTP((activity as MainActivity).RFID)
                                Bookmore!!.visibility = View.GONE
                                Bookmore!!.isClickable = false
                                Bookmore!!.isEnabled = false
                            }
                        } else {
                            if ((activity as MainActivity).isFromRFID) {
                                val bundle = Bundle()
                                (activity as MainActivity).supportFragmentManager.popBackStack();
                                (activity as MainActivity).addFragment(4, bundle)
                            } else {
//                                val intent = Intent(activity, TabMainActivity::class.java)
                                val intent = Intent(activity, TutorialActivity::class.java)

                                startActivity(intent)
                                activity?.finish()
                                clicked_releaselicker = 0
                                locker_of_on_click = 0
                                preference.clearPreference()

                            }


//                            val bundle = Bundle()
//                            (activity as MainActivity).supportFragmentManager.popBackStack();
//                            (activity as MainActivity).addFragment(4, bundle)
                        }

                        hoverLyts?.setVisibility(View.GONE)
                    }
                    hoverLyts?.setVisibility(View.GONE)
                   /* releaseBtn?.isEnabled = true
                    releaseBtn?.isClickable = true*/
                } catch (e: Exception) {
                    hoverLyts?.setVisibility(View.GONE)
                   /* releaseBtn?.isEnabled = true
                    releaseBtn?.isClickable = true*/
                }

            }

            override fun onFailure(call: Call<GenericModel>, t: Throwable) {
                hoverLyts?.setVisibility(View.GONE)
                Toast.makeText(context, "Network error. Please try again", Toast.LENGTH_LONG).show()
              /*  releaseBtn?.isEnabled = true
                releaseBtn?.isClickable = true*/
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.unregisterReceiver(broadcastinitialstatusreceived)
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

    private val broadcastinitialstatusreceived = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            releaseBtn?.isEnabled = true
            releaseBtn?.isClickable = true
            lockerOnOffBtn?.isEnabled = true
            lockerOnOffBtn?.isClickable = true
            val res = intent.extras!!.getString("DATA")
            if (res.equals("")) {

            } else if (res.length >= 18) {
                try {
                    int1 = Character.getNumericValue(res[2])
                    id1 = Integer.parseInt(final_locker)
                } catch (e: java.lang.Exception) {

                }
                if (id1!! <= 15) {
                    try {

                        if (clicked_releaselicker == 1) {
                            val isLockerClosed = returnAllLocker(res, id1!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1!!)
                            val isclosed = isLockerClosed
                            statuschange_release(isclosed)
                        } else if (locker_of_on_click == 1) {
                            val isLockerClosed = returnAllLocker(res, id1!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1!!)
                            val isclosed = isLockerClosed
                            statuschange_of_on(isclosed)
                        } else {
                            val isLockerClosed = returnAllLocker(res, id1!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1!!)
                            val isclosed = isLockerClosed
                            statuschange(isclosed)
                        }

                    } catch (e: java.lang.Exception) {

                    }

                } else {
                    id1_16 = id1!! - (16 * int1)
                    try {
                        if (clicked_releaselicker == 1) {
                            val isLockerClosed = returnAllLocker(res, id1_16!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1_16!!)
                            val isclosed = isLockerClosed
                            statuschange_release(isclosed)
                        } else if (locker_of_on_click == 1) {
                            val isLockerClosed = returnAllLocker(res, id1_16!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1_16!!)
                            val isclosed = isLockerClosed
                            statuschange_of_on(isclosed)
                        } else {
                            val isLockerClosed = returnAllLocker(res, id1_16!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1_16!!)
                            val isclosed = isLockerClosed
                            statuschange(isclosed)
                        }

                    } catch (e: java.lang.Exception) {

                    }

                    /*
                        try {
                            id1 = id1!! - (16 * int1)
                            val isLockerClosed = returnAllLocker(res, id1!!)
                            ocupency_true_0 = returnAllLocker_0cupancy(res, id1!!)
                            val isclosed = isLockerClosed
                            statuschange(isclosed)
                        } catch (e: java.lang.Exception) {

                        }*/

                }
            } else {

            }


        }

    }

    fun isOnline() {
        val status = NetWorkUtils1.getConnectivityStatusString(activity)
        if ("Not_connected_to_Internet".equals(status)) {

            img_internet!!.setText("offline")
            img_internet!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0)
        } else {

            img_internet!!.setText("online")
            img_internet!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle, 0, 0, 0)
        }
       /* val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.getActiveNetworkInfo()
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            img_internet!!.setText("online")
            img_internet!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle, 0, 0, 0)
        } else {
            img_internet!!.setText("offline")
            img_internet!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0)
        }*/
    }

    override fun onResume() {
        super.onResume()

        runnable = Runnable {
            // Do something here
            isOnline()

            // Schedule the task to repeat after 1 second
            handler1.postDelayed(
                runnable, // Runnable
                1000 // Delay in milliseconds
            )
        }
        handler1.postDelayed(
            runnable, // Runnable
            1000 // Delay in milliseconds
        )

        /* handler1.postDelayed(runnable = object:Runnable {
             public override fun run() {
                 //do something
                 isOnline()
                 handler1.postDelayed(runnable, delay.toLong())
             }
         }, delay)
 */
        clicked_releaselicker = 0
        locker_of_on_click = 0
        releaseBtn?.isEnabled = true
        releaseBtn?.isClickable = true
        lockerOnOffBtn?.isEnabled = true
        lockerOnOffBtn?.isClickable = true
        view!!.home.isEnabled = true
        view!!.home.isClickable = true
        signOut!!.isEnabled = true
        signOut!!.isClickable = true
        if (locker == null) {
//            rl_data!!.visibility=View.GONE
//            rl_no_data!!.visibility=View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        handler1.removeCallbacks(runnable)

    }

    fun doNothing(view: View) {
        // do nothing
    }

}







