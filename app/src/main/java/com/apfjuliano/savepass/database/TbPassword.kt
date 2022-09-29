package com.apfjuliano.savepass.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbpassword")
data class TbPassword(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "password") val password: String
){
    constructor(name: String, userName: String, password: String): this(0, name, userName, password)
}
