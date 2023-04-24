package de.innomos.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import de.innomos.myapplication.data.BootRecordRepo
import de.innomos.myapplication.data.MyDatabase
import de.innomos.myapplication.data.entities.BootRecord
import de.innomos.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repo: BootRecordRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repo = BootRecordRepo(MyDatabase.getDatabase(this).bootRecordDao())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repo.trackAllRecords
                .collect { records ->
                    binding.info.text = screenMessage(records)
                }
        }
    }

    private fun screenMessage(records: List<BootRecord>): String {
        return if (records.isEmpty()) {
            "No boots detected"
        } else {
            return records
                .mapIndexed { index, record ->
                    val itemPos = index + 1
                    "$itemPos - ${record.timestamp}"
                }
                .joinToString(separator = "\n")
        }
    }
}