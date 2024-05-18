package br.com.fiap.findyourmentor.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.findyourmentor.model.Match
import br.com.fiap.findyourmentor.model.User

@Database(
    entities = [User::class, Match::class],
    version = 1
)
abstract class FindYourMentorDb: RoomDatabase() {
    abstract fun matchDao(): MatchDao

    companion object {
        private lateinit var instance: FindYourMentorDb

        fun getDatabase(context: Context): FindYourMentorDb {
            if(!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context,
                    FindYourMentorDb::class.java,
                    "find_your_mentor_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}