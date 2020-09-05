package de.syex.playground.domain.usecase

import com.google.common.truth.Truth.assertThat
import de.syex.playground.TestDataProvider
import de.syex.playground.data.GitHubApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.ZoneId

@ExperimentalCoroutinesApi
class GetRepositoriesTest {

    private val api = mockk<GitHubApi>()
    private val getRepos = GetRepositories(api)

    @Test
    fun `returns Success with contained list of repositories`() = runBlockingTest {
        coEvery { api.getRepositories("user") } returns listOf(
            TestDataProvider.repositoryDto(
                id = 1,
                created_at = "2011-01-26T19:01:12Z",
                updated_at = "2015-01-26T19:14:43Z",
                description = "Awesome repo 1"
            ),
            TestDataProvider.repositoryDto(
                id = 2,
                created_at = "2011-02-26T19:01:12Z",
                updated_at = "2015-02-26T19:14:43Z",
                description = "Awesome repo 2"
            )
        )
        val getReposResult = getRepos.execute(GetRepositories.Params("user"))
        assertTrue(getReposResult.isSuccess)

        val repos = getReposResult.getOrThrow()
        assertThat(repos).hasSize(2)

        with(repos[0]) {
            assertThat(id).isEqualTo(1)
            assertThat(description).isEqualTo("Awesome repo 1")

            val createdAtDateTime = createdAt.toInstant().atZone(ZoneId.systemDefault())
            assertThat(createdAtDateTime.dayOfMonth).isEqualTo(26)
            assertThat(createdAtDateTime.monthValue).isEqualTo(1)
            assertThat(createdAtDateTime.year).isEqualTo(2011)

            val updatedAtDateTime = updatedAt.toInstant().atZone(ZoneId.systemDefault())
            assertThat(updatedAtDateTime.dayOfMonth).isEqualTo(26)
            assertThat(updatedAtDateTime.monthValue).isEqualTo(1)
            assertThat(updatedAtDateTime.year).isEqualTo(2015)
        }

        with(repos[1]) {
            assertThat(id).isEqualTo(2)
            assertThat(description).isEqualTo("Awesome repo 2")

            val createdAtDateTime = createdAt.toInstant().atZone(ZoneId.systemDefault())
            assertThat(createdAtDateTime.dayOfMonth).isEqualTo(26)
            assertThat(createdAtDateTime.monthValue).isEqualTo(2)
            assertThat(createdAtDateTime.year).isEqualTo(2011)

            val updatedAtDateTime = updatedAt.toInstant().atZone(ZoneId.systemDefault())
            assertThat(updatedAtDateTime.dayOfMonth).isEqualTo(26)
            assertThat(updatedAtDateTime.monthValue).isEqualTo(2)
            assertThat(updatedAtDateTime.year).isEqualTo(2015)
        }
    }

    @Test
    fun `returns Failure if api throws an exception`() = runBlockingTest {
        val exception = Exception("fail")
        coEvery { api.getRepositories(any()) } throws exception

        val getReposResult = getRepos.execute(GetRepositories.Params("user"))
        assertThat(getReposResult.isFailure)
        assertThat(getReposResult.exceptionOrNull()).isEqualTo(exception)
    }
}