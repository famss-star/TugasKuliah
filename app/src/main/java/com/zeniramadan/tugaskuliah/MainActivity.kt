package com.zeniramadan.tugaskuliah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeniramadan.tugaskuliah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    private lateinit var tugasAdapter: TugasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        tugasAdapter = TugasAdapter(db.GetTugas(), this)

        binding.TugasRV.layoutManager = LinearLayoutManager(this)
        binding.TugasRV.adapter = tugasAdapter

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.TombolTambah.setOnClickListener {
            val layoutTambah = Intent(this, AddTugasActivity::class.java)

            startActivity(layoutTambah)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        tugasAdapter.refreshData(db.GetTugas())
    }
}