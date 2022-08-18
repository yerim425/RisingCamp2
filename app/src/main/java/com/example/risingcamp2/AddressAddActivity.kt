package com.example.risingcamp2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.risingcamp2.databinding.ActivityAddressAddBinding

class AddressAddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddressAddBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        binding.tvSetting.setOnClickListener {
            if (binding.editText.text.toString() != "") {
                if (intent.getStringExtra("action") == "addAddr2") {
                    // editText의 Text를 "address2" key의 값으로 저장
                    editor.putString("address2", binding.editText.text.toString())
                    editor.putString("addressFocus", binding.editText.text.toString())
                    editor.commit()
                    Log.d("addActivity", "here")

                    finish()
                }
            } else {
                Toast.makeText(this, "주소를 입력하세요.", Toast.LENGTH_LONG).show()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}