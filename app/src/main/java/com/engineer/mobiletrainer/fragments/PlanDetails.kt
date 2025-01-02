package com.engineer.mobiletrainer.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.adapters.ExerciseAdapter
import com.engineer.mobiletrainer.adapters.PlansAdapter
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory
import kotlinx.coroutines.awaitAll
import java.lang.Thread.sleep

class PlanDetails : Fragment() {

    companion object {
        fun newInstance() = PlanDetails()
    }

    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (requireActivity().application as MobileTrainerApplication).plansRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseRepository,
        (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSetRepository) }
    private var plan = Plans("New Plan")
    private lateinit var planName: TextView
    private lateinit var planDesc: EditText
    private var desc: Editable = Editable.Factory.getInstance().newEditable("")
    private var exerciseList: List<Exercise> = emptyList<Exercise>()
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel


        if(arguments?.isEmpty() == false) {
            plan = arguments?.get("plan") as Plans
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_plan_details, container, false)

        recyclerView = view.findViewById(R.id.planDetails_exerciseRecyclerView)

        planName = view.findViewById(R.id.planDetails_planName)
        planName.text = plan.name

        planDesc = view.findViewById(R.id.planDetails_planDesc)
        desc.append(plan.desc)
        planDesc.text = desc

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

        plansViewModel.getPlanWithExercises(plan.pid)

        plansViewModel.planWithExercises.observe(requireActivity(), Observer { list ->
            if(list.isEmpty()) {
                //do nothing
            } else {
                exerciseList = list[0].exercises
            }
            recyclerView.layoutManager = layoutManager

            exerciseAdapter = ExerciseAdapter(exerciseList)
            recyclerView.adapter = exerciseAdapter
            println(list.size)
            println(list)
            println("Exercise List: " + exerciseList)

            exerciseAdapter.onDeleteClick = {
                exerciseList = exerciseList.minus(it)
                exerciseAdapter.setFilteredList(exerciseList)
            }

            exerciseAdapter.onItemClick = {
                val bundle = bundleOf("exercise" to it)
                view.findNavController().navigate(R.id.action_planDetails_to_exerciseDetails, bundle)
            }
        })




    }
}