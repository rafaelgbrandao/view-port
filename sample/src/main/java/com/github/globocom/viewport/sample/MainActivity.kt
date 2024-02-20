package com.github.globocom.viewport.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.globocom.viewport.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding
            .inflate(layoutInflater)
            .also { activityMainBinding = it }
            .root
        )
        activityMainBinding?.activityMainButtonMobileViewPortRecyclerView?.setOnClickListener(this)
        activityMainBinding?.activityMainButtonTvDpadRecyclerView?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            activityMainBinding?.activityMainButtonMobileViewPortRecyclerView -> {
                startIntent(ActivityViewPortRecyclerView::class.java)
            }

            activityMainBinding?.activityMainButtonTvDpadRecyclerView -> {
                startIntent(ActivityDpadRecyclerViewTv::class.java)
            }

            else -> { }
        }
    }

    private fun <E> startIntent(javaClass: Class<E>) {
        startActivity(Intent(this, javaClass))
    }
}
