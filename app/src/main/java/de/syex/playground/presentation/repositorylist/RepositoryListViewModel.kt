package de.syex.playground.presentation.repositorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.syex.playground.ServiceLocator
import de.syex.playground.domain.model.Repository
import de.syex.playground.domain.usecase.GetRepositories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Suppress("EXPERIMENTAL_API_USAGE")
class RepositoryListViewModel(
    private val getRepositories: GetRepositories,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<RepositoryListState>(RepositoryListState.Loading)
    val stateFlow: Flow<RepositoryListState> get() = _stateFlow

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() = viewModelScope.launch(dispatcher) {
        getRepositories.execute(GetRepositories.Params("Syex"))
            .fold(
                onSuccess = {
                    println("Successfully fetched repos: $it")
                    _stateFlow.value = RepositoryListState.DisplayRepositories(it)
                },
                onFailure = {
                    println("An error occurred fetching repos: $it")
                    _stateFlow.value = RepositoryListState.DisplayError
                }
            )
    }
}

sealed class RepositoryListState {

    object Loading : RepositoryListState()

    object DisplayError : RepositoryListState()

    data class DisplayRepositories(val repositories: List<Repository>) : RepositoryListState()
}

@Suppress("UNCHECKED_CAST")
class RepositoryListViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val getRepositories = ServiceLocator.getRepositories
        return RepositoryListViewModel(getRepositories) as T
    }
}