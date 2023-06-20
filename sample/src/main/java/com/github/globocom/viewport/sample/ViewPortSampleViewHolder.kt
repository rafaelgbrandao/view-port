package com.github.globocom.viewport.sample

import androidx.recyclerview.widget.RecyclerView
import com.github.globocom.viewport.sample.databinding.ViewHolderViewPortSampleBinding

class ViewPortSampleViewHolder(binding: ViewHolderViewPortSampleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val headline = binding.viewHolderViewPortTextViewHeadline

    fun bind() {
        with(headline) {
            text = context.getString(R.string.view_port_title_with_position, bindingAdapterPosition)
        }
    }
}
