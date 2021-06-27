package com.rsekar.base.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rsekar.base.model.Food
import com.rsekar.base.utils.EmptyStateType
import com.rsekar.base.viewModel.FoodViewModel
import com.rsekar.mvvm.R

class FoodListAdapter(private val listener : FoodListAdapterInterface) : BaseAdapter<MutableLiveData<FoodViewModel.FoodCellViewModel>>() {

    override fun getChildItemViewType(position: Int): Int = 100

    override fun getChildItemViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<MutableLiveData<FoodViewModel.FoodCellViewModel>> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_food_view, parent, false)
        return FoodViewHolder(view);
    }

    override fun getEmptyViewType(): EmptyStateType = EmptyStateType.NO_FOOD

    override fun onBindViewHolder(
        holder: ViewHolder<MutableLiveData<FoodViewModel.FoodCellViewModel>>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (holder is FoodViewHolder) {
            holder.itemView.setOnClickListener {
                items[position].value?.let { model ->
                    listener.didFavoriteChange(model, !(model.favorite))
                }
            }
        }

    }

    public fun update(items : ArrayList<MutableLiveData<FoodViewModel.FoodCellViewModel>>) {
        this.items = items
        notifyDataSetChanged()
    }


    class FoodViewHolder(view : View) : ViewHolder<MutableLiveData<FoodViewModel.FoodCellViewModel>>(view) {

        private val dataObserver = Observer<FoodViewModel.FoodCellViewModel>() {
            it.let { value ->
                if (value != null) {
                    configure(value)
                }
            }
        }

        private var model :  MutableLiveData<FoodViewModel.FoodCellViewModel>? = null

        override fun bind(model: MutableLiveData<FoodViewModel.FoodCellViewModel>) {
            this.model?.removeObserver(dataObserver)
            this.model = model
            model.observeForever(dataObserver)

            model.value.let {
                if (it != null) {
                    configure(it)
                }
            }
        }

        private fun configure(model : FoodViewModel.FoodCellViewModel) {
            itemView.findViewById<TextView>(R.id.title).text = model.title
            val icon = if (model.favorite) android.R.drawable.star_big_on else android.R.drawable.star_big_off
            itemView.findViewById<ImageView>(R.id.icon).setImageResource(icon)
        }

        override fun unBind() {
            this.model?.removeObserver(dataObserver)
        }
    }


    interface FoodListAdapterInterface {
        fun didFavoriteChange(model : FoodViewModel.FoodCellViewModel,favorite : Boolean)
    }
}