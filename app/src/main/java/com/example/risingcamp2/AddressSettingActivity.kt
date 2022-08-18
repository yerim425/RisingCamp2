package com.example.risingcamp2

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import com.example.risingcamp2.databinding.ActivityAddressSettingBinding

class AddressSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressSettingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userAddress: String
    private lateinit var focus:String
    private var TAG = "lifecycle"
    private lateinit var dialog_remove: Dialog // 주소 삭제 다이얼로그
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityAddressSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "AddressSettingActivity - onCreate 호출")


        // 데이터 로드
        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)
        // 첫번째 주소 적용(첫번째 주소 데이터는 무조건 가진다.)
        binding.tvAddr1.text = sharedPreferences.getString("address1", "ERROR")
        // 두번째 주소 적용 -> onResume에 구현(다이얼로그 응답 결과로 해당 뷰의 UI가 바뀌어야 하기 때문)




        // 클릭 리스너
        // 첫번째 주소 TextView
        binding.tvAddr1.setOnClickListener {
            setFocusAddr1()
        }
        // 두번째 주소 TextView
        binding.tvAddr2.setOnClickListener {
            if(sharedPreferences.contains("address2") == false){ // 두번째 주소 추가를 안한 상태라면
                var intent = Intent(this, AddressAddActivity::class.java)
                intent.putExtra("action", "addAddr2") // 두번째 주소의 추가하는 것을 알리기 위함
                startActivity(intent)
            }else{
                setFouseAddr2()
            }
        }

        // 주소 삭제 ImageView 클릭 리스너
        //  첫번째 주소 삭제 ImageView
        //  -> 설정된 주소는 최소 하나를 가져야 하므로 삭제는 불가능하며 다이얼로그에서 변경 응답을 하면 그에 따른 액티비티를 시작한다.

        //  두번째 주소 삭제 ImageView
        //  -> 삭제하겠냐는 다이얼로그를 생성한뒤 응답에 따른 처리를 한다.
        binding.imCancel2.setOnClickListener {
            var intent = Intent(this, AddressRemoveActivity::class.java)
            intent.putExtra("action", "address2")
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            // 사용자가 두 개의 주소 뷰 중 focus로 선택한 뷰의 Text 값(주소)를 MainActivity의 homeFragment에서 표시해야므로
            // 현재 Acitivity의 종료 직전에 focus에 대한 데이터 정보를 저장한다.
            var txtAddr: String
            if(binding.tvAddr1.currentTextColor == Color.WHITE){ // 첫번째 주소가 선택되어있다면
                txtAddr = binding.tvAddr1.text.toString()
            }else{
                txtAddr = binding.tvAddr2.text.toString()
                Log.d("here", txtAddr)
            }

            sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("addressFocus", txtAddr) // 선택되어있는 주소의 Text를 Focus 값으로 저장
            Log.d("txtAddr", txtAddr)
            editor.commit()
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        // onRestart()가 호출되는 경우

        Log.d(TAG, "AddressSettingActivity - onRestart 호출")

        //binding.viewBgDark.visibility = View.INVISIBLE


        // AddressAddActivity에서 두번째 주소가 추가되었거나, back 버튼을 눌러 돌아옴
        // 전자의 경우 AddressSettingActivity에 해당 주소 Text를 업데이트함
        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)
        if(sharedPreferences.contains("address2") == true) {
            binding.tvAddr2.text = sharedPreferences.getString("address2", "ERROR")
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "AddressSettingActivity - onStart 호출")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "AddressSettingActivity - onResume 호출")

        binding.viewBgDark.visibility = View.INVISIBLE

        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)

        // (onPause -> onResume) 의 경우
        // -> 사용자가 주소 삭제 액티비티에서 취소 또는 확인버튼을 눌러 현재 액티비티로 돌아옴
        if(sharedPreferences.contains("address2") == true){ // 두번째 주소 데이터를 가진다면
            binding.tvAddr2.text = sharedPreferences.getString("address2", "ERROR")
            binding.imAddr2.visibility = View.GONE
            binding.imCancel2.visibility = View.VISIBLE
        }else{ // 두번째 주소 설정이 안되어있는 경우
            binding.tvAddr2.text = ""
            binding.imAddr2.visibility = View.VISIBLE
            binding.imCancel2.visibility = View.GONE
        }


        if(sharedPreferences.getString("addressFocus", "ERROR") == binding.tvAddr1.text.toString()){
            setFocusAddr1()
        }else{
            setFouseAddr2()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "AddressSettingActivity - onPause 호출")


        binding.viewBgDark.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "AddressSettingActivity - onStop 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AddressSettingActivity - onDestroy 호출")


    }

    // address1 ui를 focus ui로 설정
    private fun setFocusAddr1(){
        //첫번째 주소 TextView가 focus가 되도록 색상 변경
        binding.tvAddr1.setTextColor(Color.WHITE)
        binding.tvAddr1.background = getDrawable(R.drawable.shape_tv_set_location_round)
        binding.imCancel1.setColorFilter(Color.WHITE)

        binding.tvAddr2.setTextColor(Color.BLACK)
        binding.tvAddr2.background = getDrawable(R.drawable.shape_tv_location_edge)
        binding.imCancel2.setColorFilter(Color.BLACK)
    }

    private fun setFouseAddr2(){
        //두번째 주소 TextView가 focus가 되도록 색상 변경
        binding.tvAddr2.setTextColor(Color.WHITE)
        binding.tvAddr2.background = getDrawable(R.drawable.shape_tv_set_location_round)
        binding.imCancel2.setColorFilter(Color.WHITE)

        binding.tvAddr1.setTextColor(Color.BLACK)
        binding.tvAddr1.background = getDrawable(R.drawable.shape_tv_location_edge)
        binding.imCancel1.setColorFilter(Color.BLACK)
    }
}