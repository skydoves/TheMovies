package com.skydoves.themovies.di

import android.app.Application
import android.arch.persistence.room.Room
import android.support.annotation.NonNull
import com.skydoves.themovies.room.AppDatabase
import com.skydoves.themovies.room.MovieDao
import com.skydoves.themovies.room.PeopleDao
import com.skydoves.themovies.room.TvDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Module
class PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "TheMovies.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(@NonNull database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideTvDao(@NonNull database: AppDatabase): TvDao {
        return database.tvDao()
    }

    @Provides
    @Singleton
    fun providePeopleDao(@NonNull database: AppDatabase): PeopleDao {
        return database.peopleDao()
    }
}
