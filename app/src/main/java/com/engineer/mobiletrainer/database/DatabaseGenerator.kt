package com.engineer.mobiletrainer.database

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.Settings
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.SettingsViewModel

class DatabaseGenerator(
    private val plansViewModel: PlansViewModel,
    private val settingsViewModel: SettingsViewModel,
    private val activity: AppCompatActivity
) {
    var allPlans: List<Plans> = emptyList()
    var allExercises: List<Exercise> = emptyList()

    var plan1 = Plans("Push")
    var plan2 = Plans("Pull")
    var plan3 = Plans("Legs")
    var plan4 = Plans("Arms")
    var plan5 = Plans("Cardio")

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

    fun generateSettings() {
        settingsViewModel.insert(Settings(setting = R.string.setting_1.toString(), value = 1))
    }

    fun generatePlans() {
        //generating plans

        plan1.desc =
            "Exercise plan focusing on pushing movement in upper body. Training mostly chest, triceps, shoulders"
        plan2.desc =
            "Exercise plan focusing on pulling movement in upper body. Training mostly back. biceps, forearms"
        plan3.desc =
            "Exercise plan focusing on legs. Training mostly quadriceps, biceps femoris, gluteus maximus"
        plan4.desc =
            "Exercise plan focusing on arms. Training mostly biceps, triceps, forearms, shoulders"
        plan5.desc = "Exercise plan focusing on improving condition."

        plansViewModel.insertPlan(plan1)
        plansViewModel.insertPlan(plan2)
        plansViewModel.insertPlan(plan3)
        plansViewModel.insertPlan(plan4)
        plansViewModel.insertPlan(plan5)

    }

    fun generateExercises() {
        //generating exercises

        exercise1.desc = """
    * Lie on a flat bench with your feet flat on the floor.
    * Grip the barbell slightly wider than shoulder-width apart.
    * Lower the barbell to your chest, keeping your elbows slightly tucked.
    * Push the barbell back up to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise2.desc = """
    * Grip parallel bars with your hands slightly wider than shoulder-width apart.
    * Lower your body by bending your elbows until your chest is near the bars.
    * Push yourself back up to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise3.desc = """
    * Grip parallel bars with your hands shoulder-width apart.
    * Lower your body by bending your elbows until your upper arms are parallel to the floor.
    * Push yourself back up to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise4.desc = """
    * Stand with your feet shoulder-width apart, holding a barbell or dumbbells at shoulder height.
    * Press the weight overhead, extending your arms fully.
    * Lower the weight back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise5.desc = """
    * Start in a plank position with your hands shoulder-width apart.
    * Lower your body by bending your elbows until your chest touches the floor.
    * Push yourself back up to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise6.desc = """
    * Stand with your feet shoulder-width apart, bending at the hips with a flat back.
    * Hold a barbell or dumbbells with an overhand grip.
    * Pull the weight towards your torso, keeping your elbows close to your body.
    * Lower the weight back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise7.desc = """
    * Grip a pull-up bar with an overhand grip, slightly wider than shoulder-width apart.
    * Hang from the bar with your arms fully extended.
    * Pull yourself up until your chin is above the bar.
    * Lower yourself back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise8.desc = """
    * Sit on the lat pull-down machine with your feet flat on the floor.
    * Grip the bar with an overhand grip, slightly wider than shoulder-width apart.
    * Pull the bar down towards your chest, keeping your back straight.
    * Return the bar to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise9.desc = """
    * Stand with your feet shoulder-width apart, with a barbell across your upper back.
    * Bend at the hips, keeping your back straight, until your torso is almost parallel to the floor.
    * Return to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise10.desc = """
    * Sit on a bench with your feet flat on the floor, holding dumbbells at shoulder height.
    * Rotate the dumbbells as you press them overhead, ending with your palms facing forward.
    * Lower the dumbbells back down to the starting position, reversing the rotation.
    * Repeat for the desired number of repetitions.
"""
        exercise11.desc = """
    * Stand with your feet shoulder-width apart, holding dumbbells at your sides.
    * Raise the dumbbells out to the sides, keeping your elbows slightly bent.
    * Lower the dumbbells back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise12.desc = """
    * Stand with your feet shoulder-width apart, holding cable handles with your arms at your sides.
    * Raise the handles out to the sides, keeping your elbows slightly bent.
    * Lower the handles back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise13.desc = """
    * Stand with your feet shoulder-width apart, toes pointing slightly outward.
    * Lower your body by bending your knees and hips, as if sitting back into a chair.
    * Keep your chest up and your back straight.
    * Return to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise14.desc = """
    * Stand on the hack squat machine with your feet close together, toes pointing slightly outward.
    * Lower your body by bending your knees, keeping your back straight.
    * Return to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise15.desc = """
    * Sit on the leg extension machine with your feet under the pad.
    * Extend your legs, keeping your back straight.
    * Lower the weight back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise16.desc = """
    * Stand with your feet shoulder-width apart, holding dumbbells at your sides.
    * Step forward with one leg and lower your body until both knees are bent at a 90-degree angle.
    * Push off with your front foot and return to the starting position.
    * Repeat with the other leg.
    * Continue alternating legs for the desired number of repetitions.
"""
        exercise17.desc = """
    * Stand with your feet hip-width apart, with a barbell in front of you.
    * Bend down and grip the barbell with an overhand grip, slightly wider than shoulder-width apart.
    * Keep your back straight and your core engaged.
    * Lift the barbell off the ground by extending your hips and knees.
    * Lower the barbell back down to the ground.
    * Repeat for the desired number of repetitions.
"""
        exercise18.desc = """
    * Stand with your feet shoulder-width apart, holding dumbbells or a barbell for added resistance.
    * Raise your heels off the ground, contracting your calf muscles.
    * Lower your heels back down to the ground.
    * Repeat for the desired number of repetitions.
"""
        exercise19.desc = """
    * Stand with your feet shoulder-width apart, holding dumbbells at your sides.
    * Curl the dumbbells towards your shoulders, keeping your elbows close to your body.
    * Lower the dumbbells back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise20.desc = """
    * Sit on a preacher curl bench with your chest against the pad.
    * Hold a dumbbell in one hand and place your upper arm on the pad.
    * Curl the dumbbell towards your shoulder, keeping your elbow close to the pad.
    * Lower the dumbbell back down to the starting position.
    * Repeat for the desired number of repetitions.
    * Switch arms and repeat.
"""
        exercise21.desc = """
    * Stand facing a cable machine with a V-bar attachment.
    * Grip the V-bar with an underhand grip, shoulder-width apart.
    * Curl the V-bar towards your shoulders, keeping your elbows close to your body.
    * Lower the V-bar back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise22.desc = """
    * Stand facing a cable machine with a rope attachment.
    * Grip the ends of the rope with an overhand grip.
    * Extend your arms down, keeping your elbows close to your body.
    * Return to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise23.desc = """
    * Sit on a bench with your feet flat on the floor.
    * Hold a dumbbell in one hand and place your upper arm on your thigh, with your elbow bent at a 90-degree angle.
    * Extend your arm back, keeping your elbow close to your body.
    * Return to the starting position.
    * Repeat for the desired number of repetitions.
    * Switch arms and repeat.
"""
        exercise24.desc = """
    * Sit on a bench with your feet flat on the floor.
    * Hold a barbell or dumbbells overhead with your arms extended.
    * Lower the weight behind your head by bending your elbows.
    * Extend your arms back up to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise25.desc = """
    * Hang from a pull-up bar with an overhand grip.
    * Raise your legs towards your chest, keeping your core engaged.
    * Lower your legs back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise26.desc = """
    * Start in a push-up position with your forearms on the floor and your body in a straight line.
    * Hold this position for the desired amount of time.
"""
        exercise27.desc = """
    * Sit on a bench with your forearms resting on your thighs, palms facing up.
    * Hold a barbell with an overhand grip.
    * Curl the barbell upwards by flexing your wrists.
    * Lower the barbell back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise28.desc = """
    * Sit on a bench with your forearms resting on your thighs, palms facing down.
    * Hold a barbell with an underhand grip.
    * Curl the barbell upwards by extending your wrists.
    * Lower the barbell back down to the starting position.
    * Repeat for the desired number of repetitions.
"""
        exercise29.desc = """
    * Maintain a comfortable pace and stride.
    * Focus on proper breathing and posture.
    * Gradually increase distance and intensity over time.
"""
        exercise30.desc = """
    * Adjust the seat height for proper leg extension.
    * Maintain a steady pedaling rhythm.
    * Vary terrain and intensity for a challenging workout.
"""
        exercise31.desc = """
    * Use a rope of appropriate length.
    * Jump with both feet together, clearing the rope.
    * Maintain a consistent rhythm and pace.
"""
        exercise32.desc = """
    * Start in a standing position.
    * Drop down into a squat position with your hands on the floor.
    * Kick your feet back into a plank position.
    * Do a push-up.
    * Return to the squat position.
    * Jump up into the air.
    * Repeat for the desired number of repetitions.
"""
        exercise33.desc = """
    * Stand with your feet together and your arms at your sides.
    * Jump while spreading your legs and raising your arms overhead.
    * Return to the starting position.
    * Repeat for the desired number of repetitions.
"""

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
    }

    fun matchPlansAndExercises() {

        plansViewModel.allPlans.observe(activity, Observer { plans: List<Plans> ->
            allPlans = plans
            if(allPlans.isNotEmpty()) {
                plan1 = allPlans.find { it.name?.equals("Push") == true }!!
                plan2 = allPlans.find { it.name?.equals("Pull") == true }!!
                plan3 = allPlans.find { it.name?.equals("Legs") == true }!!
                plan4 = allPlans.find { it.name?.equals("Arms") == true }!!
                plan5 = allPlans.find { it.name?.equals("Cardio") == true }!!
            }
        })


        plansViewModel.allExercises.observe(activity, Observer { exercises ->
            allExercises = exercises

            if(allExercises.isNotEmpty()) {
                exercise1 = allExercises.find { it.name?.equals("Benchpress") == true }!!

                exercise2 = allExercises.find { it.name?.equals("Chest Dips") == true }!!

                exercise3 = allExercises.find { it.name?.equals("Dips") == true }!!

                exercise4 = allExercises.find { it.name?.equals("OverHeadPress") == true }!!

                exercise5 = allExercises.find { it.name?.equals("PushUps") == true }!!

                exercise6 = allExercises.find { it.name?.equals("Bent Over Row") == true }!!

                exercise7 = allExercises.find { it.name?.equals("PullUps") == true }!!

                exercise8 = allExercises.find { it.name?.equals("Lat Pull Down") == true }!!

                exercise9 = allExercises.find { it.name?.equals("Good Mornings") == true }!!

                exercise10 = allExercises.find { it.name?.equals("Arnold Press") == true }!!

                exercise11 =
                    allExercises.find { it.name?.equals("Dumbbell Lateral Raise") == true }!!

                exercise12 = allExercises.find { it.name?.equals("Cable Lateral Raise") == true }!!

                exercise13 = allExercises.find { it.name?.equals("Squat") == true }!!

                exercise14 = allExercises.find { it.name?.equals("Narrow Hack Squat") == true }!!

                exercise15 = allExercises.find { it.name?.equals("Leg Extension") == true }!!

                exercise16 = allExercises.find { it.name?.equals("Dumbbell Lunge") == true }!!

                exercise17 = allExercises.find { it.name?.equals("Deadlift") == true }!!

                exercise18 = allExercises.find { it.name?.equals("Standing Calf Raise") == true }!!

                exercise19 =
                    allExercises.find { it.name?.equals("Standing Dumbbell Curl") == true }!!

                exercise20 =
                    allExercises.find { it.name?.equals("One Arm Dumbbell Preacher Curl") == true }!!

                exercise21 = allExercises.find { it.name?.equals("V-bar Cable Curl") == true }!!

                exercise22 =
                    allExercises.find { it.name?.equals("Rope Tricep Extension") == true }!!

                exercise23 =
                    allExercises.find { it.name?.equals("Reverse Single Arm Extension") == true }!!

                exercise24 = allExercises.find { it.name?.equals("Seated French Press") == true }!!

                exercise25 = allExercises.find { it.name?.equals("Hanging Leg Raise") == true }!!

                exercise26 = allExercises.find { it.name?.equals("Plank") == true }!!

                exercise27 = allExercises.find { it.name?.equals("Barbell Wrist Curl") == true }!!

                exercise28 =
                    allExercises.find { it.name?.equals("Reverse Barbell Wrist Curl") == true }!!

                exercise29 = allExercises.find { it.name?.equals("Running") == true }!!

                exercise30 = allExercises.find { it.name?.equals("Bicycle Riding") == true }!!

                exercise31 = allExercises.find { it.name?.equals("Rope Jumping") == true }!!

                exercise32 = allExercises.find { it.name?.equals("Burpees") == true }!!

                exercise33 = allExercises.find { it.name?.equals("Jumping Jacks") == true }!!

            }

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

    }

}