package br.com.fiap.findyourmentor.database.repository

import android.content.Context
import br.com.fiap.findyourmentor.database.dao.FindYourMentorDb
import br.com.fiap.findyourmentor.model.Match

class MatchRepository(context: Context) {

    var db = FindYourMentorDb.getDatabase(context).matchDao()

    fun save(match: Match): Long {
        return db.save(match = match)
    }
    fun update(match: Match): Int {
        return db.update(match = match)
    }
    fun delete(match: Match): Int {
        return db.delete(match = match)
    }
//    fun findByActiveUserId(id: Long): List<Match> {
//        return db.findByActiveUserId(id = id)
//    }
//    fun findLikes(id: Long): List<Match> {
//        return db.findLikes(id = id)
//    }
}