package br.com.fiap.findyourmentor.data

import br.com.fiap.findyourmentor.data.model.UsersResponse
import retrofit2.http.GET

interface APIService {

    @GET("/users")
    fun getUsers(): List<UsersResponse>
}