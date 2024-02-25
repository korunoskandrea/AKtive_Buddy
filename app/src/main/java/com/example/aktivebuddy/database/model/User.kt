package com.example.aktivebuddy.database.model

import android.util.Log
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.time.LocalDate
import java.util.UUID

@Serializable
data class User(
    @Serializable()
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val name: String,
    val surname: String,
    val dateOfBirth: LocalDate,
    val email: String,
    val password: String,
) {
//    companion object {
//        private const val FILE_NAME = "userData.json"
//        fun saveDataToFile(path: String, data: List<Fitness>) {
//            val jsonString = Json.encodeToString(data)
//            File(path, FILE_NAME).writeText(jsonString)
//        }
//
//        fun readDataFromFile(path: String): List<Fitness> {
//            try {
//                val file = File(path, FILE_NAME)
//                val isNewFile = file.createNewFile()
//                val jsonString = file.readText()
//                if (isNewFile || jsonString.isEmpty()) return emptyList()
//                return Json.decodeFromString(jsonString)
//            } catch (e: Exception) {
//                Log.d("ERROR ", e.toString())
//                return emptyList()
//            }
//        }
//
//        fun addNewData(path: String, newData: Fitness) {
//            val currDataList = readDataFromFile(path).toMutableList()
//            currDataList.add(newData)
//            saveDataToFile(path, currDataList)
//        }
//
//        fun deleteData(path: String, dataId: UUID) {
//            val currDataList = readDataFromFile(path).toMutableList()
//            val dataToRemove = currDataList.find { it.id == dataId }
//            dataToRemove?.let {
//                currDataList.remove(it)
//                saveDataToFile(path, currDataList)
//            }
//        }
//
//        fun updateData(path: String, updatedData: Fitness) {
//            val currDataList = readDataFromFile(path).toMutableList()
//            val existingDataIndex = currDataList.indexOfFirst {
//                it.id == updatedData.id
//            }
//            if (existingDataIndex != -1) {
//                currDataList[existingDataIndex] = updatedData
//                saveDataToFile(path, currDataList)
//            }
//        }

//        fun registerUser(
//            username: String,
//            name: String,
//            surname: String,
//            dateOfBirth: LocalDate,
//            email: String,
//            password: String
//        ) {
//        }
//    }
}
