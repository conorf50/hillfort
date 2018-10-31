package org.cfarrell.hillfort.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.helpers.readImageFromPath
import org.cfarrell.hillfort.models.HillfortModel

interface HillfortListener {
  fun onPlacemarkClick(hillfortModel: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                   private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.hillfortTitle.text = hillfort.title
      itemView.description.text = hillfort.description
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))
      itemView.setOnClickListener { listener.onPlacemarkClick(hillfort) }
    }
  }
}