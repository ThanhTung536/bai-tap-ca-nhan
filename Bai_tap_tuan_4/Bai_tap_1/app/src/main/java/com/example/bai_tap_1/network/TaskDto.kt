package com.example.bai_tap_1.network

import com.google.gson.annotations.SerializedName

data class SubtaskDto(
    @SerializedName("title") val title: String = "",
    @SerializedName("done") val done: Boolean = false
)

data class TaskDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("note") val note: String = "",
    @SerializedName("category") val category: String = "study",
    @SerializedName("date") val date: String = "",
    @SerializedName("time") val time: String = "",
    @SerializedName("priority") val priority: String = "",
    @SerializedName("remind") val remind: Boolean = false,
    @SerializedName("done") val done: Boolean = false,
    @SerializedName("subtasks") val subtasks: List<SubtaskDto> = emptyList()
)
