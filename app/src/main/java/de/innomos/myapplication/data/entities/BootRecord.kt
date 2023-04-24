package de.innomos.myapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boot_record")
class BootRecord(
    @PrimaryKey @ColumnInfo(name = "timestamp") val timestamp: Long,
)