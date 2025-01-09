package com.engineer.mobiletrainer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.ExerciseSession
import com.engineer.mobiletrainer.database.entity.ExerciseSet
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.TrainingSession
import com.engineer.mobiletrainer.database.entity.relations.ExerciseSessionWithSets
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithSessions
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithPlans
import com.engineer.mobiletrainer.database.entity.relations.PlanWithExercises
import com.engineer.mobiletrainer.database.entity.relations.PlansWithTrainingSessions
import com.engineer.mobiletrainer.database.entity.relations.TrainingSessionWithExerciseSessions
import com.engineer.mobiletrainer.database.repository.ExerciseRepository
import com.engineer.mobiletrainer.database.repository.ExerciseSessionRepository
import com.engineer.mobiletrainer.database.repository.ExerciseSetRepository
import com.engineer.mobiletrainer.database.repository.PlansExerciseCrossRefRepository
import com.engineer.mobiletrainer.database.repository.PlansRepository
import com.engineer.mobiletrainer.database.repository.ProfileRepository
import com.engineer.mobiletrainer.database.repository.TrainingSessionRepository
import kotlinx.coroutines.launch

class PlansViewModel(
    private val plansRepository: PlansRepository,
    private val exerciseRepository: ExerciseRepository,
    private val trainingSessionRepository: TrainingSessionRepository,
    private val exerciseSessionRepository: ExerciseSessionRepository,
    private val exerciseSetRepository: ExerciseSetRepository,
    private val plansExerciseCrossRefRepository: PlansExerciseCrossRefRepository
) : ViewModel() {
    val allPlans: LiveData<MutableList<Plans>> = plansRepository.allPlans.asLiveData()
    val allExercises: LiveData<MutableList<Exercise>> = exerciseRepository.allExercises.asLiveData()
    val allTrainingSessions: LiveData<MutableList<TrainingSession>> = trainingSessionRepository.allTrainingSessions.asLiveData()
    val allExerciseSessions: LiveData<MutableList<ExerciseSession>> = exerciseSessionRepository.allExerciseSessions.asLiveData()
    val allExerciseSets: LiveData<MutableList<ExerciseSet>> = exerciseSetRepository.allExerciseSets.asLiveData()

    var plansWithTrainingSessions: MutableList<PlansWithTrainingSessions> = emptyList<PlansWithTrainingSessions>().toMutableList()
    lateinit var planWithExercises: LiveData<MutableList<PlanWithExercises>>
    lateinit var exerciseFromPlan: LiveData<PlansExerciseCrossRef>
    var pecr = PlansExerciseCrossRef(0,0)
    var plan: Plans = Plans("")

    var exercisesWithSessions: MutableList<ExerciseWithSessions> = emptyList<ExerciseWithSessions>().toMutableList()
    var exerciseWithPlans: MutableList<ExerciseWithPlans> = emptyList<ExerciseWithPlans>().toMutableList()
    var exercise: Exercise = Exercise("")

    var trainingSessionWithExerciseSessions: MutableList<TrainingSessionWithExerciseSessions> = emptyList<TrainingSessionWithExerciseSessions>().toMutableList()

    var exerciseSessionWithSets: MutableList<ExerciseSessionWithSets> = emptyList<ExerciseSessionWithSets>().toMutableList()

    //Plans
    fun getAllPlans() : MutableList<Plans>? {
        return allPlans.value
    }

    fun getPlan(name: String) = viewModelScope.launch {
        plan = plansRepository.getPlan(name)
    }

    fun insertPlan(plan: Plans) = viewModelScope.launch {
        plansRepository.insert(plan = plan)
    }

    fun insertPlanExercise(plansExerciseCrossRef: PlansExerciseCrossRef) = viewModelScope.launch {
        plansRepository.insertPlanExercise(plansExerciseCrossRef)
    }

    fun updatePlan(plan: Plans) = viewModelScope.launch {
        plansRepository.update(plan = plan)
    }

    fun deletePlan(name: String) = viewModelScope.launch {
        plansRepository.delete(name = name)
    }

    fun getExerciseFromPlan(eid: Int, pid: Int) = viewModelScope.launch {
        exerciseFromPlan = plansRepository.getExerciseFromPlan(eid, pid).asLiveData()
    }

    fun getExerciseFromPlan2(eid: Int, pid: Int) = viewModelScope.launch {
        pecr = plansRepository.getExerciseFromPlan2(eid,pid)
    }

    fun getPlansWithTrainingSessions() = viewModelScope.launch {
        plansWithTrainingSessions = plansRepository.getPlansWithTrainingSessions()
    }

    fun getPlanWithExercises(pid: Int) = viewModelScope.launch {
        planWithExercises = plansRepository.getPlanWithExercises(pid).asLiveData()
    }

    //PlansExerciseCrossRef
    fun insertPlanExerciseCrossRef(plansExerciseCrossRef: PlansExerciseCrossRef) = viewModelScope.launch {
        plansExerciseCrossRefRepository.insert(plansExerciseCrossRef)
    }

    fun deletePlanExerciseCrossRef(plansExerciseCrossRef: PlansExerciseCrossRef) = viewModelScope.launch {
        plansExerciseCrossRefRepository.delete(plansExerciseCrossRef)
    }

    //Exercise
    fun getAllExercises() : MutableList<Exercise>? {
        return allExercises.value
    }

    fun getExercise(name: String) = viewModelScope.launch {
        exercise = exerciseRepository.getExercise(name)
    }

    fun insertExercise(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.insert(exercise)
    }

    fun updateExercise(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.update(exercise)
    }

    fun deleteExercise(name: String) = viewModelScope.launch {
        exerciseRepository.delete(name)
    }

    fun getExerciseWithSessions() = viewModelScope.launch {
        exercisesWithSessions = exerciseRepository.getExercisesWithSessions()
    }

    fun getExerciseWithPlans(eid: Int) = viewModelScope.launch {
        exerciseWithPlans = exerciseRepository.getExerciseWithPlans(eid)
    }

    //TrainingSession
    fun getAllTrainingSessions(): MutableList<TrainingSession>? {
        return allTrainingSessions.value
    }

    fun insertTrainingSession(trainingSession: TrainingSession) = viewModelScope.launch {
        trainingSessionRepository.insert(trainingSession)
    }

    fun updateTrainingSession(trainingSession: TrainingSession) = viewModelScope.launch {
        trainingSessionRepository.update(trainingSession)
    }

    fun deleteTrainingSession(trainingSession: TrainingSession) = viewModelScope.launch {
        trainingSessionRepository.delete(trainingSession)
    }

    fun getTrainingSessionWithExercisesSessions() = viewModelScope.launch {
        trainingSessionWithExerciseSessions = trainingSessionRepository.getTrainingSessionWithExercisesSessions()
    }

    //ExerciseSession
    fun getAllExerciseSessions(): MutableList<ExerciseSession>? {
        return allExerciseSessions.value
    }

    fun insertExerciseSession(exerciseSession: ExerciseSession) = viewModelScope.launch {
        exerciseSessionRepository.insert(exerciseSession)
    }

    fun updateExerciseSession(exerciseSession: ExerciseSession) = viewModelScope.launch {
        exerciseSessionRepository.update(exerciseSession)
    }

    fun deleteExerciseSession(exerciseSession: ExerciseSession) = viewModelScope.launch {
        exerciseSessionRepository.delete(exerciseSession)
    }

    fun getExerciseSessionWithSets() = viewModelScope.launch {
        exerciseSessionWithSets = exerciseSessionRepository.getExerciseSessionWithSets()
    }

    //ExerciseSet
    fun getAllExerciseSets(): MutableList<ExerciseSet>? {
        return allExerciseSets.value
    }

    fun insertExerciseSet(exerciseSet: ExerciseSet) = viewModelScope.launch {
        exerciseSetRepository.insert(exerciseSet)
    }

    fun updateExerciseSet(exerciseSet: ExerciseSet) = viewModelScope.launch {
        exerciseSetRepository.update(exerciseSet)
    }

    fun deleteExerciseSet(exerciseSet: ExerciseSet) = viewModelScope.launch {
        exerciseSetRepository.delete(exerciseSet)
    }

}

class PlansViewModelFactory(private val plansRepository: PlansRepository,
                            private val exerciseRepository: ExerciseRepository,
                            private val trainingSessionRepository: TrainingSessionRepository,
                            private val exerciseSessionRepository: ExerciseSessionRepository,
                            private val exerciseSetRepository: ExerciseSetRepository,
                            private val plansExerciseCrossRefRepository: PlansExerciseCrossRefRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlansViewModel::class.java))
            return PlansViewModel(plansRepository, exerciseRepository, trainingSessionRepository, exerciseSessionRepository, exerciseSetRepository, plansExerciseCrossRefRepository) as T

        throw IllegalArgumentException("Unknown Class for View Model")
    }

}