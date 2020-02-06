package ru.kazakova_net.mytargets.globaltargets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kazakova_net.mytargets.database.Target

/**
 * Created by Kazakova_net on 05.02.2020.
 */
class GlobalTargetsViewModel : ViewModel() {

    private lateinit var targets: MutableList<Target>

    init {
        resetTargetsList()
    }

    private val _navigateToSubTargets = MutableLiveData<Long>()
    val navigateToSubTargets
        get() = _navigateToSubTargets

    fun resetTargetsList() {
        targets = mutableListOf(
            Target(
                1,
                "Global Target 1",
                "Short description global target 1",
                "Long description global target 1"
            ),
            Target(
                2,
                "Global Target 2",
                "Short description global target 2",
                "Long description global target 2"
            ),
            Target(
                3,
                "Global Target 3",
                "Short description global target 3",
                "Long description global target 3"
            ),
            Target(
                4,
                "Global Target 4",
                "Short description global target 4",
                "Long description global target 4"
            ),
            Target(
                5,
                "Global Target 5",
                "Short description global target 5",
                "Long description global target 5"
            )
        )
    }

    fun onGlobalTargetClicked(targetId: Long) {
        _navigateToSubTargets.value = targetId
    }

    fun onSubTargetsNavigated() {
        _navigateToSubTargets.value = null
    }
}