package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class GetUsersViewModel(val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var getUsers: MutableLiveData<List<UserResponse>?> = MutableLiveData()

    init {
        getAllUsers()
    }
    fun getAllUsers()
    {
        viewModelScope.launch {
            getAllUserData()
        }
    }

    private fun getAllUserData() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getUsers(it)
                }

                if (response?.isSuccessful == true) {
                    //Log.d(TAG, "Get tasks response: ${response.body()}")

                    val allUserList = response.body()
                    allUserList?.let {
                        getUsers.value = allUserList
                    }


                } else {
                    Log.d(TAG, "Get allUsers error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "ProfileViewModel - getAllUserData() failed with exception: ${e.message}")
            }
        }
    }


}