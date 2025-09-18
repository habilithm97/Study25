package com.example.study25.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.study25.adapter.PostAdapter
import com.example.study25.databinding.ActivityRetrofitBinding
import com.example.study25.model.Post
import com.example.study25.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRetrofitBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this@RetrofitActivity)

        // enqueue : 비동기 요청 실행
        RetrofitClient.instance.getPosts().enqueue(object : Callback<List<Post>> {
            // 서버로부터 응답이 왔을 때 실행
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    // 응답 body를 가져오고, null이면 빈 리스트로 대체
                    val posts = response.body() ?: emptyList()
                    binding.recyclerView.adapter = PostAdapter(posts)
                }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                println("에러: ${t.message}")
            }
        })
    }
}