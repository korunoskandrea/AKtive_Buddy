package com.example.aktivebuddy.database.model

import android.util.Log
import io.github.serpro69.kfaker.Faker
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.osmdroid.util.GeoPoint
import java.util.UUID
import java.io.File

data class Fitness(
    val name: String,
    val location: Location
){

    val imageUrl: String = "https://cdn-icons-png.flaticon.com/256/2749/2749777.png"
    companion object{
//        fun generateFakeFitnessData(): Fitness {
//            val faker = Faker()
//
//            val name: String = faker.crossfit.movements()
//            val point = GeoPoint(41.0314, 21.3345)
//            val location: Location = Location("Slovenia","Maribor", "Koroska cesta 45", point)
//
//            val fitness: Fitness = Fitness(name, location)
//
//            return fitness
//        }

//        private const val FILE_NAME = "fitnessData.json"
//        fun saveDataToFile(path: String, data: List<Fitness>){
//            val jsonString = Json.encodeToString(data)
//            File(path, FILE_NAME).writeText(jsonString)
//        }
//        fun readDataFromFile(path: String): List<Fitness> {
//            try {
//                val file = File(path, FILE_NAME)
//                val isNewFile = file.createNewFile()
//                val jsonString = file.readText()
//                if(isNewFile || jsonString.isEmpty()) return emptyList()
//                return Json.decodeFromString(jsonString)
//            } catch (e: Exception){
//                Log.d("ERROR ", e.toString())
//                return emptyList()
//            }
//        }
//        fun addNewData(path:String, newData: Fitness){
//            val currDataList = readDataFromFile(path).toMutableList()
//            currDataList.add(newData)
//            saveDataToFile(path,currDataList)
//        }
//        fun deleteData(path: String, dataId:UUID){
//            val currDataList = readDataFromFile(path).toMutableList()
//            val dataToRemove = currDataList.find { it.id == dataId }
//            dataToRemove?.let {
//                currDataList.remove(it)
//                saveDataToFile(path, currDataList)
//            }
//        }
//        fun updateData(path: String, updatedData: Fitness){
//            val currDataList = readDataFromFile(path).toMutableList()
//            val existingDataIndex = currDataList.indexOfFirst{
//                it.id == updatedData.id
//            }
//            if(existingDataIndex != -1){
//                currDataList[existingDataIndex] = updatedData
//                saveDataToFile(path, currDataList)
//            }
//        }
    }
}
