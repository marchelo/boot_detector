package de.innomos.myapplication.data

import de.innomos.myapplication.data.entities.BootRecord
import kotlinx.coroutines.flow.Flow

class BootRecordRepo(private val dao: BootRecordDao) {

    val trackAllRecords: Flow<List<BootRecord>> = dao.trackBootRecords()

    suspend fun insert(bootRecord: BootRecord) {
        dao.insert(bootRecord)
    }

    suspend fun getAll(): List<BootRecord> {
        return dao.getBootRecords()
    }
}