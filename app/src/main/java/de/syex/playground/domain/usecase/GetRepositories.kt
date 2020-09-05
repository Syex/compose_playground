package de.syex.playground.domain.usecase

import de.syex.playground.data.GitHubApi
import de.syex.playground.domain.model.Repository
import de.syex.playground.domain.model.toDomainModel
import de.syex.playground.domain.usecase.GetRepositories.Params

/**
 * A `use case` to get the GitHib [repositories][Repository] from a [userName][Params.userName].
 */
class GetRepositories(
    private val api: GitHubApi
) {

    suspend fun execute(params: Params): Result<List<Repository>> {
        return try {
            val repoDtos = api.getRepositories(params.userName)
            Result.success(repoDtos.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    data class Params(val userName: String)
}