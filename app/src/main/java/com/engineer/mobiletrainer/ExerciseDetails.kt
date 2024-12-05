package com.engineer.mobiletrainer

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.adapters.ExerciseAdapter
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.fragments.PlanDetails
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseDetails : Fragment() {
    companion object {
        fun newInstance() = ExerciseDetails()
    }

    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (requireActivity().application as MobileTrainerApplication).plansRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseRepository,
        (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSetRepository) }
    private var exercise = Exercise("New Exercise")
    private lateinit var exerciseName: TextView
    private lateinit var exerciseDesc: EditText
    private var desc: Editable = Editable.Factory.getInstance().newEditable("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(arguments?.isEmpty == false) {
            exercise = arguments?.get("exercise") as Exercise
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_exercise_details, container, false)

        exerciseName = view.findViewById(R.id.exerciseDetails_name)
        exerciseName.text = exercise.name

        exerciseDesc = view.findViewById(R.id.exerciseDetails_desc)
        desc.append(exercise.desc)
        exerciseDesc.text = desc

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)


    }
}