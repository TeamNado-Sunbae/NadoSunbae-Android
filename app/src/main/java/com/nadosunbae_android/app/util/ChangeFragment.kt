package com.nadosunbae_android.app.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun AppCompatActivity.addFragment(layoutRes: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .add(layoutRes, fragment)
        .commit()
}

fun AppCompatActivity.changeFragment(layoutRes: Int, fragment: Fragment, name: String) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(layoutRes, fragment)
        .addToBackStack(name)
        .commit()
}

fun AppCompatActivity.changeFragmentNoBackStack(layoutRes: Int, fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(layoutRes, fragment)
        .commit()
}

fun AppCompatActivity.popFragmentBackStack(name: String) {
    supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
