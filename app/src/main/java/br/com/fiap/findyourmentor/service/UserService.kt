package br.com.fiap.findyourmentor.service

import br.com.fiap.findyourmentor.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/users")
    fun getUsersList(): Call<List<User>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>

}