package de.syex.playground

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import de.syex.playground.data.GitHubApi
import de.syex.playground.domain.usecase.GetRepositories
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

/**
 * A simple service locator to manage dependencies of the app.
 */
object ServiceLocator {

    val gitHubApi by lazy {
        @Suppress("EXPERIMENTAL_API_USAGE")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(
                    MediaType.get("application/json")
                )
            )
            .build()

        retrofit.create(GitHubApi::class.java)
    }

    val getRepositories: GetRepositories
        get() = GetRepositories(gitHubApi)
}