package com.sepia.pets.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sepia.pets.R
import com.sepia.pets.base.BaseFragment
import com.sepia.pets.databinding.FragmentPetDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PetDetailsFragment : BaseFragment<FragmentPetDetailsBinding>(), View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_pet_details

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetDetailsBinding
        get() = FragmentPetDetailsBinding::inflate

    private var title = ""
    private var url = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        if (arguments != null) {
            title = arguments?.getString("title").toString()
            url = arguments?.getString("content").toString()
        }
        with(binding) {
            binding.toolbar.tvTitle.text = title
            binding.toolbar.ivLeft.visibility = View.VISIBLE
            wbView.webViewClient = WebViewClient()

            wbView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progress.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progress.visibility = View.GONE
                }

            }
            wbView.loadUrl(url)
        }

    }

    private fun setListener() {
        binding.toolbar.ivLeft.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivLeft -> {
                onBackPressed()
            }
        }
    }

}