package de.syex.playground.presentation.repositorydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.syex.playground.ServiceLocator
import de.syex.playground.domain.model.Commit
import de.syex.playground.domain.model.Repository
import de.syex.playground.domain.usecase.GetCommits
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
                    _stateFlow.value = RepositoryDetailState.DisplayCommits(it)
                },
                onFailure = {
                    println("An error occurred fetching commits: $it")
                    _stateFlow.value = RepositoryDetailState.DisplayError
                }
            )
    }
}

sealed class RepositoryDetailState {

    object Loading : RepositoryDetailState()

    object DisplayError : RepositoryDetailState()

    data class DisplayCommits(val commits: List<Commit>) : RepositoryDetailState()
}

@Suppress("UNCHECKED_CAST")
class RepositoryDetailViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val getCommits = ServiceLocator.getCommits
        return RepositoryDetailViewModel(repository, getCommits) as T
    }
}