package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var secretKey = ""

    private val activationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val data = it.data?.getStringExtra("DATA")
            data?.let {
                secretKey = it
            }
        }
    }

    private val btnAction: Button by lazy {
        findViewById(R.id.btnActivation)
    }

    private val btnPayment: Button by lazy {
        findViewById(R.id.btnPayment)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAction.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.END_POINT_DATA, "activation?clientId=UNA_INDONESIA&phoneNumber=857112225588")
            activationLauncher.launch(intent)

        }

        btnPayment.setOnClickListener {

            if(secretKey.isEmpty()){
                Toast.makeText(this, "Harap aktivasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val intent = Intent(this, WebViewActivity::class.java)
            var endPoint = "payment?clientId=UNA_INDONESIA&secretKey={SECRET_KEY}&productCode=PROD1&transactionNumber=000001&amount=10000"
            endPoint = endPoint.replace("{SECRET_KEY}", secretKey)
            intent.putExtra(WebViewActivity.END_POINT_DATA, endPoint)
            startActivity(intent)
        }

    }
}