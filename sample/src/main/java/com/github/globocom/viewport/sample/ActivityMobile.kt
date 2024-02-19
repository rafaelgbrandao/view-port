package com.github.globocom.viewport.sample

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.globocom.viewport.mobile.Threshold
import com.github.globocom.viewport.sample.databinding.ActivityMainBinding
import com.github.globocom.viewport.sample.databinding.ActivityMobileBinding

class ActivityMobile : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object {
        private const val LINEAR_LAYOUT_MANAGER = 0
        private const val GRID_LAYOUT_MANAGER = 1
    }

    private val spinnerThresholdValues = arrayListOf(
        Threshold.VISIBLE.name,
        Threshold.HALF.name,
        Threshold.ALMOST_VISIBLE.name,
        Threshold.ALMOST_HIDDEN.name
    )

    private var activityMobileBinding: ActivityMobileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(ActivityMobileBinding
            .inflate(layoutInflater)
             .also { activityMobileBinding = it }
             .root)

        setRecyclerView(LINEAR_LAYOUT_MANAGER, Threshold.VISIBLE)

        activityMobileBinding?.activityMainViewPortRecyclerView?.apply {
            viewedItemsLiveData.observe(this@ActivityMobile, Observer {
                activityMobileBinding?.activityMainTextViewItemsViewedTv?.text =
                    getString(R.string.view_port_viewed_items, it.toString())
            })
            onlyNewViewedItemsLiveData.observe(this@ActivityMobile, Observer {
                activityMobileBinding?.activityMainTextViewNewViewedItems?.text =
                    getString(R.string.view_port_new_visible_items, it.toString())
            })
            lifecycleOwner = this@ActivityMobile
            threshold(Threshold.VISIBLE)
        }

        activityMobileBinding?.activityMainButtonLinearLayout?.setOnClickListener {
            setRecyclerView(LINEAR_LAYOUT_MANAGER, getSelectedThreshold())
            activityMobileBinding?.activityMainViewPortRecyclerView?.invalidate()
        }

        activityMobileBinding?.activityMainButtonGridLayout?.setOnClickListener {
            setRecyclerView(GRID_LAYOUT_MANAGER, getSelectedThreshold())
            activityMobileBinding?.activityMainViewPortRecyclerView?.invalidate()
        }

        activityMobileBinding?.activityMainSpinnerThreshold?.run {
            this.adapter = ArrayAdapter(
                this@ActivityMobile,
                android.R.layout.simple_spinner_item,
                spinnerThresholdValues
            )
            onItemSelectedListener = this@ActivityMobile
        }
    }

    override fun onDestroy() {
        activityMobileBinding = null
        super.onDestroy()
    }

    private fun setRecyclerView(
        layoutManagerOption: Int,
        threshold: Threshold
    ) {
        activityMobileBinding?.activityMainViewPortRecyclerView?.apply {
            setHasFixedSize(true)
            threshold(threshold)
            layoutManager =
                if (layoutManagerOption == GRID_LAYOUT_MANAGER) GridLayoutManager(context, 4)
                else LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ViewPortSampleAdapter((0..100).toList())
        }
    }

    private fun getSelectedThreshold(): Threshold {
        return activityMobileBinding?.activityMainSpinnerThreshold?.selectedItemPosition?.let { Threshold.valueOf(spinnerThresholdValues[it]) }
            ?: Threshold.HALF
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        activityMobileBinding?.activityMainViewPortRecyclerView?.threshold(Threshold.valueOf(spinnerThresholdValues[position]))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}
