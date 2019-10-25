package com.rit.tcs.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.rit.tcs.R
import com.rit.tcs.bussinessobject.Allassets
import com.rit.tcs.interfaces.CategorySelectedListener
import com.rit.tcs.retrofit.NetworkUtility.BASE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itassets_item_row.view.assetImg
import kotlinx.android.synthetic.main.itassets_item_row.view.assetItem
import kotlinx.android.synthetic.main.itassets_item_row.view.assetName
import kotlinx.android.synthetic.main.viewpager_item.view.*

/**
 * Created by A688629 on 02-03-2019.
 */
class CategoryAdapter(
    val items: List<Allassets>,
    val context: Context,
    val htWd: IntArray,
    val releasedButtonListener: CategorySelectedListener
) :
    RecyclerView.Adapter<ViewHolderCA>() {

    private var lastpos: Int = -1

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCA {
        return ViewHolderCA(LayoutInflater.from(context).inflate(R.layout.viewpager_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderCA, position: Int) {
        holder?.lockerName?.text = items.get(position).cat_name
        val pos = position
        Picasso.with(context).load(BASE + "/" + items.get(position).cat_image).fit()
            .placeholder(R.drawable.tcs_logo)
            .error(R.drawable.tcs_logo)
            .into(holder?.usageImg);
//        holder?.usageImg?.setImageResource(R.drawable.mouse)
        holder?.assetItem?.setOnClickListener(View.OnClickListener {
            try {
                if (items.get(position).assets != null || items.get(position).assets!!.isEmpty()) {

                    if (lastpos != -1) {
                        items.get(lastpos).setValue("")
                    }
                    if (items.get(pos).getValue().equals("NA")) {
                        items.get(pos).setValue("")
                    } else {
                        items.get(pos).setValue("NA")
                    }
                    notifyDataSetChanged()

                    releasedButtonListener.setOnClicked(items.get(position), pos)
                } else {

                    if (lastpos != -1) {
                        items.get(lastpos).setValue("")
                    }
                    if (items.get(position).getValue().equals("NA")) {
                        items.get(position).setValue("")
                    } else {
                        items.get(position).setValue("NA")
                    }
                    notifyDataSetChanged()

                    Toast.makeText(context, "No items!", Toast.LENGTH_LONG).show()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        })
        if (position == 0) {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 0)

//            params.setMargins(context.getResources().getDimension(R.dimen.margin_2) .toInt(), 0, 0, 0)
            holder.itemView.setLayoutParams(params)
        } else {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(context.getResources().getDimension(R.dimen.margin_20).toInt(), 0, 0, 0)
            holder.itemView.setLayoutParams(params)
        }

        if (items.get(position).getValue().equals("NA")) {
            lastpos = position
            items.get(position).setValue("")
            holder.relative_checked_type.setBackground(context.getDrawable(R.drawable.bg_selected))
        } else {

            items.get(position).setValue("")
            holder.relative_checked_type.setBackground(context.getDrawable(R.drawable.bg_white_selected))
        }


        holder.assetItem.getLayoutParams().height = htWd[0];
        holder.assetItem.getLayoutParams().width = htWd[1];
    }
}

class ViewHolderCA(view: View) : RecyclerView.ViewHolder(view) {
    val lockerName = view.assetName
    val usageImg = view.assetImg
    val assetItem = view.assetItem
    val relative_checked_type = view.relative_checked_type
}