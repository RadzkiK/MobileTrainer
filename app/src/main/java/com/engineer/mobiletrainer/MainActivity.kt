package com.engineer.mobiletrainer


import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.engineer.mobiletrainer.database.AppDatabase
import com.engineer.mobiletrainer.database.Settings
import com.engineer.mobiletrainer.database.SettingsDao
import com.engineer.mobiletrainer.viewmodels.SettingsViewModel
import com.engineer.mobiletrainer.viewmodels.SettingsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val settingsViewModel : SettingsViewModel by viewModels { SettingsViewModelFactory((application as MobileTrainerApplication).repository) }
    private lateinit var settingsList: List<Settings>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsViewModel.allSettings.observe(this, Observer { settings ->

        })
        //println(settingsList)

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

            else -> super.onOptionsItemSelected(item)
        }
    }
}