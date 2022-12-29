package com.healthdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.healthdiary.data.CheckInRecord
import com.healthdiary.repository.CheckInRecordRepository

class CheckInRecordViewModel (val repository: CheckInRecordRepository): ViewModel() {
    fun addRecord(record: CheckInRecord): LiveData<Boolean> =
        repository.addRecord(record)

    fun updateRecord(record: CheckInRecord): LiveData<Boolean> =
        repository.updateRecord(record)

    fun deleteRecord(email: String): LiveData<Boolean> =
        repository.deleteRecord(email)

    fun getRecord(email: String): LiveData<CheckInRecord> =
        repository.getRecord(email)

    class Provider(private val repository: CheckInRecordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CheckInRecordViewModel::class.java)) {
                return CheckInRecordViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid viewmodel")
        }
    }
}
