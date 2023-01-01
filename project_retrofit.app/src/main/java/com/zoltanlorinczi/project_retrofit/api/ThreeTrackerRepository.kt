package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.*
import retrofit2.Response

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
class ThreeTrackerRepository {

    /**
     * 'suspend' keyword means that this function can be blocking.
     * We need to be aware that we can only call them from within a coroutine or an other suspend function!
     */
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponse> {
        return RetrofitInstance.USER_API_SERVICE.login(loginRequestBody)
    }

    suspend fun getTasks(token: String): Response<List<TaskResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getTasks(token)
    }
    suspend fun getUser(token: String): Response<UserResponse>
    {
        return RetrofitInstance.USER_API_SERVICE.getUser(token)
    }
    suspend fun getUsers(token: String):Response< List <UserResponse> >
    {
        return RetrofitInstance.USER_API_SERVICE.getUsers(token)
    }
    suspend fun createTask(token: String,createTaskResponse: CreateTaskResponse) : Response<String>
    {
        return RetrofitInstance.USER_API_SERVICE.createTask(token,createTaskResponse)
    }
    suspend fun updateUser(token: String,updateRequestResponse: UpdateRequestResponse) : Response<String>
    {
        return RetrofitInstance.USER_API_SERVICE.updateUser(token,updateRequestResponse)
    }
    suspend fun getDepartment(token: String): Response<List<DepartmentResponse>>
    {
        return RetrofitInstance.USER_API_SERVICE.getDepartment(token)
    }
}