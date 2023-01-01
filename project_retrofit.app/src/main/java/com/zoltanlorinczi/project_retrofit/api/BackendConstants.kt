package com.zoltanlorinczi.project_retrofit.api

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
object BackendConstants {

    /**
     * Project backend base URL.
     */
    const val BASE_URL = "http://tracker-3track.a2hosted.com/"

    /**
     * Specific URL segments, which will be concatenated with the base URL.
     */
    const val LOGIN_URL = "login"
    const val GET_TASKS_URL = "task/getTasks"
    const val GET_USERS_URL = "user"
    const val GET_DEPARTMENT_URL = "department"
    const val UPDATE_USER = "users/updateProfile"
    const val GET_USERS = "users"
    const val CREATE_TASK = "task/create"

    /**
     * Header values.
     */
    const val HEADER_TOKEN = "token"
}