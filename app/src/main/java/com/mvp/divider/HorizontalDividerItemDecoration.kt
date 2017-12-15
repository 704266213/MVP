package com.happy.food.divider

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 项目名称：HappyFood
 * 类描述：RecyclerView 水平的分割线的绘制
 * 创建人：admin
 * 创建时间：2017/8/28 9:52
 * 修改人：admin
 * 修改时间：2017/8/28 9:52
 * 修改备注：
 * @version
 */
class HorizontalDividerItemDecoration constructor(builder: Builder) : FlexibleDividerDecoration(builder) {

    private val mMarginProvider: MarginProvider

    init {
        mMarginProvider = builder.mMarginProvider
    }

    override fun getDividerBound(position: Int, parent: RecyclerView, child: View): Rect {
        val bounds = Rect(0, 0, 0, 0)
        val transitionX = ViewCompat.getTranslationX(child).toInt()
        val transitionY = ViewCompat.getTranslationY(child).toInt()
        val params = child.layoutParams as RecyclerView.LayoutParams
        bounds.left = parent.paddingLeft +
                mMarginProvider.dividerLeftMargin(position, parent) + transitionX
        bounds.right = parent.width - parent.paddingRight -
                mMarginProvider.dividerRightMargin(position, parent) + transitionX

        val dividerSize = getDividerSize(position, parent)
        val isReverseLayout = isReverseLayout(parent)
        if (mDividerType == FlexibleDividerDecoration.DividerType.DRAWABLE) {
            // set top and bottom position of divider
            if (isReverseLayout) {
                bounds.bottom = child.top - params.topMargin + transitionY
                bounds.top = bounds.bottom - dividerSize
            } else {
                bounds.top = child.bottom + params.bottomMargin + transitionY
                bounds.bottom = bounds.top + dividerSize
            }
        } else {
            // set center point of divider
            val halfSize = dividerSize / 2
            if (isReverseLayout) {
                bounds.top = child.top - params.topMargin - halfSize + transitionY
            } else {
                bounds.top = child.bottom + params.bottomMargin + halfSize + transitionY
            }
            bounds.bottom = bounds.top
        }

        if (mPositionInsideItem) {
            if (isReverseLayout) {
                bounds.top += dividerSize
                bounds.bottom += dividerSize
            } else {
                bounds.top -= dividerSize
                bounds.bottom -= dividerSize
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
            outRect.set(0, getDividerSize(position, parent), 0, 0)
        } else {
            outRect.set(0, 0, 0, getDividerSize(position, parent))
        }
    }

    private fun getDividerSize(position: Int, parent: RecyclerView): Int {
        when {
            mPaintProvider != null -> return mPaintProvider!!.dividerPaint(position, parent).strokeWidth.toInt()
            mSizeProvider != null -> return mSizeProvider!!.dividerSize(position, parent)
            mDrawableProvider != null -> {
                val drawable = mDrawableProvider!!.drawableProvider(position, parent)
                return drawable.intrinsicHeight
            }
            else -> throw RuntimeException("failed to get size")
        }
    }

    /**
     * Interface for controlling divider margin
     */
    interface MarginProvider {

        /**
         * Returns left margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return left margin
         */
        fun dividerLeftMargin(position: Int, parent: RecyclerView): Int

        /**
         * Returns right margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return right margin
         */
        fun dividerRightMargin(position: Int, parent: RecyclerView): Int
    }

    class Builder(context: Context) : FlexibleDividerDecoration.Builder<Builder>(context) {

        internal var mMarginProvider: MarginProvider = object : MarginProvider {
            override fun dividerLeftMargin(position: Int, parent: RecyclerView): Int = 0

            override fun dividerRightMargin(position: Int, parent: RecyclerView): Int = 0
        }

        fun margin(leftMargin: Int, rightMargin: Int): Builder =
                marginProvider(object : MarginProvider {
                    override fun dividerLeftMargin(position: Int, parent: RecyclerView): Int {
                        return leftMargin
                    }

                    override fun dividerRightMargin(position: Int, parent: RecyclerView): Int {
                        return rightMargin
                    }
                })

        fun margin(horizontalMargin: Int): Builder = margin(horizontalMargin, horizontalMargin)

        fun marginResId(@DimenRes leftMarginId: Int, @DimenRes rightMarginId: Int): Builder =
                margin(mResources.getDimensionPixelSize(leftMarginId),
                        mResources.getDimensionPixelSize(rightMarginId))

        fun marginResId(@DimenRes horizontalMarginId: Int): Builder =
                marginResId(horizontalMarginId, horizontalMarginId)

        fun marginProvider(provider: MarginProvider): Builder {
            mMarginProvider = provider
            return this
        }

        fun build(): HorizontalDividerItemDecoration {
            checkBuilderParams()
            return HorizontalDividerItemDecoration(this)
        }
    }
}