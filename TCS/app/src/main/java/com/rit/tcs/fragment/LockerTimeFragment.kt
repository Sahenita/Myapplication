package com.rit.tcs.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rit.tcs.*
import com.rit.tcs.CustomText.CUstomTextHelveticaRegular
import com.rit.tcs.CustomText.CUstomTextRebotoRegular
import com.rit.tcs.bussinessobject.Allassets
import com.rit.tcs.bussinessobject.Assets
import com.rit.tcs.bussinessobject.RFIDDataModel
import com.rit.tcs.interfaces.DateTimeSelectedListener
import com.rit.tcs.interfaces.confirmClick
import com.rit.tcs.interfaces.confirmOtpClick
import com.rit.tcs.interfaces.showinterface
import com.rit.tcs.retrofit.NetworkUtility
import com.rit.tcs.retrofit.RetrofitClient
import com.rit.tcs.util.ConfigPreference
import com.rit.tcs.util.TCSPreference
import com.rit.tcs.util.getCurrentTimeUsingDateAndVar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_booking_duration.view.*
import kotlinx.android.synthetic.main.layout_booking_duration.view.assetImg
import kotlinx.android.synthetic.main.layout_booking_duration.view.home
import kotlinx.android.synthetic.main.layout_booking_duration.view.txt2
import kotlinx.android.synthetic.main.layout_booking_duration.view.txt3
import kotlinx.android.synthetic.main.layout_booking_items.view.*
import kotlinx.android.synthetic.main.layout_confirm_booking.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import com.rit.tcs.fragment.DatePickerFragment as DatePickerFragment

class LockerTimeFragment : Fragment(), DateTimeSelectedListener, Serializable, showinterface {
    override fun setOnClicked(show: Boolean?) {
        if (show!!) {
            ll_duration!!.visibility = View.GONE

        } else {
            ll_duration!!.visibility = View.VISIBLE
            txt1!!.visibility = View.VISIBLE
            txt2!!.visibility = View.GONE
            txt3!!.visibility = View.GONE
            txt4!!.visibility = View.GONE
        }
    }


    override fun setOnDateSelected(type: Int, date: String?) {
        when (type) {
            1 -> {
                endTime?.setText(date)
                showTimePicker(type)
            }
            2 -> {
                endTime?.setText(date)
               // showTimePicker(type)
            }
        }
    }

    override fun setOnTimeSelected(type: Int, time: String?) {
        when (type) {
            1 -> {
                endTime?.setText(endTime?.getText().toString() + " at " + time)
               // showDatePicker(2)
                val bundle = Bundle()
                bundle.putSerializable("Allassets", allAsset)
                bundle.putSerializable("Assets", asset)
                bundle.putString("StartTime", startTime?.text.toString())
                bundle.putString("EndTime", endTime?.text.toString())
                timePickerFragment!!.dismiss()
                (activity as MainActivity).addFragment(2, bundle)


            }
            2 -> {
                endTime?.setText(endTime?.getText().toString() + " at " + time)
                val bundle = Bundle()
                bundle.putSerializable("Allassets", allAsset)
                bundle.putSerializable("Assets", asset)
                bundle.putString("StartTime", startTime?.text.toString())
                bundle.putString("EndTime", endTime?.text.toString())
                timePickerFragment!!.dismiss()
                (activity as MainActivity).addFragment(2, bundle)
            }
        }
    }


    lateinit var asset: Assets
    lateinit var allAsset: Allassets
    lateinit var preference: TCSPreference
    private var hoverLyts: RelativeLayout? = null
    private var assetImg: ImageView? = null
    private var txt1: TextView? = null
    private var assetPath: TextView? = null
    private var assetPath1: TextView? = null

    private var oneHr: TextView? = null
    private var oneDay: TextView? = null
    private var oneWk: TextView? = null
    private var fourHr: TextView? = null
    private var twoDay: TextView? = null
    private var oneMon: TextView? = null
    private var eightHr: TextView? = null
    private var fiveDay: TextView? = null
    private var more: TextView? = null
    private var startTime: TextView? = null
    private var endTime: TextView? = null
    var home: ImageView? = null
    var ll_duration: LinearLayout? = null
    var ll_main: LinearLayout? = null
   // var txt11: TextView? = null
    var txt4: TextView? = null
    var txt2: TextView? = null
    var txt3: TextView? = null

    var datePickerFragment: DatePickerFragment? = null
    var timePickerFragment: TimePickerFragment? = null
    private var clear_data_btn: LinearLayout? = null
    var i = 0
    lateinit var config_preference: ConfigPreference

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.layout_booking_duration, container, false);
        preference = TCSPreference(context)
        config_preference = ConfigPreference(context)
        hoverLyts = view.hoverLyt11
        assetImg = view.assetImg
        clear_data_btn = view.clear_data_btn1

        txt1 = view.txt11
        txt2 = view.txt2
        txt3 = view.txt3
        txt4 = view.txt4

        assetPath = view.assetPath
        assetPath1 = view.assetPath1
        ll_duration = view.ll_duration
        ll_duration!!.visibility = View.VISIBLE
        ll_main = view.ll_main1
        oneHr = view.oneHr
        (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
        oneDay = view.oneDay
        oneWk = view.oneWk
        fourHr = view.fourHr
        twoDay = view.twoDay
        oneMon = view.oneMon
        eightHr = view.eightHr
        fiveDay = view.fiveDay
        more = view.more
        startTime = view.startTime
        endTime = view.endTime
        home = view.home


        /*  if (datePickerFragment != null
              && datePickerFragment!!.getDialog() != null
              && datePickerFragment!!.getDialog().isShowing()
              && !datePickerFragment!!.isRemoving()) {
              //dialog is showing so do something
              ll_duration!!.visibility=View.GONE

          } else {
              //dialog is not showing
              ll_duration!!.visibility=View.VISIBLE

          }
          if (timePickerFragment != null
              && timePickerFragment!!.getDialog() != null
              && timePickerFragment!!.getDialog().isShowing()
              && !timePickerFragment!!.isRemoving()) {
              //dialog is showing so do something
              ll_duration!!.visibility=View.GONE

          } else {
              //dialog is not showing
              ll_duration!!.visibility=View.VISIBLE

          }*/
/*
        clear_data_btn!!.setOnClickListener {

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

        view.home.setOnClickListener(View.OnClickListener {
            CustomPopUp.getInstance(activity).popup_logout("Do you want to loggout?",object : confirmClick {
                override fun onClick() {

                    preference.clearPreference()
//            val intent = Intent(activity, TabMainActivity::class.java)
                    val intent = Intent(activity, TutorialActivity::class.java)

                    startActivity(intent)
                    activity!!.finishAffinity()

                }
            })
        })

        view.ll_main1.setOnClickListener(View.OnClickListener {
            ll_duration!!.visibility = View.VISIBLE

        })
        allAsset = getArguments()?.getSerializable("Allassets") as Allassets
        asset = getArguments()?.getSerializable("Assets") as Assets

        assetPath?.text = allAsset.cat_name + " >> "
        assetPath1?.text = asset.name
        txt2?.text = "Choose your "
        txt3?.text = allAsset.cat_name
        txt4?.text = " type"

        Picasso.with(context).load(NetworkUtility.BASE + "/" + allAsset.cat_image).fit()
            .placeholder(R.drawable.tcs_logo)
            .error(R.drawable.tcs_logo)
            .into(assetImg);

        more?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))

            ll_duration!!.visibility = View.GONE
            txt1!!.visibility = View.GONE
            txt2!!.visibility = View.VISIBLE
            txt3!!.visibility = View.VISIBLE
            txt4!!.visibility = View.VISIBLE
            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            showDatePicker(1)
        })

        oneHr?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))

            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("1H"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })
        oneDay?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))


            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("1D"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })
        oneWk?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))


            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("1W"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })

        fourHr?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))


            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("4H"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })
        twoDay?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))

            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("2D"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })
        oneMon?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))


            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("1M"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })
        eightHr?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))


            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("8H"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })
        fiveDay?.setOnClickListener(View.OnClickListener {
            (oneHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneWk as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fourHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (twoDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (oneMon as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (eightHr as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))
            (fiveDay as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.select_time))
            (more as CUstomTextHelveticaRegular).setBackground(activity!!.getDrawable(R.drawable.deselect_time))

            startTime?.setText(getCurrentTimeUsingDateAndVar("D"))
            endTime?.setText(getCurrentTimeUsingDateAndVar("5D"))
            val bundle = Bundle()
            bundle.putSerializable("Allassets", allAsset)
            bundle.putSerializable("Assets", asset)
            bundle.putString("StartTime", startTime?.text.toString())
            bundle.putString("EndTime", endTime?.text.toString())
            (activity as MainActivity).addFragment(2, bundle)
        })

        return view
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

                    } else {
                        Toast.makeText(context, s!!.getMessage().toString(), Toast.LENGTH_LONG).show()
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

    fun showDatePicker(type: Int) {
        /*  val newFragment = DatePickerFragment()
          val bundle = Bundle()
          bundle.putInt("type", type)
          bundle.putSerializable("friendsID", this@LockerTimeFragment)
          newFragment.arguments = bundle
          newFragment.show(childFragmentManager, "Date Picker")*/
        datePickerFragment = DatePickerFragment()
        val bundle = Bundle()
        bundle.putInt("type", type)
        bundle.putSerializable("friendsID", this@LockerTimeFragment)
        bundle.putSerializable("friendsID1", this@LockerTimeFragment)
        datePickerFragment!!.arguments = bundle
        datePickerFragment!!.show(childFragmentManager, "Date Picker")

    }

    fun showTimePicker(type: Int) {
        /* val newFragment = TimePickerFragment()
         val bundle = Bundle()
         bundle.putInt("type", type)
         bundle.putSerializable("friendsID", this@LockerTimeFragment)
         newFragment.arguments = bundle
         newFragment.show(childFragmentManager, "Date Picker")*/

        timePickerFragment = TimePickerFragment()
        val bundle = Bundle()
        bundle.putInt("type", type)
        bundle.putSerializable("friendsID", this@LockerTimeFragment)
        bundle.putSerializable("friendsID1", this@LockerTimeFragment)

        timePickerFragment!!.arguments = bundle
        timePickerFragment!!.show(childFragmentManager, "Date Picker")
    }

    override fun onResume() {
        super.onResume()
        ll_duration!!.visibility = View.VISIBLE


/*
        if (datePickerFragment != null
            && datePickerFragment!!.getDialog() != null
            && datePickerFragment!!.getDialog().isShowing()
            && !datePickerFragment!!.isRemoving()) {
            //dialog is showing so do something
            ll_duration!!.visibility=View.GONE

        } else {
            //dialog is not showing
            ll_duration!!.visibility=View.VISIBLE

        }
        if (timePickerFragment != null
            && timePickerFragment!!.getDialog() != null
            && timePickerFragment!!.getDialog().isShowing()
            && !timePickerFragment!!.isRemoving()) {
            //dialog is showing so do something
            ll_duration!!.visibility=View.GONE

        } else {
            //dialog is not showing
            ll_duration!!.visibility=View.VISIBLE

        }*/

    }
    fun doNothing(view:View){
        // do nothing
    }
    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }
}