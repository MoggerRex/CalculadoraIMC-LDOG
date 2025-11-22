package com.example.avancesistemasmoviles

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TablaImcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tabla_imc)

        val btnRegresarTabla = findViewById<Button>(R.id.btnRegresarTabla)
        btnRegresarTabla.setOnClickListener {
            finish()
        }
    }
}
