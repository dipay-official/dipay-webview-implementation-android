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
import com.google.android.material.textfield.TextInputEditText

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

    private val phoneNumber: TextInputEditText by lazy {
        findViewById(R.id.phoneNumber)
    }

    private val btnLocation: Button by lazy {
        findViewById(R.id.btnLocation)
    }

    private val btnEvyHome: Button by lazy {
        findViewById(R.id.btnEvyHome)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLocation.visibility = View.GONE
        btnAction.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(
                WebViewActivity.END_POINT_DATA,
                "activation?clientId=UNA_INDONESIA&phoneNumber=${
                    phoneNumber.text.toString().trim()
                }"
            )
            activationLauncher.launch(intent)

        }

        btnPayment.setOnClickListener {

            if (secretKey.isEmpty()) {
                Toast.makeText(this, "Harap aktivasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val intent = Intent(this, WebViewActivity::class.java)
            var endPoint =
                "payment?clientId=UNA_INDONESIA&secretKey={SECRET_KEY}&productCode=PROD1&transactionNumber=000001&amount=10000"
            endPoint = endPoint.replace("{SECRET_KEY}", secretKey)
            intent.putExtra(WebViewActivity.END_POINT_DATA, endPoint)
            startActivity(intent)
        }

        btnLocation.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }

        btnEvyHome.setOnClickListener {
            if (secretKey.isEmpty()) {
                Toast.makeText(this, "Harap aktivasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val intent = Intent(this, WebViewActivity::class.java)
            var endPoint =
                "?clientId=UNA_INDONESIA&secretKey={SECRET_KEY}"
            endPoint = endPoint.replace("{SECRET_KEY}", secretKey)
            intent.putExtra(WebViewActivity.END_POINT_DATA, endPoint)
            startActivity(intent)
        }
    }
}