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
class RowingTest {
    companion object {
        private const val TEST_RU = "ru1.jpg"
        private const val TEST_RD = "rd1.jpg"
        private const val TEST_RU2 = "ru2.jpg"
        private const val TEST_RD2 = "rd2.jpg"
        private const val TEST_RU3 = "ru3.jpg"
        private const val TEST_RD3 = "rd3.jpg"
        private const val TEST_RU4 = "ru4.jpg"
        private const val TEST_RD4 = "rd4.jpg"
        private const val TEST_RU5 = "ru5.jpg"
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
    fun testRowingDownClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RD)
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
            "rowing_down",
            predictedPose
        )
    }

    @Test
    fun testRowingDownClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RD2)
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
            "rowing_down",
            predictedPose
        )
    }

    @Test
    fun testRowingDownClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RD3)
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
            "rowing_down",
            predictedPose
        )
    }

    @Test
    fun testRowingDownClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RD4)
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
            "rowing_down",
            predictedPose
        )
    }

    @Test
    fun testRowingUpClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RU)
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
            "rowing_up",
            predictedPose
        )
    }

    @Test
    fun testRowingUpClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RU2)
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
            "rowing_up",
            predictedPose
        )
    }

    @Test
    fun testRowingUpClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RU3)
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
            "rowing_up",
            predictedPose
        )
    }

    @Test
    fun testRowingUpClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RU4)
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
            "rowing_up",
            predictedPose
        )
    }

    @Test
    fun testRowingUpClassifier5() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_RU5)
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
            "rowing_up",
            predictedPose
        )
    }
}