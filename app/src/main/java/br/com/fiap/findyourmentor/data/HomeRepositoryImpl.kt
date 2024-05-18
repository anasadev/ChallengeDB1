package br.com.fiap.findyourmentor.data

import br.com.fiap.findyourmentor.data.model.toModel
import br.com.fiap.findyourmentor.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface HomeRepository {
    suspend fun getUsersList(): Flow<List<User>>
}

class HomeRepositoryImpl @Inject constructor(
    private val apiService: APIService
): HomeRepository {

    override suspend fun getUsersList() = flow {
        emit(apiService.getUsers().map { it.toModel() })
    }

}