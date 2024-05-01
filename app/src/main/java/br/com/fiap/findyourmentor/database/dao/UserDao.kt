package br.com.fiap.findyourmentor.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.findyourmentor.model.User
@Dao
interface UserDao {
    @Insert
    fun save(user: User): Long
    @Update
    fun update(user: User): Int
    @Delete
    fun delete(user: User): Int
    @Query("SELECT * FROM tb_user WHERE id = :id")
    fun findUserById(id: Long): User
    @Query("SELECT * FROM tb_user ORDER BY name ASC")
    fun findUsers(): List<User>
}