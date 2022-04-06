package com.example.firedron.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firedron.Activity.LiveActivity
import com.example.firedron.Activity.MapActivity
import com.example.firedron.databinding.ActivityMainBinding
import com.example.firedron.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private var mBinding : FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        mBinding = binding
        return mBinding?.root
    }



    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()

    }
}