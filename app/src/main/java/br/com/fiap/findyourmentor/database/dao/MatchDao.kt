package br.com.fiap.findyourmentor.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.findyourmentor.model.Match


@Dao
interface MatchDao {
    @Insert
    fun save(match: Match): Long
    @Update
    fun update(match: Match): Int
    @Delete
    fun delete(match: Match): Int
//    @Query("SELECT * FROM tb_match WHERE active_user_id = :id")
//    fun findByActiveUserId(id: Long): List<Match>
//    @Query("SELECT liked_user_id FROM tb_match WHERE active_user_id = :id")
//    fun findLikes(id: Long): List<Match>
}