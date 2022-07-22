package com.sjgroup.android_imperative

import com.sjgroup.android_imperative.di.AppModul
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkStatusCode() = runTest{
        val response = AppModul().tvShowService().apiTVShowPopular(1)
        assertEquals(response.code(), 200)
    }

    @Test
    fun responseIsSuccesfull() = runTest{
        val response = AppModul().tvShowService().apiTVShowPopular(1)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)
    }

    @Test
    fun checkTvShoListSize() = runTest{
        val response = AppModul().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        assertEquals(tvShowPopular!!.tv_shows.size, 20)
    }

    @Test
    fun checkFirstTVShowStatus() = runTest{
        val response = AppModul().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        val tvshows = tvShowPopular!!.tv_shows
        val tvShow = tvshows[0]
        assertEquals(tvShow.status, "Running")
    }
}