package com.engineer.mobiletrainer.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.camera.CameraSource
import com.engineer.mobiletrainer.data.Device
import com.engineer.mobiletrainer.ml.ModelType
import com.engineer.mobiletrainer.ml.MoveNet
import com.engineer.mobiletrainer.ml.PoseClassifier
import com.engineer.mobiletrainer.viewmodels.SettingsViewModel
import com.engineer.mobiletrainer.viewmodels.SettingsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingActivity : FragmentActivity() {
    companion object {
        private const val FRAGMENT_DIALOG = "fragment_training"
    }

    /** A [SurfaceView] for camera preview.   */
    private lateinit var surfaceView: SurfaceView

    /** Default pose estimation model is 1 (MoveNet Thunder)
     * 0 == MoveNet Lightning model
     * 1 == MoveNet Thunder model
     * 2 == MoveNet MultiPose model
     * 3 == PoseNet model
     **/
    private var modelPos = 1

    /** Default device is CPU */
    private var device = Device.CPU

    private lateinit var tvScore: TextView
    private lateinit var tvFPS: TextView
    private lateinit var tvClassificationValue1: TextView
    private lateinit var tvClassificationValue2: TextView
    private var cameraSource: CameraSource? = null
    private var isClassifyPose = true
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                openCamera()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                ErrorDialog.newInstance(getString(R.string.tfe_pe_request_permission))
                    .show(supportFragmentManager, FRAGMENT_DIALOG)
            }
        }
    private val settingsViewModel: SettingsViewModel by viewModels { SettingsViewModelFactory((this.application as MobileTrainerApplication).settingsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_training)
        // keep screen on while app is running
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        tvScore = findViewById(R.id.tvScore)
        tvFPS = findViewById(R.id.tvFps)
        surfaceView = findViewById(R.id.surfaceView)
        tvClassificationValue1 = findViewById(R.id.tvClassificationValue1)
        tvClassificationValue2 = findViewById(R.id.tvClassificationValue2)
        if (!isCameraPermissionGranted()) {
            requestPermission()
        }
        settingsViewModel.getSettingNamed(R.string.setting_1.toString()).invokeOnCompletion {
            modelPos = settingsViewModel.setting.value!!
        }
    }

    override fun onStart() {
        super.onStart()
        openCamera()
    }

    override fun onResume() {
        cameraSource?.resume()
        super.onResume()
    }

    override fun onPause() {
        cameraSource?.close()
        cameraSource = null
        super.onPause()
    }

    // check if permission is granted or not.
    private fun isCameraPermissionGranted(): Boolean {
        return checkPermission(
            Manifest.permission.CAMERA,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }

    // open camera
    private fun openCamera() {
        if (isCameraPermissionGranted()) {
            if (cameraSource == null) {
                cameraSource =
                    CameraSource(surfaceView, object : CameraSource.CameraSourceListener {
                        override fun onFPSListener(fps: Int) {
                            tvFPS.text = getString(R.string.tfe_pe_tv_fps, fps)
                        }

                        override fun onDetectedInfo(
                            personScore: Float?,
                            poseLabels: List<Pair<String, Float>>?
                        ) {
                            tvScore.text = getString(R.string.tfe_pe_tv_score, personScore ?: 0f)
                            poseLabels?.sortedByDescending { it.second }?.let {
                                tvClassificationValue1.text = getString(
                                    R.string.tfe_pe_tv_classification_value,
                                    convertPoseLabels(if (it.isNotEmpty()) it[0] else null)
                                )
                                tvClassificationValue2.text = getString(
                                    R.string.tfe_pe_tv_classification_value,
                                    convertPoseLabels(if (it.size >= 2) it[1] else null)
                                )
                            }
                        }
                    }).apply {
                        prepareCamera()
                    }
                isPoseClassifier()
                lifecycleScope.launch(Dispatchers.Main) {
                    cameraSource?.initCamera()
                }
            }
            createPoseEstimator()
        }
    }

    private fun convertPoseLabels(pair: Pair<String, Float>?): String {
        if (pair == null) return "empty"
        return "${pair.first} (${String.format("%.2f", pair.second)})"
    }

    private fun isPoseClassifier() {
        cameraSource?.setClassifier(if (isClassifyPose) PoseClassifier.create(this) else null)
    }

    // Change model when app is running
    private fun changeModel(position: Int) {
        if (modelPos == position) return
        modelPos = position
        createPoseEstimator()
    }


    private fun createPoseEstimator() {
        val poseDetector = when (modelPos) {
            0 -> {
                // MoveNet Lightning (SinglePose)
                showDetectionScore(true)
                MoveNet.create(this, device, ModelType.Lightning)
            }
            1 -> {
                // MoveNet Thunder (SinglePose)
                showDetectionScore(true)
                MoveNet.create(this, device, ModelType.Thunder)
            }
            else -> {
                null
            }
        }
        poseDetector?.let { detector ->
            cameraSource?.setDetector(detector)
        }
    }

    // Show/hide the detection score.
    private fun showDetectionScore(isVisible: Boolean) {
        tvScore.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    // Show/hide classification result.
    private fun showClassificationResult(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        tvClassificationValue1.visibility = visibility
        tvClassificationValue2.visibility = visibility
    }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                // You can use the API that requires the permission.
                openCamera()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Shows an error message dialog.
     */
    class ErrorDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(activity)
                .setMessage(requireArguments().getString(ARG_MESSAGE))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // do nothing
                }
                .create()

        companion object {

            @JvmStatic
            private val ARG_MESSAGE = "message"

            @JvmStatic
            fun newInstance(message: String): ErrorDialog = ErrorDialog().apply {
                arguments = Bundle().apply { putString(ARG_MESSAGE, message) }
            }
        }
    }
}