package com.smartheard.multipart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import com.smartheard.multipart.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    //image Picker
    var imageRequestcode = 0

    val IMAGE_CODE = 0
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        button.setOnClickListener {
            imageRequestcode = 0
            ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*")).crop()
                .maxResultSize(400, 400).start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE && data!!.data!=null){
            val file = File(data!!.data!!.path)
            uploadImage(file,file.name)
            val imgUpload = UploadImage()
            imgUpload.uploadFile(file,file.path)
            binding.imageView.setImageURI(data!!.data)
        }
    }

    private fun uploadImage(file: File, name: kotlin.String) {

    }

}