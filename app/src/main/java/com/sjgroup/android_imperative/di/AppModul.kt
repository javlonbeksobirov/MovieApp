package com.sjgroup.android_imperative.di

import android.app.Application
import com.sjgroup.android_imperative.db.AppDatabase
import com.sjgroup.android_imperative.db.TVShowDao
import com.sjgroup.android_imperative.network.Server
import com.sjgroup.android_imperative.network.Server.IS_TESTER
import com.sjgroup.android_imperative.network.services.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModul {

    /**
     * Retrofit related
     */

    @Provides
    fun server():String{
        if (IS_TESTER) return Server.getDevelopment()
        return Server.getProduction()
    }

    @Provides
    @Singleton
    fun retrofitClient():Retrofit{
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun tvShowService():TVShowService{
        return retrofitClient().create(TVShowService::class.java)
    }



    /**
     * Room related
     */

    @Provides
    @Singleton
    fun appDatabase(convext: Application): AppDatabase{
        return AppDatabase.getAppDBInstance(convext)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase): TVShowDao{
        return appDatabase.getTVShowDao()
    }
}