package br.com.fiap.findyourmentor.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val URL = "https://663f411ee3a7c3218a4c94c3.mockapi.io"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUserService(): UserService {
        return retrofitFactory.create(UserService::class.java)
    }
    fun getMatchService(): MatchService {
        return retrofitFactory.create(MatchService::class.java)
    }
}