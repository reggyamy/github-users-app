package com.reggya.github.data.local

import com.reggya.github.data.local.entities.UserEntity
import com.reggya.github.data.local.room.UsersDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersLocalDataSource @Inject constructor(private val usersDatabase: UsersDatabase) {
    private val usersDao = usersDatabase.usersDao()
    fun getUsers(): Flow<List<UserEntity>> = usersDao.getUser()
    fun getUserById(id: Int): Flow<UserEntity?> = usersDao.getUserByIdFlow(id)
    suspend fun insertUser(userEntity: UserEntity) = usersDao.insertUser(userEntity)
    suspend fun deleteUser(userEntity: UserEntity) = usersDao.deleteUser(userEntity.id)
}