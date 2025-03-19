package com.example.lawassist.database

class MockLawDao : LawDao {
    override suspend fun insertLaw(law: LawEntity): Long {
        return 1L  // Simulate successful insertion with row ID 1
    }

    override suspend fun searchLaws(query: String): List<LawEntity> {
        return listOf(
            LawEntity(id = 1, title = "Mock Law 1", description = "Mock Description 1", category = "Mock Category 1"),
            LawEntity(id = 2, title = "Mock Law 2", description = "Mock Description 2", category = "Mock Category 2")
        )
    }

    override suspend fun getAllLaws(): List<LawEntity> {
        return listOf(
            LawEntity(id = 1, title = "Mock Law 1", description = "Mock Description 1", category = "Mock Category 1"),
            LawEntity(id = 2, title = "Mock Law 2", description = "Mock Description 2", category = "Mock Category 2")
        )
    }

    override suspend fun deleteLaw(law: LawEntity): Int {
        return 1  // Simulate successful deletion of 1 row
    }
}
