package com.example.zavenearbyshoppingassistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.zavenearbyshoppingassistant.data.CachedStore

class StoreAdapter(private val items: List<CachedStore>, private val onClick: (CachedStore) -> Unit) :
    RecyclerView.Adapter<StoreAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img = v.findViewById<ImageView>(R.id.img_icon)
        val tvName = v.findViewById<TextView>(R.id.tv_name)
        val tvAddress = v.findViewById<TextView>(R.id.tv_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = items[position]
        holder.tvName.text = s.name
        holder.tvAddress.text = s.address
        holder.img.load(s.iconUrl) { crossfade(true) }
        holder.itemView.setOnClickListener { onClick(s) }
    }

    override fun getItemCount(): Int = items.size
}
