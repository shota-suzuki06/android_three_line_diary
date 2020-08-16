package com.example.android_three_line_diary.data

import androidx.lifecycle.LiveData

class DiaryRepository(private val diaryDao: DiaryDao) {

    val readAllData: LiveData<List<Diary>> = diaryDao.getAllData()

    val readAllDeleteData: LiveData<List<Diary>> = diaryDao.getAllDeleteData()

    suspend fun insertDiary(diary: Diary) {
        diaryDao.insertDiary(diary)
    }

    suspend fun updateData(diary: Diary) {
        diaryDao.updateData(diary)
    }

    suspend fun deleteData(diary: Diary) {
        diaryDao.deleteData(diary)
    }

}