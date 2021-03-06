package ru.kazakova_net.mytargets.edittarget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.kazakova_net.mytargets.database.MyTarget
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class EditTargetViewModel(
    val newTargetId: Long,
    val database: TargetsDatabaseDao
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        uiScope.launch {
            _newTarget.value = withContext(Dispatchers.IO) {
                database.get(newTargetId)
            }
        }
    }

    private val _newTarget = MutableLiveData<MyTarget>()
    val newTarget: LiveData<MyTarget>
        get() = _newTarget

    private val _navigateToParentTarget = MutableLiveData<Boolean?>()
    val navigateToInitialTarget: LiveData<Boolean?>
        get() = _navigateToParentTarget

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneToInitialTargetNavigate() {
        _navigateToParentTarget.value = null
    }

    fun onSaveTargetClicked(target: MyTarget) {
        uiScope.launch {
            update(target)

            _navigateToParentTarget.value = true
        }
    }

    private suspend fun update(target: MyTarget) {
        withContext(Dispatchers.IO) {
            database.update(target)
        }
    }
}