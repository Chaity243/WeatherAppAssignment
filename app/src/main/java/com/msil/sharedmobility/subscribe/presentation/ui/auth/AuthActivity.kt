package com.msil.sharedmobility.subscribe.presentation.ui.auth

import android.app.Activity
import android.os.Bundle
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*

import android.view.MenuItem
import androidx.core.app.ActivityCompat


class AuthActivity : BaseActivity() {

    val REQUEST_CODE_READ_SMS =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)



        initToolbar()
        getPermission()
    }



    private fun initToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        println("back pressed")
    }


    private fun getPermission() {
        // Message read permission
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS), REQUEST_CODE_READ_SMS)

    }
}
