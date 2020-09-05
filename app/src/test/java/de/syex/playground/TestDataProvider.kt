package de.syex.playground

import de.syex.playground.data.dto.LicenseDto
import de.syex.playground.data.dto.RepositoryDto
import de.syex.playground.domain.model.License
import de.syex.playground.domain.model.Repository
import java.util.*

object TestDataProvider {

    fun repositoryDto(
        id: Int = 0,
        created_at: String = "",
        description: String? = null,
        disabled: Boolean = false,
        fork: Boolean = false,
        forks_count: Int = 0,
        full_name: String = "",
        license: LicenseDto? = null,
        name: String = "",
        open_issues_count: Int = 0,
        updated_at: String = "",
        url: String = "",
        watchers_count: Int = 0
    ) = RepositoryDto(
        id,
        created_at,
        description,
        disabled,
        fork,
        forks_count,
        full_name,
        license,
        name,
        open_issues_count,
        updated_at,
        url,
        watchers_count
    )

    fun repository(
        id: Int = 0,
        createdAt: Date = Date(),
        description: String? = null,
        disabled: Boolean = false,
        fork: Boolean = false,
        forksCount: Int = 0,
        fullName: String = "",
        license: License? = null,
        name: String = "",
        openIssuesCount: Int = 0,
        updatedAt: Date = Date(),
        url: String = "",
        watchersCount: Int = 0
    ) = Repository(
        id,
        createdAt,
        description,
        disabled,
        fork,
        forksCount,
        fullName,
        license,
        name,
        openIssuesCount,
        updatedAt,
        url,
        watchersCount
    )
}