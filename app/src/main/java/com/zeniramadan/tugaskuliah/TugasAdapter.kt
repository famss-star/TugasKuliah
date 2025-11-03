package com.zeniramadan.tugaskuliah

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TugasAdapter(private var tugas: List<Tugas>, context: Context) : RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {

    class TugasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val namaTugasTextView: TextView = itemView.findViewById(R.id.namaTugasTextView)
        val mataKuliahTextView: TextView = itemView.findViewById(R.id.mataKuliahTextView)
        val namaDosenTextView: TextView = itemView.findViewById(R.id.namaDosenTextView)
        val deadlineTextView: TextView = itemView.findViewById(R.id.deadlineTextView)
    }

    private val db : DatabaseHelper = DatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_tugas, parent, false)
        return TugasViewHolder(view)
    }

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        val tugas = tugas[position]
        holder.namaTugasTextView.text = tugas.tugas
        holder.mataKuliahTextView.text = tugas.matakuliah
        holder.namaDosenTextView.text = tugas.namadosen
        holder.deadlineTextView.text = tugas.deadline

        // Visual cue for completed tasks
        if (tugas.status) {
            holder.namaTugasTextView.paintFlags = holder.namaTugasTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.alpha = 0.5f
        } else {
            holder.namaTugasTextView.paintFlags = holder.namaTugasTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.itemView.alpha = 1.0f
        }

        // Set OnClickListener for the whole item view
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailTugasActivity::class.java).apply {
                putExtra("tugas_id", tugas.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.editButton.setOnClickListener {
            val editIntent = Intent(holder.itemView.context, EditTugasActivity::class.java).apply {
                putExtra("tugas_id", tugas.id)
            }
            holder.itemView.context.startActivity(editIntent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteDataTugas(tugas.id)
            refreshData(db.getAllTugas())
            Toast.makeText(holder.itemView.context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newData: List<Tugas>) {
        tugas = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tugas.size
}