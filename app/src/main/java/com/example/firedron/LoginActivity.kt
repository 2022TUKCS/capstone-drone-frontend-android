package com.example.firedron

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.firedron.databinding.ActivityLoginBinding
import com.google.gson.annotations.SerializedName
import com.kakao.usermgmt.response.UserResponse
import com.kakao.usermgmt.response.model.User
import org.w3c.dom.Text
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("localhost:8000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val loginservice = retrofit.create(LoginService::class.java)

        binding.button.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener{
            val TextId = binding.editTextTextPersonName.text.toString()
            val TextPw = binding.editTextTextPassword.text.toString()

            loginservice.requestLogin(TextId,TextPw).enqueue(object :Callback<Login>{
                override fun onResponse(call: retrofit2.Call<Login>, response: Response<Login>) {
                    //웹통신 성공했을때 실행
                    var login = response.body() //코드,메세지

                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("성공")
                    dialog.setMessage("id = "+ TextId + "pw = " + TextPw)
                    dialog.show()
                }

                override fun onFailure(call: retrofit2.Call<Login>, t: Throwable) {
                    //웹통신 실패시 실행
                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("실패")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }

            })
        }
    }
}


