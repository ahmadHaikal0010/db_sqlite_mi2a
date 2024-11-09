package com.haikal.db_sqlite_mi2a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.haikal.db_sqlite_mi2a.helper.DBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var etNamaLengkap : EditText
    private lateinit var etKampus : EditText
    private lateinit var etEmail : EditText
    private lateinit var etTelepon : EditText
    private lateinit var btnSubmit : Button
    private lateinit var btnShowData : Button
    private lateinit var txtNama : TextView
    private lateinit var txtKampus : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etNamaLengkap = findViewById(R.id.etNamaLengkap)
        etKampus = findViewById(R.id.etKampus)
        etEmail = findViewById(R.id.etEmail)
        etTelepon = findViewById(R.id.etTelepon)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnShowData = findViewById(R.id.btnShowData)
        txtNama = findViewById(R.id.txtNama)
        txtKampus = findViewById(R.id.txtKampus)

        // add data
        btnSubmit.setOnClickListener() {
            val dbHelper = DBHelper(this, null)

            // get data dari widget edit text
            val name = etNamaLengkap.text.toString()
            val kampus = etKampus.text.toString()
            val email = etEmail.text.toString()
            val telpon = etTelepon.text.toString()

            // masukkan ke db sqlite
            dbHelper.addName(
                name,
                kampus,
                email,
                telpon
            )

            // tambahkan toast untuk notif data berhasil masuk
            Toast.makeText(this, "Data berhasil masuk", Toast.LENGTH_LONG)

            // ketika berhasil input, kita clear semua isian di widget
            etNamaLengkap.text.clear()
            etKampus.text.clear()
            etEmail.text.clear()
            etTelepon.text.clear()
        }

        btnShowData.setOnClickListener() {
            val db = DBHelper(this, null)
            val cursor = db.getName()
            cursor!!.moveToFirst() // mengambil data pertama / terbaru
            txtNama.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAMA_LENGKAP)) + "\n" )
            txtKampus.append(cursor.getString(cursor.getColumnIndex(DBHelper.KAMPUS)) + "\n" )

            while (cursor.moveToNext()) {
                txtNama.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAMA_LENGKAP)) + "\n" )
                txtKampus.append(cursor.getString(cursor.getColumnIndex(DBHelper.KAMPUS)) + "\n" )
            }
            cursor.close()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}