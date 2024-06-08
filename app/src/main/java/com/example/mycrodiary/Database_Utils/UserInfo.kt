package com.example.mycrodiary.Database_Utils

data class UserInfo(
    var email: String? = null,
    var nickname: String? = null,
    var uid: String? = null, // 사용자의 Firebase UID 추가
    var point: String = "0"
)