package ru.kazakova_net.mytargets.addnewtarget

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
import ru.kazakova_net.mytargets.databinding.FragmentAddNewTargetBinding
import ru.kazakova_net.mytargets.utility.hideKeyboard

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class AddNewTargetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddNewTargetBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_new_target, container, false
        )

        val viewModel = setupViewModel()

        binding.addNewTargetViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToParentTarget.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                activity?.let { fragmentActivity -> hideKeyboard(fragmentActivity) }

                this.findNavController().navigate(AddNewTargetFragmentDirections
                    .actionAddNewTargetFragmentToGlobalTargetsFragment())

                viewModel.doneParentTargetNavigate()
            }
        })

        viewModel.newTarget.observe(viewLifecycleOwner, Observer { newTarget->
            binding.newTarget = newTarget
        })

        return binding.root
    }

    private fun setupViewModel(): AddNewTargetViewModel {
        val arguments = AddNewTargetFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = TargetsDatabase.getInstance(application).targetsDatabaseDao
        val viewModelFactory = AddNewTargetViewModelFactory(arguments.targetId, dataSource)
        return ViewModelProvider(this, viewModelFactory)
            .get(AddNewTargetViewModel::class.java)
    }
}