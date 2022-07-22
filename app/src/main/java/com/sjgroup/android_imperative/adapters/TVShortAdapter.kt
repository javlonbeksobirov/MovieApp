package com.sjgroup.android_imperative.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sjgroup.android_imperative.R
import com.sjgroup.android_imperative.activities.DetailsActivity
import com.sjgroup.android_imperative.activities.MainActivity
import com.sjgroup.android_imperative.databinding.ItemTvShortBinding
import com.sjgroup.android_imperative.databinding.ItemTvShowBinding
import com.sjgroup.android_imperative.models.TVShow
import com.sjgroup.android_imperative.models.TVShowDetails

class TVShortAdapter(var activity: DetailsActivity, var items: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_short,parent, false)
        return TvShortViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item  = items[position]

        if(holder is TvShortViewHolder){
            Glide.with(activity).load(item).into(holder.binding.ivShort)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TvShortViewHolder(view :View):RecyclerView.ViewHolder(view){
       val binding = ItemTvShortBinding.bind(view)
    }


}