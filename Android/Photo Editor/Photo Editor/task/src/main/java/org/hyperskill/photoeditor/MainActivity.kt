package org.hyperskill.photoeditor

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import org.hyperskill.photoeditor.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var currentImage: ImageView
    private lateinit var binding: ActivityMainBinding
    private lateinit var originalImage: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()

        //do not change this line
        currentImage.setImageBitmap(createBitmap())
        originalImage = currentImage.drawable.toBitmap()
            .copy(Bitmap.Config.ARGB_8888, true)

        binding.btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
        }

        binding.slBrightness.addOnChangeListener { _, value, _ ->
            val copyImage = originalImage.copy(Bitmap.Config.ARGB_8888, true)
            val width = originalImage.width
            val height = originalImage.height
            val pixels = IntArray(width * height)
            copyImage.getPixels(pixels, 0, width, 0, 0, width, height)
            for (i in pixels.indices) {
                val pixel = pixels[i]
                val red = (Color.red(pixel) + value).coerceIn(0f, 255f).toInt()
                val green = (Color.green(pixel) + value).coerceIn(0f, 255f).toInt()
                val blue = (Color.blue(pixel) + value).coerceIn(0f, 255f).toInt()
                pixels[i] = Color.rgb(red, green, blue)
            }
            copyImage.setPixels(pixels, 0, width, 0, 0, width, height)
            currentImage.setImageBitmap(copyImage)
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoUri = result.data?.data ?: return@registerForActivityResult
                binding.ivPhoto.setImageURI(photoUri)
                originalImage = binding.ivPhoto.drawable.toBitmap()
                    .copy(Bitmap.Config.ARGB_8888, true)
            }
        }

    private fun bindViews() {
        currentImage = binding.ivPhoto
    }

    // do not change this function
    fun createBitmap(): Bitmap {
        val width = 200
        val height = 100
        val pixels = IntArray(width * height)
        // get pixel array from source

        var R: Int
        var G: Int
        var B: Int
        var index: Int

        for (y in 0 until height) {
            for (x in 0 until width) {
                // get current index in 2D-matrix
                index = y * width + x
                // get color
                R = x % 100 + 40
                G = y % 100 + 80
                B = (x+y) % 100 + 120

                pixels[index] = Color.rgb(R,G,B)

            }
        }
        // output bitmap
        val bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmapOut
    }
}