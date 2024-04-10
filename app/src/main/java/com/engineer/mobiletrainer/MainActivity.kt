package com.engineer.mobiletrainer


import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
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
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.engineer.mobiletrainer.database.AppDatabase
import com.engineer.mobiletrainer.database.Profile
import com.engineer.mobiletrainer.database.Settings
import com.engineer.mobiletrainer.database.SettingsDao
import com.engineer.mobiletrainer.viewmodels.ProfileViewModel
import com.engineer.mobiletrainer.viewmodels.ProfileViewModelFactory
import com.engineer.mobiletrainer.viewmodels.SettingsViewModel
import com.engineer.mobiletrainer.viewmodels.SettingsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    //private val settingsViewModel : SettingsViewModel by viewModels { SettingsViewModelFactory((application as MobileTrainerApplication).settingsRepository) }
    private val profileViewModel: ProfileViewModel by viewModels { ProfileViewModelFactory((application as MobileTrainerApplication).profileRepository) }
    //private var settingsList: List<Settings> = emptyList()
    private var profileList: List<Profile> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileViewModel.allProfiles.observe(this) { profiles ->
            //profileList = profiles
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
        } else {
            //if there is a profile then do nothing
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