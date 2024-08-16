package com.example.castalknote.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity(), NavigationHost {

    lateinit var binding: VB
    abstract val containerID: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory.invoke(layoutInflater)
        setContentView(binding.root)
    }

    override fun addFragment(fragment: Fragment?) {
        fragment?.let {
            supportFragmentManager.beginTransaction().apply {
                add(containerID, it)
                addToBackStack(fragment::class.java.name)
                commit()
            }
        }
    }
}

interface NavigationHost {
    fun addFragment(fragment: Fragment?)
}
