package com.fatdino.blabrrr.ui.myposts

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.databinding.ActivityMyPostBinding
import com.fatdino.blabrrr.injection.component.DaggerViewModelComponent
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.landing.LandingActivity
import com.fatdino.blabrrr.ui.myposts.adapter.MyPostsAdapter
import com.fatdino.blabrrr.ui.myposts.adapter.MyPostsAdapterInterface
import kotlinx.android.synthetic.main.activity_my_post.*

class MyPostActivity : BaseActivity() {

    lateinit var viewModel: MyPostActivityViewModel
    lateinit var mAdapter: MyPostsAdapter

    override fun setupViews() {
        viewModel = mViewModel as MyPostActivityViewModel
        val binding: ActivityMyPostBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_my_post)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val injector =
            DaggerViewModelComponent.builder().serviceModule(ServiceModule()).storageModule(
                StorageModule(this)
            ).build()
        injector.inject(viewModel)

        mAdapter = MyPostsAdapter(
            this,
            viewModel.posts.value ?: ArrayList(),
            object : MyPostsAdapterInterface {
                override fun onDeleteClicked(post: Post) {
                    showDeleteConfirmationDialog(post)
                }
            })
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.setDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.divider
                )
            )
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        btnLogout.setOnClickListener {
            viewModel.doLogout()
        }
    }

    override fun setupObservers() {
        viewModel.posts.observe(this, {
            mAdapter.notifyDataSetChanged()
        })

        viewModel.callbackLogoutSuccess.observe(this, {
            if (it) {
                startActivity(Intent(this, LandingActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                })
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(MyPostActivityViewModel::class.java)
    }

    private fun showDeleteConfirmationDialog(post: Post) {
        val builder = AlertDialog.Builder(this@MyPostActivity, R.style.MyDialog)
        builder.setTitle(R.string.delete)
        builder.setMessage(R.string.mypost_delete_confirmation_message)
        builder.setPositiveButton(getString(R.string.OK)) { _, _ ->
            viewModel.doDeletePost(post)
        }
        builder.setNegativeButton(getString(R.string.cancel), null)
        builder.create().show()
    }
}