package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentDBinding


class FragmentD : Fragment() {

    private var binding : FragmentDBinding? = null
    private val bind get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDBinding.inflate(inflater, container, false)

        bind.l1.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentD_to_fragment2)
        }



        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}