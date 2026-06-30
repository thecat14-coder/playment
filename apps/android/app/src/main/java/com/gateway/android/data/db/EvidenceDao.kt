package com.gateway.android.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenceDao {
    @Query("SELECT * FROM pending_evidence ORDER BY createdAt DESC")
    fun getAll(): Flow<List<PendingEvidence>>

    @Query("SELECT * FROM pending_evidence WHERE retryCount < 3 ORDER BY createdAt ASC")
    suspend fun getPendingRetries(): List<PendingEvidence>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(evidence: PendingEvidence)

    @Query("UPDATE pending_evidence SET retryCount = retryCount + 1 WHERE id = :id")
    suspend fun incrementRetry(id: String)

    @Delete
    suspend fun delete(evidence: PendingEvidence)

    @Query("DELETE FROM pending_evidence WHERE id = :id")
    suspend fun deleteById(id: String)
}
