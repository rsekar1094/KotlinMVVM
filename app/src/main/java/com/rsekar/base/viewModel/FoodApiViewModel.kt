package com.rsekar.base.viewModel

import android.os.Looper
import android.util.Log
import com.rsekar.base.model.Food
import com.rsekar.base.utils.states.DataRequestState
import com.rsekar.base.utils.states.LoaderState
import com.rsekar.base.viewModel.Base.BaseSingleListViewModel
import java.util.*
import kotlin.collections.ArrayList

class FoodApiViewModel() : BaseSingleListViewModel<Food>() {

    init {
        paginationState.isPaginatedSupport = true
    }

    override fun fetchDataForCurrentState() {
        dataRequestState = DataRequestState.WAITING_FOR_RESPONSE

        loaderState.value = if (paginationState.isPaginating)  LoaderState.BOTTOM_PROGRESS_ON else LoaderState.TOP_PROGRESS_ON

        android.os.Handler(Looper.getMainLooper()).postDelayed({

            if (paginationState.isPaginating) {
                val count = items.value!!.size
                var newItems = items.value ?: ArrayList<Food>();
                newItems.add(Food(UUID.randomUUID().toString(), ""+(count+1), false))
                newItems.add(Food(UUID.randomUUID().toString(), ""+(count+2), false))
                newItems.add(Food(UUID.randomUUID().toString(), ""+(count+3), false))
                newItems.add(Food(UUID.randomUUID().toString(), ""+(count+4), false))

                items.value = newItems;
            } else {
                var item = ArrayList<Food>()
                item.add(Food(UUID.randomUUID().toString(), "0", false))
                item.add(Food(UUID.randomUUID().toString(), "1", false))
                item.add(Food(UUID.randomUUID().toString(), "2", false))
                item.add(Food(UUID.randomUUID().toString(), "3", false))
                item.add(Food(UUID.randomUUID().toString(), "4", false))
                item.add(Food(UUID.randomUUID().toString(), "5", false))
                item.add(Food(UUID.randomUUID().toString(), "6", false))
                item.add(Food(UUID.randomUUID().toString(), "7", false))
                item.add(Food(UUID.randomUUID().toString(), "8", false))
                item.add(Food(UUID.randomUUID().toString(), "9", false))
                item.add(Food(UUID.randomUUID().toString(), "10", false))
                item.add(Food(UUID.randomUUID().toString(), "11", false))
                item.add(Food(UUID.randomUUID().toString(), "12", false))
                item.add(Food(UUID.randomUUID().toString(), "13", false))
                item.add(Food(UUID.randomUUID().toString(), "14", false))
                item.add(Food(UUID.randomUUID().toString(), "15", false))
                item.add(Food(UUID.randomUUID().toString(), "16", false))
                item.add(Food(UUID.randomUUID().toString(), "17", false))
                item.add(Food(UUID.randomUUID().toString(), "18", false))
                item.add(Food(UUID.randomUUID().toString(), "19", false))
                item.add(Food(UUID.randomUUID().toString(), "20", false))
                item.add(Food(UUID.randomUUID().toString(), "21", false))
                item.add(Food(UUID.randomUUID().toString(), "22", false))
                item.add(Food(UUID.randomUUID().toString(), "23", false))
                item.add(Food(UUID.randomUUID().toString(), "24", false))
                item.add(Food(UUID.randomUUID().toString(), "25", false))
                item.add(Food(UUID.randomUUID().toString(), "26", false))
                item.add(Food(UUID.randomUUID().toString(), "27", false))
                item.add(Food(UUID.randomUUID().toString(), "28", false))

                items.value = item;
            }

            paginationState.hasNextPage = paginationState.currentPageNumber <= 10

            paginationState.isPaginating = false
            dataRequestState = DataRequestState.DATA_RECEIVED;
            loaderState.value = LoaderState.NONE

        }, 1500)

    }
}