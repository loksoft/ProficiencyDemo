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

class StateAdapter(state: State) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_WITHOUT_DESC = 0
        const val ITEM_WITH_ALL = 1
    }

    private var stateList: ArrayList<StateStructure> = state.rows!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == ITEM_WITHOUT_DESC) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.cus_item_with_out_desc, parent, false)
            StateViewHolderWithoutDesc(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.cus_state_item, parent, false)
            StateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_WITHOUT_DESC) {
            val stateInfo: StateStructure = stateList[position]
            (holder as StateViewHolderWithoutDesc).titleWithoutDecText.text = stateInfo.title
            Picasso.get().load(stateInfo.imageHref).placeholder(R.drawable.ic_launcher_foreground).into(holder.titleWithoutDecDp)
        } else if (getItemViewType(position) == ITEM_WITH_ALL){
            val stateInfo: StateStructure = stateList[position]
            (holder as StateViewHolder).titleText.text = stateInfo.title
            holder.descriptionText.text = stateInfo.description
            Picasso.get().load(stateInfo.imageHref).placeholder(R.drawable.ic_launcher_foreground).into(holder.itemDp)
        }
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun getItemViewType(position: Int): Int {
        val stateStructure = stateList[position]
        return if (!TextUtils.isEmpty(stateStructure.title) && TextUtils.isEmpty(stateStructure.description)) {
            ITEM_WITHOUT_DESC
        } else {
            ITEM_WITH_ALL
        }
    }

    class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleText: TextView = itemView.findViewById(R.id.item_title)
        var descriptionText: TextView = itemView.findViewById(R.id.item_description)
        var itemDp: CircleImageView = itemView.findViewById(R.id.item_dp)
    }

    class StateViewHolderWithoutDesc(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleWithoutDecText: TextView = itemView.findViewById(R.id.item_without_desc_title)
        var titleWithoutDecDp: CircleImageView = itemView.findViewById(R.id.item_without_desc_dp)
    }
}