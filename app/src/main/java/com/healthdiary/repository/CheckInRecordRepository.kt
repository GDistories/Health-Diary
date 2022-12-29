package com.healthdiary.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.healthdiary.data.CheckInRecord

class CheckInRecordRepository {
    private val db = Firebase.firestore

    fun checkRecord(email: String, date:String): MutableLiveData<String> {
        var existId: MutableLiveData<String> = MutableLiveData()
        db.collection("CheckInRecord")
            .whereEqualTo("email", email)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                LogUtils.e(documents.isEmpty)
                if(documents.isEmpty){
                    existId.value = "NotCheckInYet"
                }
                for (document in documents) {
                    LogUtils.e(document==null)
                    Log.e(TAG, "Record - rep - ${document.id} => ${document.data}")
                    if (existId != null) {
                        existId.value = document.id
                    }
                    Log.e(TAG,"Record - rep - exist: $existId")
                }
//                if(documents != null){
//                    for (document in documents) {
//                        Log.e(TAG, "Record - rep - ${document.id} => ${document.data}")
//                        if (existId != null) {
//                            existId.value = document.id
//                        }
//                        Log.e(TAG,"Record - rep - exist: $existId")
//                    }
//                }
//                else{
//                    Log.e(TAG,"Record - rep - not exist: none")
//                    existId?.value = "NotCheckInYet"
//                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Record - rep - Error getting documents: ", exception)
            }


//                //todo: 判断get()是否为空

        return existId!!
    }

    fun addRecord(Record: CheckInRecord) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        println(Record.email)
        db.collection("CheckInRecord").add(Record)
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun updateRecord(Record: CheckInRecord, Id: String) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        db.collection("CheckInRecord").document(Id).set(Record)
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
