package com.sew.youtubemvvm.activity

import androidx.appcompat.app.AppCompatActivity

/**
 * @author Pratham Arora
 *
 * This is an activity in which all types of static helper functions and objects are kept and
 * can be used across all the application
 *
 */

open class BaseActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val MOVIES_DATA_SET_URL =
            "https://api.themoviedb.org/3/movie/550/similar?api_key=628a19477d84ed9a5e800d6a8ad69b0d&language=en-US&page=1"
        const val INTENT_MOVIE_KEY = "singleMoviePosition"
    }
}