package ru.kazakova_net.mytargets.targetdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.kazakova_net.mytargets.database.Target
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by NKazakova on 10.02.2020.
 */
class TargetDetailViewModel(
    val targetId: Long,
    val database: TargetsDatabaseDao
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        uiScope.launch {
            _target.value = withContext(Dispatchers.IO) {
                database.get(targetId)
            }
        }
    }

    val childTargets = database.getChildTargets(targetId)

    private val _target = MutableLiveData<Target>()
    val target: LiveData<Target>
        get() = _target

    private val _navigateToEditTarget = MutableLiveData<Long?>()
    val navigateToEditTarget: LiveData<Long?>
        get() = _navigateToEditTarget

    private val _navigateToAddTarget = MutableLiveData<Long?>()
    val navigateToAddTarget: LiveData<Long?>
        get() = _navigateToAddTarget

    private val _navigateToDetailsChildTarget = MutableLiveData<Long>()
    val navigateToDetailsChildTarget: LiveData<Long>
        get() = _navigateToDetailsChildTarget

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onEditTargetClicked(target: Target) {
        _navigateToEditTarget.value = target.targetId
    }

    fun onAddTargetClicked(target: Target) {
        uiScope.launch {
            val newTarget = Target()
            newTarget.parentId = target.targetId

            insert(newTarget)

            _navigateToAddTarget.value = getNewTargetFromDatabase()?.targetId
        }
    }

    fun onChildTargetClicked(targetId: Long) {
        _navigateToDetailsChildTarget.value = targetId
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

    fun doneEditTargetNavigate() {
        _navigateToEditTarget.value = null
    }

    fun doneAddTargetNavigate() {
        _navigateToAddTarget.value = null
    }

    fun doneDetailsChildTargetNavigate() {
        _navigateToDetailsChildTarget.value = null
    }
}