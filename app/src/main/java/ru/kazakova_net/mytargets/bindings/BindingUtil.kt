package ru.kazakova_net.mytargets.bindings

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.kazakova_net.mytargets.R
import ru.kazakova_net.mytargets.database.MyTarget

/**
 * Created by NKazakova on 13.02.2020.
 */

@BindingAdapter("childTargetsCountString")
fun TextView.setChildTargetsCountString(item: MyTarget?) {
    item?.let {
        text = context.getString(R.string.child_targets_count, it.childCount)
    }
}

@BindingAdapter("targetIsCompletedImage")
fun ImageView.setTargetIsCompletedImage(item: MyTarget?) {
    item?.let {
        setImageResource(
            when (it.isCompleted) {
                true -> R.drawable.ic_check_circle
                false -> R.drawable.ic_crop_square
            }
        )
    }
}