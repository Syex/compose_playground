package de.syex.playground.data.dto

import kotlinx.serialization.Serializable

/**
 * A JSON model for a GitHub repository. A lot of keys are omitted for simplicity and only the
 * relevant ones for this task are being kept.
 */
@Serializable
data class RepositoryDto(
    val id: Int,
    val created_at: String,
    val description: String?,
    val disabled: Boolean,
    val fork: Boolean,
    val forks_count: Int,
    val full_name: String,
    val license: LicenseDto?,
    val name: String,
    val open_issues_count: Int,
    val updated_at: String,
    val url: String,
    val watchers_count: Int
)

@Serializable
data class LicenseDto(
    val key: String,
    val name: String,
    val url: String
)