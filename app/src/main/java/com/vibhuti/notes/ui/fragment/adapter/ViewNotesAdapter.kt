package com.vibhuti.notes.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vibhuti.notes.databinding.LayoutNotesItemBinding
import com.vibhuti.notes.entity.Note

class ViewNotesAdapter(val list: ArrayList<Note>) :
    RecyclerView.Adapter<ViewNotesAdapter.ViewHolder>() {

    lateinit var longClickHandler: (Note, Int, View) -> Unit
    lateinit var clickHandler: (Note, Int, View) -> Unit

    inner class ViewHolder(private val binding: LayoutNotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun init() {
            val item = list[adapterPosition]
            binding.note = item

            binding.root.setOnClickListener {
                clickHandler(item, adapterPosition, it)
            }

            binding.root.setOnLongClickListener {
                longClickHandler(item, adapterPosition, binding.tvTime)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutNotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init()
    }

    override fun getItemCount(): Int = list.size
}