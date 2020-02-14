package ru.kazakova_net.mytargets.globaltargets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.kazakova_net.mytargets.database.MyTarget
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
    val navigateToSubTargets: LiveData<Long>
        get() = _navigateToSubTargets

    private val _navigateToAddNewTarget = MutableLiveData<Long?>()
    val navigateToAddNewTarget: LiveData<Long?>
        get() = _navigateToAddNewTarget

    private val _refreshTaskList = MutableLiveData<Boolean?>()
    val refreshTaskList: LiveData<Boolean?>
        get() = _refreshTaskList

    fun onGlobalTargetItemClicked(targetId: Long) {
        _navigateToSubTargets.value = targetId

    }

    fun onGlobalTargetCompleteClicked(target: MyTarget) {
        uiScope.launch {
            target.isCompleted = !target.isCompleted

            update(target)

            _refreshTaskList.value = true
        }
    }

    fun doneSubTargetsNavigate() {
        _navigateToSubTargets.value = null
    }

    fun doneAddNewTargetNavigate() {
        _navigateToAddNewTarget.value = null
    }

    fun doneRefreshTaskList() {
        _refreshTaskList.value = null
    }

    fun onAddNewTargetClicked() {
        uiScope.launch {
            val target = MyTarget()

            insert(target)

            _navigateToAddNewTarget.value = getNewTargetFromDatabase()?.targetId
        }
    }

    private suspend fun insert(newTarget: MyTarget) {
        withContext(Dispatchers.IO) {
            database.insert(newTarget)
        }
    }

    private suspend fun update(target: MyTarget) {
        withContext(Dispatchers.IO) {
            database.update(target)
        }
    }

    private suspend fun getNewTargetFromDatabase(): MyTarget? {
        return withContext(Dispatchers.IO) {
            database.getNewTarget()
        }
    }
}