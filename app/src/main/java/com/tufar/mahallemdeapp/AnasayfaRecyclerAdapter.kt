package com.tufar.mahallemdeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class AnasayfaRecyclerAdapter (val firmaList: ArrayList<Firma>) : RecyclerView.Adapter<AnasayfaRecyclerAdapter.FirmaHolder>() {
    class FirmaHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirmaHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return FirmaHolder(view)
    }

    override fun onBindViewHolder(holder: FirmaHolder, position: Int) {
        holder.itemView.firmaAdi.text = firmaList[position].firmaAdi
        holder.itemView.adres.text = firmaList[position].adres
        holder.itemView.telefon.text = firmaList[position].telefon
        Picasso.get().load(firmaList[position].gorselYolu).into(holder.itemView.imageView)
    }

    override fun getItemCount(): Int {
        return firmaList.size
    }
}