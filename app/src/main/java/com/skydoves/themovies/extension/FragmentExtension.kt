package com.skydoves.themovies.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

fun <T : ViewModel> Fragment.vm(factory: ViewModelProvider.Factory, model: KClass<T>): T {
  return ViewModelProviders.of(this, factory).get(model.java)
}

fun <T : ViewModel> FragmentActivity.vm(factory: ViewModelProvider.Factory, model: KClass<T>): T {
  return ViewModelProviders.of(this, factory).get(model.java)
}
