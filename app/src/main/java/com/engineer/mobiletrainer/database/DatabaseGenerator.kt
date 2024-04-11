package com.engineer.mobiletrainer.database

import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.viewmodels.PlansViewModel

class DatabaseGenerator(
    private val plansViewModel: PlansViewModel
) {

    fun generatePlans() {
        //generating plans
        val plan1 = Plans("Push")
        val plan2 = Plans("Pull")
        val plan3 = Plans("Legs")
        val plan4 = Plans("Arms")
        val plan5 = Plans("Cardio")

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

        //generating exercises
        val exercise1 = Exercise("Benchpress")
        val exercise2 = Exercise("Chest Dips")
        val exercise3 = Exercise("Dips")
        val exercise4 = Exercise("OverHeadPress")
        val exercise5 = Exercise("PushUps")
        val exercise6 = Exercise("Bent Over Row")
        val exercise7 = Exercise("PullUps")
        val exercise8 = Exercise("Lat Pull Down")
        val exercise9 = Exercise("Good Mornings")
        val exercise10 = Exercise("Arnold Press")
        val exercise11 = Exercise("Dumbbell Lateral Raise")
        val exercise12 = Exercise("Cable Lateral Raise")
        val exercise13 = Exercise("Squat")
        val exercise14 = Exercise("Narrow Hack Squat")
        val exercise15 = Exercise("Leg Extension")
        val exercise16 = Exercise("Dumbbell Lunge")
        val exercise17 = Exercise("Deadlift")
        val exercise18 = Exercise("Standing Calf Raise")
        val exercise19 = Exercise("Standing Dumbbell Curl")
        val exercise20 = Exercise("One Arm Dumbbell Preacher Curl")
        val exercise21 = Exercise("V-bar Cable Curl")
        val exercise22 = Exercise("Rope Tricep Extension")
        val exercise23 = Exercise("Reverse Single Arm Extension")
        val exercise24 = Exercise("Seated French Press")
        val exercise25 = Exercise("Hanging Leg Raise")
        val exercise26 = Exercise("Plank")
        val exercise27 = Exercise("Barbell Wrist Curl")
        val exercise28 = Exercise("Reverse Barbell Wrist Curl")
        val exercise29 = Exercise("Running")
        val exercise30 = Exercise("Bicycle Riding")
        val exercise31 = Exercise("Rope Jumping")
        val exercise32 = Exercise("Burpees")
        val exercise33 = Exercise("Jumping Jacks")

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

    }


}