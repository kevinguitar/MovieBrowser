package com.kevingt.moviebrowser.custom

import android.view.View
import androidx.viewpager.widget.ViewPager

class AlphaScalePageTransformer : ViewPager.PageTransformer {

    companion object {
        const val SCALE_MAX = 0.8f
        const val ALPHA_MAX = 0.5f
    }

    override fun transformPage(page: View, position: Float) {
        val scale =
            if (position < 0)
                ((1 - SCALE_MAX) * position) + 1
            else
                ((SCALE_MAX - 1) * position) + 1
        val alpha =
            if (position < 0)
                ((1 - ALPHA_MAX) * position) + 1
            else
                ((ALPHA_MAX - 1) * position) + 1

        page.pivotX = if (position < 0) page.width.toFloat() else 0f
        page.pivotY = page.height.toFloat() / 2

        page.scaleX = scale
        page.scaleY = scale
        page.alpha = Math.abs(alpha)
    }

}