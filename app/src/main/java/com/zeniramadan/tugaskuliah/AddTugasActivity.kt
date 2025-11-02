package com.zeniramadan.tugaskuliah

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeniramadan.tugaskuliah.databinding.ActivityAddTugasBinding

class AddTugasActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTugasBinding
    private lateinit var db : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTugasBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.SaveImageView.setOnClickListener {
            val namatugas = binding.namaTugasEditText.text.toString()
            val matakuliah = binding.mataKuliahEditText.text.toString()
            val namadosen = binding.namaDosenEditText.text.toString()
            val deadline = binding.deadlineEditText.text.toString()

            val tugas = Tugas(namatugas, matakuliah, namadosen, deadline)

            db.InsertTugas(tugas)

            finish()
            Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

private fun Any.setOnClickListener(function: () -> Unit) {}
