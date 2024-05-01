package br.com.fiap.findyourmentor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    @ColumnInfo(name = "profile_type") var profileType: String = "",
    var presentation: String = "",
    @ColumnInfo(name = "interests_list")
    var interestsList: List<String>
)
