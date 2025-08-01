package com.reggya.github.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reggya.github.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
	
	abstract fun usersDao(): UsersDao
	
}