package de.syex.playground.domain.model

import de.syex.playground.data.dto.LicenseDto
import de.syex.playground.data.dto.RepositoryDto
import java.time.Instant
import java.util.*

/**
 * Domain model for a GitHub repository.
 */
data class Repository(
    val id: Int,
    val createdAt: Date,
    val description: String?,
    val disabled: Boolean,
    val fork: Boolean,
    val forksCount: Int,
    val fullName: String,
    val license: License?,
    val name: String,
    val openIssuesCount: Int,
    val updatedAt: Date,
    val url: String,
    val watchersCount: Int
)

data class License(
    val key: String,
    val name: String,
    val url: String
)

fun RepositoryDto.toDomainModel() = Repository(
    id = id,
    createdAt = Date.from(Instant.parse(created_at)),
    description = description,
    disabled = disabled,
    fork = fork,
    forksCount = forks_count,
    fullName = full_name,
    license = license?.toDomainModel(),
    name = name,
    openIssuesCount = open_issues_count,
    updatedAt = Date.from(Instant.parse(updated_at)),
    url = url,
    watchersCount = watchers_count

)

fun LicenseDto.toDomainModel() = License(
    key = key, name = name, url = url
)