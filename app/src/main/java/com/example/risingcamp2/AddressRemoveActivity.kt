package com.example.risingcamp2

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.risingcamp2.databinding.ActivityAddressRemoveBinding

class AddressRemoveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressRemoveBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressRemoveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dm: DisplayMetrics = resources.displayMetrics
        window.setLayout((dm.widthPixels *0.8).toInt(), (dm.heightPixels *0.2).toInt())

        // sharedPreference 파일 데이터 가져오기
        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        binding.dialogYes.setOnClickListener {
            // 주소 데이터 삭제
            editor.remove("address2")
            editor.putString("addressFocus", sharedPreferences.getString("address1", "ERROR"))
            editor.commit()
            finish()
        }
        binding.dialogNo.setOnClickListener {
            finish()
        }


    }

}