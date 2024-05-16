package br.com.fiap.findyourmentor.service

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import br.com.fiap.findyourmentor.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @GET("/users")
    fun getUsersList(): Call<List<User>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>

    @POST("/users")
    fun pushUser(@Body user: User ): Call<User>

    @PUT("/users/{id}")
    fun updateUser(@Path("id") id: Long, @Body user: User ): Call<User>


}