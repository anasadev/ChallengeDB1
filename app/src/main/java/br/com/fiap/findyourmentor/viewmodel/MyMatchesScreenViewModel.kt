package br.com.fiap.findyourmentor.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.findyourmentor.model.Match
import br.com.fiap.findyourmentor.model.User
import br.com.fiap.findyourmentor.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMatchesScreenViewModel: ViewModel() {

    private val _usersList = MutableLiveData<List<User>>()
    val usersList: LiveData<List<User>> = _usersList
    private val _usersListFiltered = MutableLiveData<List<User>>()
    val usersListFiltered: LiveData<List<User>> = _usersListFiltered
    private val _matchesList = MutableLiveData<List<Match>>()
    val matchesList: LiveData<List<Match>> = _matchesList


    fun findMatchesByUser(userConnectedId: Long): List<Match> {
        var matchesList: List<Match> = mutableListOf()
        val callMatch =
            RetrofitFactory().getMatchService().getMatchesByConnectedUserId(userConnectedId)

        callMatch.enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if(response.body() != null){
                    _matchesList.value = response.body()!!
                }
            }
            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                Log.i("FIAP", "onResponde: ${t.message}")
            }
        })
        return matchesList
    }

     fun findAllUsers(): List<User>{
        val call = RetrofitFactory().getUserService().getUsersList()
        var allUsers: List<User> = mutableListOf()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _usersList.value = response.body()!!
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.i("FIAP", "onResponde: ${t.message}")
            }
        })
        return allUsers
    }

    fun usersListFilter(listUsers: List<User>?, listMatches: List<Match>?) {
        val likedUsersId: MutableList<Long> = mutableListOf()

        listMatches?.forEach{
            if(it.isLiked){
                likedUsersId.add(it.likedUserId)
            }
        }
        _usersListFiltered.value = listUsers?.filter { it.id in likedUsersId }
    }
}
