package com.example.caponecoroutineexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.caponecoroutineexample.databinding.CatItemBinding
import com.example.caponecoroutineexample.utility.loadImage

class DataAdapter(
    private val urls: List<String>,
    private val itemClicked: (String) -> Unit
) : RecyclerView.Adapter<DataAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = CatViewHolder.inflate(parent).also { holder ->
        holder.itemView.setOnClickListener {
            itemClicked.invoke(urls[holder.adapterPosition])
        }
    }
    override fun onBindViewHolder(holder: CatViewHolder, position: Int): Unit = with(holder) {
        bind(urls[position])
    }

    override fun getItemCount() = urls.size

    class CatViewHolder(
        private val binding: CatItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) = with(binding) {
            ivImage.loadImage(url)
        }

        companion object {
            fun inflate(parent: ViewGroup) = CatViewHolder(
                CatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}