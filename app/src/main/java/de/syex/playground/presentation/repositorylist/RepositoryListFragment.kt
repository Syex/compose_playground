package de.syex.playground.presentation.repositorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.syex.playground.R
import kotlinx.android.synthetic.main.repository_list_fragment.*
import kotlinx.android.synthetic.main.repository_list_fragment.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A [Fragment] to list public GitHub repositories of a user.
 */
class RepositoryListFragment : Fragment() {

    private val viewModel: RepositoryListViewModel by viewModels { RepositoryListViewModelFactory() }
    private val repoAdapter by lazy {
        RepositoryListAdapter(
            onRepoClickCallback = { viewModel.onRepositoryClicked(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repository_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.repositoryRecyclerView.apply {
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        viewModel.stateFlow
            .onEach { updateState(it) }
            .launchIn(lifecycleScope)
    }

    private fun updateState(state: RepositoryListState) = when (state) {
        RepositoryListState.Loading -> progressBar.show()
        RepositoryListState.DisplayError -> Toast.makeText(
            requireContext(),
            "An error occurred",
            Toast.LENGTH_SHORT
        ).show()
        is RepositoryListState.DisplayRepositories -> {
            progressBar.hide()
            repoAdapter.submitList(state.repositories)
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar
            ?.setTitle(R.string.title_repository_list)
    }
}