package com.rit.tcs.fragment

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.rit.tcs.*
import com.rit.tcs.adapter.CategoryAdapter
import com.rit.tcs.adapter.LockerAdapter
import com.rit.tcs.bussinessobject.Allassets
import com.rit.tcs.bussinessobject.Assets
import com.rit.tcs.bussinessobject.RFIDDataModel
import com.rit.tcs.interfaces.CategorySelectedListener
import com.rit.tcs.interfaces.ItemSelectedListener
import com.rit.tcs.interfaces.confirmClick
import com.rit.tcs.interfaces.confirmOtpClick
import com.rit.tcs.retrofit.RetrofitClient
import com.rit.tcs.util.ConfigPreference
import com.rit.tcs.util.TCSPreference
import kotlinx.android.synthetic.main.layout_booking_items.view.*
import kotlinx.android.synthetic.main.layout_booking_items.view.clear_data_btn
import kotlinx.android.synthetic.main.layout_booking_items.view.home
import kotlinx.android.synthetic.main.layout_booking_items.view.hoverLogin51
import kotlinx.android.synthetic.main.layout_locker.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LockerBookingFragment : Fragment(), CategorySelectedListener, ItemSelectedListener {

    private var scrollListener: RecyclerView.OnScrollListener? = null
    var itemShow: Int = 0
    var pos: Int = 0
    private var clear_data_btn: LinearLayout? = null
    var i = 0
    lateinit var config_preference: ConfigPreference

    lateinit var assetList1st: List<Allassets>
    var firstVisibleItemPosition: Int = 0
    var firstVisibleItemPosition_child: Int = 0

    @SuppressLint("ObjectAnimatorBinding")
    override fun setOnClicked(allassets: Allassets?, position: Int) {
        pos = position
        view!!.rightImg.isClickable = true
        view!!.leftImg.isClickable = true
        view!!.rightImg.setImageResource(R.drawable.ic_right)
        view!!.leftImg.setImageResource(R.drawable.ic_left)
        view!!.rightImg.rotation = 360F
        view!!.leftImg.rotation = 360F
        itemShow = 1
        allAsset = allassets!!


        view!!.txt1.visibility = View.VISIBLE
        view!!.txt2.visibility = View.VISIBLE
        view!!.txt3.visibility = View.VISIBLE
        view!!.txt.visibility = View.GONE
        txt1!!.text = "Choose your "
        txt2!!.text = allassets.cat_name
        txt3!!.text = " type"
        viewPager!!.setVisibility(View.VISIBLE)
        assetItemRclVw!!.setVisibility(View.GONE)
        val animation1 = AnimationUtils.loadAnimation(
            context,
            R.anim.fadein
        )

        if (allassets?.assets!!.size > 0) {

            view!!.back_to_cat.visibility=View.VISIBLE

            val fadeOut = ObjectAnimator.ofFloat(assetItemRclVw, "alpha", 1.0f, 0.0f)
            fadeOut.duration = 400
            val fadein = ObjectAnimator.ofFloat(assetItemRclVw, "alpha", 0.0f, 1.0f)
            fadein.duration = 400

            /*
                     * Here we add our button to center layout's ViewGroupOverlay
                     * when first fade-out animation ends.
                     */
            val container = assetItemRclVw!!.getParent() as ViewGroup
            val anim = ObjectAnimator.ofFloat(viewPager, "translationX", container.height * .18f)
            anim.setDuration(400)

            anim.addListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(animation: Animator) {
                    viewPager!!.setAlpha(0.05f)
                    viewPager!!.startAnimation(animation1)


                }

                override fun onAnimationRepeat(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    viewPager!!.setAlpha(1f)

                    // container.overlay.remove(assetItemRclVw)
                }

                override fun onAnimationCancel(animation: Animator) {
                    viewPager!!.setAlpha(1f)

                    //container.overlay.remove(assetItemRclVw)
                }
            })

            fadeOut.addListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(arg0: Animator) {
                    assetItemRclVw!!.setAlpha(1f)

                }

                override fun onAnimationRepeat(arg0: Animator) {}

                override fun onAnimationEnd(arg0: Animator) {
                    container.overlay.add(assetItemRclVw)
                    assetItemRclVw!!.setAlpha(0f)
                    anim.start()
                }

                override fun onAnimationCancel(arg0: Animator) {
                    container.overlay.add(assetItemRclVw)
                    assetItemRclVw!!.setAlpha(0f)
                    anim.start()
                }
            })

            fadeOut.start()

            fadein.addListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(arg0: Animator) {
                    viewPager!!.setAlpha(0.0f)

                }

                override fun onAnimationRepeat(arg0: Animator) {}

                override fun onAnimationEnd(arg0: Animator) {
                    container.overlay.add(assetItemRclVw)
                    viewPager!!.setAlpha(1.0f)
                    anim.start()
                }

                override fun onAnimationCancel(arg0: Animator) {
                    container.overlay.add(assetItemRclVw)
                    viewPager!!.setAlpha(1.0f)
                    anim.start()
                }
            })

            fadein.start()

            setRecyclerViewScrollListener_child()

            setDisplayForProducts(allassets?.assets!!.asList())

        } else {
            view!!.back_to_cat.visibility=View.GONE
            CustomPopUp.getInstance(activity).popup_confirm("No assets avaiable for this category", object : confirmClick {
                override fun onClick() {

                    view!!.txt1.visibility = View.GONE
                    view!!.txt2.visibility = View.GONE
                    view!!.txt3.visibility = View.GONE
                    view!!.back_to_cat.visibility=View.GONE
                    view!!.txt.visibility = View.VISIBLE
                    view!!.viewPager!!.setVisibility(View.GONE)
                    view!!.rightImg.isClickable = true
                    view!!.leftImg.isClickable = true
                    view!!.rightImg.visibility = View.VISIBLE
                    view!!.leftImg.visibility = View.VISIBLE
                    view!!.leftImg.isClickable = true
                    view!!.rightImg.setImageResource(R.drawable.ic_right)
                    view!!.leftImg.setImageResource(R.drawable.ic_left)
                    view!!.rightImg.rotation = 360F
                    view!!.leftImg.rotation = 360F
                    assetItemRclVw!!.setVisibility(View.VISIBLE)

                    // getProfileLocker((activity as MainActivity).RFID)
                    itemShow = 0
                    firstVisibleItemPosition = 0
                    setRecyclerViewScrollListener()
                    assetItemRclVw?.smoothSnapToPosition(0)

//                    categoryAdapter = CategoryAdapter(allAssetList, context!!, getDisplayHtWd(), this@LockerBookingFragment)
//                    assetItemRclVw!!.adapter = categoryAdapter
                }

            })
        }


    }

    override fun setOnItemClicked(assets: Assets?) {
        asset = assets!!
        val bundle = Bundle()
        bundle.putSerializable("Allassets", allAsset)
        bundle.putSerializable("Assets", assets)
        (activity as MainActivity).addFragment(1, bundle)
    }


    lateinit var asset: Assets
    lateinit var allAsset: Allassets
    lateinit var assetList: List<Assets>
    lateinit var allAssetList: List<Allassets>
    lateinit var preference: TCSPreference
    private var hoverLyts: RelativeLayout? = null
    private var txt: TextView? = null
    private var txt1: TextView? = null
    private var txt2: TextView? = null
    private var txt3: TextView? = null
    private  var back_to_cat:TextView?=null
    private var rightImg: ImageView? = null
    private var leftImg: ImageView? = null

    private var assetItemRclVw: RecyclerView? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var lockerAdapter: LockerAdapter? = null
    private var viewPager: RecyclerView? = null
    var home: ImageView? = null
    var count: Int = 0
    var count1: Int = 0
    var int_c: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.layout_booking_items, container, false);
        allAssetList = ArrayList<Allassets>() as List<Allassets>
        preference = TCSPreference(context)
        config_preference = ConfigPreference(context)

        hoverLyts = view.hoverLogin51
        home = view.home
        clear_data_btn = view.clear_data_btn

        back_to_cat=view.back_to_cat


       /* clear_data_btn!!.setOnClickListener {

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

        view.home.setSafeOnClickListener {
            CustomPopUp.getInstance(activity).popup_logout("Do you want to loggout?",object : confirmClick {
                override fun onClick() {
                    preference.clearPreference()
                   // val intent = Intent(activity, TabMainActivity::class.java)
                     val intent = Intent(activity, TutorialActivity::class.java)
                    startActivity(intent)
                    activity!!.finishAffinity()
                }
            })

        }

        view.back_to_cat.setSafeOnClickListener {
            if ((activity as MainActivity).blank==1) {
                (activity as MainActivity).backfinishtrue_for_lockermanagement=0
                 val bundle = Bundle()
                (activity as MainActivity).supportFragmentManager.popBackStack()
                (activity as MainActivity).supportFragmentManager.popBackStack()
                (activity as MainActivity).supportFragmentManager.popBackStack()
                (activity as MainActivity).supportFragmentManager.popBackStack()
                (activity as MainActivity).addFragment(6, bundle)
        }else{
                (activity as MainActivity).backfinishtrue_for_lockermanagement=3
                 val bundle = Bundle()
                (activity as MainActivity).addFragment(4, bundle)
            }

            val bundle = Bundle()
//            (activity as MainActivity).supportFragmentManager.popBackStack();
//            (activity as MainActivity).supportFragmentManager.popBackStack();
//            (activity as MainActivity).supportFragmentManager.popBackStack();
//            (activity as MainActivity).supportFragmentManager.popBackStack()

        }


        assetItemRclVw = view.assetItemRclVw
        viewPager = view.viewPager
        txt1=view?.txt1
        txt2=view?.txt2
        txt3=view?.txt3

        val layoutManager1 = LinearLayoutManager(context)
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL)
        val layoutManager2 = LinearLayoutManager(context)
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL)
        assetItemRclVw?.setLayoutManager(layoutManager1)
        assetItemRclVw?.setNestedScrollingEnabled(false);
        viewPager?.setLayoutManager(layoutManager2)

        assetItemRclVw!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    allAssetList[pos].value = "NA"

                    categoryAdapter!!.notifyDataSetChanged()
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    allAssetList[pos].value = "NA"

                    categoryAdapter!!.notifyDataSetChanged()

                } else {
                    allAssetList[pos].value = "NA"

                    //myassetList.get(pos).setSelected1("NA")

                    categoryAdapter!!.notifyDataSetChanged()

                }
            }
        })





        setRecyclerViewScrollListener()
        setRecyclerViewScrollListener_child()
        view.rightImg.setOnClickListener(View.OnClickListener {
            if (itemShow == 0) {
                if (allAssetList.size >= firstVisibleItemPosition + 3) {
                    assetItemRclVw?.smoothSnapToPosition(firstVisibleItemPosition + 3)
                    if (allAssetList.size - (firstVisibleItemPosition + 3) <= 3) {
                       // view.rightImg.isClickable = false
                      //  view.leftImg.isClickable = true
                        view.rightImg.setImageResource(R.drawable.ic_left)
                        view.rightImg.rotation = 180F
                        view.leftImg.setImageResource(R.drawable.ic_right)
                        view.leftImg.rotation = 180F
                        assetItemRclVw?.smoothSnapToPosition(firstVisibleItemPosition + 3)
                    }else{
                        view.leftImg.setImageResource(R.drawable.ic_right)
                        view.leftImg.rotation = 180F
                    }
                }
            } else if (itemShow == 1) {


                if (allAsset?.assets!!.size >= firstVisibleItemPosition_child + 3) {
                    viewPager?.smoothSnapToPosition(firstVisibleItemPosition_child + 3)
                    if (allAsset?.assets!!.size - (firstVisibleItemPosition_child + 3) <= 3) {
                        // view.rightImg.isClickable = false
                        //  view.leftImg.isClickable = true
                        view.rightImg.setImageResource(R.drawable.ic_left)
                        view.rightImg.rotation = 180F
                        view.leftImg.setImageResource(R.drawable.ic_right)
                        view.leftImg.rotation = 180F
                        viewPager?.smoothSnapToPosition(firstVisibleItemPosition + 3)
                    }else{//newlyadded
                        view.leftImg.setImageResource(R.drawable.ic_right)
                        view.leftImg.rotation = 180F
                    }//newlyadded
                }


               /* if (allAsset?.assets!!.size > 0) {

                    if (allAsset?.assets!!.size >= firstVisibleItemPosition_child + 3) {
                        viewPager?.smoothSnapToPosition(firstVisibleItemPosition_child + 3)
                        if (allAsset?.assets!!.size - (firstVisibleItemPosition_child + 3) <= 3) {
                            view.rightImg.isClickable = false
                            view.leftImg.isClickable = true
                            view.rightImg.setImageResource(R.drawable.ic_left)
                            view.rightImg.rotation = 180F
                            view.leftImg.setImageResource(R.drawable.ic_right)
                            view.leftImg.rotation = 180F
                            viewPager?.smoothSnapToPosition(firstVisibleItemPosition_child + 3)
                        }
                    }
                }*/
            }

        })

        view.leftImg.setOnClickListener(View.OnClickListener {
            if (itemShow == 0) {
                if (firstVisibleItemPosition - 3 >= 0) {
                    assetItemRclVw?.smoothSnapToPosition(firstVisibleItemPosition - 3)
                    view.rightImg.setImageResource(R.drawable.ic_right)
                    view.rightImg.rotation = 360F
                    if (firstVisibleItemPosition == 0) {
                       // view.leftImg.isClickable = false
                        view.leftImg.setImageResource(R.drawable.ic_left)
                        view.leftImg.rotation = 360F
                      //  view.rightImg.isClickable = true
                        view.rightImg.setImageResource(R.drawable.ic_right)
                        view.rightImg.rotation = 360F
                        // viewPager?.smoothSnapToPosition(firstVisibleItemPosition_child -3 )

                    }else if(firstVisibleItemPosition> 0){
                        view.rightImg.setImageResource(R.drawable.ic_right)
                        view.rightImg.rotation = 360F
                    }
                } else if (firstVisibleItemPosition < 3 && firstVisibleItemPosition > 0) {
                    assetItemRclVw?.smoothSnapToPosition(0)

                }
            } else if (itemShow == 1) {

                if (firstVisibleItemPosition_child - 3 >= 0) {
                    viewPager?.smoothSnapToPosition(firstVisibleItemPosition_child - 3)
                    view.rightImg.setImageResource(R.drawable.ic_right)//newly added
                    view.rightImg.rotation = 360F//newly added
                    if (firstVisibleItemPosition_child == 0) {
                       // view.leftImg.isClickable = false
                        view.leftImg.setImageResource(R.drawable.ic_left)
                        view.leftImg.rotation = 360F
                        //view.rightImg.isClickable = true
                        view.rightImg.setImageResource(R.drawable.ic_right)
                        view.rightImg.rotation = 360F
                    }else if(firstVisibleItemPosition_child> 0){//newly added
                        view.rightImg.setImageResource(R.drawable.ic_right)
                        view.rightImg.rotation = 360F
                    }//newly added
                } else if (firstVisibleItemPosition_child < 3 && firstVisibleItemPosition_child > 0) {
                    viewPager?.smoothSnapToPosition(0)

                }
            }

        })

        getProfileLocker((activity as MainActivity).RFID)
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
                        preference.startToSavePreference()
                        preference.savePreference("String", "UserId", s.profile[0].id)
                        preference.savePreference("String", "token", s.secretkey)
                        preference.stopToSavePreference()
                        allAssetList = s?.allassets!!.asList()
                        allAssetList[0].value = "NA"
                        /*if(s?.myassets!=null && s?.myassets.size>0){
                            val bundle = Bundle()
                            (activity as MainActivity).supportFragmentManager.popBackStack();
                            (activity as MainActivity).addFragment(3, bundle)
                            hoverLyts?.setVisibility(View.GONE)
                        }else {*/
                        setDisplayForSpace(allAssetList)


                        /*}*/
                    } else {

//                        val intent = Intent(activity, TabMainActivity::class.java)
                        val intent = Intent(activity, TutorialActivity::class.java)

                        startActivity(intent)
                        activity?.finish()
                        preference.clearPreference()

                     //   Toast.makeText(context, s!!.getMessage().toString(), Toast.LENGTH_LONG).show()
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

    private fun setDisplayForSpace(allassets: List<Allassets>) {

        // assetList1st = (allassets.subList(0, 3))

        categoryAdapter = CategoryAdapter(allassets, context!!, getDisplayHtWd(), this@LockerBookingFragment)
        assetItemRclVw!!.adapter = categoryAdapter

//        assetItemRclVw?.adapter =
//            CategoryAdapter(allassets, context!!,getDisplayHtWd(), this@LockerBookingFragment)
    }

    private fun setDisplayForProducts(assets: List<Assets>) {
        // assetList1st = (assets.subList(0,4));

        if (allAsset.cat_image == null)
            allAsset.cat_image = "No Image"
        lockerAdapter =
            LockerAdapter(assets, context!!, getDisplayHtWd(), this@LockerBookingFragment, allAsset.cat_image)
        viewPager!!.adapter = lockerAdapter

    }

    private fun getDisplayHtWd(): IntArray {
        val displaymetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        //if you need three fix imageview in width
        val devicewidth = displaymetrics.widthPixels / 4

        //if you need 4-5-6 anything fix imageview in height
        val deviceheight = displaymetrics.heightPixels / 2
        return intArrayOf(deviceheight, devicewidth);
    }

    private fun getDisplayHtWd1(): IntArray {
        val displaymetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        //if you need three fix imageview in width
        val devicewidth = displaymetrics.widthPixels / 5

        //if you need 4-5-6 anything fix imageview in height
        val deviceheight = displaymetrics.heightPixels / 2
        return intArrayOf(deviceheight, devicewidth);
    }

    fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int {
                return snapMode
            }

            override fun getHorizontalSnapPreference(): Int {
                return snapMode
            }
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                firstVisibleItemPosition =
                    (assetItemRclVw?.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if (allAssetList.size - firstVisibleItemPosition < 3) {
                    view!!.rightImg.setImageResource(R.drawable.ic_left)
                    view!!.rightImg.rotation = 180F
                    view!!.leftImg.setImageResource(R.drawable.ic_right)
                    view!!.leftImg.rotation = 180F
                } else if (firstVisibleItemPosition == 0) {
                    view!!.leftImg.setImageResource(R.drawable.ic_left)
                    view!!.leftImg.rotation = 360F
                    view!!.rightImg.setImageResource(R.drawable.ic_right)
                    view!!.rightImg.rotation = 360F
                }else if (firstVisibleItemPosition > 0) {
                    view!!.leftImg.setImageResource(R.drawable.ic_right)
                    view!!.leftImg.rotation = 180F
                    view!!.rightImg.setImageResource(R.drawable.ic_right)
                    view!!.rightImg.rotation = 360F
                }
            }
        }
        assetItemRclVw?.addOnScrollListener(
            scrollListener as RecyclerView.OnScrollListener
        )
    }

    private fun setRecyclerViewScrollListener_child() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                firstVisibleItemPosition_child =
                    (viewPager?.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if (allAsset?.assets!!.size - firstVisibleItemPosition_child <= 3) {
                    view!!.rightImg.setImageResource(R.drawable.ic_left)
                    view!!.rightImg.rotation = 180F
                    view!!.leftImg.setImageResource(R.drawable.ic_right)
                    view!!.leftImg.rotation = 180F
                }  else if (firstVisibleItemPosition_child == 0) {
                    view!!.leftImg.setImageResource(R.drawable.ic_left)
                    view!!.leftImg.rotation = 360F
                    view!!.rightImg.setImageResource(R.drawable.ic_right)
                    view!!.rightImg.rotation = 360F
                }else if (firstVisibleItemPosition_child > 0) {
                    view!!.leftImg.setImageResource(R.drawable.ic_right)
                    view!!.leftImg.rotation = 180F
                    view!!.rightImg.setImageResource(R.drawable.ic_right)
                    view!!.rightImg.rotation = 360F
                }
            }
        }
        viewPager?.addOnScrollListener(
            scrollListener as RecyclerView.OnScrollListener
        )
    }

    override fun onResume() {
        super.onResume()
        itemShow = 0
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