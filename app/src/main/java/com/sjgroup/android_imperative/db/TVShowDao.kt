package com.sjgroup.android_imperative.db

import androidx.room.*
import com.sjgroup.android_imperative.models.TVShow
import javax.inject.Inject

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tv_show")
    suspend fun getTVShowsFromDB(): List<TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShowToDB(tvShow: TVShow)

    @Query("DELETE FROM tv_show")
    suspend fun deleteTvShowsFromDB()
}