package de.syex.playground.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommitDto(
    val commit: CommitDetailsDto,
)

@Serializable
data class CommitDetailsDto(
    val committer: CommitterDto,
    val message: String
)

@Serializable
data class CommitterDto(
    val date: String,
    val email: String,
    val name: String
)