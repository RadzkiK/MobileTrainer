/* Copyright 2021 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================
*/

package com.engineer.mobiletrainer.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.Paint
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.media.ImageReader
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceView
import kotlinx.coroutines.suspendCancellableCoroutine
import com.engineer.mobiletrainer.VisualizationUtils
import com.engineer.mobiletrainer.YuvToRgbConverter
import com.engineer.mobiletrainer.data.BodyPart
import com.engineer.mobiletrainer.data.Person
import com.engineer.mobiletrainer.ml.PoseClassifier
import com.engineer.mobiletrainer.ml.PoseDetector
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.time.ZonedDateTime.now
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class CameraSource(
    private val surfaceView: SurfaceView,
    private val listener: CameraSourceListener? = null
) {

    companion object {
        private const val PREVIEW_WIDTH = 640
        private const val PREVIEW_HEIGHT = 480

        /** Threshold for confidence score. */
        private const val MIN_CONFIDENCE = .2f
        private const val TAG = "Camera Source"
    }

    private val lock = Any()
    private var detector: PoseDetector? = null
    private var classifier: PoseClassifier? = null
    private var isTrackerEnabled = false
    private var yuvConverter: YuvToRgbConverter = YuvToRgbConverter(surfaceView.context)
    private lateinit var imageBitmap: Bitmap
    private var imageFileName: String = ""

    /** Frame count that have been processed so far in an one second interval to calculate FPS. */
    private var fpsTimer: Timer? = null
    private var frameProcessedInOneSecondInterval = 0
    private var framesPerSecond = 0

    /** Detects, characterizes, and connects to a CameraDevice (used for all camera operations) */
    private val cameraManager: CameraManager by lazy {
        val context = surfaceView.context
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    /** Readers used as buffers for camera still shots */
    private var imageReader: ImageReader? = null

    /** The [CameraDevice] that will be opened in this fragment */
    private var camera: CameraDevice? = null

    /** Internal reference to the ongoing [CameraCaptureSession] configured with our parameters */
    private var session: CameraCaptureSession? = null

    /** [HandlerThread] where all buffer reading operations run */
    private var imageReaderThread: HandlerThread? = null

    /** [Handler] corresponding to [imageReaderThread] */
    private var imageReaderHandler: Handler? = null
    private var cameraId: String = ""

    suspend fun initCamera() {
        camera = openCamera(cameraManager, cameraId)
        imageReader =
            ImageReader.newInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, ImageFormat.YUV_420_888, 3)
        imageReader?.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage()
            if (image != null) {
                if (!::imageBitmap.isInitialized) {
                    imageBitmap =
                        Bitmap.createBitmap(
                            PREVIEW_WIDTH,
                            PREVIEW_HEIGHT,
                            Bitmap.Config.ARGB_8888
                        )
                }
                yuvConverter.yuvToRgb(image, imageBitmap)
                // Create rotated version for portrait display
                val rotateMatrix = Matrix()
                rotateMatrix.postRotate(90.0f)

                val rotatedBitmap = Bitmap.createBitmap(
                    imageBitmap, 0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT,
                    rotateMatrix, false
                )
                processImage(rotatedBitmap)
                //Log.d(TAG, "Processed image: ${rotatedBitmap}")
                image.close()
            }
        }, imageReaderHandler)

        imageReader?.surface?.let { surface ->
            session = createSession(listOf(surface))
            val cameraRequest = camera?.createCaptureRequest(
                CameraDevice.TEMPLATE_PREVIEW
            )?.apply {
                addTarget(surface)
            }
            cameraRequest?.build()?.let {
                session?.setRepeatingRequest(it, null, null)
            }
        }
    }

    private suspend fun createSession(targets: List<Surface>): CameraCaptureSession =
        suspendCancellableCoroutine { cont ->
            camera?.createCaptureSession(targets, object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(captureSession: CameraCaptureSession) =
                    cont.resume(captureSession)

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    cont.resumeWithException(Exception("Session error"))
                }
            }, null)
        }

    @SuppressLint("MissingPermission")
    private suspend fun openCamera(manager: CameraManager, cameraId: String): CameraDevice =
        suspendCancellableCoroutine { cont ->
            manager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) = cont.resume(camera)

                override fun onDisconnected(camera: CameraDevice) {
                    camera.close()
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    if (cont.isActive) cont.resumeWithException(Exception("Camera error"))
                }
            }, imageReaderHandler)
        }

    fun prepareCamera() {
        for (cameraId in cameraManager.cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)

            // We don't use a front facing camera in this sample.
            val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
            if (cameraDirection != null &&
                cameraDirection == CameraCharacteristics.LENS_FACING_FRONT
            ) {
                continue
            }
            this.cameraId = cameraId
        }
    }

    fun setDetector(detector: PoseDetector) {
        synchronized(lock) {
            if (this.detector != null) {
                this.detector?.close()
                this.detector = null
            }
            this.detector = detector
        }
    }

    fun setClassifier(classifier: PoseClassifier?) {
        synchronized(lock) {
            if (this.classifier != null) {
                this.classifier?.close()
                this.classifier = null
            }
            this.classifier = classifier
        }
    }

    fun resume() {
        imageReaderThread = HandlerThread("imageReaderThread").apply { start() }
        imageReaderHandler = Handler(imageReaderThread!!.looper)
        fpsTimer = Timer()
        fpsTimer?.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    framesPerSecond = frameProcessedInOneSecondInterval
                    frameProcessedInOneSecondInterval = 0
                }
            },
            0,
            1000
        )
    }

    fun close() {
        session?.close()
        session = null
        camera?.close()
        camera = null
        imageReader?.close()
        imageReader = null
        stopImageReaderThread()
        detector?.close()
        detector = null
        classifier?.close()
        classifier = null
        fpsTimer?.cancel()
        fpsTimer = null
        frameProcessedInOneSecondInterval = 0
        framesPerSecond = 0
    }

    fun stopPreview() {
        session?.stopRepeating()
        session?.close()
        camera?.close()
        camera = null
    }

    // process image
    private fun processImage(bitmap: Bitmap) {
        val persons = mutableListOf<Person>()
        var classificationResult: List<Pair<String, Float>>? = null
        var result: List<Pair<String, Float>> = emptyList()

        synchronized(lock) {
            detector?.estimatePoses(bitmap)?.let {
                persons.addAll(it)



                val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MobileTrainer")
                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val filename = "persons.csv"
                val file = File(directory, filename)
                val fileExits = file.exists()
                val fileWriter = FileWriter(file, true)

                // if the model only returns one item, allow running the Pose classifier.
                if (persons.isNotEmpty()) {
                    classifier?.run {
                        classificationResult = classify(persons[0])
                        result = classificationResult as List<Pair<String, Float>>
                    }
                    var clas_value = "No classification"
                    var clas_score = 0.0f
                    if(result.isNotEmpty()) {
                        val sorted: List<Pair<String, Float>> = result.sortedByDescending { item -> item.second }
                        clas_value = sorted[0].first
                        clas_score = sorted[0].second
                    }

                    if(!fileExits){
                        fileWriter.appendLine("file_name,NOSE_x,NOSE_y,NOSE_score,LEFT_EYE_x,LEFT_EYE_y,LEFT_EYE_score,RIGHT_EYE_x,RIGHT_EYE_y,RIGHT_EYE_score,LEFT_EAR_x,LEFT_EAR_y,LEFT_EAR_score,RIGHT_EAR_x,RIGHT_EAR_y,RIGHT_EAR_score,LEFT_SHOULDER_x,LEFT_SHOULDER_y,LEFT_SHOULDER_score,RIGHT_SHOULDER_x,RIGHT_SHOULDER_y,RIGHT_SHOULDER_score,LEFT_ELBOW_x,LEFT_ELBOW_y,LEFT_ELBOW_score,RIGHT_ELBOW_x,RIGHT_ELBOW_y,RIGHT_ELBOW_score,LEFT_WRIST_x,LEFT_WRIST_y,LEFT_WRIST_score,RIGHT_WRIST_x,RIGHT_WRIST_y,RIGHT_WRIST_score,LEFT_HIP_x,LEFT_HIP_y,LEFT_HIP_score,RIGHT_HIP_x,RIGHT_HIP_y,RIGHT_HIP_score,LEFT_KNEE_x,LEFT_KNEE_y,LEFT_KNEE_score,RIGHT_KNEE_x,RIGHT_KNEE_y,RIGHT_KNEE_score,LEFT_ANKLE_x,LEFT_ANKLE_y,LEFT_ANKLE_score,RIGHT_ANKLE_x,RIGHT_ANKLE_y,RIGHT_ANKLE_score,class_score,class_name")
                    }
                    for(person in persons) {
                        val keyPoints = person.keyPoints
                        val nose = keyPoints.find { item -> item.bodyPart == BodyPart.NOSE }
                        val leftEye = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_EYE }
                        val rightEye = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_EYE }
                        val leftEar = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_EAR }
                        val rightEer = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_EAR }
                        val leftShoulder = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_SHOULDER }
                        val rightShoulder = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_SHOULDER }
                        val leftElbow = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_ELBOW }
                        val rightElbow = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_ELBOW }
                        val leftWrist = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_WRIST }
                        val rightWrist = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_WRIST }
                        val leftHip = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_HIP }
                        val rightHip = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_HIP }
                        val leftKnee = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_KNEE }
                        val rightKnee = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_KNEE }
                        val leftAnkle = keyPoints.find { item -> item.bodyPart == BodyPart.LEFT_ANKLE }
                        val rightAnkle = keyPoints.find { item -> item.bodyPart == BodyPart.RIGHT_ANKLE }

                        fileWriter.appendLine("${imageFileName},${nose?.coordinate?.x},${nose?.coordinate?.y},${nose?.score}," +
                                "${leftEye?.coordinate?.x},${leftEye?.coordinate?.y},${leftEye?.score}," +
                                "${rightEye?.coordinate?.x},${rightEye?.coordinate?.y},${rightEye?.score}," +
                                "${leftEar?.coordinate?.x},${leftEar?.coordinate?.y},${leftEar?.score}," +
                                "${rightEer?.coordinate?.x},${rightEer?.coordinate?.y},${rightEer?.score}," +
                                "${leftShoulder?.coordinate?.x},${leftShoulder?.coordinate?.y},${leftShoulder?.score}," +
                                "${rightShoulder?.coordinate?.x},${rightShoulder?.coordinate?.y},${rightShoulder?.score}," +
                                "${leftElbow?.coordinate?.x},${leftElbow?.coordinate?.y},${leftElbow?.score}," +
                                "${rightElbow?.coordinate?.x},${rightElbow?.coordinate?.y},${rightElbow?.score}," +
                                "${leftWrist?.coordinate?.x},${leftWrist?.coordinate?.y},${leftWrist?.score}," +
                                "${rightWrist?.coordinate?.x},${rightWrist?.coordinate?.y},${rightWrist?.score}," +
                                "${leftHip?.coordinate?.x},${leftHip?.coordinate?.y},${leftHip?.score}," +
                                "${rightHip?.coordinate?.x},${rightHip?.coordinate?.y},${rightHip?.score}," +
                                "${leftKnee?.coordinate?.x},${leftKnee?.coordinate?.y},${leftKnee?.score}," +
                                "${rightKnee?.coordinate?.x},${rightKnee?.coordinate?.y},${rightKnee?.score}," +
                                "${leftAnkle?.coordinate?.x},${leftAnkle?.coordinate?.y},${leftAnkle?.score}," +
                                "${rightAnkle?.coordinate?.x},${rightAnkle?.coordinate?.y},${rightAnkle?.score}," +
                                "$clas_score,$clas_value")
                    }
                }
                fileWriter.flush()
                fileWriter.close()
                Log.d(TAG, "File $filename has been saved in: ${file.absolutePath}")
            }
        }
        frameProcessedInOneSecondInterval++
        if (frameProcessedInOneSecondInterval == 1) {
            // send fps to view
            listener?.onFPSListener(framesPerSecond)
        }

        // if the model returns only one item, show that item's score.
        if (persons.isNotEmpty()) {
            listener?.onDetectedInfo(persons[0].score, classificationResult)
        }

        //save image without drawing recognized posture
        imageFileName = "image_${now().toInstant().toEpochMilli()}_${bitmap}.jpeg"
        val file = saveBitmapToFile(surfaceView.context, bitmap, imageFileName)
        Log.d(TAG, "Saved image: ${file?.absolutePath}")

        //visualize points and joints on person
        visualize(persons, bitmap)
        //visualize(persons, bitmap, classificationResult)
    }

    private fun visualize(persons: List<Person>, bitmap: Bitmap) {

        val outputBitmap = VisualizationUtils.drawBodyKeypoints(
            bitmap,
            persons.filter { it.score > MIN_CONFIDENCE }, isTrackerEnabled
        )

        val holder = surfaceView.holder
        val surfaceCanvas = holder.lockCanvas()
        surfaceCanvas?.let { canvas ->
            val screenWidth: Int
            val screenHeight: Int
            val left: Int
            val top: Int

            if (canvas.height > canvas.width) {
                val ratio = outputBitmap.height.toFloat() / outputBitmap.width
                screenWidth = canvas.width
                left = 0
                screenHeight = (canvas.width * ratio).toInt()
                top = (canvas.height - screenHeight) / 2
            } else {
                val ratio = outputBitmap.width.toFloat() / outputBitmap.height
                screenHeight = canvas.height
                top = 0
                screenWidth = (canvas.height * ratio).toInt()
                left = (canvas.width - screenWidth) / 2
            }
            val right: Int = left + screenWidth
            val bottom: Int = top + screenHeight

            canvas.drawBitmap(
                outputBitmap, Rect(0, 0, outputBitmap.width, outputBitmap.height),
                Rect(left, top, right, bottom), null
            )

//            imageFileName = "image_$outputBitmap.png"
//            val file = saveBitmapToFile(surfaceView.context, outputBitmap, imageFileName)
//            Log.d(TAG, "Saved image: ${file?.absolutePath}")

            surfaceView.holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun visualize(persons: List<Person>, bitmap: Bitmap, result: List<Pair<String, Float>>? ) {

        val outputBitmap = VisualizationUtils.drawBodyKeypoints(
            bitmap,
            persons.filter { it.score > MIN_CONFIDENCE }, isTrackerEnabled
        )

        val newcanvas = Canvas(outputBitmap)
        val holder = surfaceView.holder
        val surfaceCanvas = holder.lockCanvas()
        if(result != null) {
            val paint = Paint().apply {
                color = Color.GREEN
                textSize = 30f
            }

            var yOffset = paint.textSize // Start at the top with the first line

            for ((text, value) in result) {
                val displayText = "$text: $value.4f"
                newcanvas.drawText(displayText, 10f, yOffset, paint) // 10f is the x-offset
                yOffset += paint.textSize * 1.2f // Adjust line spacing as needed

            }
            Log.d(TAG, "${result}")
        }
        surfaceCanvas?.let { canvas ->
            val screenWidth: Int
            val screenHeight: Int
            val left: Int
            val top: Int

            if (canvas.height > canvas.width) {
                val ratio = outputBitmap.height.toFloat() / outputBitmap.width
                screenWidth = canvas.width
                left = 0
                screenHeight = (canvas.width * ratio).toInt()
                top = (canvas.height - screenHeight) / 2
            } else {
                val ratio = outputBitmap.width.toFloat() / outputBitmap.height
                screenHeight = canvas.height
                top = 0
                screenWidth = (canvas.height * ratio).toInt()
                left = (canvas.width - screenWidth) / 2
            }
            val right: Int = left + screenWidth
            val bottom: Int = top + screenHeight

            canvas.drawBitmap(
                outputBitmap, Rect(0, 0, outputBitmap.width, outputBitmap.height),
                Rect(left, top, right, bottom), null
            )

            //val file = saveBitmapToFile(surfaceView.context, outputBitmap, "image_${outputBitmap}.png")
            //Log.d(TAG, "Saved image: ${file?.absolutePath}")

            surfaceView.holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun stopImageReaderThread() {
        imageReaderThread?.quitSafely()
        try {
            imageReaderThread?.join()
            imageReaderThread = null
            imageReaderHandler = null
        } catch (e: InterruptedException) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun takePhoto() {

        val ORIENTATIONS = SparseIntArray().apply {
            append(Surface.ROTATION_0, 0)
            append(Surface.ROTATION_90, 90)
            append(Surface.ROTATION_180, 180)
            append(Surface.ROTATION_270, 270)
        }
        // Ensure the session is not null before capturing
        session?.let { captureSession ->

            val file = File(surfaceView.context.getExternalFilesDir(null), "photo.png")

            /* imageReader =
                ImageReader.newInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, ImageFormat.YUV_420_888, 3)
            imageReader?.setOnImageAvailableListener({ reader ->
                val image = reader.acquireLatestImage()
                if (image != null) {
                    if (!::imageBitmap.isInitialized) {
                        imageBitmap =
                            Bitmap.createBitmap(
                                PREVIEW_WIDTH,
                                PREVIEW_HEIGHT,
                                Bitmap.Config.ARGB_8888
                            )
                    }
                    yuvConverter.yuvToRgb(image, imageBitmap)
                    // Create rotated version for portrait display
                    val rotateMatrix = Matrix()
                    rotateMatrix.postRotate(90.0f)

                    val rotatedBitmap = Bitmap.createBitmap(
                        imageBitmap, 0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT,
                        rotateMatrix, false
                    )
                    processImage(rotatedBitmap)
                    image.close()
                }
            }, imageReaderHandler) */

            // Set up an ImageReader to get the captured image
            //val imageReader = ImageReader.newInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, ImageFormat.JPEG, 1)
            //val imageReader = ImageReader.newInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, ImageFormat.YUV_420_888, 3)
            //val outputSurface = imageReader?.surface

            // Create a capture request for taking a picture
            val captureRequestBuilder = camera?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureRequestBuilder?.addTarget(imageReader?.surface!!)

            // Set the orientation based on device rotation
            val rotation = (surfaceView.context as Activity).windowManager.defaultDisplay.rotation
            captureRequestBuilder?.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))

            // Set up a listener for when the image is available
            imageReader?.setOnImageAvailableListener({ reader ->
                val image = reader.acquireLatestImage()
                if (image != null) {
                    // Save the image to file
                    //stopPreview()
                    val bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
                    processImage(bitmap)
                    image.close()
                }
            }, imageReaderHandler)

            // Capture the photo
            try {
                captureSession.capture(captureRequestBuilder?.build()!!, object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                        super.onCaptureCompleted(session, request, result)
                        Log.d(TAG, "Photo taken: ${file.absolutePath}")
                    }
                }, imageReaderHandler)
            } catch (e: Exception) {
                Log.e(TAG, "Camera exception while capturing photo: ${e.message}")
            }
        }

    }

    fun saveBitmapToFile(context: Context, bitmap: Bitmap, filename: String): File? {
        //val directory = context.getExternalFilesDir(null) // or getFilesDir() for internal storage
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MobileTrainer")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, filename)

        return try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // PNG is a lossless format, you can use JPEG as well
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    interface CameraSourceListener {
        fun onFPSListener(fps: Int)

        fun onDetectedInfo(personScore: Float?, poseLabels: List<Pair<String, Float>>?)
    }
}
