package com.izzed.indianflavors.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.izzed.indianflavors.databinding.FragmentDetailBinding
import com.izzed.indianflavors.model.Product
import java.text.DecimalFormat

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}