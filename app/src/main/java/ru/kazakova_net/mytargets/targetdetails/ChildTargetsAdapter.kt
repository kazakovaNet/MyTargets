package ru.kazakova_net.mytargets.targetdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kazakova_net.mytargets.database.MyTarget
import ru.kazakova_net.mytargets.databinding.ListItemChildTargetsBinding

/**
 * Created by Kazakova_net on 06.02.2020.
 */
class ChildTargetsAdapter(val clickListener: ChildTargetsListener) :
    ListAdapter<MyTarget, ChildTargetsAdapter.ViewHolder>(ChildTargetsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemChildTargetsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyTarget, clickListener: ChildTargetsListener) {
            binding.target = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemChildTargetsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ChildTargetsDiffCallback : DiffUtil.ItemCallback<MyTarget>() {

    override fun areItemsTheSame(oldItem: MyTarget, newItem: MyTarget): Boolean {
        return oldItem.targetId == newItem.targetId
    }

    override fun areContentsTheSame(oldItem: MyTarget, newItem: MyTarget): Boolean {
        return oldItem == newItem
    }
}

class ChildTargetsListener(val clickListener: (targetId: Long) -> Unit) {
    fun onClick(target: MyTarget) = clickListener(target.targetId)
}
