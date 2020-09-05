package de.syex.playground.domain.usecase

import de.syex.playground.data.GitHubApi
import de.syex.playground.domain.model.Commit
import de.syex.playground.domain.model.toDomainModel
import de.syex.playground.domain.usecase.GetCommits.Params

/**
 * A `use case` to get the GitHib [commits][Commit] from a [userName][Params.userName] and
 * [repoName][Params.repoName].
 */
class GetCommits(
    private val api: GitHubApi
) {

    suspend fun execute(params: Params): Result<List<Commit>> {
        return try {
            val repoDtos = api.getCommits(params.userName, params.repoName)
            Result.success(repoDtos.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    data class Params(val userName: String, val repoName: String)
}