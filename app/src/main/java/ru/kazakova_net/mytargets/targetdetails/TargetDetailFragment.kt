package ru.kazakova_net.mytargets.targetdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kazakova_net.mytargets.R
import ru.kazakova_net.mytargets.database.TargetsDatabase
import ru.kazakova_net.mytargets.databinding.FragmentTargetDetailBinding


/**
 * Created by NKazakova on 10.02.2020.
 */
class TargetDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTargetDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_target_detail, container, false
        )

        val viewModel = setupViewModel()

        binding.targetDetailViewModel = viewModel
        binding.lifecycleOwner = this

        val layoutManager = LinearLayoutManager(context)
        binding.childTargetsList.layoutManager=layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            binding.childTargetsList.context,
            layoutManager.orientation
        )
        binding.childTargetsList.addItemDecoration(dividerItemDecoration)

        val adapter = ChildTargetsAdapter(ChildTargetsListener { targetId ->
            viewModel.onChildTargetClicked(targetId)
        })

        binding.childTargetsList.adapter = adapter

        viewModel.target.observe(viewLifecycleOwner, Observer { target ->
            binding.target = target
            this.activity?.title = target.title
        })

        viewModel.navigateToEditTarget.observe(viewLifecycleOwner, Observer { targetId ->
            targetId?.let {
                this.findNavController().navigate(
                    TargetDetailFragmentDirections
                        .actionTargetDetailFragmentToEditTargetFragment(targetId)
                )

                viewModel.doneEditTargetNavigate()
            }
        })

        viewModel.navigateToAddTarget.observe(viewLifecycleOwner, Observer { targetId ->
            targetId?.let {
                this.findNavController().navigate(
                    TargetDetailFragmentDirections
                        .actionTargetDetailFragmentToAddTargetFragment(targetId)
                )

                viewModel.doneAddTargetNavigate()
            }
        })

        viewModel.navigateToDetailsChildTarget.observe(viewLifecycleOwner, Observer { targetId ->
            targetId?.let {
                this.findNavController().navigate(
                    TargetDetailFragmentDirections
                        .actionTargetDetailFragmentSelf(targetId)
                )

                viewModel.doneDetailsChildTargetNavigate()
            }
        })

        viewModel.childTargets.observe(viewLifecycleOwner, Observer { targets ->
            targets?.let {
                adapter.submitList(targets)
            }
        })

        return binding.root
    }

    private fun setupViewModel(): TargetDetailViewModel {
        val arguments = TargetDetailFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = TargetsDatabase.getInstance(application).targetsDatabaseDao
        val viewModelFactory = TargetDetailViewModelFactory(arguments.targetId, dataSource)
        return ViewModelProvider(this, viewModelFactory)
            .get(TargetDetailViewModel::class.java)
    }
}