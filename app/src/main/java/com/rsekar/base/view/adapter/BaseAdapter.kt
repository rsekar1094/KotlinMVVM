package com.rsekar.base.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.rsekar.base.utils.EmptyStateType
import com.rsekar.mvvm.R

abstract class BaseAdapter<T> :
        RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>()  {

    companion object {
        private const val EMPTY_VIEW_TYPE = 800
        private const val PAGINATION_INDICATOR_VIEW_TYPE = 801
    }

    protected var items = ArrayList<T>();
    private var isPaginationIndicatorVisible : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return when (viewType) {
            EMPTY_VIEW_TYPE -> {
                val emptyView = LayoutInflater.from(parent.context).inflate(getEmptyViewResourceId() ?: R.layout.view_default_empty_state, parent, false)
                EmptyStateViewHolder(emptyView ?: View(parent.context))
            }

            PAGINATION_INDICATOR_VIEW_TYPE -> {
                val progressBar = ProgressBar(parent.context);
                progressBar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                progressBar.setPadding(16)
                PaginationIndicatorHolder(progressBar)
            }

            else -> {
                getChildItemViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val totalItemSize = items.size
        if (totalItemSize > position) {
            holder.bind(items[position])
        } else if (holder is EmptyStateViewHolder<T>) {
           getEmptyViewType().let {
               if (it != null) {
                   holder.configure(it)
               }
           }
        }
    }

    override fun onViewRecycled(holder: ViewHolder<T>) {
        super.onViewRecycled(holder)

        holder.unBind()
    }

    override fun getItemCount(): Int {
        var count = items.size

        if (isPaginationIndicatorVisible) {
            count += 1
        } else if (getEmptyViewType() != null && count == 0) {
            count += 1
        }

        return count
    }

    override fun getItemViewType(position: Int): Int {
        return if (getEmptyViewType() != null && items.size == 0) {
            EMPTY_VIEW_TYPE
        } else if (isPaginationIndicatorVisible && position == itemCount - 1) {
            PAGINATION_INDICATOR_VIEW_TYPE
        } else {
            getChildItemViewType(position)
        }
    }


    protected fun getEmptyViewResourceId() : Int? {
        return null
    }

    public fun setIsPaginationIndicatorVisible(visible : Boolean) {
        Log.d("BaseAdapter", "setIsPaginationIndicatorVisible $visible")

        val oldValue = isPaginationIndicatorVisible;
        isPaginationIndicatorVisible = visible;

        if (oldValue != visible) {
            val indicatorIndex = items.size
            if (visible) {
                notifyItemInserted(indicatorIndex)
            } else {
                notifyItemRemoved(indicatorIndex)
            }
        }
    }



    abstract fun getChildItemViewType(position: Int): Int

    abstract fun getChildItemViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T>

    abstract fun getEmptyViewType(): EmptyStateType?


    abstract class ViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(model : T)
        abstract fun unBind()
    }

    open class EmptyStateViewHolder<T>(view : View) : ViewHolder<T>(view) {
        fun configure(emptyState : EmptyStateType) {
            itemView.findViewById<TextView>(R.id.title).text = emptyState.getInfo().title
            itemView.findViewById<TextView>(R.id.description).text = emptyState.getInfo().description
            itemView.findViewById<ImageView>(R.id.icon).setImageResource(emptyState.getInfo().icon)
        }

        override fun bind(model: T) {

        }

        override fun unBind() {

        }
    }

    open class PaginationIndicatorHolder<T>(view : View) : ViewHolder<T>(view) {
        override fun bind(model: T) {

        }
        override fun unBind() {

        }
    }
}