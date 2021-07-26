package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.data.LocalStorage
import com.example.myapplication.databinding.ActivitySettingClientIdBinding

class SettingClientIdActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingClientIdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_client_id)
        setUp()
    }

    private fun setUp() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            btnSave.setOnClickListener {
                val clientId = etClientId.text.toString()
                if(clientId.isEmpty()){
                    Toast.makeText(this@SettingClientIdActivity, "Client ID tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                LocalStorage.setClientId(clientId)
                Toast.makeText(this@SettingClientIdActivity, "Client ID [${clientId}] berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etClientId.setText(LocalStorage.getClientId() ?: "")
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