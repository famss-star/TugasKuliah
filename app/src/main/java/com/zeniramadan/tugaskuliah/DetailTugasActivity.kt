package com.zeniramadan.tugaskuliah

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeniramadan.tugaskuliah.databinding.ActivityDetailTugasBinding

class DetailTugasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTugasBinding
    private lateinit var db: DatabaseHelper
    private var tugasId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        db = DatabaseHelper(this)

        tugasId = intent.getIntExtra("tugas_id", -1)
        if (tugasId != -1) {
            val tugas = db.getTugasByID(tugasId)
            if (tugas != null) {
                binding.detailNamaTugasTextView.text = tugas.tugas
                binding.detailMataKuliahTextView.text = tugas.matakuliah
                binding.detailNamaDosenTextView.text = tugas.namadosen
                binding.detailDeskripsiTextView.text = tugas.deskripsi
                binding.detailDeadlineTextView.text = tugas.deadline

                if (tugas.status) {
                    binding.doneButton.text = "Batal Tandai Selesai"
                } else {
                    binding.doneButton.text = "Tandai Selesai"
                }
            }
        }

        binding.doneButton.setOnClickListener {
            val tugas = db.getTugasByID(tugasId)
            if (tugas != null) {
                val newStatus = !tugas.status
                db.updateStatus(tugasId, newStatus)
                Toast.makeText(this, if (newStatus) "Tugas ditandai selesai" else "Tugas ditandai belum selesai", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
