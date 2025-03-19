package com.example.lawassist.repository

import com.example.lawassist.database.LawDao
import com.example.lawassist.database.LawEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class LawRepository(private val lawDao: LawDao) {

    // Inserts a new law into the database
    suspend fun insertLaw(law: LawEntity) {
        withContext(Dispatchers.IO) {
            try {
                lawDao.insertLaw(law)
            } catch (e: Exception) {
                // Handle the error, possibly log it
                e.printStackTrace()
                // You can also throw an exception if needed
                // throw Exception("Failed to insert law")
            }
        }
    }

    // Search for laws based on a query string
    suspend fun searchLaws(query: String): List<LawEntity> {
        return withContext(Dispatchers.IO) {
            try {
                lawDao.searchLaws("%$query%") // '%' for partial matching
            } catch (e: Exception) {
                // Handle error and log it
                e.printStackTrace()
                emptyList() // Returning an empty list on error
            }
        }
    }

    // Get all laws from the database
    suspend fun getAllLaws(): List<LawEntity> {
        return withContext(Dispatchers.IO) {
            try {
                lawDao.getAllLaws()
            } catch (e: Exception) {
                // Handle error and log it
                e.printStackTrace()
                emptyList() // Returning an empty list on error
            }
        }
    }

    // Deletes a law from the database
    suspend fun deleteLaw(law: LawEntity) {
        withContext(Dispatchers.IO) {
            try {
                lawDao.deleteLaw(law)
            } catch (e: Exception) {
                // Handle error and log it
                e.printStackTrace()
            }
        }
    }
}
