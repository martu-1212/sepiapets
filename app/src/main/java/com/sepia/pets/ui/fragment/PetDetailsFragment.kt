package com.sepia.pets.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
class PetDetailsFragment : BaseFragment<FragmentPetDetailsBinding>(){

    override val layoutId: Int
        get() = R.layout.fragment_pet_details

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetDetailsBinding
        get() = FragmentPetDetailsBinding::inflate

    private var url = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        with(binding) {
//            toolbarApp.tvTitle.text = getString(R.string.faq)
            wbView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY

            wbView.settings.builtInZoomControls = true
            wbView.settings.useWideViewPort = true
            wbView.settings.loadWithOverviewMode = true
            wbView.webViewClient = WebViewClient()
            wbView.settings.javaScriptEnabled = true
            wbView.settings.builtInZoomControls = true
            wbView.settings.displayZoomControls = false
            wbView.settings.setSupportZoom(false)

            wbView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    try {
                        val httpIntent = Intent(Intent.ACTION_VIEW)
                        httpIntent.data = Uri.parse(url.toString())
                        startActivity(httpIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return true
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

//    private fun setListener() {
//        binding.toolbarApp.ivLeft.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.ivLeft -> {
//                navController.navigateUp()
//            }
//        }
//    }

}