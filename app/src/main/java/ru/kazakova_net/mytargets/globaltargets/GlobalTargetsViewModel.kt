package ru.kazakova_net.mytargets.globaltargets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.kazakova_net.mytargets.database.Target
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 05.02.2020.
 */
class GlobalTargetsViewModel(
    val database: TargetsDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val targets = database.getAllGlobalTargets()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToSubTargets = MutableLiveData<Long>()
    val navigateToSubTargets
        get() = _navigateToSubTargets

    private val _navigateToAddNewTarget = MutableLiveData<Long?>()
    val navigateToAddNewTarget
        get() = _navigateToAddNewTarget

    fun onGlobalTargetClicked(targetId: Long) {
        _navigateToSubTargets.value = targetId
    }

    fun doneSubTargetsNavigate() {
        _navigateToSubTargets.value = null
    }

    fun onAddNewTargetClicked() {
        uiScope.launch {
            val target = Target()

            insert(target)

            _navigateToAddNewTarget.value = getNewTargetFromDatabase()?.targetId
        }
    }

    private suspend fun insert(newTarget: Target) {
        withContext(Dispatchers.IO) {
            database.insert(newTarget)
        }
    }

    private suspend fun getNewTargetFromDatabase(): Target? {
        return withContext(Dispatchers.IO) {
            database.getNewTarget()
        }
    }

    fun doneAddNewTargetNavigate() {
        _navigateToAddNewTarget.value = null
    }
}