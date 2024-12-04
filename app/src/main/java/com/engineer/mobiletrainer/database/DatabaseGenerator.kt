package com.engineer.mobiletrainer.database

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import java.lang.Thread.sleep

class DatabaseGenerator(
    private val plansViewModel: PlansViewModel,
    private val activity: AppCompatActivity
) {

    fun generatePlans() {
        //generating plans
        var plan1 = Plans("Push")
        var plan2 = Plans("Pull")
        var plan3 = Plans("Legs")
        var plan4 = Plans("Arms")
        var plan5 = Plans("Cardio")
        var allPlans: List<Plans> = emptyList()

        plan1.desc = "Exercise plan focusing on pushing movement in upper body. Training mostly chest, triceps, shoulders"
        plan2.desc = "Exercise plan focusing on pulling movement in upper body. Training mostly back. biceps, forearms"
        plan3.desc = "Exercise plan focusing on legs. Training mostly quadriceps, biceps femoris, gluteus maximus"
        plan4.desc = "Exercise plan focusing on arms. Training mostly biceps, triceps, forearms, shoulders"
        plan5.desc = "Exercise plan focusing on improving condition."

        plansViewModel.insertPlan(plan1)
        plansViewModel.insertPlan(plan2)
        plansViewModel.insertPlan(plan3)
        plansViewModel.insertPlan(plan4)
        plansViewModel.insertPlan(plan5)

//        plansViewModel.getPlan("Push")
//        plan1 = plansViewModel.plan
//
//        plansViewModel.getPlan("Pull")
//        plan2 = plansViewModel.plan
//
//        plansViewModel.getPlan("Legs")
//        plan3 = plansViewModel.plan
//
//        plansViewModel.getPlan("Arms")
//        plan4 = plansViewModel.plan
//
//        plansViewModel.getPlan("Cardio")
//        plan5 = plansViewModel.plan


        //generating exercises
        var exercise1 = Exercise("Benchpress")
        var exercise2 = Exercise("Chest Dips")
        var exercise3 = Exercise("Dips")
        var exercise4 = Exercise("OverHeadPress")
        var exercise5 = Exercise("PushUps")
        var exercise6 = Exercise("Bent Over Row")
        var exercise7 = Exercise("PullUps")
        var exercise8 = Exercise("Lat Pull Down")
        var exercise9 = Exercise("Good Mornings")
        var exercise10 = Exercise("Arnold Press")
        var exercise11 = Exercise("Dumbbell Lateral Raise")
        var exercise12 = Exercise("Cable Lateral Raise")
        var exercise13 = Exercise("Squat")
        var exercise14 = Exercise("Narrow Hack Squat")
        var exercise15 = Exercise("Leg Extension")
        var exercise16 = Exercise("Dumbbell Lunge")
        var exercise17 = Exercise("Deadlift")
        var exercise18 = Exercise("Standing Calf Raise")
        var exercise19 = Exercise("Standing Dumbbell Curl")
        var exercise20 = Exercise("One Arm Dumbbell Preacher Curl")
        var exercise21 = Exercise("V-bar Cable Curl")
        var exercise22 = Exercise("Rope Tricep Extension")
        var exercise23 = Exercise("Reverse Single Arm Extension")
        var exercise24 = Exercise("Seated French Press")
        var exercise25 = Exercise("Hanging Leg Raise")
        var exercise26 = Exercise("Plank")
        var exercise27 = Exercise("Barbell Wrist Curl")
        var exercise28 = Exercise("Reverse Barbell Wrist Curl")
        var exercise29 = Exercise("Running")
        var exercise30 = Exercise("Bicycle Riding")
        var exercise31 = Exercise("Rope Jumping")
        var exercise32 = Exercise("Burpees")
        var exercise33 = Exercise("Jumping Jacks")
        var allExercises: List<Exercise> = emptyList()

        plansViewModel.insertExercise(exercise1)
        plansViewModel.insertExercise(exercise2)
        plansViewModel.insertExercise(exercise3)
        plansViewModel.insertExercise(exercise4)
        plansViewModel.insertExercise(exercise5)
        plansViewModel.insertExercise(exercise6)
        plansViewModel.insertExercise(exercise7)
        plansViewModel.insertExercise(exercise8)
        plansViewModel.insertExercise(exercise9)
        plansViewModel.insertExercise(exercise10)
        plansViewModel.insertExercise(exercise11)
        plansViewModel.insertExercise(exercise12)
        plansViewModel.insertExercise(exercise13)
        plansViewModel.insertExercise(exercise14)
        plansViewModel.insertExercise(exercise15)
        plansViewModel.insertExercise(exercise16)
        plansViewModel.insertExercise(exercise17)
        plansViewModel.insertExercise(exercise18)
        plansViewModel.insertExercise(exercise19)
        plansViewModel.insertExercise(exercise20)
        plansViewModel.insertExercise(exercise21)
        plansViewModel.insertExercise(exercise22)
        plansViewModel.insertExercise(exercise23)
        plansViewModel.insertExercise(exercise24)
        plansViewModel.insertExercise(exercise25)
        plansViewModel.insertExercise(exercise26)
        plansViewModel.insertExercise(exercise27)
        plansViewModel.insertExercise(exercise28)
        plansViewModel.insertExercise(exercise29)
        plansViewModel.insertExercise(exercise30)
        plansViewModel.insertExercise(exercise31)
        plansViewModel.insertExercise(exercise32)
        plansViewModel.insertExercise(exercise33)

        plansViewModel.allPlans.observe(activity, Observer { plans: List<Plans> ->
            allPlans = plans
            plan1 = allPlans.find { it.name?.equals("Push") == true }!!
            plan2 = allPlans.find { it.name?.equals("Pull") == true }!!
            plan3 = allPlans.find { it.name?.equals("Legs") == true }!!
            plan4 = allPlans.find { it.name?.equals("Arms") == true }!!
            plan5 = allPlans.find { it.name?.equals("Cardio") == true }!!
        })


        plansViewModel.allExercises.observe(activity, Observer { exercises ->
            allExercises = exercises


            exercise1 = allExercises.find {it.name?.equals("Benchpress") == true}!!

            exercise2 = allExercises.find {it.name?.equals("Chest Dips") == true}!!

            exercise3 = allExercises.find {it.name?.equals("Dips") == true}!!

            exercise4 = allExercises.find {it.name?.equals("OverHeadPress") == true}!!

            exercise5 = allExercises.find {it.name?.equals("PushUps") == true}!!

            exercise6 = allExercises.find {it.name?.equals("Bent Over Row") == true}!!

            exercise7 = allExercises.find {it.name?.equals("PullUps") == true}!!

            exercise8 = allExercises.find {it.name?.equals("Lat Pull Down") == true}!!

            exercise9 = allExercises.find {it.name?.equals("Good Mornings") == true}!!

            exercise10 = allExercises.find {it.name?.equals("Arnold Press") == true}!!

            exercise11 = allExercises.find {it.name?.equals("Dumbbell Lateral Raise") == true}!!

            exercise12 = allExercises.find {it.name?.equals("Cable Lateral Raise") == true}!!

            exercise13 = allExercises.find {it.name?.equals("Squat") == true}!!

            exercise14 = allExercises.find {it.name?.equals("Narrow Hack Squat") == true}!!

            exercise15 = allExercises.find {it.name?.equals("Leg Extension") == true}!!

            exercise16 = allExercises.find {it.name?.equals("Dumbbell Lunge") == true}!!

            exercise17 = allExercises.find {it.name?.equals("Deadlift") == true}!!

            exercise18 = allExercises.find {it.name?.equals("Standing Calf Raise") == true}!!

            exercise19 = allExercises.find {it.name?.equals("Standing Dumbbell Curl") == true}!!

            exercise20 = allExercises.find {it.name?.equals("One Arm Dumbbell Preacher Curl") == true}!!

            exercise21 = allExercises.find {it.name?.equals("V-bar Cable Curl") == true}!!

            exercise22 = allExercises.find {it.name?.equals("Rope Tricep Extension") == true}!!

            exercise23 = allExercises.find {it.name?.equals("Reverse Single Arm Extension") == true}!!

            exercise24 = allExercises.find {it.name?.equals("Seated French Press") == true}!!

            exercise25 = allExercises.find {it.name?.equals("Hanging Leg Raise") == true}!!

            exercise26 = allExercises.find {it.name?.equals("Plank") == true}!!

            exercise27 = allExercises.find {it.name?.equals("Barbell Wrist Curl") == true}!!

            exercise28 = allExercises.find {it.name?.equals("Reverse Barbell Wrist Curl") == true}!!

            exercise29 = allExercises.find {it.name?.equals("Running") == true}!!

            exercise30 = allExercises.find {it.name?.equals("Bicycle Riding") == true}!!

            exercise31 = allExercises.find {it.name?.equals("Rope Jumping") == true}!!

            exercise32 = allExercises.find {it.name?.equals("Burpees") == true}!!

            exercise33 = allExercises.find {it.name?.equals("Jumping Jacks") == true}!!

            //making pairs to fill the plans
            //plan1
            val p1e1 = PlansExerciseCrossRef(plan1.pid, exercise1.eid)
            val p1e2 = PlansExerciseCrossRef(plan1.pid, exercise2.eid)
            val p1e3 = PlansExerciseCrossRef(plan1.pid, exercise3.eid)
            val p1e4 = PlansExerciseCrossRef(plan1.pid, exercise4.eid)
            val p1e5 = PlansExerciseCrossRef(plan1.pid, exercise5.eid)
            //plan2
            val p2e6 = PlansExerciseCrossRef(plan2.pid, exercise6.eid)
            val p2e7 = PlansExerciseCrossRef(plan2.pid, exercise7.eid)
            val p2e8 = PlansExerciseCrossRef(plan2.pid, exercise8.eid)
            val p2e9 = PlansExerciseCrossRef(plan2.pid, exercise9.eid)
            val p2e19 = PlansExerciseCrossRef(plan2.pid, exercise19.eid)
            val p2e21 = PlansExerciseCrossRef(plan2.pid, exercise21.eid)
            //plan3
            val p3e13 = PlansExerciseCrossRef(plan3.pid, exercise13.eid)
            val p3e17 = PlansExerciseCrossRef(plan3.pid, exercise17.eid)
            val p3e14 = PlansExerciseCrossRef(plan3.pid, exercise14.eid)
            val p3e15 = PlansExerciseCrossRef(plan3.pid, exercise15.eid)
            val p3e16 = PlansExerciseCrossRef(plan3.pid, exercise16.eid)
            val p3e18 = PlansExerciseCrossRef(plan3.pid, exercise18.eid)
            //plan4
            val p4e3 = PlansExerciseCrossRef(plan4.pid, exercise3.eid)
            val p4e10 = PlansExerciseCrossRef(plan4.pid, exercise10.eid)
            val p4e11 = PlansExerciseCrossRef(plan4.pid, exercise11.eid)
            val p4e19 = PlansExerciseCrossRef(plan4.pid, exercise19.eid)
            val p4e24 = PlansExerciseCrossRef(plan4.pid, exercise24.eid)
            val p4e22 = PlansExerciseCrossRef(plan4.pid, exercise22.eid)
            val p4e27 = PlansExerciseCrossRef(plan4.pid, exercise27.eid)
            val p4e28 = PlansExerciseCrossRef(plan4.pid, exercise28.eid)
            //plan5
            val p5e30 = PlansExerciseCrossRef(plan5.pid, exercise30.eid)
            val p5e32 = PlansExerciseCrossRef(plan5.pid, exercise32.eid)
            val p5e31 = PlansExerciseCrossRef(plan5.pid, exercise31.eid)

            plansViewModel.insertPlanExercise(p1e1)
            plansViewModel.insertPlanExercise(p1e2)
            plansViewModel.insertPlanExercise(p1e3)
            plansViewModel.insertPlanExercise(p1e4)
            plansViewModel.insertPlanExercise(p1e5)
            plansViewModel.insertPlanExercise(p2e6)
            plansViewModel.insertPlanExercise(p2e7)
            plansViewModel.insertPlanExercise(p2e8)
            plansViewModel.insertPlanExercise(p2e9)
            plansViewModel.insertPlanExercise(p2e19)
            plansViewModel.insertPlanExercise(p2e21)
            plansViewModel.insertPlanExercise(p3e13)
            plansViewModel.insertPlanExercise(p3e17)
            plansViewModel.insertPlanExercise(p3e14)
            plansViewModel.insertPlanExercise(p3e15)
            plansViewModel.insertPlanExercise(p3e16)
            plansViewModel.insertPlanExercise(p3e18)
            plansViewModel.insertPlanExercise(p4e3)
            plansViewModel.insertPlanExercise(p4e10)
            plansViewModel.insertPlanExercise(p4e11)
            plansViewModel.insertPlanExercise(p4e19)
            plansViewModel.insertPlanExercise(p4e24)
            plansViewModel.insertPlanExercise(p4e22)
            plansViewModel.insertPlanExercise(p4e27)
            plansViewModel.insertPlanExercise(p4e28)
            plansViewModel.insertPlanExercise(p5e30)
            plansViewModel.insertPlanExercise(p5e32)
            plansViewModel.insertPlanExercise(p5e31)

        })

//        plansViewModel.getExercise("Benchpress")
//        exercise1 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Chest Dips")
//        exercise2 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Dips")
//        exercise3 = plansViewModel.exercise
//
//        plansViewModel.getExercise("OverHeadPress")
//        exercise4 = plansViewModel.exercise
//
//        plansViewModel.getExercise("PushUps")
//        exercise5 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Bent Over Row")
//        exercise6 = plansViewModel.exercise
//
//        plansViewModel.getExercise("PullUps")
//        exercise7 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Lat Pull Down")
//        exercise8 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Good Mornings")
//        exercise9 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Arnold Press")
//        exercise10 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Dumbbell Lateral Raise")
//        exercise11 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Cable Lateral Raise")
//        exercise12 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Squat")
//        exercise13 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Narrow Hack Squat")
//        exercise14 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Leg Extension")
//        exercise15 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Dumbbell Lunge")
//        exercise16 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Deadlift")
//        exercise17 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Standing Calf Raise")
//        exercise18 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Standing Dumbbell Curl")
//        exercise19 = plansViewModel.exercise
//
//        plansViewModel.getExercise("One Arm Dumbbell Preacher Curl")
//        exercise20 = plansViewModel.exercise
//
//        plansViewModel.getExercise("V-bar Cable Curl")
//        exercise21 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Rope Tricep Extension")
//        exercise22 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Reverse Single Arm Extension")
//        exercise23 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Seated French Press")
//        exercise24 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Hanging Leg Raise")
//        exercise25 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Plank")
//        exercise26 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Barbell Wrist Curl")
//        exercise27 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Reverse Barbell Wrist Curl")
//        exercise28 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Running")
//        exercise29 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Bicycle Riding")
//        exercise30 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Rope Jumping")
//        exercise31 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Burpees")
//        exercise32 = plansViewModel.exercise
//
//        plansViewModel.getExercise("Jumping Jacks")
//        exercise33 = plansViewModel.exercise





    }


}