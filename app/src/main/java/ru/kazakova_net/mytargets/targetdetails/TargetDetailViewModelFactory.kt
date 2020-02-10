package ru.kazakova_net.mytargets.targetdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by NKazakova on 10.02.2020.
 */
class TargetDetailViewModelFactory(
    private val targetId: Long,
    private val dataSource: TargetsDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TargetDetailViewModel::class.java)) {
            return TargetDetailViewModel(targetId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}