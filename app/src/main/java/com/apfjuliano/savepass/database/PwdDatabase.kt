package com.apfjuliano.savepass.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = arrayOf(Password::class, Client::class, Book:class), version = 1)
//@Database(entities = [Password::class, Client::class, Book:class], version = 1)
@Database(entities = [TbPassword::class], version = 1)
abstract class PwdDatabase: RoomDatabase() {

    abstract fun passwordDao(): PasswordDao

    /*
    Another DAOs come here too, One DAO to each Entity
    Ex:
    abstract fun bookDao(): BookDao
    abstract fun clientDao(): ClientDao
     */
}

object PwdDb {
    fun getInstance(context: Context): PwdDatabase =
        Room.databaseBuilder(
            context,
            PwdDatabase::class.java,
            "database"
        ).allowMainThreadQueries().build()
}