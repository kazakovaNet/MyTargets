package ru.kazakova_net.mytargets.globaltargets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kazakova_net.mytargets.R
import ru.kazakova_net.mytargets.databinding.FragmentGlobalTargetsBinding

/**
 * Created by Kazakova_net on 05.02.2020.
 */
class GlobalTargetsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGlobalTargetsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_global_targets, container, false
        )

        val viewModel = ViewModelProvider(this).get(GlobalTargetsViewModel::class.java)

        binding.globalTargetsViewModel = viewModel

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}