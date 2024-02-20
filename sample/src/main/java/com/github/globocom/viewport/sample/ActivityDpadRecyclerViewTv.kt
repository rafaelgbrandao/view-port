package com.github.globocom.viewport.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.globocom.viewport.sample.databinding.ActivityDpadRecyclerViewTvBinding

class ActivityDpadRecyclerViewTv : AppCompatActivity() {

    private var activityDpadRecyclerViewTvBinding: ActivityDpadRecyclerViewTvBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(ActivityDpadRecyclerViewTvBinding
            .inflate(layoutInflater)
             .also { activityDpadRecyclerViewTvBinding = it }
             .root)

        activityDpadRecyclerViewTvBinding?.activityDpadTvRecyclerView?.apply {
            setHasFixedSize(true)
            viewedItemsLiveData.observe(this@ActivityDpadRecyclerViewTv, Observer {
                activityDpadRecyclerViewTvBinding?.activityDpadTvRecyclerViewTextViewVisibleItems?.text =
                    getString(R.string.view_port_viewed_items, it.toString())
            })
            onlyNewViewedItemsLiveData.observe(this@ActivityDpadRecyclerViewTv, Observer {
                activityDpadRecyclerViewTvBinding?.activityDpadTvRecyclerViewTextViewNewVisibleItems?.text =
                    getString(R.string.view_port_new_visible_items, it.toString())
            })
            lifecycleOwner = this@ActivityDpadRecyclerViewTv
            adapter = ViewPortAdapter((0..100).toList())
        }
    }

    override fun onDestroy() {
        activityDpadRecyclerViewTvBinding = null
        super.onDestroy()
    }

}
