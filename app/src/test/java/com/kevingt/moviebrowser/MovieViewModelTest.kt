package com.kevingt.moviebrowser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.movie.MovieViewModel
import com.kevingt.moviebrowser.util.Constant
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var apiManager: ApiManager

    lateinit var viewModel: MovieViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MovieViewModel(apiManager)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getMovieSuccessTest() {
        coEvery { apiManager.getMovie(any()) } returns
                HttpResult.Success(Response.success(Movie()))

        runBlocking {
            viewModel.getMovie(0)
        }

        coVerify(exactly = 1) { apiManager.getMovie(any()) }
        assertEquals(Movie(), viewModel.movie.value)
        assertEquals(null, viewModel.errorMessage.value)
    }

    @Test
    fun getMovieApiErrorTest() {
        coEvery { apiManager.getMovie(any()) } returns
                HttpResult.Success(
                    Response.error(
                        404,
                        ResponseBody.create(null, "")
                    )
                )

        runBlocking {
            viewModel.getMovie(0)
        }

        coVerify(exactly = 1) { apiManager.getMovie(any()) }
        assertEquals(Constant.API_ERROR_MESSAGE, viewModel.errorMessage.value)
        assertEquals(null, viewModel.movie.value)
    }

    @Test
    fun getMovieInternetErrorTest() {
        coEvery { apiManager.getMovie(any()) } returns
                HttpResult.NetworkError(Throwable())

        runBlocking {
            viewModel.getMovie(0)
        }

        coVerify(exactly = 1) { apiManager.getMovie(any()) }
        assertEquals(Constant.NETWORK_ERROR_MESSAGE, viewModel.errorMessage.value)
        assertEquals(null, viewModel.movie.value)
    }

}