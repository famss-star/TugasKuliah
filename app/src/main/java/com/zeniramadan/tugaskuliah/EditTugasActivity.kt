package com.zeniramadan.tugaskuliah

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeniramadan.tugaskuliah.databinding.ActivityEditTugasBinding
import java.util.Calendar

class EditTugasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTugasBinding
    private lateinit var db: DatabaseHelper
    private var tugasId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTugasBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        enableEdgeToEdge()
        setContentView(binding.root)

        tugasId = intent.getIntExtra("tugas_id", -1)
        if (tugasId == -1) {
            finish()
            return
        }

        val tugas = db.getTugasByID(tugasId)
        if (tugas != null) {
            binding.editNamaTugasEditText.setText(tugas.tugas)
            binding.editMataKuliahEditText.setText(tugas.matakuliah)
            binding.editNamaDosenEditText.setText(tugas.namadosen)
            binding.editDeskripsiEditText.setText(tugas.deskripsi)
            binding.editDeadlineTextView.text = tugas.deadline
        }

        binding.editDeadlineTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)

                    val timePickerDialog = TimePickerDialog(
                        this,
                        { _, selectedHour, selectedMinute ->
                            val selectedDateTime = "$selectedDay/${selectedMonth + 1}/$selectedYear $selectedHour:$selectedMinute"
                            binding.editDeadlineTextView.text = selectedDateTime
                        },
                        hour,
                        minute,
                        true
                    )
                    timePickerDialog.show()
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.editSaveButton.setOnClickListener {
            val newTugas = binding.editNamaTugasEditText.text.toString()
            val newMataKuliah = binding.editMataKuliahEditText.text.toString()
            val newNamaDosen = binding.editNamaDosenEditText.text.toString()
            val newDeskripsi = binding.editDeskripsiEditText.text.toString()
            val newDeadline = binding.editDeadlineTextView.text.toString()

            if (newTugas.isNotEmpty() && newMataKuliah.isNotEmpty() && newNamaDosen.isNotEmpty() && newDeadline.isNotEmpty()) {
                val updatedTugas = Tugas(tugasId, newTugas, newMataKuliah, newNamaDosen, newDeadline, newDeskripsi)
                db.editDataTugas(updatedTugas)
                finish()
                Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
