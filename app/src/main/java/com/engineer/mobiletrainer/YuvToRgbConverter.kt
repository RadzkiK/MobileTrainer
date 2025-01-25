package com.engineer.mobiletrainer

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.media.Image

class YuvToRgbConverter {

    @Synchronized
    fun yuvToRgb(image: Image, output: Bitmap) {
        // Upewnij się, że obraz jest w formacie YUV_420_888
        require(image.format == ImageFormat.YUV_420_888) { "Unsupported image format!" }

        val yBuffer = image.planes[0].buffer // Y
        val uBuffer = image.planes[1].buffer // U
        val vBuffer = image.planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        // Skopiuj dane Y do tablicy NV21
        yBuffer.get(nv21, 0, ySize)

        // Skopiuj dane V i U (interleave VU zamiast UV)
        val chromaRowStride = image.planes[1].rowStride
        val chromaPixelStride = image.planes[1].pixelStride

        var offset = ySize
        for (row in 0 until image.height / 2) {
            var column = 0
            while (column < image.width / 2) {
                val vuIndex = row * chromaRowStride + column * chromaPixelStride
                nv21[offset++] = vBuffer[vuIndex] // V
                nv21[offset++] = uBuffer[vuIndex] // U
                column++
            }
        }

        // Przetwórz NV21 na Bitmapę
        val yuvImage = android.graphics.YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val outputStream = java.io.ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, image.width, image.height), 100, outputStream)
        val jpegByteArray = outputStream.toByteArray()

        // Konwertuj JPEG na bitmapę
        val bitmap = android.graphics.BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.size)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, output.width, output.height, true)

        // Skopiuj dane do wyjściowego bitmapu
        val canvas = android.graphics.Canvas(output)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, null)
    }
}