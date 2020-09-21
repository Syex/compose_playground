package de.syex.playground.presentation.repositorydetail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.syex.playground.ServiceLocator
import de.syex.playground.domain.model.Commit
import de.syex.playground.domain.model.Repository
import de.syex.playground.domain.usecase.GetCommits
import de.syex.playground.extension.toLocalDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.format.TextStyle
import java.util.*

@Suppress("EXPERIMENTAL_API_USAGE")
class RepositoryDetailViewModel(
    private val repository: Repository,
    private val getCommits: GetCommits,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<RepositoryDetailState>(RepositoryDetailState.Loading)
    val stateFlow: Flow<RepositoryDetailState> get() = _stateFlow

    init {
        fetchCommits()
    }

    private fun fetchCommits() = viewModelScope.launch(dispatcher) {
        val splitRepoFullName = repository.fullName.split("/")
        getCommits.execute(GetCommits.Params(splitRepoFullName[0], splitRepoFullName[1]))
            .fold(
                onSuccess = {
                    println("Successfully fetched commits: $it")
                    _stateFlow.value = RepositoryDetailState.DisplayCommits(it.toCommitViewDatas())
                },
                onFailure = {
                    println("An error occurred fetching commits: $it")
                    _stateFlow.value = RepositoryDetailState.DisplayError
                }
            )
    }
}


@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun List<Commit>.toCommitViewDatas(): List<CommitViewData> {
    val monthToCommits = groupBy { it.commitDate.toLocalDate().month }
        .toSortedMap { month1, month2 -> month1.value.compareTo(month2.value) }
    val maxCommitsInAnyMonth = monthToCommits.values.maxByOrNull { it.size }?.size ?: 0
    return monthToCommits.map { (month, commits) ->
        CommitViewData(
            maxCommitsInAnyMonth = maxCommitsInAnyMonth,
            monthName = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()),
            commits = commits
        )
    }
}

sealed class RepositoryDetailState {

    object Loading : RepositoryDetailState()

    object DisplayError : RepositoryDetailState()

    data class DisplayCommits(val commitViewDatas: List<CommitViewData>) : RepositoryDetailState()
}

data class CommitViewData(
    val maxCommitsInAnyMonth: Int,
    val monthName: String,
    val commits: List<Commit>
)

@Suppress("UNCHECKED_CAST")
class RepositoryDetailViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val getCommits = ServiceLocator.getCommits
        return RepositoryDetailViewModel(repository, getCommits) as T
    }
}