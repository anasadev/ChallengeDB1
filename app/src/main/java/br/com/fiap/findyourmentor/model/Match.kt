package br.com.fiap.findyourmentor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_match")
data class Match(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "active_user_id") var activeUserId: Long = 0,
    @ColumnInfo(name = "liked_user_id") var likedUserId: Long = 0,
    @ColumnInfo(name = "is_liked") var isLiked: Boolean = false,
)
