package br.com.fiap.findyourmentor.database.repository

import android.content.Context
import br.com.fiap.findyourmentor.database.dao.UserDb
import br.com.fiap.findyourmentor.model.User

class UserRepository(context: Context) {

    var db = UserDb.getDatabase(context).userDao()

    fun save(user: User): Long {
        return db.save(user = user)
    }
    fun update(user: User): Int {
        return db.update(user = user)
    }
    fun delete(user: User): Int {
        return db.delete(user = user)
    }
    fun findUserById(id: Long): User {
        return db.findUserById(id = id)
    }
    fun findUsers(): List<User> {
        return db.findUsers()
    }
}