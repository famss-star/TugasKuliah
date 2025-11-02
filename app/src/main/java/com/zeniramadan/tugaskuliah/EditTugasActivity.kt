package com.zeniramadan.tugaskuliah

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeniramadan.tugaskuliah.databinding.ActivityEditTugasBinding

class EditTugasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTugasBinding
    private lateinit var db: DatabaseHelper
    private var findTugas : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditTugasBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        findTugas = intent.getStringExtra("tugas").toString()

        val tugas = db.findDatabyTugas(findTugas)
        binding.editNamaTugasEditText.setText(tugas?.tugas)
        binding.editMataKuliahEditText.setText(tugas?.matakuliah)
        binding.editNamaDosenEditText.setText(tugas?.namadosen)
        binding.editDeadlineEditText.setText(tugas?.deadline)

        binding.editSaveButton.setOnClickListener {
            val newTugas = binding.editNamaTugasEditText.text.toString()
            val newMataKuliah = binding.editMataKuliahEditText.text.toString()
            val newNamaDosen = binding.editNamaDosenEditText.text.toString()
            val newDeadline = binding.editDeadlineEditText.text.toString()

            val updateTugas = Tugas(newTugas, newMataKuliah, newNamaDosen, newDeadline)

            db.editDataTugas(findTugas, updateTugas)
            finish()
            Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}