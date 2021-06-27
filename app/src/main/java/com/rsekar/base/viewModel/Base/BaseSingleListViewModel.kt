package com.rsekar.base.viewModel.Base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rsekar.base.model.pagination.PaginationState
import com.rsekar.base.utils.states.DataRequestState
import com.rsekar.base.utils.states.LoaderState

abstract class BaseSingleListViewModel<T> : ViewModel() {

    companion object {
        private const val TAG : String = "BaseSingleListViewModel"
    }

    protected var dataRequestState : DataRequestState  = DataRequestState.IDLE

    protected var loaderState : MutableLiveData<LoaderState>  = MutableLiveData<LoaderState>(
        LoaderState.NONE);


    protected var items : MutableLiveData<ArrayList<T>> = MutableLiveData<ArrayList<T>>()

    protected val paginationState : PaginationState = PaginationState()

    @Suppress("UNCHECKED_CAST")
    public fun getItems() : LiveData<ArrayList<T>> = items as LiveData<ArrayList<T>>

    public fun getLoaderState() = loaderState as LiveData<LoaderState>

    public fun requestNextPageData() {
        if (this.paginationState.isPaginating) {
            return
        }

        if (paginationState.isPaginatedSupport && paginationState.hasNextPage) {
            paginationState.currentPageNumber += 1
            paginationState.isPaginating = true
            Log.d(TAG, "requestNextPageData "+paginationState.currentPageNumber)
            fetchDataForCurrentState()
        } else {
             paginationState.isPaginating = false
        }
    }

    public fun refreshData() {
        Log.d(TAG, "refreshData")
        reset()
        fetchDataForCurrentState()
    }

    public fun isNewDataRequestNeedOnCreate() : Boolean = dataRequestState == DataRequestState.IDLE

    private fun reset() {
        Log.d(TAG, "reset")
        paginationState.currentPageNumber = 1
        paginationState.hasNextPage = true;
    }


    protected abstract fun fetchDataForCurrentState()
}