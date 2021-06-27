package com.rsekar.base.model.pagination

import androidx.lifecycle.MutableLiveData

class PaginationState {
    ///Whether current api support/needed pagination
    var isPaginatedSupport : Boolean = false

    ///Holds the current loaded page number
    var currentPageNumber : Int = 1

    ///Whether it has contents to pull in next page or not
    var hasNextPage : Boolean = true

    ///Is currently a pending request is waiting for the data
    var isPaginating : Boolean =  false
}