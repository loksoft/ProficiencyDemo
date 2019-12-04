package com.prdemo.a829886.projectdemo.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prdemo.a829886.projectdemo.R
import com.prdemo.a829886.projectdemo.model.State
import com.prdemo.a829886.projectdemo.model.StateStructure
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

/**
 * This class binds the data to the each item in a recycler view
 */

class StateAdapter(state: State) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    private var stateList: ArrayList<StateStructure> ? = state.rows

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cus_state_item, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val stateInfo: StateStructure ? = stateList?.get(position)
        holder.titleText.text = stateInfo?.title
        holder.descriptionText.visibility = (if (!TextUtils.isEmpty(stateInfo?.description))
            View.VISIBLE.apply {
                holder.descriptionText.text = stateInfo?.description
            }
        else View.GONE)
        Picasso.get().load(stateInfo?.imageHref).placeholder(R.drawable.ic_launcher_foreground).into(holder.itemDp)
    }

    override fun getItemCount(): Int {
        return stateList!!.size
    }

    class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleText: TextView = itemView.findViewById(R.id.item_title)
        var descriptionText: TextView = itemView.findViewById(R.id.item_description)
        var itemDp: CircleImageView = itemView.findViewById(R.id.item_dp)
    }
}