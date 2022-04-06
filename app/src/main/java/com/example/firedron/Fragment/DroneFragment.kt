package com.example.firedron.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firedron.databinding.FragmentDroneBinding
import com.example.firedron.databinding.FragmentFlyBinding
import com.example.firedron.databinding.FragmentHomeBinding

class DroneFragment : Fragment(){

    private var mBinding : FragmentDroneBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDroneBinding.inflate(inflater,container,false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}