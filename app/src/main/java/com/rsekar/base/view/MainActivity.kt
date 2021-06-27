package com.rsekar.base.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsekar.base.utils.states.DataRequestState
import com.rsekar.base.utils.extension.addScrollListener
import com.rsekar.base.utils.states.LoaderState
import com.rsekar.base.view.adapter.FoodListAdapter
import com.rsekar.base.view.appViews.SwipeRefreshRecyclerView
import com.rsekar.base.viewModel.FoodViewModel
import com.rsekar.mvvm.R

class MainActivity : AppCompatActivity(),FoodListAdapter.FoodListAdapterInterface {

    private lateinit var swipeRecyclerView : SwipeRefreshRecyclerView;
    private lateinit var foodAdapter : FoodListAdapter;
    private lateinit var foodViewModel : FoodViewModel;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRecyclerView = findViewById<SwipeRefreshRecyclerView>(R.id.recyclerview)
        foodAdapter = FoodListAdapter(this)
        swipeRecyclerView.recyclerView.adapter = foodAdapter
        swipeRecyclerView.recyclerView.layoutManager = LinearLayoutManager(this)

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        addObserversFor(foodViewModel)

        if (foodViewModel.foodApiViewModel.isNewDataRequestNeedOnCreate()) {
            foodViewModel.foodApiViewModel.refreshData()
        }

        addPaginationAndRefreshObservers(foodViewModel)
    }


    private fun addObserversFor(viewModel : FoodViewModel) {
        viewModel.foodCellModels.observe(this, Observer {
            foodAdapter.update(it)
        })

        viewModel.foodApiViewModel.getLoaderState().observe(this,{
            if (it == null) {
                return@observe
            }

            when (it) {
                LoaderState.NONE -> {
                    swipeRecyclerView.isRefreshing = false
                    foodAdapter.setIsPaginationIndicatorVisible(false)
                    stopLoader()
                }

                LoaderState.MAIN_PROGRESS_ON -> {
                    startLoader()
                }

                LoaderState.BOTTOM_PROGRESS_ON -> {
                    foodAdapter.setIsPaginationIndicatorVisible(true)
                    swipeRecyclerView.recyclerView.smoothScrollToPosition(foodAdapter.itemCount - 1)
                }

                LoaderState.TOP_PROGRESS_ON -> {
                    swipeRecyclerView.isRefreshing = true
                }
            }
        })
    }

    private fun addPaginationAndRefreshObservers(viewModel : FoodViewModel) {
        swipeRecyclerView.setOnRefreshListener {
            viewModel.foodApiViewModel.refreshData()
        }

        swipeRecyclerView.recyclerView.addScrollListener {
            Log.d("MainActivity","addOnPagination")
            viewModel.foodApiViewModel.requestNextPageData()
        }
    }


    private fun startLoader() {
        findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
    }

    private fun stopLoader() {
        findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
    }

    ///FoodListAdapter.FoodListAdapterInterface
    override fun didFavoriteChange(model: FoodViewModel.FoodCellViewModel, favorite: Boolean) {
        foodViewModel.update(model,favorite)
    }
}