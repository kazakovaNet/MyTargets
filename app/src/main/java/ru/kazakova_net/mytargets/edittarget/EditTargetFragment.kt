package ru.kazakova_net.mytargets.edittarget

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
import ru.kazakova_net.mytargets.databinding.FragmentEditTargetBinding
import ru.kazakova_net.mytargets.utility.hideKeyboard

/**
 * Created by Kazakova_net on 08.02.2020.
 */
class EditTargetFragment : Fragment() {

    private var isEdit: Boolean = false
    private var targetId: Long = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditTargetBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_target, container, false
        )

        val viewModel = setupViewModel()

        binding.editTargetViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToInitialTarget.observe(viewLifecycleOwner, Observer {
            if (it != true) {
                return@Observer
            }
            activity?.let { fragmentActivity -> hideKeyboard(fragmentActivity) }

            if (isEdit) {
                this.findNavController().navigate(
                    EditTargetFragmentDirections
                        .actionEditTargetFragmentToTargetDetailFragment(targetId)
                )
            } else {
                this.findNavController().navigate(
                    EditTargetFragmentDirections
                        .actionEditTargetFragmentToGlobalTargetsFragment()
                )
            }

            viewModel.doneParentTargetNavigate()
        })

        viewModel.newTarget.observe(viewLifecycleOwner, Observer { newTarget ->
            binding.newTarget = newTarget
        })

        return binding.root
    }

    private fun setupViewModel(): EditTargetViewModel {
        val arguments = EditTargetFragmentArgs.fromBundle(arguments!!)
        isEdit = arguments.isEdit
        targetId = arguments.targetId
        val application = requireNotNull(this.activity).application
        val dataSource = TargetsDatabase.getInstance(application).targetsDatabaseDao
        val viewModelFactory = EditTargetViewModelFactory(targetId, dataSource)
        return ViewModelProvider(this, viewModelFactory)
            .get(EditTargetViewModel::class.java)
    }
}