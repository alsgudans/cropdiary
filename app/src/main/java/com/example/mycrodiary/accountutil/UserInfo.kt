package com.example.mycrodiary.accountutil

data class UserInfo(
    var email: String? = null,
    var nickname: String? = null,
    var uid: String? = null // 사용자의 Firebase UID 추가
)