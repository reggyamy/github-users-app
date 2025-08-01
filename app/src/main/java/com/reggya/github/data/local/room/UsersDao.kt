package com.reggya.github.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.reggya.github.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
	
	@Insert
	suspend fun insertUser(userEntity: UserEntity)
	
	@Query("SELECT * FROM  users")
	fun getUser(): Flow<List<UserEntity>>

	@Query("DELETE FROM users WHERE id= :id")
	suspend fun deleteUser(id: Int): Int
	
	@Query("SELECT * FROM users WHERE id = :id")
	fun getUserByIdFlow(id: Int): Flow<UserEntity?>
	
}