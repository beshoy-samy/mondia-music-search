package com.bsamy.musix.utils.recycler

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(context: Context, @DimenRes val dimen: Int) :
    RecyclerView.ItemDecoration() {

    private val bottomSize = context.resources.getDimension(dimen).toInt()


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = bottomSize
    }
}