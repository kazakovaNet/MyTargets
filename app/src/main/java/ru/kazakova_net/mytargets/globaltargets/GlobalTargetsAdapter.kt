package ru.kazakova_net.mytargets.globaltargets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kazakova_net.mytargets.database.MyTarget
import ru.kazakova_net.mytargets.databinding.ListItemGlobalTargetsBinding

/**
 * Created by Kazakova_net on 06.02.2020.
 */
class GlobalTargetsAdapter(
    private val itemClickListener: GlobalTargetsItemListener,
    private val completeListener: GlobalTargetsCompleteListener
) :
    ListAdapter<MyTarget, GlobalTargetsAdapter.ViewHolder>(GlobalTargetsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener, completeListener)
    }

    class ViewHolder private constructor(val binding: ListItemGlobalTargetsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: MyTarget,
            clickItemListener: GlobalTargetsItemListener,
            completeListener: GlobalTargetsCompleteListener
        ) {
            binding.target = item
            binding.clickItemListener = clickItemListener
            binding.completeClickListener = completeListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGlobalTargetsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class GlobalTargetsDiffCallback : DiffUtil.ItemCallback<MyTarget>() {

    override fun areItemsTheSame(oldItem: MyTarget, newItem: MyTarget): Boolean {
        return oldItem.targetId == newItem.targetId
    }

    override fun areContentsTheSame(oldItem: MyTarget, newItem: MyTarget): Boolean {
        return oldItem == newItem
    }
}

class GlobalTargetsItemListener(val itemClickListener: (targetId: Long) -> Unit) {
    fun onClick(target: MyTarget) = itemClickListener(target.targetId)
}

class GlobalTargetsCompleteListener(val clickListener: (target: MyTarget) -> Unit) {
    fun onClick(target: MyTarget) = clickListener(target)
}