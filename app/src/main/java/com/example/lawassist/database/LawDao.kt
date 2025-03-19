package com.example.lawassist.database

import androidx.room.*

@Dao
interface LawDao {

    // ✅ Insert Law (returns Long)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaw(law: LawEntity): Long

    // ✅ Corrected Search Query with Wildcards
    @Query("SELECT * FROM laws WHERE title LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%'")
    suspend fun searchLaws(query: String): List<LawEntity>

    // ✅ Fetch All Laws (Returning List)
    @Query("SELECT * FROM laws")
    suspend fun getAllLaws(): List<LawEntity>

    // ✅ Delete Law (Returns number of rows affected)
    @Delete
    suspend fun deleteLaw(law: LawEntity): Int
}
