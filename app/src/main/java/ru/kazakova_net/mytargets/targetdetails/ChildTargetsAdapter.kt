package ru.kazakova_net.mytargets.targetdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kazakova_net.mytargets.database.Target
import ru.kazakova_net.mytargets.databinding.ListItemChildTargetsBinding

/**
 * Created by Kazakova_net on 06.02.2020.
 */
class ChildTargetsAdapter(val clickListener: ChildTargetsListener) :
    ListAdapter<Target, ChildTargetsAdapter.ViewHolder>(ChildTargetsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemChildTargetsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Target, clickListener: ChildTargetsListener) {
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

class ChildTargetsDiffCallback : DiffUtil.ItemCallback<Target>() {

    override fun areItemsTheSame(oldItem: Target, newItem: Target): Boolean {
        return oldItem.targetId == newItem.targetId
    }

    override fun areContentsTheSame(oldItem: Target, newItem: Target): Boolean {
        return oldItem == newItem
    }
}

class ChildTargetsListener(val clickListener: (targetId: Long) -> Unit) {
    fun onClick(target: Target) = clickListener(target.targetId)
}
