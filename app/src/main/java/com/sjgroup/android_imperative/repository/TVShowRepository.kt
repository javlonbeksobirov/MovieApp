package com.sjgroup.android_imperative.repository

import com.sjgroup.android_imperative.db.TVShowDao
import com.sjgroup.android_imperative.models.TVShow
import com.sjgroup.android_imperative.network.services.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tvShowService: TVShowService,
        private val tvShowDao: TVShowDao
                                           ) {

    /**
     * Retrofit related
     */

    suspend fun apiTvShowPopular(page: Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTvShowDetails(q: Int) = tvShowService.apiTVShowDetails(q)




    /**
     * Room related
     */

    suspend fun getTvShowsFromDB() = tvShowDao.getTVShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTVShowToDB(tvShow)
    suspend fun deleteTvShowsFromDB() = tvShowDao.deleteTvShowsFromDB()

}


