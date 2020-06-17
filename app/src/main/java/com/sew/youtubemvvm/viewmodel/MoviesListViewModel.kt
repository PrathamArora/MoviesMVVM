package com.sew.youtubemvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sew.youtubemvvm.service.model.Movie
import com.sew.youtubemvvm.service.repository.MoviesRepository
import com.sew.youtubemvvm.view.callback.IMoviesRepositoryCallback


/**
 * @author Pratham Arora
 *
 * View Model class to communicate Live data between repository (model) and front end (view)
 */
class MoviesListViewModel : ViewModel(), IMoviesRepositoryCallback {

    private var mMoviesRepository: MoviesRepository? = null
    private val mIsUpdating = MutableLiveData<Boolean>()

    companion object {
        private val mMoviesMutableList = MutableLiveData<ArrayList<Movie>>()
        private val mMoviesArrayList = ArrayList<Movie>()
    }


    init {
        mMoviesMutableList.value = mMoviesArrayList
        mMoviesRepository = MoviesRepository.getInstance()
        mMoviesRepository!!.getMoviesList(this)
        mIsUpdating.value = true
    }

    override fun updateMoviesDataSet(moviesDataSet: ArrayList<Movie>) {
        mIsUpdating.postValue(false)
        mMoviesArrayList.clear()
        mMoviesArrayList.addAll(moviesDataSet)
        mMoviesMutableList.postValue(mMoviesArrayList)
    }

    /**
     * updates the changes to all the concerned classes watching over the data set
     */
    fun updatedSingleItem() {
        mIsUpdating.postValue(false)
        mMoviesMutableList.postValue(mMoviesArrayList)
    }

    /**
     * returns the data set in the form of LiveData
     *
     * @return LiveData of ArrayList to be observed for changes
     */
    fun getMoviesDataSet(): LiveData<ArrayList<Movie>>? {
        return mMoviesMutableList
    }

    /**
     * @return LiveData of Boolean to notify the view whether repository is currently fetching data
     * or it has already fetched data
     */
    fun isUpdating(): LiveData<Boolean> {
        return mIsUpdating
    }
}