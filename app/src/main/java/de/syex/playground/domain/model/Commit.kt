package de.syex.playground.domain.model

import de.syex.playground.data.dto.CommitDto
import java.time.Instant
import java.util.*

data class Commit(
    val message: String,
    val commitDate: Date,
    val commiterEmail: String,
    val commiterName: String
)

fun CommitDto.toDomainModel() = Commit(
    message = commit.message,
    commitDate = Date.from(Instant.parse(commit.committer.date)),
    commiterEmail = commit.committer.email,
    commiterName = commit.committer.name
)