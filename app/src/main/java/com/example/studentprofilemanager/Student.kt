package com.example.studentprofilemanager

import java.io.Serializable

data class Student(
    val mssv: String,
    val fullName: String,
    val className: String,
    val email: String,
    val gpa: Double
) : Serializable