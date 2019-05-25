package com.kevingt.moviebrowser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.Discover
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.discover.DiscoverViewModel
import com.kevingt.moviebrowser.util.Constant
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DiscoverViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var apiManager: ApiManager

    lateinit var viewModel: DiscoverViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = DiscoverViewModel(apiManager)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun discoverMovieSuccessTest() {
        coEvery { apiManager.discoverMovie(any(), any(), any()) } returns
                HttpResult.Success(Response.success(Discover()))

        runBlocking {
            viewModel.discoverMovie(Constant.GENRE_LIST[0], Constant.SORT_LIST[0])
        }

        coVerify(exactly = 1) { apiManager.discoverMovie(any(), any(), any()) }
        assertEquals(listOf<Movie>(), viewModel.discoverData.value)
        assertEquals(null, viewModel.errorMessage.value)
    }

    @Test
    fun searchMovieSuccessTest() {
        coEvery { apiManager.searchMovie(any(), any()) } returns
                HttpResult.Success(Response.success(Discover()))

        runBlocking {
            viewModel.searchMovie("Unit Test")
        }

        coVerify(exactly = 1) { apiManager.searchMovie(any(), any()) }
        assertEquals(listOf<Movie>(), viewModel.discoverData.value)
        assertEquals(null, viewModel.errorMessage.value)
    }
}