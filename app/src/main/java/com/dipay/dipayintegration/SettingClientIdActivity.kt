package com.dipay.dipayintegration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.dipayintegration.R
import com.dipay.dipayintegration.data.LocalStorage
import com.example.dipayintegration.databinding.ActivitySettingClientIdBinding

class SettingClientIdActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingClientIdBinding
    lateinit var viewType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_client_id)
        viewType = intent.getStringExtra("ViewType") ?: "CLIENT_ID"
        setUp()
    }

    private fun setUp() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            when(viewType){
                "CLIENT_ID" -> {
                    etLayoutClientId.hint = "Client Id"
                    btnSave.text = "Simpan Client Id"
                    binding.etClientId.setText(LocalStorage.getClientId() ?: "")
                }
                "URL" -> {
                    etLayoutClientId.hint = "Base URL"
                    btnSave.text = "Simpan Base URL"
                    binding.etClientId.setText(LocalStorage.baseUrl ?: "")

                }
            }
            btnSave.setOnClickListener {
                when(viewType){
                    "CLIENT_ID" -> {
                        val clientId = etClientId.text.toString()
                        if(clientId.isEmpty()){
                            Toast.makeText(this@SettingClientIdActivity, "Client ID tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        LocalStorage.setClientId(clientId)
                        Toast.makeText(this@SettingClientIdActivity, "Client ID [${clientId}] berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    "URL" -> {
                        val baseUrl = etClientId.text.toString()
                        if(baseUrl.isEmpty()){
                            Toast.makeText(this@SettingClientIdActivity, "Base URL tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        LocalStorage.baseUrl = baseUrl
                        Toast.makeText(this@SettingClientIdActivity, "Base URL [${baseUrl}] berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}