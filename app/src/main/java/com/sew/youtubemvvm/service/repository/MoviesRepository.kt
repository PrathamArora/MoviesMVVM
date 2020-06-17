package com.sew.youtubemvvm.service.repository

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.sew.youtubemvvm.service.model.Movie
import com.sew.youtubemvvm.view.callback.IMoviesRepositoryCallback
import com.sew.youtubemvvm.activity.BaseActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Pratham Arora
 *
 * Repository singleton class to preserve data from 3rd party API
 */
class MoviesRepository {

    companion object {
        @Volatile
        private var moviesRepositoryInstance: MoviesRepository? = null
        private var moviesDataList: ArrayList<Movie>? = null

        /**
         * Synchronized method to generate a single instance of this class
         *
         * @return moviesRepositoryInstance to get movies list
         */
        fun getInstance(): MoviesRepository {
            synchronized(this) {
                if (moviesRepositoryInstance == null) {
                    moviesRepositoryInstance = MoviesRepository()
                    moviesDataList = ArrayList()
                }
                return moviesRepositoryInstance!!
            }
        }
    }

    /**
     * function which fetches data from API and returns in the form of a callback. So, the class
     * calling this method has to implement IMoviesRepositoryCallback to get results
     *
     * @param iMoviesRepositoryCallback object of IMoviesRepositoryCallback interface
     */
    fun getMoviesList(iMoviesRepositoryCallback: IMoviesRepositoryCallback) {
        if (moviesDataList.isNullOrEmpty()) {
            moviesDataList = ArrayList()
        }

        if (moviesDataList!!.size == 0) {
            setMoviesList(iMoviesRepositoryCallback)
            return
        }
        iMoviesRepositoryCallback.updateMoviesDataSet(moviesDataList!!)
    }

    @SuppressLint("StaticFieldLeak")
    private fun setMoviesList(iMoviesRepositoryCallback: IMoviesRepositoryCallback) {

        object : AsyncTask<Void?, Void?, ArrayList<Movie>>() {

            val REQUEST_METHOD = "GET"
            val READ_TIMEOUT = 15000
            val CONNECTION_TIMEOUT = 15000

            override fun doInBackground(vararg params: Void?): ArrayList<Movie> {
                val dataSetURL = URL(BaseActivity.MOVIES_DATA_SET_URL)
                val connection: HttpURLConnection = dataSetURL.openConnection() as HttpURLConnection

                try {
                    connection.requestMethod = REQUEST_METHOD
                    connection.readTimeout = READ_TIMEOUT
                    connection.connectTimeout = CONNECTION_TIMEOUT

                    val streamReader = InputStreamReader(connection.inputStream)
                    val reader = BufferedReader(streamReader)

                    val stringBuilder = StringBuilder()
                    val inputLine: String = reader.readLine()
                    stringBuilder.append(inputLine)

                    reader.close()
                    streamReader.close()


                    val jsonMoviesDataSet =
                        JSONObject(stringBuilder.toString()).optJSONArray("results")
                    if (jsonMoviesDataSet != null) {
                        for (i in 0 until jsonMoviesDataSet.length()) {
                            moviesDataList!!.add(
                                Movie.getMovieObjectFromJSON(
                                    jsonMoviesDataSet.getJSONObject(
                                        i
                                    )
                                )
                            )
                        }
                    }
                } finally {
                    return moviesDataList!!
                }

            }

            override fun onPostExecute(result: ArrayList<Movie>?) {
                super.onPostExecute(result)
                iMoviesRepositoryCallback.updateMoviesDataSet(moviesDataList!!)
            }
        }.execute()
    }
}