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
class BenchpressTest {
    companion object {
        private const val TEST_BU = "bu1.jpg"
        private const val TEST_BD = "bd.jpg"
        private const val TEST_BU2 = "bu2.jpg"
        private const val TEST_BD2 = "bd2.jpg"
        private const val TEST_BU3 = "bu3.jpg"
        private const val TEST_BD3 = "bd3.jpg"
        private const val TEST_BU4 = "bu4.jpg"
        private const val TEST_BD4 = "bd4.jpg"
        private const val TEST_BU5 = "bu5.jpg"
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
    fun testBenchpressDownClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BD)
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
            "benchpress_down",
            predictedPose
        )
    }

    @Test
    fun testBenchpressDownClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BD2)
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
            "benchpress_down",
            predictedPose
        )
    }

    @Test
    fun testBenchpressDownClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BD3)
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
            "benchpress_down",
            predictedPose
        )
    }

    @Test
    fun testBenchpressDownClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BD4)
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
            "benchpress_down",
            predictedPose
        )
    }

    @Test
    fun testBenchpressUpClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BU)
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
            "benchpress_up",
            predictedPose
        )
    }

    @Test
    fun testBenchpressUpClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BU2)
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
            "benchpress_up",
            predictedPose
        )
    }

    @Test
    fun testBenchpressUpClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BU3)
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
            "benchpress_up",
            predictedPose
        )
    }

    @Test
    fun testBenchpressUpClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BU4)
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
            "benchpress_up",
            predictedPose
        )
    }

    @Test
    fun testBenchpressUpClassifier5() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_BU5)
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
            "benchpress_up",
            predictedPose
        )
    }
}