package com.example.kinopedia.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(private val offsetLeft: Int, private val offsetRight: Int, private val offsetTop: Int, private val offsetBottom: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = offsetLeft
        outRect.right = offsetRight
        outRect.top = offsetTop
        outRect.bottom = offsetBottom
    }
}