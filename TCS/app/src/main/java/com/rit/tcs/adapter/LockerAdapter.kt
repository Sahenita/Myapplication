package com.rit.tcs.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.rit.tcs.R
import com.rit.tcs.bussinessobject.Assets
import com.rit.tcs.interfaces.ItemSelectedListener
import com.rit.tcs.interfaces.ReleaseButtonListener
import com.rit.tcs.retrofit.NetworkUtility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itassets_item_row.view.*

/**
 * Created by A688629 on 02-03-2019.
 */
class LockerAdapter(
    val items: List<Assets>,
    val context: Context,
    val htWd: IntArray,
    val releasedButtonListener: ItemSelectedListener,
    val catImg: String
) :
    RecyclerView.Adapter<ViewHolderLA>() {


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLA {
        return ViewHolderLA(LayoutInflater.from(context).inflate(R.layout.viewpager_item1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderLA, position: Int) {
        holder?.lockerName?.text = items.get(position).name
        Picasso.with(context).load(NetworkUtility.BASE + "/" + catImg).fit()
            .placeholder(R.drawable.tcs_logo)
            .error(R.drawable.tcs_logo)
            .into(holder?.usageImg);
        holder?.assetItem?.setOnClickListener(View.OnClickListener {
            releasedButtonListener.setOnItemClicked(items.get(position))
        })

        if (position == 0)
        {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 0)

//            params.setMargins(context.getResources().getDimension(R.dimen.margin_2) .toInt(), 0, 0, 0)
            holder.itemView.setLayoutParams(params)
        }
        else
        {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 0)
            holder.itemView.setLayoutParams(params)
        }


        holder.assetItem.getLayoutParams().height = htWd[0];
        holder.assetItem.getLayoutParams().width = htWd[1];
    }
}

class ViewHolderLA(view: View) : RecyclerView.ViewHolder(view) {
    val lockerName = view.assetName
    val usageImg = view.assetImg
    val assetItem = view.assetItem
}