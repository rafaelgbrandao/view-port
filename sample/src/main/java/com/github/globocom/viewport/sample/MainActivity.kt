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

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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

    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(ActivityMainBinding
            .inflate(layoutInflater)
             .also { activityMainBinding = it }
             .root)

        setRecyclerView(LINEAR_LAYOUT_MANAGER, Threshold.VISIBLE)

        activityMainBinding?.activityMainViewPortRecyclerView?.apply {
            viewedItemsLiveData.observe(this@MainActivity, Observer {
                activityMainBinding?.activityMainTextViewItemsViewedTv?.text =
                    getString(R.string.view_port_viewed_items, it.toString())
            })
            onlyNewViewedItemsLiveData.observe(this@MainActivity, Observer {
                activityMainBinding?.activityMainTextViewNewViewedItems?.text =
                    getString(R.string.view_port_new_visible_items, it.toString())
            })
            lifecycleOwner = this@MainActivity
            threshold(Threshold.VISIBLE)
        }

        activityMainBinding?.activityMainButtonLinearLayout?.setOnClickListener {
            setRecyclerView(LINEAR_LAYOUT_MANAGER, getSelectedThreshold())
            activityMainBinding?.activityMainViewPortRecyclerView?.invalidate()
        }

        activityMainBinding?.activityMainButtonGridLayout?.setOnClickListener {
            setRecyclerView(GRID_LAYOUT_MANAGER, getSelectedThreshold())
            activityMainBinding?.activityMainViewPortRecyclerView?.invalidate()
        }

        activityMainBinding?.activityMainSpinnerThreshold?.run {
            this.adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                spinnerThresholdValues
            )
            onItemSelectedListener = this@MainActivity
        }
    }

    override fun onDestroy() {
        activityMainBinding = null
        super.onDestroy()
    }

    private fun setRecyclerView(
        layoutManagerOption: Int,
        threshold: Threshold
    ) {
        activityMainBinding?.activityMainViewPortRecyclerView?.apply {
            setHasFixedSize(true)
            threshold(threshold)
            layoutManager =
                if (layoutManagerOption == GRID_LAYOUT_MANAGER) GridLayoutManager(context, 4)
                else LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            adapter = ViewPortSampleAdapter((0..100).toList())
        }
    }

    private fun getSelectedThreshold(): Threshold {
        return activityMainBinding?.activityMainSpinnerThreshold?.selectedItemPosition?.let { Threshold.valueOf(spinnerThresholdValues[it]) }
            ?: Threshold.HALF
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        activityMainBinding?.activityMainViewPortRecyclerView?.threshold(Threshold.valueOf(spinnerThresholdValues[position]))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}
