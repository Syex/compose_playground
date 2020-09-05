package de.syex.playground.presentation.repositorydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepositoryDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel
}

@Suppress("UNCHECKED_CAST")
class RepositoryDetailViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepositoryDetailViewModel() as T
    }
}