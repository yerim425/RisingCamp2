package com.example.risingcamp2

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.risingcamp2.databinding.ActivityMainBinding
import com.example.risingcamp2.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    private val TAG = "lifecycle"
    private lateinit var binding: ActivityMainBinding
    private lateinit var nowAddress:String  // 새로 변경된 주소
    private lateinit var preAddress: String // 이전에 저장해 놓은 주소
    private lateinit var sharedPreferences: SharedPreferences

    /*
    private var homeFragment = supportFragmentManager.findFragmentById(R.id.frag_home) as HomeFragment?
    private var lifeFragment = supportFragmentManager.findFragmentById(R.id.frag_life) as LifeFragment?
    private var locationFragment = supportFragmentManager.findFragmentById(R.id.frag_location) as LocationFragment?
    private var chatFragment = supportFragmentManager.findFragmentById(R.id.frag_chat) as ChatFragment?
    private var myPageFragment = supportFragmentManager.findFragmentById(R.id.frag_my_page) as MyPageFragment?
     */

    private var homeFragment = HomeFragment()
    private var lifeFragment = LifeFragment()
    private var locationFragment = LocationFragment()
    private var chatFragment = ChatFragment()
    private var myPageFragment = MyPageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "MainActivity - onCreate 호출")


        // fragment를 framelayout에 연결
        // 1. bottomNavigationView 객체를 가져옴
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // 2. // frameLayout에 homeFragment를 추가함 (앱이 처음 실행되었을 때 보여질 Fragment 설정)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, HomeFragment()).commitAllowingStateLoss()
        // 3. bottomNavigationView의 item 선택 리스너
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) { // item들의 id 중에서
                R.id.menuHome -> { // menuHome을 선택하면
                    homeFragment = HomeFragment() // HomeFragment 객체를 새로 생성하여
                    // frameLayout에 homeFragment 배치
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment).commit()
                    // 게시물 추가 버튼 보이기 활성화
                    binding.ivAdd.visibility = View.VISIBLE
                    true // 성공하면 true 리턴
                }
                R.id.menuLife -> {
                    lifeFragment = LifeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, lifeFragment).commit()
                    binding.ivAdd.visibility = View.VISIBLE
                    true
                }
                R.id.menuLocation -> {
                    locationFragment = LocationFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, locationFragment).commit()
                    binding.ivAdd.visibility = View.VISIBLE
                    true
                }
                R.id.menuChat -> {
                    chatFragment = ChatFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, chatFragment).commit()
                    binding.ivAdd.visibility = View.GONE // 게시물 추가 버튼 보이기 비활성화
                    true
                }
                R.id.menuMypage -> {
                    myPageFragment = MyPageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, myPageFragment).commit()
                    binding.ivAdd.visibility = View.GONE
                    true
                }
                else -> false
            }
        }

        // 나중에 주소를 변경하였을 때 비교를 하기 위해 이전에 설정되어있던 주소를 저장해 놓음
        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)

        /* 초기 데이터 저장
        var editor = sharedPreferences.edit()
        editor.putString("address1", "삼성동")
        editor.putString("addressFocus", "삼성동")
        editor.commit()
        */

        preAddress = sharedPreferences.getString("addressFocus", "ERROR").toString()
        nowAddress = preAddress
    }


    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainActivity - onRestart 호출")

        // AddressSettingActivity에서 설정한 주소가 이전에 설정해 놓은 주소와 다르면

        sharedPreferences = getSharedPreferences("userAddress", MODE_PRIVATE)
        nowAddress = sharedPreferences.getString("addressFocus", "ERROR").toString()

        // 2. Activity에 Toast 띄움
        if(preAddress != nowAddress){
            Toast.makeText(this, "현재 동네가 '" + nowAddress + "'으로 변경되었습니다.", Toast.LENGTH_LONG).show()
        }
        preAddress = nowAddress

        // 주소 변경 등의 데이터를 재설정하기 위해 fragment 초기화
        homeFragment = HomeFragment()
        lifeFragment = LifeFragment()
        locationFragment = LocationFragment()
        chatFragment = ChatFragment()
        myPageFragment = MyPageFragment()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart 호출")
    }

    fun getAddress(): String{
        return nowAddress
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume 호출")
        homeFragment = HomeFragment()


    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity - onPause 호출")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity - onDestroy 호출")

    }


}