package com.engineer.mobiletrainer.activities


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.DatabaseGenerator
import com.engineer.mobiletrainer.database.entity.Profile
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.ProfileViewModel
import com.engineer.mobiletrainer.viewmodels.ProfileViewModelFactory
import com.engineer.mobiletrainer.viewmodels.SettingsViewModel
import com.engineer.mobiletrainer.viewmodels.SettingsViewModelFactory
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels { ProfileViewModelFactory((application as MobileTrainerApplication).profileRepository) }
    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (application as MobileTrainerApplication).plansRepository,
        (application as MobileTrainerApplication).exerciseRepository,
        (application as MobileTrainerApplication).trainingSessionRepository,
        (application as MobileTrainerApplication).exerciseSessionRepository,
        (application as MobileTrainerApplication).exerciseSetRepository,
        (application as MobileTrainerApplication).plansExerciseCrossRefRepository) }
    private val settingsViewModel: SettingsViewModel by viewModels { SettingsViewModelFactory((application as MobileTrainerApplication).settingsRepository )}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileViewModel.allProfiles.observe(this) { profiles ->
            if (profiles.isEmpty()) {
            //If there is no profile for the user show a dialog to create a profile, it can be fully filled later
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.nouser_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val button = dialog.findViewById<Button>(R.id.nouser_dialog_confirm)
            val name = dialog.findViewById<EditText>(R.id.nouser_dialog_name)
            val surname = dialog.findViewById<EditText>(R.id.nouser_dialog_surname)

            button.setOnClickListener(View.OnClickListener { view ->
                if(name.text.isNotEmpty() && surname.text.isNotEmpty()) {

                    profileViewModel.insert(Profile(name.text.toString(), surname.text.toString()))
                    val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return@OnClickListener
                    with (sharedPref.edit()) {
                        putString(R.string.user_name.toString(), name.text.toString())
                        apply()
                    }
                    dialog.dismiss()

                } else {
                    val builder2 = AlertDialog.Builder(this)
                    builder2.setTitle("Error")
                    builder2.setMessage("Fill credentials")
                    builder2.setNegativeButton("OK") {dialog, which ->
                    }
                    builder2.show()
                }
            })
                dialog.show()
                //also if this is first time let's generate some values in database
                val databaseGenerator = DatabaseGenerator(plansViewModel, settingsViewModel, this)
                try {
                    databaseGenerator.generatePlans()
                    sleep(100)
                    databaseGenerator.generateExercises()
                    sleep(100)
                    databaseGenerator.matchPlansAndExercises()
                    databaseGenerator.generateSettings()

                } catch (e: Exception) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("Run application again")
                }

        } else {
            //if there is a profile then do nothing
            val name: String? = profiles[0].name
            val uid: Int = profiles[0].uid
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return@observe
            with (sharedPref.edit()) {
                putString(R.string.user_name.toString(), name)
                putInt(R.string.uid.toString(), uid)
                apply()
            }

        }
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                findNavController(R.id.fragmentContainerView3).navigate(R.id.action_global_settings2)
                true
            }
            R.id.menu_profile -> {
                findNavController(R.id.fragmentContainerView3).navigate(R.id.action_global_profile)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}