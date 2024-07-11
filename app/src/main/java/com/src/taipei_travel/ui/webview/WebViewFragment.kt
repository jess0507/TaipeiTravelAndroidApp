package com.src.taipei_travel.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.src.taipei_travel.databinding.FragmentWebviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {
    private var binding: FragmentWebviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argJson = arguments?.getString("argJsonString")
        val webViewState = Gson().fromJson(argJson, WebViewState::class.java)

        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = webViewState.title

        val webView = binding?.webview ?: return
        webView.settings.javaScriptEnabled = true

        // Set WebViewClient to handle page navigation within the WebView
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding?.progressBar?.visibility = View.GONE
            }
        }

        // Set WebChromeClient to handle page loading progress
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding?.progressBar?.progress = newProgress
                if (newProgress == 100) {
                    binding?.progressBar?.visibility = View.GONE
                } else {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
            }
        }

        // Load a URL
        loadUrl(webViewState.url)
    }

    private fun loadUrl(url: String) {
        binding?.webview?.loadUrl(url)
    }
}