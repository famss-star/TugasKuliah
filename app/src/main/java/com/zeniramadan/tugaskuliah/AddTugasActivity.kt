package com.zeniramadan.tugaskuliah

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeniramadan.tugaskuliah.databinding.ActivityAddTugasBinding
import java.util.Calendar

class AddTugasActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTugasBinding
    private lateinit var db : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTugasBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.deadlineTextView.setOnClickListener {
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
                            binding.deadlineTextView.text = selectedDateTime
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

        binding.saveButton.setOnClickListener {
            val namatugas = binding.namaTugasEditText.text.toString()
            val matakuliah = binding.mataKuliahEditText.text.toString()
            val namadosen = binding.namaDosenEditText.text.toString()
            val deskripsi = binding.deskripsiEditText.text.toString()
            val deadline = binding.deadlineTextView.text.toString()

            if (namatugas.isNotEmpty() && matakuliah.isNotEmpty() && namadosen.isNotEmpty() && deadline.isNotEmpty()) {
                val tugas = Tugas(0, namatugas, matakuliah, namadosen, deadline, deskripsi)
                db.InsertTugas(tugas)
                finish()
                Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
