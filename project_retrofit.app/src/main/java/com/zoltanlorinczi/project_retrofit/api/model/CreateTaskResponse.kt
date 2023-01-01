package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class CreateTaskResponse (

    @SerializedName("title")
    var title : String,

    @SerializedName("description")
    var description: String,

    @SerializedName("assignedToUserId")
    var assidned_to_user : Int,

    @SerializedName("priority")
    var priority: Int,

    @SerializedName("deadline")
    var deadline: Long,

    @SerializedName("departmentId")
    var department_id : Int,

    @SerializedName("status")
    var status : Int

)