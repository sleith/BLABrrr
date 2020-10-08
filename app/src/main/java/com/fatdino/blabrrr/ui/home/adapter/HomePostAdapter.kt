package com.fatdino.blabrrr.ui.home.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.databinding.ItemHomePostBinding

class HomePostAdapter(val activity: Activity, val postList: List<Post>) :
    RecyclerView.Adapter<HomePostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHomePostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_post,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        Log.e(javaClass.simpleName, "getItemCount ${postList.size}")
        return postList.size
    }

    inner class ViewHolder(private val binding: ItemHomePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = HomePostAdapterViewModel()

        fun bind(post: Post) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}