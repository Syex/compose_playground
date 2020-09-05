package de.syex.playground.presentation.repositorydetail

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import de.syex.playground.R
import kotlinx.android.synthetic.main.view_commit_bar.view.*

class CommitBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var nameOfMonth: String = ""
        set(value) {
            field = value
            monthName.text = value
        }

    init {
        inflate(context, R.layout.view_commit_bar, this)
        orientation = VERTICAL

        commitBar.updateLayoutParams {
            height = 96.toDp(context)
        }
    }
}

private fun Int.toDp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
).toInt()