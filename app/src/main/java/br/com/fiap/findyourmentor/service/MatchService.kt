package br.com.fiap.findyourmentor.service

import br.com.fiap.findyourmentor.model.Match
import br.com.fiap.findyourmentor.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MatchService {

    @GET("/matches")
    fun getUsersList(): Call<List<User>>

    @GET("/users/{id}/matches")
    fun getMatchesByConnectedUserId(@Path("id") id: Long): Call<List<Match>>

    @POST("/matches")
    fun pushMatch(@Body match: Match): Call<Match>

    @PUT("/matches/{id}")
    fun updateUser(@Path("id") id: Long, @Body match: Match): Call<Match>

}