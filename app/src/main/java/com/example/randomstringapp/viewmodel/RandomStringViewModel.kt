package com.example.randomstringapp.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.randomstringapp.model.RandomStringItem
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class RandomStringViewModel  @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val CONTENT_URI = "content://com.iav.contestdataprovider/text"
    private val COLUMN_NAME = "data"

    private val contentResolver: ContentResolver = application.contentResolver
    private val _randomStrings = MutableLiveData<List<RandomStringItem>>()
    val randomStrings: LiveData<List<RandomStringItem>> = _randomStrings


   fun fetchRandomString(length: String) {
       val uri = Uri.parse("content://com.iav.contestdataprovider/text")
       val queryArgs = Bundle().apply {
           putInt(ContentResolver.QUERY_ARG_LIMIT, length.toInt())  // Ensure it's not null
       }

       try {
           contentResolver.query(uri, arrayOf(COLUMN_NAME), queryArgs, null)?.use { cursor ->
               if (cursor.moveToFirst()) {
                   val timestamp = System.currentTimeMillis() // Current time in milliseconds

                   // Define a formatter (ISO 8601 format)
                   val dateFormat = SimpleDateFormat("h:mm a, M/d/yyyy", Locale.getDefault())
                   dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")  // Set to UTC

                 //  val formattedDate = dateFormat.format(Date(timestamp))
                   val formattedDate = dateFormat.format(Date(System.currentTimeMillis()))
                   val generatedString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                   val jsonObject = JSONObject(generatedString)

                   // Extract "value"
                   val value = jsonObject.getJSONObject("randomText").getString("value")
                   val newItem = RandomStringItem(value, length.toInt(), formattedDate.toString())
                   _randomStrings.value = _randomStrings.value.orEmpty() + newItem
                   Log.d("ContentProvider", "Received: $generatedString")
               } else {
                   Log.e("ContentProvider", "No data returned from provider")
               }
           } ?: Log.e("ContentProvider", "Cursor is null")
       } catch (e: Exception) {
           Log.e("ContentProvider", "Query failed: ${e.message}", e)
       }
   }
    fun deleteAllStrings() {
        _randomStrings.value = emptyList()
    }

    fun deleteString(item: RandomStringItem) {
        _randomStrings.value = _randomStrings.value?.filterNot { it == item }
    }
}
