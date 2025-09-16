package com.example.study25.network

import com.example.study25.model.Post
import retrofit2.Call
import retrofit2.http.GET

// 서버 통신 규약 정의
interface ApiService {
    @GET("posts") // GET 요청으로 "posts" 엔드 포인트 호출
    fun getPosts(): Call<List<Post>> // 응답을 Post 객체 리스트로 변환해 반환
}