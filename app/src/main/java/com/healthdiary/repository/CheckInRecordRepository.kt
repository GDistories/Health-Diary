package com.healthdiary.repository

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.healthdiary.data.CheckInRecord

class CheckInRecordRepository {
    private val db = Firebase.firestore

    fun addRecord(Record: CheckInRecord) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        println(Record.email)
        db.collection("CheckInRecord").document(Record.email.toString()).set(Record)
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun updateRecord(Record: CheckInRecord) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        db.collection("CheckInRecord").document(Record.email.toString()).set(Record)
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun deleteRecord(email: String) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        db.collection("CheckInRecord").document(email).delete()
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getRecord(email: String) : MutableLiveData<CheckInRecord> {
        val RecordLiveData: MutableLiveData<CheckInRecord> = MutableLiveData()
        db.collection("CheckInRecord").document(email).get()
            .addOnSuccessListener { result ->
                val Record = CheckInRecord(
//                    result.data?.get("name").toString(),
//                    result.id,
//                    result.data?.get("phone").toString(),
//                    result.data?.get("birthday").toString(),
//                    result.data?.get("gender").toString()
                )
                RecordLiveData.value = Record
                LogUtils.e(Record.email)
            }
            .addOnFailureListener {
                RecordLiveData.value = null
            }
        return RecordLiveData
    }

    companion object {
        val repository = CheckInRecordRepository()
    }
}
