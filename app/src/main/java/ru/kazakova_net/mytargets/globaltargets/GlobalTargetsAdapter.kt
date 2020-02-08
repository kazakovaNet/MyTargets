package ru.kazakova_net.mytargets.globaltargets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kazakova_net.mytargets.database.Target
import ru.kazakova_net.mytargets.databinding.ListItemGlobalTargetsBinding

/**
 * Created by Kazakova_net on 06.02.2020.
 */
class GlobalTargetsAdapter(val clickListener: GlobalTargetsListener) :
    ListAdapter<Target, GlobalTargetsAdapter.ViewHolder>(GlobalTargetsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemGlobalTargetsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Target, clickListener: GlobalTargetsListener) {
            binding.target = item
            binding.clickListener = clickListener
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

class GlobalTargetsDiffCallback : DiffUtil.ItemCallback<Target>() {

    override fun areItemsTheSame(oldItem: Target, newItem: Target): Boolean {
        return oldItem.targetId == newItem.targetId
    }

    override fun areContentsTheSame(oldItem: Target, newItem: Target): Boolean {
        return oldItem == newItem
    }
}

class GlobalTargetsListener(val clickListener: (targetId: Long) -> Unit) {
    fun onClick(target: Target) = clickListener(target.targetId)
}
