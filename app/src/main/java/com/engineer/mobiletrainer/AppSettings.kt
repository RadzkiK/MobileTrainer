package com.engineer.mobiletrainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.engineer.mobiletrainer.database.entity.Settings
import com.engineer.mobiletrainer.viewmodels.SettingsViewModel
import com.engineer.mobiletrainer.viewmodels.SettingsViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AppSettings.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppSettings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var spnModel: Spinner
    private lateinit var saveButton: Button
    private var modelPos = 1
    private var settingsList: List<Settings> = emptyList()
    private val settingsViewModel: SettingsViewModel by viewModels { SettingsViewModelFactory((requireActivity().application as MobileTrainerApplication).settingsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        spnModel = view.findViewById(R.id.spnModel2)
        saveButton = view.findViewById(R.id.settingsButton)

        initSpinner()

        settingsViewModel.allSettings.observe(requireActivity(), Observer { settings ->
            settingsList = settings
            val setting = settingsList.find { it.setting?.equals(R.string.setting_1.toString()) == true }

            if(setting?.value != null) {
                modelPos = setting.value
            }
            println("model is:$modelPos")
            spnModel.setSelection(modelPos)
        })

        saveButton.setOnClickListener(View.OnClickListener() {view2 ->
            settingsViewModel.insert(Settings(R.string.setting_1.toString(), modelPos))
            view2.findNavController().navigate(R.id.action_global_mainPage)
        })


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AppSettings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var changeModelListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // do nothing
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            changeModel(position)
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tfe_pe_models_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnModel.adapter = adapter
            spnModel.onItemSelectedListener = changeModelListener
        }

    }

    private fun changeModel(position: Int) {
        if (modelPos == position) return
        modelPos = position
    }
}