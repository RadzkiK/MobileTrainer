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
import com.engineer.mobiletrainer.database.entity.relations.ExercisesWithPlans
import com.engineer.mobiletrainer.database.entity.relations.PlansWithExercises
import com.engineer.mobiletrainer.database.entity.relations.PlansWithTrainingSessions
import com.engineer.mobiletrainer.database.entity.relations.TrainingSessionWithExerciseSessions
import com.engineer.mobiletrainer.database.repository.ExerciseRepository
import com.engineer.mobiletrainer.database.repository.ExerciseSessionRepository
import com.engineer.mobiletrainer.database.repository.ExerciseSetRepository
import com.engineer.mobiletrainer.database.repository.PlansRepository
import com.engineer.mobiletrainer.database.repository.ProfileRepository
import com.engineer.mobiletrainer.database.repository.TrainingSessionRepository
import kotlinx.coroutines.launch

class PlansViewModel(
    private val plansRepository: PlansRepository,
    private val exerciseRepository: ExerciseRepository,
    private val trainingSessionRepository: TrainingSessionRepository,
    private val exerciseSessionRepository: ExerciseSessionRepository,
    private val exerciseSetRepository: ExerciseSetRepository
) : ViewModel() {
    val allPlans: LiveData<List<Plans>> = plansRepository.allPlans.asLiveData()
    val allExercises: LiveData<List<Exercise>> = exerciseRepository.allExercises.asLiveData()
    val allTrainingSessions: LiveData<List<TrainingSession>> = trainingSessionRepository.allTrainingSessions.asLiveData()
    val allExerciseSessions: LiveData<List<ExerciseSession>> = exerciseSessionRepository.allExerciseSessions.asLiveData()
    val allExerciseSets: LiveData<List<ExerciseSet>> = exerciseSetRepository.allExerciseSets.asLiveData()

    private var plansWithTrainingSessions: List<PlansWithTrainingSessions> = emptyList()
        get() = field
    private var plansWithExercises: List<PlansWithExercises> = emptyList()
        get() = field
    private var plan: Plans = Plans("")
        get() = field

    private var exercisesWithSessions: List<ExerciseWithSessions> = emptyList()
        get() = field
    private var exercisesWithPlans: List<ExercisesWithPlans> = emptyList()
        get() = field
    private var exercise: Exercise = Exercise("")
        get() = field

    private var trainingSessionWithExerciseSessions: List<TrainingSessionWithExerciseSessions> = emptyList()
        get() = field

    private var exerciseSessionWithSets: List<ExerciseSessionWithSets> = emptyList()
        get() = field

    //Plans
    fun getAllPlans() : List<Plans>? {
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

    fun getPlansWithTrainingSessions() = viewModelScope.launch {
        plansWithTrainingSessions = plansRepository.getPlansWithTrainingSessions()
    }

    fun getPlansWithExercises() = viewModelScope.launch {
        plansWithExercises = plansRepository.getPlansWithExercises()
    }

    //Exercise
    fun getAllExercises() : List<Exercise>? {
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

    fun getExerciseWithPlans() = viewModelScope.launch {
        exercisesWithPlans = exerciseRepository.getExercisesWithPlans()
    }

    //TrainingSession
    fun getAllTrainingSessions(): List<TrainingSession>? {
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
    fun getAllExerciseSessions(): List<ExerciseSession>? {
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
    fun getAllExerciseSets(): List<ExerciseSet>? {
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
                            private val exerciseSetRepository: ExerciseSetRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlansViewModel::class.java))
            return PlansViewModel(plansRepository, exerciseRepository, trainingSessionRepository, exerciseSessionRepository, exerciseSetRepository) as T

        throw IllegalArgumentException("Unknown Class for View Model")
    }

}