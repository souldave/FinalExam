package com.example.davegilbier.finalexam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Dave Gilbier on 21/03/2018.
 */
class Adapter(val mAlbum: ArrayList<Album>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.albumtxt.text = mAlbum[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.song_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mAlbum.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumtxt = itemView.findViewById(R.id.album_txt) as TextView
    }
}
