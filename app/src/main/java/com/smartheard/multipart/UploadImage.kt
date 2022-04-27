package com.smartheard.multipart

import android.util.Log
import android.webkit.MimeTypeMap
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class UploadImage {
    val client = OkHttpClient()

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
    fun   uploadFile (sourceFile : File, uploadedFileName :   String?  = null) {

        Thread  {
            val  mimeType  =  getMimeType(sourceFile);
            if  (mimeType  ==   null ) {
                Log .e( " file error " ,  " Not able to get mime type " )
                return@Thread
            }
            val  fileName :   String   =   if  (uploadedFileName  ==   null )  sourceFile.name  else  uploadedFileName


            try  {
                // toggleProgressDialog( true )

                val  requestBody : RequestBody =
                    MultipartBody . Builder ()
                        .setType( MultipartBody . FORM )
                        .addFormDataPart( " image " , fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()



                val  request : Request =   Request . Builder ().url("url").post(requestBody).build()

                val  response : Response =  client.newCall(request).execute()

                if  (response.isSuccessful) {  try {

                    val jsonData = response.body?.string()
                    val Jobject = JSONObject(jsonData)
                    Log.e("SuccessRes","$Jobject")


                } catch (e: Exception) {
                    Log.e("ResponseErr", "uploadingPatientRegImg: ${e.message}")
                }

                    Log .e( " File upload " , " success, path:  $response " )



                    //  Log .d( " File upload " , " success, path:  $serverUploadDirectoryPath$fileName " )
                    // showToast( " File uploaded successfully at  $serverUploadDirectoryPath$fileName " )
                }  else  {
                    Log .e( " File upload " ,  " failed " )
                    //   showToast( " File uploading failed " )
                }
            }  catch  (ex :   Exception ) {
                ex.printStackTrace()
                Log .e( " File upload " ,  " failed " )
                // showToast( " File uploading failed " )
            }
            // toggleProgressDialog( false )
        }.start()
    }

}