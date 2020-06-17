package com.sew.youtubemvvm.service.model

import com.sew.youtubemvvm.activity.BaseActivity
import org.json.JSONObject
import java.io.Serializable

/**
 * @author Pratham Arora
 *
 * Movie data model to store details of the objects coming from 3rd party API
 *
 */
class Movie(
    var movieTitle: String?,
    var movieDescription: String?,
    var posterURL: String?,
    var voteCount: Int?,
    var isLiked: Boolean?
) : Serializable {
    companion object {

        /**
         * Converts the JSON object coming from API to Movie class by picking up necessary details
         * like Title, Overview, Poster URL and Vote count
         *
         * @param movieJsonObject which has all the required and non required data fields related to
         * the movie class
         *
         */
        fun getMovieObjectFromJSON(movieJsonObject: JSONObject): Movie {
            return Movie(
                movieJsonObject.optString("title"),
                movieJsonObject.optString("overview"),
                BaseActivity.IMAGE_BASE_URL + movieJsonObject.optString("poster_path"),
                movieJsonObject.optInt("vote_count", 0),
                false
            )
        }
    }
}