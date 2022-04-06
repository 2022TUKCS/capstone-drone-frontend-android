package com.example.firedron.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firedron.databinding.FragmentLiveBinding
import com.example.firedron.databinding.FragmentMypageBinding

class MypageFragment : Fragment(){

    private var mBinding : FragmentMypageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater,container,false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}