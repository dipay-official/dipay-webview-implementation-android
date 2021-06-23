package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.Activation
import com.example.myapplication.model.Payment
import com.example.myapplication.model.ResponseCallback
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class WebViewActivity : AppCompatActivity() {
    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val endPoint: String? by lazy {
        intent.getStringExtra(END_POINT_DATA)
    }

    @SuppressLint("AddJavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        if (endPoint.isNullOrEmpty()) {
            finish()
        }


        webView.addJavascriptInterface(EvyJavascriptInterface(object : Callbacks {
            override fun onPayment(data: String) {

                val gson = GsonBuilder().create()
                val type = object : TypeToken<ResponseCallback<Payment>>() {}.type
                val response = gson.fromJson<ResponseCallback<Payment>>(data, type)
                if (response.code == "00") {
                    // If succeeded

                    Log.d("onPayment", response.data.toString())
                    // Do whatever you want to do

                    Toast.makeText(this@WebViewActivity, response.data.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    // If failed

                    Toast.makeText(this@WebViewActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onClose() {
                finish()
            }

            override fun onActivation(data: String) {
                val gson = GsonBuilder().create()
                val type = object : TypeToken<ResponseCallback<Activation>>() {}.type
                val response = gson.fromJson<ResponseCallback<Activation>>(data, type)

                if (response.code == "00") {
                    // If succeeded

                    Log.d("onActivation", response.data.secretKey ?: "")
                    // Do whatever you want to do
                    Toast.makeText(this@WebViewActivity, "Success activation with secretkey : ${response.data.secretKey}", Toast.LENGTH_SHORT).show()

                    setResult(RESULT_OK, Intent().also {
                        it.putExtra("DATA", response.data.secretKey)
                    })

                    finish()
                } else {
                    // If failed

                    Toast.makeText(this@WebViewActivity, "Failed", Toast.LENGTH_SHORT).show()
                }

            }

        }), "EvyWebView")

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                val js = resources.openRawResource(R.raw.evycallback).bufferedReader()
                    .use { it.readText() }.trimIndent()

                webView.loadUrl(js)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }
        }

        webView.settings.javaScriptEnabled = true

        webView.loadUrl("http://192.168.88.28:3000/${endPoint}")

    }

    companion object {
        const val END_POINT_DATA = "END_POINT_DATA"
    }
}