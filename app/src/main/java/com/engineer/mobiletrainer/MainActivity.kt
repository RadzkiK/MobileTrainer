package com.engineer.mobiletrainer


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.engineer.mobiletrainer.database.AppDatabase
import com.engineer.mobiletrainer.database.Settings
import com.engineer.mobiletrainer.database.SettingsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

//    private lateinit var appDatabase : AppDatabase
//    private lateinit var settingsDao: SettingsDao
//    private lateinit var appSettingsList : List<Settings>
//    private var was_db = true
//    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        appDatabase = AppDatabase.getInstance(this.applicationContext)
//
//        runBlocking {
//            job = launch(Dispatchers.IO) {
//                settingsDao = appDatabase.settingsDao()
//                appSettingsList = settingsDao.getAll()
//                if(appSettingsList.isEmpty()) {
//                    was_db = false
//                    settingsDao.insertSetting(Settings("modelPos", 0))
//                }
//            }
//        }
//        if(was_db == true) {
//            job?.cancel()
//        }

    }

}