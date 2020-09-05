package de.syex.playground

import de.syex.playground.data.dto.LicenseDto
import de.syex.playground.data.dto.RepositoryDto

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
}