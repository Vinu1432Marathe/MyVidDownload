package com.clipcatcher.video.highspeed.savemedia.download.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.clipcatcher.video.highspeed.savemedia.download.R

class InterestAdapter (
    private val items: List<String>,
    private val onSelectionChanged: (List<String>) -> Unit
) : RecyclerView.Adapter<InterestAdapter.ViewHolder>() {

    private val selectedItems = mutableSetOf<String>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTag: TextView = view.findViewById(R.id.tvTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_interest, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTag.text = item

        val isSelected = selectedItems.contains(item)
        holder.tvTag.setBackgroundResource(
            if (isSelected) R.drawable.button_bg else R.drawable.card_bg
        )
        holder.tvTag.setTextColor(
            if (isSelected) Color.WHITE else Color.BLACK
        )

        holder.itemView.setOnClickListener {
            if (isSelected) {
                selectedItems.remove(item)
            } else {
                if (selectedItems.size < 3) {
                    selectedItems.add(item)
                } else {
                    Toast.makeText(
                        holder.itemView.context,
                        "You can only select 3 items",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            notifyItemChanged(position)
            onSelectionChanged(selectedItems.toList())
        }
    }

    fun getSelectedItems(): List<String> = selectedItems.toList()
}