package com.rsekar.base.utils.extension

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rsekar.base.model.Food
import com.rsekar.base.utils.states.LoaderState
import com.rsekar.base.view.adapter.BaseAdapter
import com.rsekar.base.view.appViews.SwipeRefreshRecyclerView

fun RecyclerView.addScrollListener(onReachEnd: () -> Unit) {

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {

            with(layoutManager as LinearLayoutManager) {

                val totalItemCount = itemCount
                val lastVisibleItemPosition = findLastVisibleItemPosition()

                ///if user seeing the last or last before element and there is no loading then fire for the next
                ///page request
                if (totalItemCount - lastVisibleItemPosition <= 1) {
                    onReachEnd()
                }

            }
        }
    })
}