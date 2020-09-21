package de.syex.playground.presentation.repositorydetail

import com.google.common.truth.Truth.assertThat
import de.syex.playground.domain.model.Commit
import de.syex.playground.extension.toDate
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class RepositoryDetailViewModelTest {

    private val testCommits = listOf(
        Commit(
            "Commit 2",
            LocalDate.of(2019, 5, 22).toDate(),
            "user@org.com",
            "user"
        ),
        Commit(
            "Commit 3",
            LocalDate.of(2019, 8, 11).toDate(),
            "user@org.com",
            "user"
        ),
        Commit(
            "Commit 1",
            LocalDate.of(2019, 2, 2).toDate(),
            "user@org.com",
            "user"
        ),
        Commit(
            "Commit 4",
            LocalDate.of(2019, 8, 15).toDate(),
            "user@org.com",
            "user"
        )
    )

    @Test
    fun `converts list of commits to list of CommitViewData`() {
        val commitViewDatas = testCommits.toCommitViewDatas()
        assertThat(commitViewDatas.size).isEqualTo(3)

        with(commitViewDatas[0]) {
            assertThat(commits).containsExactly(testCommits[2])
            assertThat(monthName).isEqualTo(
                Month.FEBRUARY.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
            )
        }

        with(commitViewDatas[1]) {
            assertThat(commits).containsExactly(testCommits[0])
            assertThat(monthName).isEqualTo(
                Month.MAY.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
            )
        }

        with(commitViewDatas[2]) {
            assertThat(commits).containsExactly(testCommits[1], testCommits[3])
            assertThat(monthName).isEqualTo(
                Month.AUGUST.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
            )
        }

        commitViewDatas.forEach {
            assertThat(it.maxCommitsInAnyMonth).isEqualTo(2)
        }
    }
}
