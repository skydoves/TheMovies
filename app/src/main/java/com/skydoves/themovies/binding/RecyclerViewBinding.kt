package com.skydoves.themovies.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.themovies.extension.bindResource
import com.skydoves.themovies.models.Resource
import com.skydoves.themovies.models.entity.Movie
import com.skydoves.themovies.models.entity.Person
import com.skydoves.themovies.models.entity.Tv
import com.skydoves.themovies.view.adapter.MovieListAdapter
import com.skydoves.themovies.view.adapter.PeopleAdapter
import com.skydoves.themovies.view.adapter.TvListAdapter

@BindingAdapter("adapterMovieList")
fun bindAdapterMovieList(view: RecyclerView, resource: Resource<List<Movie>>?) {
  view.bindResource(resource) {
    if (resource != null) {
      val adapter = view.adapter as? MovieListAdapter
      adapter?.addMovieList(resource)
    }
  }
}

@BindingAdapter("adapterPersonList")
fun bindAdapterPersonList(view: RecyclerView, resource: Resource<List<Person>>?) {
  view.bindResource(resource) {
    if (resource != null) {
      val adapter = view.adapter as? PeopleAdapter
      adapter?.addPeople(resource)
    }
  }
}

@BindingAdapter("adapterTvList")
fun bindAdapterTvList(view: RecyclerView, resource: Resource<List<Tv>>?) {
  view.bindResource(resource) {
    if (resource != null) {
      val adapter = view.adapter as? TvListAdapter
      adapter?.addTvList(resource)
    }
  }
}
