package com.example.android_three_line_diary.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Diary>>
    val readAllDeleteData: LiveData<List<Diary>>
    private val repository: DiaryRepository

    init {
        val diaryDao = DiaryDatabase.getDatabase(application).diaryDao()
        repository = DiaryRepository(diaryDao)
        readAllData = repository.readAllData
        readAllDeleteData = repository.readAllDeleteData
    }

    fun insertDiary(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDiary(diary)
        }
    }

    fun updateData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(diary)
        }
    }

    fun deleteData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(diary)
        }
    }

}

