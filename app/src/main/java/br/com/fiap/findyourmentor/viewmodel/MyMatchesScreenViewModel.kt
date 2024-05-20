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

    private val _matchesList = MutableLiveData<List<Match>>()
    val matchesList: LiveData<List<Match>> = _matchesList
    private val _usersList = MutableLiveData<List<User>>()
    val usersList: LiveData<List<User>> = _usersList
    private val _usersListFiltered = MutableLiveData<List<User>>()
    val usersListFiltered: LiveData<List<User>> = _usersListFiltered

    fun findMatchesByUser(userConnectedId: Long){
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
    }

    private fun findAllUsers(){
        var call = RetrofitFactory().getUserService().getUsersList()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _usersList.value = response.body()!!
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.i("FIAP", "onResponde: ${t.message}")
            }
        })
    }

    fun usersListFiltered(matchesList: List<Match>?) {
        findAllUsers()
        val likedUsersId: MutableList<Long> = mutableListOf()
        matchesList?.forEach{
            if(it.isLiked){
                likedUsersId.add(it.likedUserId)
            }
        }
        _usersListFiltered.value = _usersList.value?.filter { it.id in likedUsersId }
    }
}