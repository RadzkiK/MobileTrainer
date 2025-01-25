package com.engineer.mobiletrainer.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.viewmodels.MainPageViewModel

class MainPage : Fragment() {

    companion object {
        fun newInstance() = MainPage()
    }

    private lateinit var viewModel: MainPageViewModel
    private lateinit var plans_button: Button
    private lateinit var history_button: Button
    private lateinit var calendar_button: Button
    private lateinit var reps_button: Button
    private lateinit var hello: TextView
    private lateinit var lastTraining: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        plans_button = view.findViewById(R.id.mainPage_tile_plans)
        calendar_button = view.findViewById(R.id.mainPage_tile_calendar)
        history_button = view.findViewById(R.id.mainPage_tile_history)
        reps_button = view.findViewById(R.id.mainPage_tile_rep)
        hello = view.findViewById(R.id.mainPage_hello)

        plans_button.setOnClickListener(View.OnClickListener {view ->
            view.findNavController().navigate(R.id.action_mainPage_to_plansFragment)
        })

        reps_button.setOnClickListener(View.OnClickListener {view ->
            view.findNavController().navigate(R.id.action_mainPage_to_exerciseChoice)
        })

        history_button.setOnClickListener(View.OnClickListener {view ->
            view.findNavController().navigate(R.id.action_mainPage_to_exercisesFragment)
        })

        calendar_button.setOnClickListener(View.OnClickListener {view ->
            view.findNavController().navigate(R.id.action_mainPage_to_calendarFragment)
        })

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val userName = sharedPref?.getString(R.string.user_name.toString(), "")

        hello.text = "Hello $userName!"



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
    }

}