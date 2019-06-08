/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.themovies.view.ui.details.person

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.skydoves.themovies.R
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.extension.checkIsMaterialVersion
import com.skydoves.themovies.extension.observeLiveData
import com.skydoves.themovies.extension.visible
import com.skydoves.themovies.extension.vm
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.Status
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.network.PersonDetail
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class PersonDetailActivity : AppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel by lazy { vm(viewModelFactory, PersonDetailViewModel::class) }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_person_detail)
    supportPostponeEnterTransition()

    initializeUI()
  }

  private fun initializeUI() {
    toolbar_home.setOnClickListener { onBackPressed() }
    toolbar_title.text = getPersonFromIntent().name
    getPersonFromIntent().profile_path?.let {
      Glide.with(this).load(Api.getPosterPath(it))
        .apply(RequestOptions().circleCrop())
        .listener(object : RequestListener<Drawable> {
          override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            supportStartPostponedEnterTransition()
            observeViewModel()
            return false
          }

          override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            supportStartPostponedEnterTransition()
            observeViewModel()
            return false
          }
        })
        .into(person_detail_profile)
    }
    person_detail_name.text = getPersonFromIntent().name
  }

  private fun observeViewModel() {
    observeLiveData(viewModel.getPersonObservable()) { updatePersonDetail(it) }
    viewModel.postPersonId(getPersonFromIntent().id)
  }

  private fun updatePersonDetail(resource: Resource<PersonDetail>) {
    when (resource.status) {
      Status.LOADING -> Unit
      Status.SUCCESS -> {
        resource.data?.let {
          person_detail_biography.text = it.biography
          detail_person_tags.tags = it.also_known_as
          if (it.also_known_as.isNotEmpty()) {
            detail_person_tags.visible()
          }
        }
      }
      Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
    }
  }

  private fun getPersonFromIntent(): Person {
    return intent.getParcelableExtra(personId) as Person
  }

  companion object {
    const val intent_requestCode = 1000
    const val personId = "person"

    fun startActivity(fragment: Fragment, activity: FragmentActivity?, person: Person, view: View) {
      if (activity != null) {
        if (checkIsMaterialVersion()) {
          val intent = Intent(activity, PersonDetailActivity::class.java)
          ViewCompat.getTransitionName(view)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
            intent.putExtra(personId, person)
            activity.startActivityFromFragment(fragment, intent, intent_requestCode, options.toBundle())
          }
        } else {
          activity.startActivityForResult<PersonDetailActivity>(intent_requestCode, personId to person)
        }
      }
    }
  }
}
