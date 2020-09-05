package de.syex.playground.data

import de.syex.playground.data.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitHubApi {

    @GET("users/{user}/repos")
    @Headers("Accept: application/vnd.github.v3+json")
    suspend fun getRepositories(
        @Path("user") userName: String,
    ): List<RepositoryDto>
}