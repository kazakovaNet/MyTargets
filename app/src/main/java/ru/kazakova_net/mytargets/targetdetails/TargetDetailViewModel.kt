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

    private val _target = MutableLiveData<Target>()
    val target: LiveData<Target>
        get() = _target

    private val _navigateToEditTarget = MutableLiveData<Long?>()
    val navigateToEditTarget: LiveData<Long?>
        get() = _navigateToEditTarget

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onEditTargetClicked(target: Target) {
        _navigateToEditTarget.value = target.targetId
    }

    fun doneEditTargetNavigate() {
        _navigateToEditTarget.value = null
    }
}