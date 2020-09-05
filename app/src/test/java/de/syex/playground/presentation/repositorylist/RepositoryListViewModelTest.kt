package de.syex.playground.presentation.repositorylist

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import de.syex.playground.TestDataProvider
import de.syex.playground.domain.usecase.GetRepositories
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

/**
 * The tests sadly crash currently. It appears to be a bug and I filed an issue here
 * https://github.com/mockk/mockk/issues/485 in case someone is interested.
 */
@ExperimentalTime
@ExperimentalCoroutinesApi
class RepositoryListViewModelTest {

    private val getRepositories = mockk<GetRepositories>()

    private val viewModel by lazy {
        RepositoryListViewModel(getRepositories, TestCoroutineDispatcher())
    }

    @Test
    fun `after successfully fetching repos goes to DisplayRepositories state`() = runBlockingTest {
        val repos = listOf(TestDataProvider.repository(id = 2222))
        coEvery { getRepositories.execute(any()) } returns Result.success(repos)

        viewModel.stateFlow.test {
            var nextState = expectItem()
            assertThat(nextState).isInstanceOf(RepositoryListState.Loading::class.java)

            nextState = expectItem()
            assertThat(nextState).isInstanceOf(RepositoryListState.DisplayRepositories::class.java)
            nextState as RepositoryListState.DisplayRepositories
            assertThat(nextState.repositories).isEqualTo(repos)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when fetching repos fails goes to DisplayError state`() = runBlockingTest {
        coEvery { getRepositories.execute(any()) } returns Result.failure(Exception())

        viewModel.stateFlow.test {
            var nextState = expectItem()
            assertThat(nextState).isInstanceOf(RepositoryListState.Loading::class.java)

            nextState = expectItem()
            assertThat(nextState).isInstanceOf(RepositoryListState.DisplayError::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }
}