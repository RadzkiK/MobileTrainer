package com.engineer.mobiletrainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.findNavController

class ExerciseChoice : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_choice, container, false)

        view.findViewById<LinearLayout>(R.id.squatView).setOnClickListener(View.OnClickListener() {
            view -> view.findNavController().navigate(R.id.action_exerciseChoice_to_trainingActivity)
        })

        return view
    }
}