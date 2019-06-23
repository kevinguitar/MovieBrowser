package com.kevingt.moviebrowser

import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.Discover
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.upcoming.UpcomingViewModel
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
class UpcomingViewModelTest : BaseUnitTest() {

    @MockK
    lateinit var apiManager: ApiManager

    lateinit var viewModel: UpcomingViewModel

    override fun init() {
        viewModel = UpcomingViewModel(apiManager)
    }

    @Test
    fun getUpcomingSuccessTest() {
        coEvery { apiManager.getUpcoming(any()) } returns
                HttpResult.Success(Response.success(Discover()))

        runBlocking {
            viewModel.getUpcoming()
        }

        coVerify(exactly = 1) { apiManager.getUpcoming(any()) }
        assertEquals(listOf<Movie>(), viewModel.upcomingData.value)
        assertEquals(null, viewModel.errorMessage.value)
        assertEquals(false, viewModel.isLoading.value)
    }

}