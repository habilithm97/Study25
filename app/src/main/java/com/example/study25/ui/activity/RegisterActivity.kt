package com.example.study25.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.study25.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        with(binding) {
            with(btnRegister) {
                setOnClickListener {
                    val email = emailInput.text.toString().trim()
                    val password = pwInput.text.toString().trim()
                    val confirmPassword = pwConfirmInput.text.toString().trim()

                    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(applicationContext, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    if (confirmPassword != password) {
                        Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    // Firebase Authentication으로 이메일과 비밀번호로 새 사용자 생성
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { create -> // 생성 완료 시 호출
                            if (create.isSuccessful) {
                                val user = auth.currentUser // 현재 로그인된 사용자
                                user?.sendEmailVerification() // 이메일로 인증 메일 발송
                                    ?.addOnCompleteListener { email -> // 이메일 발송 완료 시 호출
                                        if (email.isSuccessful) {
                                            Toast.makeText(applicationContext, "회원가입 성공! 이메일을 확인하세요.", Toast.LENGTH_LONG).show()
                                            auth.signOut() // 인증 전에는 자동 로그인 되지 않도록 로그아웃
                                            finish()
                                        } else {
                                            Toast.makeText(applicationContext, "이메일 인증 발송 실패", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(applicationContext, "회원 가입 실패", Toast.LENGTH_SHORT).show()
                            }
                        }

                }
            }
        }
    }
}