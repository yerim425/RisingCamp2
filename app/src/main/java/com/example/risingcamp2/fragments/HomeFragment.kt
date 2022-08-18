package com.example.risingcamp2.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.risingcamp2.AddressSettingActivity
import com.example.risingcamp2.MainActivity
import com.example.risingcamp2.R
import com.example.risingcamp2.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val TAG = "lifecycle"
    private lateinit var binding: FragmentHomeBinding
    private var address: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        address = (activity as MainActivity).getAddress()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Fragment - onCreate")
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        Log.d(TAG, "Fragment - onCreateView")



        binding.tvAddr.setOnClickListener {
            var intent = Intent(activity, AddressSettingActivity::class.java)
            startActivity(intent)
        }


        //return inflater.inflate(R.layout.fragment_home, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Fragment - onStart")

        address = (activity as MainActivity).getAddress()
        binding.tvAddr.text = address
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Fragment - onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Fragment - onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Fragment - onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Fragment - onDestroy")

    }

    fun changeAddrText(addr: String){
        Log.d("fragment", "changeAddrText "+addr)

        //this.requireView().findViewById<TextView>(R.id.tv_addr)!!.text.toString()


    }
}