package com.kyjsoft.tp09plant.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.kyjsoft.tp09plant.databinding.ActivityCameraBinding
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException

class CameraActivity : AppCompatActivity() {

    val binding by lazy{ActivityCameraBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) // 상태표시줄이랑 아래 물리버튼 영역까지 확대
        binding.fabImageCapture.setOnClickListener { clickCapture() }

        // 동적 퍼미션 -> 접근을 위한 퍼미션
        var permission : Array<String?>? = null

        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ) else arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if(checkSelfPermission(permission[0]!!) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permission, 10)
        } else {
            // 퍼미션 받으면 프리뷰를 시작한다.
            startPreview()
        }
    }

    // 동적 퍼미션 카메라 기능 사용할 때 필요한 퍼미션
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 10){
            for(grantresult in grantResults){
                if(grantresult == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "카메라 사용 기능 불가", Toast.LENGTH_SHORT).show()
                    finish()
                    return
                }
            }
            startPreview()
        }
    }

    var imageCapture: ImageCapture? = null

    private fun clickCapture(){

        if(imageCapture == null) return

        // 1. 이미지를 캡쳐하고 파일에 파일명 붙여주기 (날짜로)
        val filename = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg") // 확장자 타입 정해주기
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) contentValues.put(
            MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/CameraX-Image"
        )

        // 2. 찍은 사진 이미지 경로 관리 객체 -> 이 때 필요한게 파일명
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        ).build()

        // 3. 이미지 캡쳐한테 사진 취득하라고 명령하기 -> 그 때 필요한 게 경로
        imageCapture!!.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(this@CameraActivity, "촬영성공", Toast.LENGTH_SHORT).show()

//                    val uri = outputFileResults.savedUri

                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(this@CameraActivity, "오류로 인해 캡쳐가 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun startPreview(){
        // 1.카메라 on/off랑 액티비티 생명주기랑 맞추기
        val listenableFuture = ProcessCameraProvider.getInstance(this)
        // 2. preview 준비가 끝났음을 알리는 리스너 (카메라준비, 이미지 캡쳐 객체준비)
        listenableFuture.addListener({

            try {
                // 카메라 기능 객체 가져오기
                val cameraProvider = listenableFuture.get()

                // 프리뷰 객체 생성
                val preview = Preview.Builder().build()
                // 프리뷰 객체가 사용할 고속 버퍼 뷰(SurfaceView, 화면을 빠르게 잡아내는 뷰) 설정
                preview.setSurfaceProvider(binding.previewView!!.surfaceProvider)

                // 디바이스에 있는 카메라 중 하나를 선택
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                cameraProvider.unbindAll() // 혹시 기존에 연결되어있는 카메라 기능들을 제거하고 내 앱 생명주기에 맞춰서 카메라 preview를 제어하도록

                // bind 하기 전에 이미지 캡쳐를 하는 객체 생성하기
                imageCapture = ImageCapture.Builder().build()

                // preview랑 imageCapture 둘다 생명 주기 에 붙여줌
                cameraProvider.bindToLifecycle(
                    this@CameraActivity,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))

    }
}