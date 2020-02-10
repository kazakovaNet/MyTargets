package ru.kazakova_net.mytargets.globaltargets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kazakova_net.mytargets.R
import ru.kazakova_net.mytargets.database.TargetsDatabase
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

        val viewModel = setupViewModel()

        binding.globalTargetsViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = GlobalTargetsAdapter(GlobalTargetsListener { targetId ->
            viewModel.onGlobalTargetClicked(targetId)
        })

        binding.globalTargetsList.adapter = adapter

        viewModel.navigateToSubTargets.observe(viewLifecycleOwner, Observer { targetId ->
            targetId?.let {
                this.findNavController().navigate(
                    GlobalTargetsFragmentDirections
                        .actionGlobalTargetsFragmentToTargetDetailFragment(targetId)
                )

                viewModel.doneSubTargetsNavigate()
            }
        })

        viewModel.targets.observe(viewLifecycleOwner, Observer { targets ->
            targets?.let {
                adapter.submitList(targets)
            }
        })

        viewModel.navigateToAddNewTarget.observe(viewLifecycleOwner, Observer { newTargetId ->
            newTargetId?.let {
                this.findNavController().navigate(
                    GlobalTargetsFragmentDirections
                        .actionGlobalTargetsFragmentToAddNewTargetFragment(newTargetId)
                )

                viewModel.doneAddNewTargetNavigate()
            }
        })

        return binding.root
    }

    private fun setupViewModel(): GlobalTargetsViewModel {
        val application = requireNotNull(this.activity).application
        val dataSource = TargetsDatabase.getInstance(application).targetsDatabaseDao
        val viewModelFactory = GlobalTargetsViewModelFactory(dataSource, application)
        return ViewModelProvider(this, viewModelFactory)
            .get(GlobalTargetsViewModel::class.java)
    }
}