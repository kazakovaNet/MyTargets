package ru.kazakova_net.mytargets.edittarget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class EditTargetViewModelFactory(
    private val targetId: Long,
    private val dataSource: TargetsDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTargetViewModel::class.java)) {
            return EditTargetViewModel(targetId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}