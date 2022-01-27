package com.dipay.dipayintegration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.dipayintegration.R
import com.dipay.dipayintegration.data.LocalStorage
import com.example.dipayintegration.databinding.ActivityMainBinding

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

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            btnLocation.visibility = View.GONE
            btnActivation.setOnClickListener {
                val phone = phoneNumber.text.toString().trim()

                if(phone.isEmpty()){
                    Toast.makeText(this@MainActivity, "Nomor telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra(
                    WebViewActivity.END_POINT_DATA,
                    "activation?clientId=${LocalStorage.getClientId()}&phoneNumber=${
                        phoneNumber.text.toString().trim()
                    }"
                )
                activationLauncher.launch(intent)

            }

            btnPayment.setOnClickListener {

                if (secretKey.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Harap aktivasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                var endPoint =
                    "payment?clientId={CLIENT_ID}&secretKey={SECRET_KEY}&productCode=PROD1&transactionNumber=000001&amount=10000"
                endPoint = endPoint.replace("{SECRET_KEY}", secretKey).replace("{CLIENT_ID}", LocalStorage.getClientId() as String)
                intent.putExtra(WebViewActivity.END_POINT_DATA, endPoint)
                startActivity(intent)
            }

            btnLocation.setOnClickListener {
                val intent = Intent(this@MainActivity, LocationActivity::class.java)
                startActivity(intent)
            }

            btnEvyHome.setOnClickListener {
                if (secretKey.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Harap aktivasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                var endPoint =
                    "?clientId={CLIENT_ID}&secretKey={SECRET_KEY}"
                endPoint = endPoint.replace("{SECRET_KEY}", secretKey).replace("{CLIENT_ID}", LocalStorage.getClientId() as String)
                intent.putExtra(WebViewActivity.END_POINT_DATA, endPoint)
                startActivity(intent)
            }

            btnClientId.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingClientIdActivity::class.java)
                startActivity(intent)
            }

            btnBaseUrl.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingClientIdActivity::class.java)
                    .also {
                        it.putExtra("ViewType", "URL")
                    }
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalStorage.getClientId().let {
            binding.apply {
                btnActivation.isEnabled = it != null
                btnEvyHome.isEnabled = it != null
                btnPayment.isEnabled = it != null
            }
        }

    }
}