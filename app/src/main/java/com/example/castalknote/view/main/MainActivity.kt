package com.example.castalknote.view.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.castalknote.base.BaseActivity
import com.example.castalknote.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override val containerID: Int
        get() = binding.containerMainFragment.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(MainFragment())

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStackImmediate()
                } else {
                    finish()
                }
            }
        })
    }
}
