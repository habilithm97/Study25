package com.example.study25.model

// 서버에서 받아 올 JSON 데이터를 담음
data class Post(
    val userId: Int, // 작성자 id
    val id: Int, // 게시물 id
    val title: String, // 게시물 제목
    val body: String // 게시물 본문
)