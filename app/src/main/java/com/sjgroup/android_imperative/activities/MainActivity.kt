package com.sjgroup.android_imperative.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjgroup.android_imperative.adapters.TVShowAdapter
import com.sjgroup.android_imperative.databinding.ActivityMainBinding
import com.sjgroup.android_imperative.models.TVShow
import com.sjgroup.android_imperative.utills.Logger
import com.sjgroup.android_imperative.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivty() {
    private val TAG = MainActivity::class.java.simpleName
    public val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initObservers()
        val lm = GridLayoutManager(this, 2)
        binding.rvHome.layoutManager = lm
        refreshAdapter(ArrayList())
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (lm.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPage = viewModel.tvShowPopular.value!!.pages
                    if (nextPage <= totalPage) {
                        viewModel.apiTVShowPopular(nextPage)
                    }
                }
            }
        })

        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }
        viewModel.apiTVShowPopular(1)
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.rvHome.adapter = adapter
    }

    private fun initObservers() {
        // retrofit related

        viewModel.tvShowsFromApi.observe(this, {
            Logger.d(TAG, it.size.toString())

            adapter.setNewTVShows(it)
        })

        viewModel.errorMessage.observe(this, {
            Logger.d(TAG, it.toString())
        })

        viewModel.isLoading.observe(this, {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })

        // Room related
    }

    fun callDetailsActivity(tvShow: TVShow, sharedImageView: ImageView) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, sharedImageView, ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }
}