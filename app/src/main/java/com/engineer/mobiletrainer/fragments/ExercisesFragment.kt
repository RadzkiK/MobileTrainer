package com.engineer.mobiletrainer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.adapters.ExercisesAdapter
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [ExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExercisesFragment : Fragment() {
    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (requireActivity().application as MobileTrainerApplication).plansRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseRepository,
        (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSetRepository,
        (requireActivity().application as MobileTrainerApplication).plansExerciseCrossRefRepository) }
    private lateinit var addButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var exercises: MutableList<Exercise> = emptyList<Exercise>().toMutableList()
    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_exercises, container, false)

        addButton = view.findViewById(R.id.exercises_add)
        recyclerView = view.findViewById(R.id.exercises_recyclerview)
        searchView = view.findViewById(R.id.exercises_searchview)
        addButton.setOnClickListener(View.OnClickListener {view ->
            view.findNavController().navigate(R.id.action_exercisesFragment_to_exerciseDetails)
            println(exercises)
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        exercisesAdapter = ExercisesAdapter(emptyList())
        recyclerView.adapter = exercisesAdapter
        exercisesAdapter.onItemClick = {
            val bundle = bundleOf("exercise" to it)
            view.findNavController().navigate(R.id.action_exercisesFragment_to_exerciseDetails, bundle)
        }
        plansViewModel.allExercises.observe(viewLifecycleOwner, { list ->
            exercises = list
            println(exercises)
            exercisesAdapter.setFilteredList(exercises)
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

            private fun filterList(query: String?) {
                if (query != null) {
                    val filteredList = emptyList<Exercise>().toMutableList()
                    for (i in exercises) {
                        if(i.name?.lowercase(Locale.ROOT)?.contains(query.lowercase()) == true) {
                            filteredList.add(i)
                        }
                    }
                    if(filteredList.isEmpty()) {
                        Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                        exercisesAdapter.setFilteredList(filteredList)
                    }else {
                        exercisesAdapter.setFilteredList(filteredList)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        plansViewModel.getAllExercises()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExercisesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExercisesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}