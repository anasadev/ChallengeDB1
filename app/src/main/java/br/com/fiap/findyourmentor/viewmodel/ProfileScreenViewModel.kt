package br.com.fiap.findyourmentor.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import br.com.fiap.findyourmentor.model.Match
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileScreenViewModel: ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun findUser(userId: Long) {

        val call = RetrofitFactory().getUserService().getUserById(userId)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                _user.value = response.body()!!
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.i("FIAP", "onResponde: ${t.message}")
            }
        })
    }

    fun unlikeUser(userId: String, userConnectedId: String, navController: NavController){
        val match = Match(
            id = 0,
            userId = userConnectedId.toLong(),
            likedUserId = userId.toLong(),
            isLiked = false
        )
        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {
                val callMatch = RetrofitFactory().getMatchService().pushMatch(match)

                callMatch.enqueue(object : Callback<Match> {
                    override fun onResponse(
                        call: Call<Match>,
                        response: Response<Match>
                    ) {
                        navController.navigate("home/${userConnectedId}")
                    }

                    override fun onFailure(call: Call<Match>, t: Throwable) {
                        Log.i("FIAP", t.stackTrace.toString())
                    }
                })
            }
        }
    }

    fun likeUser(userId: String, userConnectedId: String){
        val match = Match(
            id = 0,
            userId = userConnectedId.toLong(),
            likedUserId = userId.toLong(),
            isLiked = true
        )
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val callMatch = RetrofitFactory().getMatchService().pushMatch(match)

                callMatch.enqueue(object : Callback<Match> {
                    override fun onResponse(
                        call: Call<Match>,
                        response: Response<Match>
                    ) {
                    }

                    override fun onFailure(call: Call<Match>, t: Throwable) {
                        Log.i("FIAP", t.stackTrace.toString())
                    }

                })
            }
        }
    }

}