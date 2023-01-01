package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.CreateTaskResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateTaskViewModel (private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun create(title : String,description: String,assidned_to_user : Int,priority: Int,deadline: Long,department_id : Int,status : Int) {
        val requestBody = CreateTaskResponse(title,description,assidned_to_user,priority,deadline,department_id,status)
        viewModelScope.launch {
            executeCreateTask(requestBody)
        }
    }

    private suspend fun executeCreateTask(requestBody: CreateTaskResponse) {
        try {
            val token: String? = App.sharedPreferences.getStringValue(
                SharedPreferencesManager.KEY_TOKEN, "Empty token!")

            val response = withContext(Dispatchers.IO) {
                token?.let { repository.createTask(it, requestBody) }
            }

            if (response != null) {
                if (response.isSuccessful) {
                    Log.d(TAG, "CreateTask response: ${response.body()}")
                    isSuccessful.value = true

                } else {
                    //Log.d(TAG, "CreateTask error response: ${response.message()}")
                    isSuccessful.value = false
                }
            }
        } catch (e: Exception) {
            //Log.d(TAG, " CreateTaskViewModel - updateUser() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}