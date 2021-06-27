package com.rsekar.base.view.appViews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshRecyclerView(context: Context, attributeSet: AttributeSet) : SwipeRefreshLayout(context,attributeSet)  {

    public val recyclerView : RecyclerView = RecyclerView(context);

    init {
        recyclerView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(recyclerView)
    }
}