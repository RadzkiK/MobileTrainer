package com.engineer.mobiletrainer.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithPlans
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseDetails : Fragment() {
    companion object {
        fun newInstance() = ExerciseDetails()
    }

    private val plansViewModel: PlansViewModel by viewModels {
        PlansViewModelFactory(
            (requireActivity().application as MobileTrainerApplication).plansRepository,
            (requireActivity().application as MobileTrainerApplication).exerciseRepository,
            (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
            (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
            (requireActivity().application as MobileTrainerApplication).exerciseSetRepository,
            (requireActivity().application as MobileTrainerApplication).plansExerciseCrossRefRepository
        )
    }
    private var exercise = Exercise("New Exercise")
    private lateinit var exerciseName: TextView
    private lateinit var exerciseDesc: EditText
    private lateinit var deleteButton: ImageButton
    private lateinit var saveButton: ImageButton
    private var desc: Editable = Editable.Factory.getInstance().newEditable("")
    private var isSaved = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments?.isEmpty == false) {
            exercise = arguments?.get("exercise") as Exercise
            isSaved = true
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

        saveButton = view.findViewById(R.id.exerciseDetails_saveButton)
        saveButton.setOnClickListener(View.OnClickListener {
            if(isSaved) {
                updateExercise()
            } else {
                saveExercise()
            }
        })

        deleteButton = view.findViewById(R.id.exerciseDetails_deleteButton)
        deleteButton.setOnClickListener(View.OnClickListener {
            if(isSaved) {
                deleteExerciseWithPlans(view)
                plansViewModel.deleteExercise(exercise.name!!).invokeOnCompletion {
                    Log.d(
                        R.string.tag_database_change.toString(),
                        "Exercise: ${exercise.name} has been deleted."
                    )
                }
            } else {
                Toast.makeText(context, "Exercise isn't saved yet", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

    }

    private fun saveExercise() {
        if(exerciseName.text.isEmpty()) {
            Toast.makeText(context, "Exercise name can't be empty!", Toast.LENGTH_SHORT).show()
        } else {
            exercise.name = exerciseName.text.toString()
        }
        if(exerciseDesc.text.isEmpty()) {
            Toast.makeText(context, "Exercise description can't be empty!", Toast.LENGTH_SHORT).show()
        } else {
            exercise.desc = exerciseDesc.text.toString()
        }
        if(exercise.name?.isEmpty() == false && exercise.desc.isNotEmpty()) {
            plansViewModel.insertExercise(exercise).invokeOnCompletion {
                Log.d(R.string.tag_database_change.toString(), "Exercise: $exercise has been saved")
                plansViewModel.getExercise(exercise.name!!).invokeOnCompletion {
                    exercise = plansViewModel.exercise
                    Log.d("ExerciseDetails", "Exercise: $exercise has eid: ${exercise.eid}")
                }
            }
        } else {
            Toast.makeText(context, "Saving exercise ended unsuccessfully.", Toast.LENGTH_SHORT).show()
            Log.d("ExerciseDetails", "Couldn't save exercise!")
        }
        isSaved = true
    }

    private fun updateExercise() {
        if(exerciseName.text.isEmpty()) {
            Toast.makeText(context, "Exercise name can't be empty!", Toast.LENGTH_SHORT).show()
        } else {
            exercise.name = exerciseName.text.toString()
        }
        if(exerciseDesc.text.isEmpty()) {
            Toast.makeText(context, "Exercise description can't be empty!", Toast.LENGTH_SHORT).show()
        } else {
            exercise.desc = exerciseDesc.text.toString()
        }
        if(exercise.name?.isEmpty() == false && exercise.desc.isNotEmpty()) {
            plansViewModel.updateExercise(exercise).invokeOnCompletion {
                Log.d(
                    R.string.tag_database_change.toString(),
                    "Exercise: $exercise has been updated"
                )
            }
        } else {
            Toast.makeText(context, "Saving exercise ended unsuccessfully.", Toast.LENGTH_SHORT).show()
            Log.d("ExerciseDetails", "Couldn't save exercise!")
        }
    }

    private fun deleteExerciseWithPlans(view: View) {
        var crossRef: PlansExerciseCrossRef
        var exerciseWithPlans: ExerciseWithPlans
        var deletedPlansExerciseCrossRef: MutableList<PlansExerciseCrossRef> = emptyList<PlansExerciseCrossRef>().toMutableList()
        plansViewModel.getExerciseWithPlans(exercise.eid).invokeOnCompletion {
            if(plansViewModel.exerciseWithPlans.isNotEmpty()) {
                exerciseWithPlans = plansViewModel.exerciseWithPlans[0]
                for(plan in exerciseWithPlans.plans) {
                    plansViewModel.getExerciseFromPlan2(exercise.eid, plan.pid).invokeOnCompletion {
                        crossRef = plansViewModel.pecr
                        Log.d("ExerciseDetails", "CrossRef from pecr: $crossRef")
                        deletedPlansExerciseCrossRef.add(crossRef)
                        plansViewModel.deletePlanExerciseCrossRef(crossRef).invokeOnCompletion {
                            deletedPlansExerciseCrossRef = deletedPlansExerciseCrossRef.minus(crossRef).toMutableList()
                            Log.d(
                                R.string.tag_database_change.toString(),
                                "PlansExereciseCrossRef with eid: ${crossRef.eid} and pid: ${crossRef.pid} has been deleted."
                            )
                        }
                        view.findNavController().popBackStack()
                    }
                }
            } else {
                Log.d(
                    "ExerciseDetails",
                    "exerciseWithPlans is empty, didn't find any plans connected with exercise: $exercise"
                )
                view.findNavController().popBackStack()
            }
        }

    }
}