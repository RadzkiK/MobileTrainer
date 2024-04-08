package com.engineer.mobiletrainer

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.engineer.mobiletrainer.data.Device
import com.engineer.mobiletrainer.ml.ModelType
import com.engineer.mobiletrainer.ml.MoveNet
import com.engineer.mobiletrainer.ml.PoseClassifier
import com.engineer.mobiletrainer.ml.PoseDetector
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeadliftTest {
    companion object {
        private const val TEST_DU = "du.jpg"
        private const val TEST_DD = "dd.jpg"
        private const val TEST_DU2 = "du2.jpg"
        private const val TEST_DD2 = "dd2.jpg"
        private const val TEST_DU3 = "du3.jpg"
        private const val TEST_DD3 = "dd3.jpg"
        private const val TEST_DU4 = "du4.jpg"
        private const val TEST_DD4 = "dd4.jpg"
        private const val TEST_DU5 = "du5.jpg"
    }

    private lateinit var appContext: Context
    private lateinit var poseDetector: PoseDetector
    private lateinit var poseClassifier: PoseClassifier

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        poseDetector = MoveNet.create(appContext, Device.CPU, ModelType.Lightning)
        poseClassifier = PoseClassifier.create(appContext)
    }

    @Test
    fun testDeadliftDownClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DD)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_down",
            predictedPose
        )
    }

    @Test
    fun testDeadliftDownClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DD2)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_down",
            predictedPose
        )
    }

    @Test
    fun testDeadliftDownClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DD3)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_down",
            predictedPose
        )
    }

    @Test
    fun testDeadliftDownClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DD4)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_down",
            predictedPose
        )
    }

    @Test
    fun testDeadliftUpClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DU)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_up",
            predictedPose
        )
    }

    @Test
    fun testDeadliftUpClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DU2)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_up",
            predictedPose
        )
    }

    @Test
    fun testDeadliftUpClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DU3)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_up",
            predictedPose
        )
    }

    @Test
    fun testDeadliftUpClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DU4)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_up",
            predictedPose
        )
    }

    @Test
    fun testDeadliftUpClassifier5() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_DU5)
        // As Movenet use previous frame to optimize detection result, we run it multiple times
        // using the same image to improve result.
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        poseDetector.estimatePoses(input)
        val person = poseDetector.estimatePoses(input)[0]
        val classificationResult = poseClassifier.classify(person)
        val predictedPose = classificationResult.maxByOrNull { it.second }?.first ?: "n/a"
        TestCase.assertEquals(
            "Predicted pose is different from ground truth.",
            "deadlift_up",
            predictedPose
        )
    }
}