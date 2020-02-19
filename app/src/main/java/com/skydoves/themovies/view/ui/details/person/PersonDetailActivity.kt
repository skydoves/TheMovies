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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.skydoves.themovies.R
import com.skydoves.themovies.compose.ViewModelActivity
import com.skydoves.themovies.databinding.ActivityPersonDetailBinding
import com.skydoves.themovies.extension.checkIsMaterialVersion
import com.skydoves.themovies.models.entity.Person
import kotlinx.android.synthetic.main.toolbar_default.*

class PersonDetailActivity : ViewModelActivity() {

  private val viewModel: PersonDetailViewModel by injectViewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding<ActivityPersonDetailBinding>(R.layout.activity_person_detail).run {
      lifecycleOwner = this@PersonDetailActivity
      viewModel = this@PersonDetailActivity.viewModel.apply { postPersonId(getPersonFromIntent().id) }
      person = getPersonFromIntent()
    }
    initializeUI()
  }

  private fun initializeUI() {
    toolbar_home.setOnClickListener { onBackPressed() }
    toolbar_title.text = getPersonFromIntent().name
  }

  private fun getPersonFromIntent() = intent.getParcelableExtra(personId) as Person

  companion object {
    const val personId = "person"
    private const val intent_requestCode = 1000

    fun startActivity(context: Context?, person: Person, view: View) {
      if (context is Activity) {
        val intent = Intent(context, PersonDetailActivity::class.java)
        intent.putExtra(personId, person)
        if (checkIsMaterialVersion()) {
          ViewCompat.getTransitionName(view)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, it)
            context.startActivityForResult(intent, intent_requestCode, options.toBundle())
          }
        } else {
          context.startActivity(intent)
        }
      }
    }
  }
}
