package de.innomos.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.innomos.myapplication.data.entities.BootRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface BootRecordDao {

    @Query("SELECT * FROM boot_record ORDER BY timestamp ASC")
    fun trackBootRecords(): Flow<List<BootRecord>>

    @Query("SELECT * FROM boot_record ORDER BY timestamp ASC")
    suspend fun getBootRecords(): List<BootRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bootRecord: BootRecord)
}