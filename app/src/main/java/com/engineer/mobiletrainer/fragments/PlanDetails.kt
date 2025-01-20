package com.engineer.mobiletrainer.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.adapters.ExerciseAdapter
import com.engineer.mobiletrainer.adapters.ExercisesAdapter
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.TrainingSession
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

class PlanDetails : Fragment() {

    companion object {
        fun newInstance() = PlanDetails()
    }

    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (requireActivity().application as MobileTrainerApplication).plansRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseRepository,
        (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSetRepository,
        (requireActivity().application as MobileTrainerApplication).plansExerciseCrossRefRepository)}
    private var plan = Plans("New Plan")
    private lateinit var planName: TextView
    private lateinit var planDesc: EditText
    private var desc: Editable = Editable.Factory.getInstance().newEditable("")
    private var exerciseList: MutableList<Exercise> = emptyList<Exercise>().toMutableList()
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var deleteButton: ImageButton
    private lateinit var saveButton: ImageButton
    private lateinit var addButton: ImageButton
    private lateinit var addToCalendarButton: ImageButton
    private var isSaved = false
    private var deletedExercises: MutableList<Exercise> = emptyList<Exercise>().toMutableList()
    private var deletedPlansExerciseCrossRef: MutableList<PlansExerciseCrossRef> = emptyList<PlansExerciseCrossRef>().toMutableList()
    private var addedExercises: MutableList<Exercise> = emptyList<Exercise>().toMutableList()
    private var crossRef: PlansExerciseCrossRef = PlansExerciseCrossRef(0,0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel


        if(arguments?.isEmpty() == false) {
            plan = arguments?.get("plan") as Plans
            isSaved = true
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

        addToCalendarButton = view.findViewById(R.id.planDetails_addToCalendar)
        addToCalendarButton.setOnClickListener(View.OnClickListener {
            showDatePickerDialog()
        })

        addButton = view.findViewById(R.id.plansDetails_addExercise)
        addButton.setOnClickListener(View.OnClickListener {

            var exercises: List<Exercise> = emptyList<Exercise>()
            var exercisesAdapter: ExercisesAdapter
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.add_exercise)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val dialogSearchView = dialog.findViewById<SearchView>(R.id.add_exercise_searchview)
            val dialogRecyclerView = dialog.findViewById<RecyclerView>(R.id.add_exercise_recyclerview)

            val layoutManager = LinearLayoutManager(context)

            dialogRecyclerView.layoutManager = layoutManager
            exercisesAdapter = ExercisesAdapter(emptyList())
            dialogRecyclerView.adapter = exercisesAdapter
            exercisesAdapter.onItemClick = {
                addedExercises.add(it)
                exerciseList.add(it)
                exerciseAdapter.setFilteredList(exerciseList)
                dialog.hide()
            }
            plansViewModel.allExercises.observe(viewLifecycleOwner, { list ->
                exercises = list
                println(exercises)
                exercisesAdapter.setFilteredList(exercises)
            })

            dialogSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
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

            dialog.show()
        })

        deleteButton = view.findViewById(R.id.planDetails_deletePlan)
        deleteButton.setOnClickListener(View.OnClickListener {
            if(isSaved) {
                getCrossRefForAllExercises()
                plansViewModel.deletePlan(plan.name!!).invokeOnCompletion {
                    Log.d(R.string.tag_database_change.toString(), "Plan: ${plan.name} has been deleted.")
                    view.findNavController().popBackStack()
                }
            } else {
                Toast.makeText(context, "Plan isn't saved yet", Toast.LENGTH_SHORT).show()
            }
        })

        saveButton = view.findViewById(R.id.planDetails_savePlan)
        saveButton.setOnClickListener(View.OnClickListener {
            if(isSaved) {
                if(planName.text.isEmpty()) {
                    Toast.makeText(context, "Plan name can't be empty!", Toast.LENGTH_SHORT).show()
                } else {
                    plan.name = planName.text.toString()
                }
                if(planDesc.text.isEmpty()) {
                    Toast.makeText(context, "Plan description can't be empty!", Toast.LENGTH_SHORT).show()
                } else {
                    plan.desc = planDesc.text.toString()
                }
                if(exerciseList.isEmpty()) {
                    Toast.makeText(context, "List of exercises can't be empty!", Toast.LENGTH_SHORT).show()
                }
                if(plan.name?.isEmpty() == false && plan.desc.isNotEmpty() && exerciseList.isNotEmpty()) {
                    while(deletedPlansExerciseCrossRef.isNotEmpty()) {
                        for (ref in deletedPlansExerciseCrossRef) {
                            Log.d("PlanDetails","deletedPlansExerciseCrossRef: $deletedPlansExerciseCrossRef")
                            Log.d("PlanDetails","CrossRef to delete: $ref")
                            deletedPlansExerciseCrossRef = deletedPlansExerciseCrossRef.minus(ref).toMutableList()

                            plansViewModel.deletePlanExerciseCrossRef(ref).invokeOnCompletion {
                                Log.d(R.string.tag_database_change.toString(), "PlansExereciseCrossRef with eid: ${ref.eid} and pid: ${ref.pid} has been deleted.")
                            }
                        }
                    }
                    plansViewModel.updatePlan(plan).invokeOnCompletion {
                        for (exercise in addedExercises) {
                            plansViewModel.insertPlanExerciseCrossRef(PlansExerciseCrossRef(plan.pid, exercise.eid)).invokeOnCompletion {
                                Log.d(R.string.tag_database_change.toString(), "PlansExerciseCrossRef has ben added: pid: ${plan.pid} eid: ${exercise.eid}")
                            }
                        }
                        Log.d(R.string.tag_database_change.toString(), "Plan: $plan has been saved")
                    }
                } else {
                    Toast.makeText(context, "Saving plan ended unsuccessfully.", Toast.LENGTH_SHORT).show()
                    Log.d("PlanDetails", "Couldn't save plan!")
                }
            } else {
                if(planName.text.isEmpty()) {
                    Toast.makeText(context, "Plan name can't be empty!", Toast.LENGTH_SHORT).show()
                } else {
                    plan.name = planName.text.toString()
                }
                if(planDesc.text.isEmpty()) {
                    Toast.makeText(context, "Plan description can't be empty!", Toast.LENGTH_SHORT).show()
                } else {
                    plan.desc = planDesc.text.toString()
                }
                if(exerciseList.isEmpty()) {
                    Toast.makeText(context, "List of exercises can't be empty!", Toast.LENGTH_SHORT).show()
                }
                if(plan.name?.isEmpty() == false && plan.desc.isNotEmpty() && exerciseList.isNotEmpty()) {
                    plansViewModel.insertPlan(plan).invokeOnCompletion {
                        Log.d(R.string.tag_database_change.toString(), "Plan: $plan has been saved")
                        plansViewModel.getPlan(plan.name!!).invokeOnCompletion {
                            plan = plansViewModel.plan
                            Log.d("PlanDetails", "Plan: $plan has pid: ${plan.pid}")
                            for (exercise in exerciseList) {
                                plansViewModel.insertPlanExerciseCrossRef(PlansExerciseCrossRef(plan.pid, exercise.eid)).invokeOnCompletion {
                                    Log.d(R.string.tag_database_change.toString(), "PlansExerciseCrossRef has ben added: pid: ${plan.pid} eid: ${exercise.eid}")
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Saving plan ended unsuccessfully.", Toast.LENGTH_SHORT).show()
                    Log.d("PlanDetails", "Couldn't save plan!")
                }
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

        plansViewModel.getPlanWithExercises(plan.pid)

        recyclerView.layoutManager = layoutManager
        exerciseAdapter = ExerciseAdapter(exerciseList)
        recyclerView.adapter = exerciseAdapter
        plansViewModel.planWithExercises.observe(requireActivity(), Observer { list ->
            if(list.isEmpty()) {
                //do nothing
            } else {
                exerciseList = list[0].exercises.toMutableList()
            }

            Log.d("PlanDetails", "list size: ${list.size.toString()}")
            Log.d("PlanDetails", "list: " + list.toString())
            Log.d("PlanDetails","Exercise List: " + exerciseList)

            exerciseAdapter.onDeleteClick = {
                Log.d("PlanDetails", "Exercise $it clicked to deleted from $plan")
                plansViewModel.getExerciseFromPlan2(it.eid, plan.pid).invokeOnCompletion {
                    crossRef = plansViewModel.pecr
                    Log.d("PlanDetails","CrossRef from pecr: $crossRef")
                    deletedPlansExerciseCrossRef.add(crossRef)
                }
                exerciseList = exerciseList.minus(it).toMutableList()
                Log.d("PlanDetails","Exercise List: $exerciseList")
                deletedExercises.add(it)
                Log.d("PlanDetails","Deleted exercises: $deletedExercises")
                exerciseAdapter.setFilteredList(exerciseList)
            }

            exerciseAdapter.onItemClick = {
                val bundle = bundleOf("exercise" to it)
                view.findNavController().navigate(R.id.action_planDetails_to_exerciseDetails, bundle)
            }
            exerciseAdapter.setFilteredList(exerciseList)
        })



    }

    private fun getCrossRefForAllExercises() {
        for(exercise in exerciseList) {
            plansViewModel.getExerciseFromPlan2(exercise.eid, plan.pid).invokeOnCompletion {
                crossRef = plansViewModel.pecr
                Log.d("PlanDetails","CrossRef from pecr: $crossRef")
                deletedPlansExerciseCrossRef.add(crossRef)
                plansViewModel.deletePlanExerciseCrossRef(crossRef).invokeOnCompletion {
                    deletedPlansExerciseCrossRef = deletedPlansExerciseCrossRef.minus(crossRef).toMutableList()
                    Log.d(R.string.tag_database_change.toString(), "PlansExereciseCrossRef with eid: ${crossRef.eid} and pid: ${crossRef.pid} has been deleted.")
                }
            }
            exerciseList = exerciseList.minus(exercise).toMutableList()
            println("Exercise List: $exerciseList")
            deletedExercises.add(exercise)
            println("Deleted exercises: $deletedExercises")
            exerciseAdapter.setFilteredList(exerciseList)
        }
    }

    private fun showDatePickerDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.date_picker)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val datePicker = dialog.findViewById<DatePicker>(R.id.datepicker_datePicker)
        val timePicker = dialog.findViewById<TimePicker>(R.id.datepicker_timePicker)
        val okButton: Button = dialog.findViewById(R.id.datepicker_okButton)
        val cancelButton: Button = dialog.findViewById(R.id.datepicker_cancelButton)

        okButton.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth
            val hour = timePicker.hour
            val minute = timePicker.minute

//            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
//            val selectedTime = LocalTime.of(hour, minute)
            val date = LocalDateTime.of(year, month + 1, dayOfMonth, hour, minute, 0)
            val toSave = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            val trainingSession = TrainingSession(plan.pid, plan.name, toSave)

            plansViewModel.insertTrainingSession(trainingSession).invokeOnCompletion {
                Log.d("PlanDetails", "$trainingSession added to database ")
            }

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        plansViewModel.getPlanWithExercises(plan.pid)
    }
}