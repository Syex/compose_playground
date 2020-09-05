package de.syex.playground.presentation.repositorylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.syex.playground.R
import de.syex.playground.domain.model.Repository
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repository.view.*
import java.text.SimpleDateFormat
import java.util.*

class RepositoryListAdapter(
    private val onRepoClickCallback: (Repository) -> Unit
) : ListAdapter<Repository, RepositoryListAdapter.ViewHolder>(
    RepoItemCallback()
) {

    private val dateFormat = SimpleDateFormat("dd.MM.yyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
            .let { ViewHolder(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        with(holder.containerView) {
            setOnClickListener { onRepoClickCallback(repo) }
            repoName.text = repo.name
            issueCount.text = repo.openIssuesCount.toString()
            forkCount.text = repo.forksCount.toString()
            licenseName.text = repo.license?.name
            createdAtText.text = dateFormat.format(repo.createdAt)
            updatedAtText.text = dateFormat.format(repo.updatedAt)
        }
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

}

private class RepoItemCallback : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}