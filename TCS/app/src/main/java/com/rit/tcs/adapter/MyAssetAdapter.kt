package com.rit.tcs.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rit.tcs.bussinessobject.Myassets
import com.rit.tcs.interfaces.MyAssetSelectedListener
import com.rit.tcs.util.changeDateFormatEO
import kotlinx.android.synthetic.main.itassets_booked_item_row.view.*
import android.R



/**
 * Created by A688629 on 02-03-2019.
 */
class MyAssetAdapter(
    val items: List<Myassets>,
    val context: Context,
    val releasedButtonListener: MyAssetSelectedListener
) :
    RecyclerView.Adapter<ViewHolderMA>() {


    private var lastpos: Int = -1

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMA {
        return ViewHolderMA(LayoutInflater.from(context).inflate(com.rit.tcs.R.layout.itassets_booked_item_row, parent, false))
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: ViewHolderMA, position: Int) {
        val viewHolder = holder as ViewHolderMA
        val pos = position

        if (items.get(position).getSelected1().equals("NA")) {
            lastpos = position
            items.get(position).setSelected1("")
            viewHolder?.selectedCenter?.visibility = View.VISIBLE
            viewHolder?.rl_items?.setBackgroundColor(Color.parseColor("#0093ff"))
            viewHolder?.assetName?.setTextColor(Color.parseColor("#ffffff"))
           // viewHolder?.passcode?.setTextColor(Color.parseColor("#ffffff"))
            viewHolder?.assetId?.setTextColor(Color.parseColor("#e8e8e8"))
            viewHolder?.lockerPasscode?.setTextColor(Color.parseColor("#e8e8e8"))
            viewHolder?.bookingDateTm?.setTextColor(Color.parseColor("#e1e5e8"))
            viewHolder?.releaseDateTm?.setTextColor(Color.parseColor("#e1e5e8"))
            viewHolder?.bookingDateTmTxt?.setTextColor(Color.parseColor("#034fb5"))
            viewHolder?.releaseDateTmTxt?.setTextColor(Color.parseColor("#034fb5"))
        } else {

            items.get(position).setSelected1("")
            viewHolder?.selectedCenter?.visibility = View.INVISIBLE
            viewHolder?.rl_items?.setBackgroundColor(Color.parseColor("#fbfbfb"))
            viewHolder?.assetName?.setTextColor(Color.parseColor("#686868"))
            //viewHolder?.passcode?.setTextColor(Color.parseColor("#6c6c6c"))
            viewHolder?.assetId?.setTextColor(Color.parseColor("#9f9e9e"))
            viewHolder?.lockerPasscode?.setTextColor(Color.parseColor("#9f9f9f"))
            viewHolder?.bookingDateTm?.setTextColor(Color.parseColor("#505050"))
            viewHolder?.releaseDateTm?.setTextColor(Color.parseColor("#505050"))
            viewHolder?.bookingDateTmTxt?.setTextColor(Color.parseColor("#a2a2a2"))
            viewHolder?.releaseDateTmTxt?.setTextColor(Color.parseColor("#a09f9f"))
        }


        viewHolder?.assetName?.text = items.get(position).name
       // viewHolder?.passcode?.text = "Passcode - " + items.get(position).passcode
        viewHolder?.assetId?.text = items.get(position).serialnumber
        // holder?.lockerPasscode?.text = items.get(position).passcode
        viewHolder?.bookingDateTm
        viewHolder?.releaseDateTm
        viewHolder?.bookingDateTmTxt?.text = changeDateFormatEO(items.get(position).start_time)
        viewHolder?.releaseDateTmTxt?.text = changeDateFormatEO(items.get(position).end_time)


        viewHolder?.information.setOnClickListener { view ->

        }

        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            if (lastpos != -1) {
                items.get(lastpos).setSelected1("")
            }
            if (items.get(pos).getSelected1().equals("NA")) {
                items.get(pos).setSelected1("")
                //releasedButtonListener.setOnClicked(items.get(position), items.get(position).locker_number,items.get(position).occupency,items.get(position).booking_id,items.get(position).serialnumber,position)

            } else {
                items.get(pos).setSelected1("NA")
              //  releasedButtonListener.setOnClicked(items.get(position), items.get(position).locker_number,items.get(position).occupency,items.get(position).booking_id,items.get(position).serialnumber,position)

            }
            notifyDataSetChanged()
            releasedButtonListener.setOnClicked(items.get(position), items.get(position).locker_number,items.get(position).occupency,items.get(position).booking_id,items.get(position).serialnumber,pos)


            // eventItemclick.onItemClickfornavigation(position,view);
        })

        if (position == items.size) {
            viewHolder?.txt_view?.visibility = View.INVISIBLE
        } else {
            viewHolder?.txt_view?.visibility = View.VISIBLE

        }
    }
}

class ViewHolderMA(view: View) : RecyclerView.ViewHolder(view) {
    val txt_view = view.txt_view
    val selectedCenter = view.selectedCenter
    val assetItems = view.assetItems
    val assetName = view.assetNames
    val passcode = view.passcode
    val assetId = view.assetId
    val lockerPasscode = view.lockerPasscode
    val bookingDateTm = view.bookingDateTm
    val releaseDateTm = view.releaseDateTm
    val bookingDateTmTxt = view.bookingDateTmTxt
    val releaseDateTmTxt = view.releaseDateTmTxt
    val rl_items = view.rl_items
    val information = view.information

}