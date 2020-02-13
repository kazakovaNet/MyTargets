package ru.kazakova_net.mytargets.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.kazakova_net.mytargets.R
import ru.kazakova_net.mytargets.database.Target

/**
 * Created by NKazakova on 13.02.2020.
 */

@BindingAdapter("childTargetsCountString")
fun TextView.setChildTargetsCountString(item: Target?) {
    item?.let {
        text = context.getString(R.string.child_targets_count, it.childCount)
    }
}