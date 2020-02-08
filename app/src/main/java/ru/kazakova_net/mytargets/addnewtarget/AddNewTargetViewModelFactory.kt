package ru.kazakova_net.mytargets.addnewtarget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class AddNewTargetViewModelFactory(
    private val targetId: Long,
    private val dataSource: TargetsDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewTargetViewModel::class.java)) {
            return AddNewTargetViewModel(targetId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}