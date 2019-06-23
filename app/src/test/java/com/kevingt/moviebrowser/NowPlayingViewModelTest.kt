package com.kevingt.moviebrowser

import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.Discover
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.now_playing.NowPlayingViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class NowPlayingViewModelTest : BaseUnitTest() {

    @MockK
    lateinit var apiManager: ApiManager

    lateinit var viewModel: NowPlayingViewModel

    override fun init() {
        viewModel = NowPlayingViewModel(apiManager)
    }

    @Test
    fun getNowPlayingSuccessTest() {
        coEvery { apiManager.getNowPlaying(any()) } returns
                HttpResult.Success(Response.success(Discover()))

        runBlocking {
            viewModel.getNowPlaying()
        }

        coVerify(exactly = 1) { apiManager.getNowPlaying(any()) }
        assertEquals(listOf<Movie>(), viewModel.nowPlayingData.value)
        assertEquals(null, viewModel.errorMessage.value)
        assertEquals(false, viewModel.isLoading.value)
    }
}