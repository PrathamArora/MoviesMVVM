package com.sew.youtubemvvm.view.callback

import com.sew.youtubemvvm.service.model.Movie

/**
 * @author Pratham Arora
 *
 * An interface to bring data from repository to the required class
 */
interface IMoviesRepositoryCallback {

    /**
     * @param moviesDataSet passes the data set from repository to the class implementing this
     */
    fun updateMoviesDataSet(moviesDataSet : ArrayList<Movie>)
}