package com.example.android_three_line_diary.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDiary(diary: Diary)

    @Query("SELECT * FROM diary_table WHERE deleted = :isDeleted ORDER BY date ASC")
    fun getAllData(isDeleted: Boolean = false): LiveData<List<Diary>>

    @Query("SELECT * FROM diary_table WHERE deleted = :isDeleted ORDER BY date ASC")
    fun getAllDeleteData(isDeleted: Boolean = true): LiveData<List<Diary>>

    @Update
    suspend fun updateData(diary: Diary)

    @Delete
    suspend fun deleteData(diary: Diary)

}