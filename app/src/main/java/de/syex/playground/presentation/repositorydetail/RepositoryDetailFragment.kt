package de.syex.playground.presentation.repositorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import de.syex.playground.R
import kotlinx.android.synthetic.main.repository_detail_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RepositoryDetailFragment : Fragment() {

    val args: RepositoryDetailFragmentArgs by navArgs()
    private val viewModel: RepositoryDetailViewModel by viewModels {
        RepositoryDetailViewModelFactory(args.repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repository_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateFlow
            .onEach { updateState(it) }
            .launchIn(lifecycleScope)
    }

    private fun updateState(state: RepositoryDetailState) = when (state) {
        RepositoryDetailState.Loading -> {
            commitsLabel.isVisible = false
            progressBar.show()
        }
        RepositoryDetailState.DisplayError -> Toast.makeText(
            requireContext(),
            "An error occurred",
            Toast.LENGTH_SHORT
        ).show()
        is RepositoryDetailState.DisplayCommits -> {
            progressBar.hide()
            commitsLabel.isVisible = true
            commitsCounts.text = state.commitViewDatas.sumBy { it.commits.size }.toString()

            // just taking the first for simplicity, now, instead of showing all
            val commitViewData = state.commitViewDatas.first()
            commitBarView.isVisible = true
            commitBarView.commitsPerMonth = CommitsPerMonth(
                nameOfMonth = commitViewData.monthName,
                commitsInMonth = commitViewData.commits.size,
                maximumCommitsInAnyMonth = commitViewData.maxCommitsInAnyMonth
            )
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = args.repository.name
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
