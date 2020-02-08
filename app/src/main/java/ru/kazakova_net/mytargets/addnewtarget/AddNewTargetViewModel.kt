package ru.kazakova_net.mytargets.addnewtarget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kazakova_net.mytargets.database.TargetsDatabaseDao

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class AddNewTargetViewModel(
    val targetId: Long,
    val database: TargetsDatabaseDao
) : ViewModel() {

    private val _navigateToParentTarget = MutableLiveData<Boolean?>()
    val navigateToParentTarget: LiveData<Boolean?>
        get() = _navigateToParentTarget

    fun doneParentTargetNavigate() {
        _navigateToParentTarget.value = null
    }

    fun onSaveTargetClicked(){
        _navigateToParentTarget.value = true
    }
}