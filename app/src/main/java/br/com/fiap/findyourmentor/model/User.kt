package br.com.fiap.findyourmentor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var location: String = "",
    @ColumnInfo(name = "profile_type") var profileType: String = "",
    var presentation: String = "",
    @ColumnInfo(name = "interests_list")
    var interestsList: String = "",
    var availability: String = "",
    var experience: String = ""
)
