package com.example.avancesistemasmoviles

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historial) // ← así, con tu nombre REAL

        val prefs = getSharedPreferences("appIMC", MODE_PRIVATE)
        val contenedor = findViewById<LinearLayout>(R.id.contenedorHistorial)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            finish()  // ← Esto cierra esta pantalla y regresa a la anterior
        }


        // Obtener historial guardado
        val historial = prefs.getStringSet("historialIMC", emptySet())!!.toList().reversed()

        if (historial.isEmpty()) {
            val mensaje = TextView(this)
            mensaje.text = "No hay historial todavía."
            mensaje.textSize = 18f
            contenedor.addView(mensaje)
            return
        }

        // Mostrar historial
        historial.forEach { item ->
            val txt = TextView(this)
            txt.text = item
            txt.textSize = 16f
            txt.setPadding(0, 10, 0, 10)
            contenedor.addView(txt)
        }
    }
}
