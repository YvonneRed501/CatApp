package com.example.catapp.data

import com.example.catapp.domain.CredentialsRepository

class CredentialsRepositoryImplementation(private val credentialsDao: CredentialsDao): CredentialsRepository {


     override suspend fun checkCredentials(username: String, password: String) = credentialsDao.checkCredentials(username = username, password = password)

    override suspend fun checkUsernameExists(username: String) = credentialsDao.checkUsernameExists(username = username)

    override suspend fun insertCredentials(username: String, password: String)  = credentialsDao.insert(credentials = Credentials(username = username, password = password) )

    override suspend fun deleteCredentials(username: String, password: String) = credentialsDao.delete(credentials = Credentials(username = username, password = password) )

    override suspend fun updateCredentials(username: String, password: String) = credentialsDao.update(credentials = Credentials(username = username, password = password) )
}