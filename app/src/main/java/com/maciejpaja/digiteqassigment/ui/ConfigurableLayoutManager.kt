package com.maciejpaja.digiteqassigment.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.math.abs


class ConfigurableLayoutManager(private val numberOfRows: Int, private val numberOfColumns: Int) :
    LayoutManager() {

    private var orientationHelperForHorizontal: OrientationHelper? = null
    private var offset = 0
    private var lastItemEnd = 0

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

        var currentRow = 1
        var currentColumn = 1
        var screenCount = 1

        for (position in 0 until itemCount) {
            val view = recycler.getViewForPosition(position)
            addView(view)

            val itemWidth = width / numberOfColumns
            val itemHeight = height / numberOfRows

            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = itemWidth
            layoutParams.height = itemHeight
            view.layoutParams = layoutParams

            if (position == numberOfColumns * numberOfRows * screenCount) {
                screenCount++
                currentRow = 1
                currentColumn = 1
            }

            val left = itemWidth * (currentColumn - 1) + width * (screenCount -1)
            val right = left + itemWidth
            val top = if (currentRow == 1) 0 else itemHeight * (currentRow -1)
            val bottom = top + (if (currentRow == 0) itemHeight else itemHeight * currentRow)
            currentColumn++

            if ((position + 1).rem(numberOfColumns) == 0 && position != 0) {
                currentRow++
                currentColumn = 1
            }

            measureChild(view, width, width)
            layoutDecorated(view, left, top, right, bottom)

            val lastItem = getChildAt(itemCount - 1)
            lastItemEnd = lastItem?.run {
                getDecoratedRight(lastItem) + (lastItem.layoutParams as ViewGroup.MarginLayoutParams).rightMargin
            } ?: 0
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (childCount == 0 || dx == 0) {
            return 0
        }

        ensureLayoutState()
        val scrollDirection = if (dx > 0) 1 else -1
        val absoluteDx = abs(dx.toDouble()).toInt()
        val horizontalOffset = calculateHorizontalOffsetForScroll(scrollDirection)
        if (horizontalOffset < 0) {
            return 0
        }

        val scrolled = if (absoluteDx > horizontalOffset) scrollDirection * horizontalOffset else dx
        orientationHelperForHorizontal?.offsetChildren(-scrolled)
        return scrolled
    }

    private fun calculateHorizontalOffsetForScroll(layoutDirection: Int) =
        if (layoutDirection == 1) {
            lastItemEnd + offset - width
        } else {
            -offset
        }

    override fun offsetChildrenHorizontal(dx: Int) {
        super.offsetChildrenHorizontal(dx)
        offset += dx
    }

    private fun ensureLayoutState() {
        if (orientationHelperForHorizontal == null) {
            orientationHelperForHorizontal =
                OrientationHelper.createOrientationHelper(this, RecyclerView.HORIZONTAL)
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

}