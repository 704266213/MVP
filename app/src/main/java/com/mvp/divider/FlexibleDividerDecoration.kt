package com.mvp.divider

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *
 */
abstract class FlexibleDividerDecoration protected constructor(builder: Builder<*>) : RecyclerView.ItemDecoration() {

    protected enum class DividerType {
        DRAWABLE, PAINT, COLOR
    }

    protected var mDividerType = DividerType.DRAWABLE
    private var mVisibilityProvider: VisibilityProvider
    protected var mPaintProvider: PaintProvider? = null
    private var mColorProvider: ColorProvider? = null
    protected var mDrawableProvider: DrawableProvider? = null
    protected var mSizeProvider: SizeProvider? = null
    private var mShowLastDivider: Boolean = false
    protected var mPositionInsideItem: Boolean = false
    private var mPaint: Paint? = null

    init {
        when {
            builder.mPaintProvider != null -> {
                mDividerType = DividerType.PAINT
                mPaintProvider = builder.mPaintProvider
            }
            builder.mColorProvider != null -> {
                mDividerType = DividerType.COLOR
                mColorProvider = builder.mColorProvider
                mPaint = Paint()
                setSizeProvider(builder)
            }
            else -> {
                mDividerType = DividerType.DRAWABLE
                mDrawableProvider = if (builder.mDrawableProvider == null) {
                    val a = builder.mContext.obtainStyledAttributes(ATTRS)
                    val divider = a.getDrawable(0)
                    a.recycle()
                    object : DrawableProvider {
                        override fun drawableProvider(position: Int, parent: RecyclerView): Drawable {
                            return divider
                        }
                    }
                } else {
                    builder.mDrawableProvider
                }
                mSizeProvider = builder.mSizeProvider
            }
        }

        mVisibilityProvider = builder.mVisibilityProvider
        mShowLastDivider = builder.mShowLastDivider
        mPositionInsideItem = builder.mPositionInsideItem
    }

    private fun setSizeProvider(builder: Builder<*>) {
        mSizeProvider = builder.mSizeProvider
        if (mSizeProvider == null) {
            mSizeProvider = object : SizeProvider {
                override fun dividerSize(position: Int, parent: RecyclerView): Int = DEFAULT_SIZE
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val adapter = parent.adapter ?: return

        val itemCount = adapter.itemCount
        val lastDividerOffset = getLastDividerOffset(parent)
        val validChildCount = parent.childCount
        var lastChildPosition = -1
        for (i in 0 until validChildCount) {
            val child = parent.getChildAt(i)
            val childPosition = parent.getChildAdapterPosition(child)

            if (childPosition < lastChildPosition) {
                // Avoid remaining divider when animation starts
                continue
            }
            lastChildPosition = childPosition

            if (!mShowLastDivider && childPosition >= itemCount - lastDividerOffset) {
                // Don't draw divider for last line if mShowLastDivider = false
                continue
            }

            if (wasDividerAlreadyDrawn(childPosition, parent)) {
                // No need to draw divider again as it was drawn already by previous column
                continue
            }

            val groupIndex = getGroupIndex(childPosition, parent)
            if (mVisibilityProvider.shouldHideDivider(groupIndex, parent)) {
                continue
            }

            val bounds = getDividerBound(groupIndex, parent, child)
            when (mDividerType) {
                FlexibleDividerDecoration.DividerType.DRAWABLE -> {
                    val drawable = mDrawableProvider!!.drawableProvider(groupIndex, parent)
                    drawable.bounds = bounds
                    drawable.draw(c)
                }
                FlexibleDividerDecoration.DividerType.PAINT -> {
                    mPaint = mPaintProvider!!.dividerPaint(groupIndex, parent)
                    c.drawLine(bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), mPaint!!)
                }
                FlexibleDividerDecoration.DividerType.COLOR -> {
                    mPaint!!.color = mColorProvider!!.dividerColor(groupIndex, parent)
                    mPaint!!.strokeWidth = mSizeProvider!!.dividerSize(groupIndex, parent).toFloat()
                    c.drawLine(bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), mPaint!!)
                }
            }
        }
    }

    override fun getItemOffsets(rect: Rect, v: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(v)
        val itemCount = parent.adapter.itemCount
        val lastDividerOffset = getLastDividerOffset(parent)
        if (!mShowLastDivider && position >= itemCount - lastDividerOffset) {
            // Don't set item offset for last line if mShowLastDivider = false
            return
        }

        val groupIndex = getGroupIndex(position, parent)
        if (mVisibilityProvider.shouldHideDivider(groupIndex, parent)) {
            return
        }

        setItemOffsets(rect, groupIndex, parent)
    }

    /**
     * Check if recyclerview is reverse layout
     *
     * @param parent RecyclerView
     * @return true if recyclerview is reverse layout
     */
    protected fun isReverseLayout(parent: RecyclerView): Boolean {
        val layoutManager = parent.layoutManager
        return (layoutManager as? LinearLayoutManager)?.reverseLayout == true
    }

    /**
     * In the case mShowLastDivider = false,
     * Returns offset for how many views we don't have to draw a divider for,
     * for LinearLayoutManager it is as simple as not drawing the last child divider,
     * but for a GridLayoutManager it needs to take the span count for the last items into account
     * until we use the span count configured for the grid.
     *
     * @param parent RecyclerView
     * @return offset for how many views we don't have to draw a divider or 1 if its a
     * LinearLayoutManager
     */
    private fun getLastDividerOffset(parent: RecyclerView): Int {
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanSizeLookup = layoutManager.spanSizeLookup
            val spanCount = layoutManager.spanCount
            val itemCount = parent.adapter.itemCount
            (itemCount - 1 downTo 0)
                    .filter { spanSizeLookup.getSpanIndex(it, spanCount) == 0 }
                    .forEach { return itemCount - it }
        }

        return 1
    }

    /**
     * Determines whether divider was already drawn for the row the item is in,
     * effectively only makes sense for a grid
     *
     * @param position current view position to draw divider
     * @param parent   RecyclerView
     * @return true if the divider can be skipped as it is in the same row as the previous one.
     */
    private fun wasDividerAlreadyDrawn(position: Int, parent: RecyclerView): Boolean {
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanSizeLookup = layoutManager.spanSizeLookup
            val spanCount = layoutManager.spanCount
            return spanSizeLookup.getSpanIndex(position, spanCount) > 0
        }

        return false
    }

    /**
     * Returns a group index for GridLayoutManager.
     * for LinearLayoutManager, always returns position.
     *
     * @param position current view position to draw divider
     * @param parent   RecyclerView
     * @return group index of items
     */
    private fun getGroupIndex(position: Int, parent: RecyclerView): Int {
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanSizeLookup = layoutManager.spanSizeLookup
            val spanCount = layoutManager.spanCount
            return spanSizeLookup.getSpanGroupIndex(position, spanCount)
        }

        return position
    }

    protected abstract fun getDividerBound(position: Int, parent: RecyclerView, child: View): Rect

    protected abstract fun setItemOffsets(outRect: Rect, position: Int, parent: RecyclerView)

    /**
     * Interface for controlling divider visibility
     */
    interface VisibilityProvider {

        /**
         * Returns true if divider should be hidden.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return True if the divider at position should be hidden
         */
        fun shouldHideDivider(position: Int, parent: RecyclerView): Boolean
    }

    /**
     * Interface for controlling paint instance for divider drawing
     */
    interface PaintProvider {

        /**
         * Returns [Paint] for divider
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Paint instance
         */
        fun dividerPaint(position: Int, parent: RecyclerView): Paint
    }

    /**
     * Interface for controlling divider color
     */
    interface ColorProvider {

        /**
         * Returns [android.graphics.Color] value of divider
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Color value
         */
        fun dividerColor(position: Int, parent: RecyclerView): Int
    }

    /**
     * Interface for controlling drawable object for divider drawing
     */
    interface DrawableProvider {

        /**
         * Returns drawable instance for divider
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Drawable instance
         */
        fun drawableProvider(position: Int, parent: RecyclerView): Drawable
    }

    /**
     * Interface for controlling divider size
     */
    interface SizeProvider {

        /**
         * Returns size value of divider.
         * Height for horizontal divider, width for vertical divider
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Size of divider
         */
        fun dividerSize(position: Int, parent: RecyclerView): Int
    }

    open class Builder<T : Builder<T>>(internal val mContext: Context) {
        internal var mResources: Resources = mContext.resources
        internal var mPaintProvider: PaintProvider? = null
        internal var mColorProvider: ColorProvider? = null
        internal var mDrawableProvider: DrawableProvider? = null
        internal var mSizeProvider: SizeProvider? = null
        internal var mVisibilityProvider: VisibilityProvider = object : VisibilityProvider {
            override fun shouldHideDivider(position: Int, parent: RecyclerView): Boolean = false
        }
        internal var mShowLastDivider = false
        internal var mPositionInsideItem = false

        fun paint(paint: Paint): T = paintProvider(object : PaintProvider {
            override fun dividerPaint(position: Int, parent: RecyclerView): Paint {
                return paint
            }
        })

        fun paintProvider(provider: PaintProvider): T {
            mPaintProvider = provider
            return this as T
        }

        fun color(color: Int): T = colorProvider(object : ColorProvider {
            override fun dividerColor(position: Int, parent: RecyclerView): Int {
                return color
            }
        })

        fun colorResId(@ColorRes colorId: Int): T = color(ContextCompat.getColor(mContext, colorId))

        fun colorProvider(provider: ColorProvider): T {
            mColorProvider = provider
            return this as T
        }

        fun drawable(@DrawableRes id: Int): T = drawable(ContextCompat.getDrawable(mContext, id)!!)

        fun drawable(drawable: Drawable): T = drawableProvider(object : DrawableProvider {
            override fun drawableProvider(position: Int, parent: RecyclerView): Drawable {
                return drawable
            }
        })

        fun drawableProvider(provider: DrawableProvider): T {
            mDrawableProvider = provider
            return this as T
        }

        fun size(size: Int): T = sizeProvider(object : SizeProvider {
            override fun dividerSize(position: Int, parent: RecyclerView): Int {
                return size
            }
        })

        fun sizeResId(@DimenRes sizeId: Int): T = size(mResources.getDimensionPixelSize(sizeId))

        fun sizeProvider(provider: SizeProvider): T {
            mSizeProvider = provider
            return this as T
        }

        fun visibilityProvider(provider: VisibilityProvider): T {
            mVisibilityProvider = provider
            return this as T
        }

        fun showLastDivider(): T {
            mShowLastDivider = true
            return this as T
        }

        fun positionInsideItem(positionInsideItem: Boolean): T {
            mPositionInsideItem = positionInsideItem
            return this as T
        }

        protected fun checkBuilderParams() {
            if (mPaintProvider != null) {
                if (mColorProvider != null) {
                    throw IllegalArgumentException(
                            "Use setColor method of Paint class to specify line color. Do not provider ColorProvider if you set PaintProvider.")
                }
                if (mSizeProvider != null) {
                    throw IllegalArgumentException(
                            "Use setStrokeWidth method of Paint class to specify line size. Do not provider SizeProvider if you set PaintProvider.")
                }
            }
        }
    }

    companion object {

        private val DEFAULT_SIZE = 2
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}