package com.mvp.divider

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *
 */
class VerticalDividerItemDecoration private constructor(builder: Builder) : FlexibleDividerDecoration(builder) {

    private val mMarginProvider: MarginProvider

    init {
        mMarginProvider = builder.mMarginProvider
    }

    override fun getDividerBound(position: Int, parent: RecyclerView, child: View): Rect {
        val bounds = Rect(0, 0, 0, 0)
        val transitionX = ViewCompat.getTranslationX(child).toInt()
        val transitionY = ViewCompat.getTranslationY(child).toInt()
        val params = child.layoutParams as RecyclerView.LayoutParams
        bounds.top = parent.paddingTop +
                mMarginProvider.dividerTopMargin(position, parent) + transitionY
        bounds.bottom = parent.height - parent.paddingBottom -
                mMarginProvider.dividerBottomMargin(position, parent) + transitionY

        val dividerSize = getDividerSize(position, parent)
        val isReverseLayout = isReverseLayout(parent)
        if (mDividerType == FlexibleDividerDecoration.DividerType.DRAWABLE) {
            // set left and right position of divider
            if (isReverseLayout) {
                bounds.right = child.left - params.leftMargin + transitionX
                bounds.left = bounds.right - dividerSize
            } else {
                bounds.left = child.right + params.rightMargin + transitionX
                bounds.right = bounds.left + dividerSize
            }
        } else {
            // set center point of divider
            val halfSize = dividerSize / 2
            if (isReverseLayout) {
                bounds.left = child.left - params.leftMargin - halfSize + transitionX
            } else {
                bounds.left = child.right + params.rightMargin + halfSize + transitionX
            }
            bounds.right = bounds.left
        }

        if (mPositionInsideItem) {
            if (isReverseLayout) {
                bounds.left += dividerSize
                bounds.right += dividerSize
            } else {
                bounds.left -= dividerSize
                bounds.right -= dividerSize
            }
        }

        return bounds
    }

    override fun setItemOffsets(outRect: Rect, position: Int, parent: RecyclerView) {
        if (mPositionInsideItem) {
            outRect.set(0, 0, 0, 0)
            return
        }

        if (isReverseLayout(parent)) {
            outRect.set(getDividerSize(position, parent), 0, 0, 0)
        } else {
            outRect.set(0, 0, getDividerSize(position, parent), 0)
        }
    }

    private fun getDividerSize(position: Int, parent: RecyclerView): Int {
        when {
            mPaintProvider != null -> return mPaintProvider!!.dividerPaint(position, parent).strokeWidth.toInt()
            mSizeProvider != null -> return mSizeProvider!!.dividerSize(position, parent)
            mDrawableProvider != null -> {
                val drawable = mDrawableProvider!!.drawableProvider(position, parent)
                return drawable.intrinsicWidth
            }
            else -> throw RuntimeException("failed to get size")
        }
    }

    /**
     * Interface for controlling divider margin
     */
    interface MarginProvider {

        /**
         * Returns top margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return top margin
         */
        fun dividerTopMargin(position: Int, parent: RecyclerView): Int

        /**
         * Returns bottom margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return bottom margin
         */
        fun dividerBottomMargin(position: Int, parent: RecyclerView): Int
    }

    class Builder(context: Context) : FlexibleDividerDecoration.Builder<Builder>(context) {

        internal var mMarginProvider: MarginProvider = object : MarginProvider {
            override fun dividerTopMargin(position: Int, parent: RecyclerView): Int = 0

            override fun dividerBottomMargin(position: Int, parent: RecyclerView): Int = 0
        }

        fun margin(topMargin: Int, bottomMargin: Int): Builder =
                marginProvider(object : MarginProvider {
                    override fun dividerTopMargin(position: Int, parent: RecyclerView): Int {
                        return topMargin
                    }

                    override fun dividerBottomMargin(position: Int, parent: RecyclerView): Int {
                        return bottomMargin
                    }
                })

        fun margin(verticalMargin: Int): Builder = margin(verticalMargin, verticalMargin)

        fun marginResId(@DimenRes topMarginId: Int, @DimenRes bottomMarginId: Int): Builder =
                margin(mResources.getDimensionPixelSize(topMarginId),
                        mResources.getDimensionPixelSize(bottomMarginId))

        fun marginResId(@DimenRes verticalMarginId: Int): Builder =
                marginResId(verticalMarginId, verticalMarginId)

        fun marginProvider(provider: MarginProvider): Builder {
            mMarginProvider = provider
            return this
        }

        fun build(): VerticalDividerItemDecoration {
            checkBuilderParams()
            return VerticalDividerItemDecoration(this)
        }
    }
}