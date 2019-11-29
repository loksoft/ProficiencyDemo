package com.prdemo.a829886.projectdemo

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prdemo.a829886.projectdemo.databinding.CusStateItemBinding
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class StateAdapter(val context: Context, private val stateList : ArrayList<States>) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val cusStateItemBinding : CusStateItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.cus_state_item,parent,false)
        return StateViewHolder(cusStateItemBinding)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val states = stateList[position]
        holder.setBind(states)
    }


    class StateViewHolder(private var stateItemBinding: CusStateItemBinding) : RecyclerView.ViewHolder(stateItemBinding.root) {

        var itemDp : CircleImageView? = null

        fun setBind(stateObj : States) {
            stateItemBinding.state = stateObj
            itemDp = stateItemBinding.itemDp

            val imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                stateObj.imageHref.replace("http","https")
                            else
                                stateObj.imageHref

            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(itemDp)
            stateItemBinding.executePendingBindings()
        }
    }
}