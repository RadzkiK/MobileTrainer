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
class SquatTest {
    companion object {
        private const val TEST_SU = "su1.jpg"
        private const val TEST_SD = "sd1.jpg"
        private const val TEST_SU2 = "su2.jpg"
        private const val TEST_SD2 = "sd2.jpg"
        private const val TEST_SU3 = "su3.jpg"
        private const val TEST_SD3 = "sd3.jpg"
        private const val TEST_SU4 = "su4.jpg"
        private const val TEST_SD4 = "sd4.jpg"
        private const val TEST_SU5 = "su5.jpg"
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
    fun testSquatDownClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SD)
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
            "squat_down",
            predictedPose
        )
    }

    @Test
    fun testSquatDownClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SD2)
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
            "squat_down",
            predictedPose
        )
    }

    @Test
    fun testSquatDownClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SD3)
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
            "squat_down",
            predictedPose
        )
    }

    @Test
    fun testSquatDownClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SD4)
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
            "squat_down",
            predictedPose
        )
    }

    @Test
    fun testSquatUpClassifier() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SU)
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
            "squat_up",
            predictedPose
        )
    }

    @Test
    fun testSquatUpClassifier2() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SU2)
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
            "squat_up",
            predictedPose
        )
    }

    @Test
    fun testSquatUpClassifier3() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SU3)
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
            "squat_up",
            predictedPose
        )
    }

    @Test
    fun testSquatUpClassifier4() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SU4)
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
            "squat_up",
            predictedPose
        )
    }

    @Test
    fun testSquatUpClassifier5() {
        val input = EvaluationUtils.loadBitmapAssetByName(TEST_SU5)
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
            "squat_up",
            predictedPose
        )
    }
}