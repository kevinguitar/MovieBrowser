package com.kevingt.moviebrowser

import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.movie.MovieViewModel
import com.kevingt.moviebrowser.util.Constant
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
class MovieViewModelTest : BaseUnitTest() {

    @MockK
    lateinit var apiManager: ApiManager

    lateinit var viewModel: MovieViewModel

    override fun init() {
        viewModel = MovieViewModel(apiManager)
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
        coEvery { apiManager.getMovie(any()) } returns HttpResult.ApiError("")

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