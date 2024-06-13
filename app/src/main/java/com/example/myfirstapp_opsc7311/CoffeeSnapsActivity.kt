package com.example.myfirstapp_opsc7311

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.myfirstapp_opsc7311.databinding.ActivityCoffeeSnapsBinding
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoffeeSnapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoffeeSnapsBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeSnapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navToolbar)

        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        val toggleOnOff = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.navToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggleOnOff)
        toggleOnOff.syncState()

        val cameraPermissionCallback = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permissionGranted ->
            if (permissionGranted) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Cannot take a photo without camera permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        cameraPermissionCallback.launch(android.Manifest.permission.CAMERA)

        binding.photoFab.setOnClickListener {
            val fileName = "Coffee_${System.currentTimeMillis()}.jpg"
            val file = File(externalMediaDirs.first(), fileName)
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

            imageCapture?.takePicture(outputFileOptions, imgCaptureExecutor, object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("CoffeeSnapsActivity", "Photo capture failed: ${exception.message}", exception)
                    Toast.makeText(this@CoffeeSnapsActivity, "Error capturing photo: ${exception.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("CoffeeSnapsActivity", "Photo saved to ${outputFileResults.savedUri}")
                    runOnUiThread { binding.imgSavedPhoto.setImageURI(outputFileResults.savedUri) }
                }
            })
        }
    }

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.imgCameraImage.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CoffeeSnapsActivity", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }
}
