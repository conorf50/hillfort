package org.cfarrell.hillfort.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_hllfort.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.helpers.readImageFromPath
import org.cfarrell.hillfort.models.HillfortModel
import java.lang.Exception
import java.text.DecimalFormat

interface HillfortListener {
  fun onHillfortClick(hillfortModel: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                   private val listener: HillfortListener) : androidx.recyclerview.widget.RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.hillfortCardTitle.text = hillfort.title
      itemView.hillfortCardDescription.text = hillfort.description
      itemView.cardImageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))

      val date = "4/11/2018" // replace with visited date
      if (hillfort.visitedFlag == true){
        itemView.visitedFlag.setVisibility(View.VISIBLE) // only display the text if a hillfort has been visited
        itemView.visitedFlag.text = "Visited on " + date
      }

      if (!(hillfort.lat == 0.0 || hillfort.lng == 0.0)){
        itemView.cardHillfortLocation.setText(DecimalFormat("#.##").format(hillfort.lat) + "," + DecimalFormat("#.##").format(hillfort.lng))
    }
    else{
      itemView.cardHillfortLocation.setText("No location provided")
    }
      // set a listener
      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
    }
  }
}