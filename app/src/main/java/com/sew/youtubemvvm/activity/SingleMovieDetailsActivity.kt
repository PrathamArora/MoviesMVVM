@file:Suppress("DEPRECATION")

package com.sew.youtubemvvm.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sew.youtubemvvm.R
import com.sew.youtubemvvm.service.model.Movie
import com.sew.youtubemvvm.viewmodel.MoviesListViewModel
import kotlinx.android.synthetic.main.activity_single_movie_details.*


/**
 * @author Pratham Arora
 *
 * This activity shows the details of a movie once a user clicks on a card from the launcher activity
 *
 */
class SingleMovieDetailsActivity : BaseActivity() {

    private var mMoviesListViewModel: MoviesListViewModel? = null

    private var mPosition: Int? = 0
    private var mMoviesList: ArrayList<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie_details)

        mMoviesListViewModel = ViewModelProviders.of(this).get(
            MoviesListViewModel::class.java
        )


        val intent = intent
        mPosition = intent.getIntExtra(INTENT_MOVIE_KEY, 0)
        mMoviesList = mMoviesListViewModel!!.getMoviesDataSet()?.value

        initializeView(mPosition!!)

        imgBackButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Initializes the views of this activity
     */
    private fun initializeView(position: Int) {
        val movieObject = mMoviesList?.get(position)

        tvMovieTitle?.text = movieObject?.movieTitle
        tvMovieDescription?.text = movieObject?.movieDescription

        val defaultOptions: RequestOptions = RequestOptions()
            .error(R.drawable.blade_runner)

        Glide.with(this).setDefaultRequestOptions(defaultOptions)
            .load(movieObject?.posterURL)
            .into(imgMoviePoster)

        initLikeButton(movieObject!!)

        btnLike?.setOnClickListener {
            movieObject.isLiked = !movieObject.isLiked!!
            mMoviesListViewModel?.updatedSingleItem()
            initLikeButton(movieObject)
        }

    }

    /**
     * Manipulates the Like button on this screen
     */
    private fun initLikeButton(movieObject: Movie) {
        if (movieObject.isLiked!!) {
            btnLike?.text = (movieObject.voteCount?.plus(1)).toString()
            btnLike?.setBackgroundColor(
                resources.getColor(
                    R.color.likedButtonColor,
                    null
                )
            )
        } else {
            btnLike?.text = (movieObject.voteCount).toString()
            btnLike?.setBackgroundColor(
                resources.getColor(
                    R.color.notLikedButtonColor,
                    null
                )
            )
        }

    }
}
