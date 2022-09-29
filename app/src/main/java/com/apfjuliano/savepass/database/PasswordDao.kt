package com.apfjuliano.savepass.database

import androidx.room.*

@Dao
interface PasswordDao {

    @Query("SELECT * FROM tbpassword")
    fun getPasswords(): List<TbPassword>

    @Query("SELECT * FROM tbpassword WHERE id = :id")
    fun getPassword(id: Int): List<TbPassword>

    @Insert
    fun insertPassword(tbpassword: TbPassword)

    @Insert
    fun insertPasswords(vararg tbpassword: TbPassword)

    @Delete
    fun deletePassword(tbpassword: TbPassword)

    @Update
    fun updatePassword(tbpassword: TbPassword)
}