# Tugas Kuliah

Aplikasi Android untuk mengelola tugas-tugas kuliah dengan mudah dan terorganisir.

## 📱 Deskripsi

Tugas Kuliah adalah aplikasi manajemen tugas kuliah yang membantu mahasiswa untuk mencatat, mengelola, dan melacak tugas-tugas perkuliahan mereka. Aplikasi ini memungkinkan pengguna untuk menyimpan informasi detail tentang setiap tugas termasuk nama tugas, mata kuliah, nama dosen, dan deadline.

## ✨ Fitur

- **Tambah Tugas**: Menambahkan tugas baru dengan informasi lengkap
- **Lihat Daftar Tugas**: Menampilkan semua tugas dalam bentuk list
- **Edit Tugas**: Mengubah informasi tugas yang sudah ada
- **Hapus Tugas**: Menghapus tugas yang sudah selesai atau tidak diperlukan
- **Penyimpanan Lokal**: Data tugas disimpan secara lokal menggunakan SQLite database

## 🛠️ Teknologi yang Digunakan

- **Bahasa**: Kotlin
- **Platform**: Android
- **Min SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 36
- **Database**: SQLite
- **Build System**: Gradle (Kotlin DSL)
- **UI Framework**: View Binding, RecyclerView
- **Libraries**:
  - AndroidX Core KTX
  - AndroidX AppCompat
  - Material Components
  - ConstraintLayout

## 📁 Struktur Proyek

```
TugasKuliah/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/zeniramadan/tugaskuliah/
│   │   │   │   ├── MainActivity.kt              # Activity utama untuk menampilkan daftar tugas
│   │   │   │   ├── AddTugasActivity.kt          # Activity untuk menambah tugas baru
│   │   │   │   ├── EditTugasActivity.kt         # Activity untuk mengedit tugas
│   │   │   │   ├── Tugas.kt                     # Data class untuk model Tugas
│   │   │   │   ├── TugasAdapter.kt              # Adapter untuk RecyclerView
│   │   │   │   └── DatabaseHelper.kt            # Helper class untuk operasi database SQLite
│   │   │   ├── res/                             # Resource files (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml              # Manifest aplikasi
│   │   ├── androidTest/                          # Android instrumentation tests
│   │   └── test/                                 # Unit tests
│   ├── build.gradle.kts                          # Build configuration untuk module app
│   └── proguard-rules.pro                        # ProGuard rules
├── gradle/                                        # Gradle wrapper files
├── build.gradle.kts                               # Root build configuration
├── settings.gradle.kts                            # Project settings
└── README.md                                      # File ini

```

## 🚀 Instalasi dan Setup

### Prasyarat

- Android Studio (versi terbaru)
- JDK 11 atau lebih tinggi
- Android SDK dengan API Level 24 atau lebih tinggi

### Langkah Instalasi

1. Clone repository ini:
   ```bash
   git clone https://github.com/zeniramadan/TugasKuliah.git
   ```

2. Buka project di Android Studio:
   - File → Open → Pilih folder TugasKuliah

3. Tunggu proses gradle sync selesai

4. Jalankan aplikasi:
   - Pilih emulator atau device fisik
   - Klik tombol Run (▶️) atau tekan Shift + F10

## 💡 Cara Penggunaan

1. **Menambah Tugas Baru**:
   - Tap tombol tambah (+) di halaman utama
   - Isi form dengan:
     - Nama Tugas
     - Mata Kuliah
     - Nama Dosen
     - Deadline
   - Tap tombol simpan

2. **Melihat Daftar Tugas**:
   - Semua tugas akan ditampilkan di halaman utama dalam bentuk card

3. **Mengedit Tugas**:
   - Tap pada tugas yang ingin diedit
   - Ubah informasi yang diperlukan
   - Simpan perubahan

4. **Menghapus Tugas**:
   - Tap tombol hapus pada card tugas yang ingin dihapus

## 📊 Model Data

Aplikasi ini menggunakan data model `Tugas` dengan struktur:

```kotlin
data class Tugas(
    val tugas: String,        // Nama tugas
    val matakuliah: String,   // Nama mata kuliah
    val namadosen: String,    // Nama dosen
    val deadline: String      // Tanggal deadline
)
```

## 🤝 Kontribusi

Kontribusi selalu diterima! Jika Anda ingin berkontribusi:

1. Fork repository ini
2. Buat branch fitur baru (`git checkout -b feature/FiturBaru`)
3. Commit perubahan (`git commit -m 'Menambah fitur baru'`)
4. Push ke branch (`git push origin feature/FiturBaru`)
5. Buat Pull Request

## 📄 Lisensi

Project ini dibuat untuk keperluan pembelajaran dan tugas kuliah.

## 👨‍💻 Author

**Zeni Ramadan**

---

*Dibuat dengan ❤️ untuk memudahkan mahasiswa dalam mengelola tugas kuliah*
