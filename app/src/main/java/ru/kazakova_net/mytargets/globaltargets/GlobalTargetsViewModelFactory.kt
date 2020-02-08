package ru.kazakova_net.mytargets.globaltargets

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class GlobalTargetsViewModelFactory(
    private val dataSource: TargetsDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlobalTargetsViewModel::class.java)) {
            return GlobalTargetsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}