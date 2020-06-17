package com.sew.youtubemvvm.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sew.youtubemvvm.R
import com.sew.youtubemvvm.service.model.Movie
import com.sew.youtubemvvm.activity.BaseActivity
import com.sew.youtubemvvm.activity.SingleMovieDetailsActivity

/**
 * @author Pratham Arora
 *
 * Adapter class to render the movies data coming from the repository
 *
 * @param mMoviesList a list of Movie object in it
 * @param context application or activity level context is required
 */
class MoviesListAdapter(private var mMoviesList: ArrayList<Movie>, private var context: Context?) :
    RecyclerView.Adapter<MoviesListAdapter.MovieDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_card, parent, false)
        return MovieDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) {
        holder.initMovieDetails(context, mMoviesList, position)
    }


    /**
     * @param itemView it takes the components of XML layout R.layout.movie_card
     */
    class MovieDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgMoviePoster: ImageView? = null
        private var tvMovieTitle: TextView? = null
        private var tvMovieDescription: TextView? = null
        private var btnLike: Button? = null
        private var cardMovieDetails: CardView? = null

        init {
            imgMoviePoster = itemView.findViewById(R.id.imgMoviePoster)
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle)
            tvMovieDescription = itemView.findViewById(R.id.tvMovieDescription)
            btnLike = itemView.findViewById(R.id.btnLike)
            cardMovieDetails = itemView.findViewById(R.id.cardMovieDetails)
        }

        /**
         * @param movie Movie object to set details into the components of the view
         * @param context to fetch required button colors from resources and depict whether the movie
         * is liked by the user or not
         */
        @SuppressLint("SetTextI18n")
        private fun initLikeButton(movie: Movie, context: Context) {
            if (movie.isLiked!!) {
                btnLike?.text = (movie.voteCount?.plus(1)).toString()
                btnLike?.setBackgroundColor(
                    context.resources.getColor(
                        R.color.likedButtonColor,
                        null
                    )
                )
            } else {
                btnLike?.text = (movie.voteCount).toString()
                btnLike?.setBackgroundColor(
                    context.resources.getColor(
                        R.color.notLikedButtonColor,
                        null
                    )
                )
            }
        }


        /**
         * @param context to allow Glide to fetch poster image from the URL and place it into image holder
         * @param mMoviesList List of all movies coming from API
         * @param position position of the current movie object to be processed
         */
        fun initMovieDetails(context: Context?, mMoviesList: ArrayList<Movie>, position: Int) {
            val movie = mMoviesList[position]
            tvMovieTitle?.text = movie.movieTitle
            tvMovieDescription?.text = movie.movieDescription

            val defaultOptions: RequestOptions = RequestOptions()
                .error(R.drawable.blade_runner)

            Glide.with(context!!).setDefaultRequestOptions(defaultOptions)
                .load(movie.posterURL)
                .into(imgMoviePoster!!)

            initLikeButton(movie, context)


            btnLike?.setOnClickListener {
                movie.isLiked = !movie.isLiked!!
                initLikeButton(movie, context)
            }

            cardMovieDetails?.setOnClickListener {
                val intent = Intent(context, SingleMovieDetailsActivity::class.java)
                intent.putExtra(BaseActivity.INTENT_MOVIE_KEY, position)
                context.startActivity(intent)
            }

        }

    }

}