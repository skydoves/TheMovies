package com.skydoves.themovies.extension

import androidx.recyclerview.widget.RecyclerView
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Status
import org.jetbrains.anko.toast

fun RecyclerView.bindResource(resource: Resource<Any>?, onSuccess: () -> Unit) {
  if (resource != null) {
    when (resource.status) {
      Status.LOADING -> Unit
      Status.SUCCESS -> onSuccess()
      Status.ERROR -> this.context.toast(resource.errorEnvelope?.status_message.toString())
    }
  }
}
