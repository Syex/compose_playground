package de.syex.playground.presentation.repositorydetail

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import de.syex.playground.R
import kotlinx.android.synthetic.main.view_commit_bar.view.*

private const val MAX_HEIGHT_IN_DP = 160f

class CommitBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var commitsPerMonth: CommitsPerMonth = CommitsPerMonth()
        set(value) {
            field = value
            monthName.text = value.nameOfMonth

            commitBar.updateLayoutParams {
                val percentageOfMaxCommits =
                    value.commitsInMonth.toFloat() / value.maximumCommitsInAnyMonth
                val heightForCommits = MAX_HEIGHT_IN_DP * percentageOfMaxCommits
                height = heightForCommits.toInt().toDp(context)
            }
        }

    init {
        inflate(context, R.layout.view_commit_bar, this)
        orientation = VERTICAL

        commitBar.updateLayoutParams {
            height = 96.toDp(context)
        }
    }
}

data class CommitsPerMonth(
    val nameOfMonth: String = "",
    val commitsInMonth: Int = 0,
    val maximumCommitsInAnyMonth: Int = Int.MAX_VALUE
)

private fun Int.toDp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
).toInt()