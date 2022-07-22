package com.sjgroup.android_imperative.activities

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.sjgroup.android_imperative.R
import com.sjgroup.android_imperative.adapters.TVShortAdapter
import com.sjgroup.android_imperative.databinding.ActivityDetailsBinding
import com.sjgroup.android_imperative.databinding.ActivityMainBinding
import com.sjgroup.android_imperative.utills.Logger
import com.sjgroup.android_imperative.viewModel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivty() {
    private val TAG = DetailsActivity::class.java.simpleName
    lateinit var binding : ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {
        initObservers()
        binding.rvShorts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val iv_details = binding.ivDetails
        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }

        val extras = intent.extras

        val show_id = extras!!.getLong("show_id")
        val show_img = extras!!.getString("show_img")
        val show_name = extras!!.getString("show_name")
        val show_network = extras!!.getString("show_network")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val imageTransitionName = extras.getString("iv_movie")
            binding.ivDetails.transitionName = imageTransitionName
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(iv_details)

        viewModel.apiTVShowDetails(show_id.toInt())
    }

    private fun initObservers() {
        viewModel.tvShowDetaild.observe(this,{
            Logger.d(TAG, it.toString())

            // refreshAdapter
            refreshAdapter(it.tvShow.pictures)
            binding.tvDetails.text = it.tvShow.description
        })

        viewModel.errorMessage.observe(this, {
            Logger.d(TAG, it.toString())
        })

        viewModel.isLoading.observe(this, {
            Logger.d(TAG, it.toString())
            if(it){
                binding.pbLoading.visibility = View.VISIBLE
            }else{
                binding.pbLoading.visibility = View.GONE
            }
        })
    }

    private fun refreshAdapter(items: List<String>) {
        val adapter = TVShortAdapter(this, items)
        binding.rvShorts.adapter = adapter
    }
}