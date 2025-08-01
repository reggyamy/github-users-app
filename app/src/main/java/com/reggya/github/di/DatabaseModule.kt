package com.reggya.github.di

import android.content.Context
import androidx.room.Room
import com.reggya.github.data.local.room.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UsersDatabase {
         return Room.databaseBuilder(
             context,
             UsersDatabase::class.java,
             "users_database"
         ).build()
    }
}