@file:Suppress("DEPRECATION")

package com.sew.youtubemvvm.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sew.youtubemvvm.R
import com.sew.youtubemvvm.view.adapter.MoviesListAdapter
import com.sew.youtubemvvm.viewmodel.MoviesListViewModel
import kotlinx.android.synthetic.main.activity_movies_list.*

/**
 * @author Pratham Arora
 *
 * This is the launcher activity of the application, which brings Movie Details from a 3rd party API
 *
 */
class MoviesListActivity : BaseActivity() {

    private var mAdapter: MoviesListAdapter? = null
    private var mMoviesListViewModel: MoviesListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        mMoviesListViewModel = ViewModelProviders.of(this).get(
            MoviesListViewModel::class.java
        )

        setObservers()

        initRecyclerView()
    }

    /**
     * Sets the observers on the view model objects and gets updated as soon as there is a change
     * in the original variables
     *
     */
    private fun setObservers() {
        mMoviesListViewModel!!.getMoviesDataSet()
            ?.observe(this,
                Observer {
                    mAdapter?.notifyDataSetChanged()
                })

        mMoviesListViewModel!!.isUpdating()
            .observe(this,
                Observer {
                    if (it) {
                        pbFullScreen.visibility = View.VISIBLE
                    } else {
                        pbFullScreen.visibility = View.GONE
                    }
                })
    }


    /**
     * Initializes the Movie Adapter and attaches it to the recyclerview
     *
     */
    private fun initRecyclerView() {
        mAdapter = MoviesListAdapter(
            mMoviesListViewModel!!
                .getMoviesDataSet()!!
                .value!!,
            this
        )

        rvMoviesList.layoutManager = LinearLayoutManager(this)
        rvMoviesList.adapter = mAdapter
    }

}
