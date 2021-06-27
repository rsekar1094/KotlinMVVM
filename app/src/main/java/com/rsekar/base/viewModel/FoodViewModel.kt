package com.rsekar.base.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {

    val foodApiViewModel : FoodApiViewModel = FoodApiViewModel();

    var foodCellModels : MutableLiveData<ArrayList<MutableLiveData<FoodCellViewModel>>> =  MutableLiveData<ArrayList<MutableLiveData<FoodCellViewModel>>>()


    init {
        foodApiViewModel.getItems().observeForever {
            foodCellModels.value = ArrayList(it?.map { value -> MutableLiveData<FoodCellViewModel>(FoodCellViewModel(value.id,value.name,value.isFavorite)) })
        }
    }

    public fun update(model : FoodCellViewModel,favorite: Boolean) {
        foodCellModels.value?.first { it.value?.let { v -> v.id == model.id } == true }.let {
            it?.value?.favorite = favorite
            it?.value = it?.value
        }
    }


    class FoodCellViewModel(public val id : String,
                            public val title : String,public var favorite : Boolean)
}