package com.nadosunbae_android.app.presentation.ui.review

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityWebViewBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadUrlFromIntent()
        initWebView()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 웹뷰 back 가능하도록
        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.wvContent.canGoBack()) {
            binding.wvContent.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun loadUrlFromIntent() {
        this.url = intent.getStringExtra("url")
    }

    private fun initWebView() {

        with (binding.wvContent) {

            // 웹뷰 설정
            with (settings) {

                loadWithOverviewMode = true     // WebView 화면크기에 맞추도록 설정
                useWideViewPort = true  // wide viewport 설정

                setSupportZoom(true)  // 줌 설정 여부
                builtInZoomControls = false  // 줌 확대/축소 버튼 여부

                javaScriptEnabled = true // 자바스크립트 사용 여부
                javaScriptCanOpenWindowsAutomatically = true  // 자바스크립트가 window.open() 사용할 수 있도록 설정

                setSupportMultipleWindows(true)     // 멀티 윈도우 사용 여부

                domStorageEnabled = true        // 로컬 스토리지 사용여부
            }


            webChromeClient = WebChromeClient()
            webViewClient = BaseWebViewClient()

        }

        if (url != null)
            binding.wvContent.loadUrl(url!!)
    }

    class BaseWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (view != null && url != null)
                view.loadUrl(url)
            return true
        }
    }

}