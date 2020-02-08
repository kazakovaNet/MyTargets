package ru.kazakova_net.mytargets.globaltargets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kazakova_net.mytargets.database.Target
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 05.02.2020.
 */
class GlobalTargetsViewModel(
    val database: TargetsDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    val targets = database.getAllTargets()

    init {
        resetTargetsList()
    }

    private val _navigateToSubTargets = MutableLiveData<Long>()
    val navigateToSubTargets
        get() = _navigateToSubTargets

    fun resetTargetsList() {
    }

    fun onGlobalTargetClicked(targetId: Long) {
        _navigateToSubTargets.value = targetId
    }

    fun doneSubTargetsNavigate() {
        _navigateToSubTargets.value = null
    }
}