package br.com.fiap.findyourmentor.data.model

import br.com.fiap.findyourmentor.model.User


data class UsersResponse(
    val id: String,
    val name: String,
    val location: String,
    var profileType: String,
    val presentation: String,
    val interestsList: String,
)

fun UsersResponse.toModel() = User(
    id = this.id.toLong(),
    name = this.name,
    location = this.location,
    profileType= this.profileType,
    presentation = this.presentation,
    interestsList = this.interestsList
)