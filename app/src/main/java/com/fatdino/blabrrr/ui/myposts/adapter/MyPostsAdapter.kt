package com.fatdino.blabrrr.ui.myposts.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.databinding.ItemMyPostBinding

interface MyPostsAdapterInterface {
    fun onDeleteClicked(post: Post)
}

class MyPostsAdapter(
    val activity: Activity,
    private val postList: List<Post>,
    private val callback: MyPostsAdapterInterface
) :
    RecyclerView.Adapter<MyPostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMyPostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_my_post,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class ViewHolder(private val binding: ItemMyPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = MyPostsAdapterViewModel()

        fun bind(post: Post) {
            viewModel.bind(post)
            binding.viewModel = viewModel

            val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
            btnDelete.tag = post
            btnDelete.setOnClickListener {
                callback.onDeleteClicked(it.tag as Post)
            }
        }
    }
}