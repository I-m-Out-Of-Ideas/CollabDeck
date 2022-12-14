package com.example.testapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File

class editProfileActivity : AppCompatActivity() {

    val photoFileName = "profile.jpg"
    var photoFile: File? = null
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    lateinit var ivProfileImage: ImageView
    lateinit var tvUsernameE: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        ivProfileImage = findViewById(R.id.ivProfileImage)
        tvUsernameE = findViewById(R.id.tvUsernameE)

        val user = ParseUser.getCurrentUser()

        //TODO: populate ivProfileImage with current profile image
        Glide.with(this).load(user.getParseFile("pfp")?.url).placeholder(R.drawable.loading).thumbnail(Glide.with(this).load(R.drawable.loading)).into(ivProfileImage)
        //so that when the user opens edit profile they can see their pfp
        tvUsernameE.text = ParseUser.getCurrentUser().username.toString()

        // take picture
        findViewById<Button>(R.id.openCamera).setOnClickListener{
            onLaunchCamera()
        }

        // accept changes, return user to profile screen
        findViewById<Button>(R.id.confirm_button).setOnClickListener {
            Log.d("ACTIVITY" , "save btn clicked")
            // loading screen while saving pic
            Glide.with(this).asGif().load(R.drawable.loading).into(ivProfileImage);
            user.put("pfp", ParseFile(photoFile))
            user.saveInBackground() {exception ->
                Log.d("ACTIVITY" , "saving prof pic")
                if (exception != null) {
                    Log.d("ACTIVITY" , "confirmBtn fail ${exception}")
                }
                else {
                    Log.d("ACTIVITY", "confirmBtn success")
                    // once saved, return user to profile screen
                    val intent = Intent(this@editProfileActivity, profile_activity::class.java)
                    startActivity(intent)
                }
            }
        }

        // cancel changes, return user to profile screen
        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            val intent = Intent(this@editProfileActivity, profile_activity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //camera photo is on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                //RESIZE BITMAP and load taken image into preview
                Glide.with(this).load(takenImage).into(ivProfileImage)

            } else {
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ACTIVITY")

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    companion object {
        val TAG = "editProfileActivity"
    }
}