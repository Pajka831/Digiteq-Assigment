package com.maciejpaja.digiteqassigment.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class ConfigurableLayoutManager(private val numberOfRows: Int, private val numberOfColumns: Int) :
    LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        fill(recycler)
    }

    private fun fill(recycler: RecyclerView.Recycler) {
        if (itemCount == 0) return
        detachAndScrapAttachedViews(recycler)

        var currentRow = 0
        var currentColumn = 0

        for (position in 0 until itemCount) {
            val view = recycler.getViewForPosition(position)
            addView(view)

            val itemWidth = width / numberOfColumns
            val itemHeight = height / numberOfRows

            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = itemWidth
            layoutParams.height = itemHeight
            view.layoutParams = layoutParams


            var left = itemWidth * currentColumn
            var right = left + itemWidth
            var top = if (currentRow == 0) 0 else itemHeight * currentRow
            var bottom = top + (if (currentRow == 0) itemHeight else itemHeight * currentRow)
            currentColumn++

            if ((position + 1).rem(numberOfColumns) == 0 && position != 0) {
                currentRow++
                currentColumn = 0
            }


            measureChild(view, width, width)
            layoutDecorated(view, left, top, right, bottom)
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

}