package com.example.krealogi.network.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login")
    val name: String

) : Parcelable {

    @Dao
    interface DataAccessObject {
        @Insert
        suspend fun insert(item: UserTable)

        @Update
        suspend fun update(item: UserTable)

        @Delete
        suspend fun delete(item: UserTable)

        @Transaction
        @Query("SELECT * FROM UserTable ORDER BY id DESC")
        suspend fun getList(): List<UserTable>

        @Query("DELETE FROM UserTable WHERE id = :id")
        suspend fun deleteById(id: Int)

        @Query("SELECT COUNT(id) FROM UserTable")
        suspend fun getRowCount(): Int

    }
}