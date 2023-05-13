package com.example.storyapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.databinding.ItemRowUserBinding

class StoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem){
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(story)
            }
            binding.tvUserStory.text = story.name
            Glide.with(itemView).load(story.photoUrl).into(binding.imgStory)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StoryAdapter.ViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listStory[position])
    }

    override fun getItemCount() = listStory.size

    interface OnItemClickListener {
        fun onItemClick(data: ListStoryItem)
    }
}