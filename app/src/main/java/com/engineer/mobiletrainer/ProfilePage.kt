package com.engineer.mobiletrainer

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.engineer.mobiletrainer.database.entity.Profile
import com.engineer.mobiletrainer.viewmodels.ProfileViewModel
import java.lang.NumberFormatException
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfilePage : Fragment() {

    companion object {
        fun newInstance() = ProfilePage()
    }

    private val viewModel: ProfileViewModel by activityViewModels()
    private var profileList: List<Profile> = emptyList()
    private var profile: Profile = Profile("", "")
    private lateinit var textDate: TextView
    private lateinit var sexSpinner: Spinner
    private lateinit var textName: TextView
    private lateinit var textSurname: TextView
    private lateinit var textHeight: TextView
    private lateinit var textWeight: TextView
    private lateinit var saveButton: Button
    private var name: String = ""
    private var surname: String = ""
    private var height: Int? = null
    private var weight: Int? = null
    private var birthDate: Date? = null
    private var sexValue: Int? = null
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        textDate = view.findViewById<TextView>(R.id.profile_date_touch)
        sexSpinner = view.findViewById<Spinner>(R.id.profile_spn_sex)
        textName = view.findViewById<TextView>(R.id.profile_name)
        textSurname = view.findViewById<TextView>(R.id.profile_surname)
        textHeight = view.findViewById<TextView>(R.id.profile_height)
        textWeight = view.findViewById<TextView>(R.id.profile_weight)
        saveButton = view.findViewById<Button>(R.id.profile_save_button)

        initSpinner()

        textDate.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })

        viewModel.allProfiles.observe(requireActivity(), Observer { profiles ->
            profileList = profiles
            profile = profileList[0]

            name = profile.name.toString()
            surname = profile.surname.toString()
            height = profile.height
            weight = profile.weight
            birthDate = Date(profile.birthDate)
            sexValue = profile.sex

            if(sexValue != null) {
                sexSpinner.setSelection(sexValue!!)
            }

            textName.text = name
            textSurname.text = surname
            textHeight.text = height.toString()
            textWeight.text = weight.toString()
            if(birthDate != null) {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(birthDate!!.time)
                textDate.text = formattedDate
            }


        })

        saveButton.setOnClickListener(View.OnClickListener { view ->
            save()
            view.findNavController().navigate(R.id.action_global_mainPage)
        })

        return view
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                birthDate = selectedDate.time as Date?
                textDate.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sex_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            sexSpinner.adapter = adapter
            sexSpinner.onItemSelectedListener = changeSexListener
        }
    }

    private var changeSexListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // do nothing
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            sexValue = position
        }
    }

    private fun save() {
        profile.name = textName.text.toString()
        profile.surname = textSurname.text.toString()
        try {
            profile.height = textHeight.text.toString().toInt()
            profile.weight = textWeight.text.toString().toInt()
        } catch (e: NumberFormatException) {

        }
        profile.birthDate = birthDate!!.time
        profile.sex = sexValue!!

        viewModel.update(profile)
    }
}
