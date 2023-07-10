package com.example.dairy.presentation.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorator(
    context: Context,
    spacing: Float = 4f
) : RecyclerView.ItemDecoration() {

    private val spacingPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacing,
        context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacingMiddle = spacingPx / 2
        val viewHolder = parent.getChildViewHolder(view)


        val currentPosition = parent.getChildAdapterPosition(view).takeIf {
            it != RecyclerView.NO_POSITION
        } ?: viewHolder.oldPosition

        when (currentPosition) {
            0 -> {
                outRect.left = 0
                outRect.right = spacingMiddle
            }
            state.itemCount - 1 -> {
                outRect.left = spacingMiddle
                outRect.right = 0
            }
            else -> {
                outRect.left = spacingMiddle
                outRect.right = spacingMiddle
            }
        }
    }
}