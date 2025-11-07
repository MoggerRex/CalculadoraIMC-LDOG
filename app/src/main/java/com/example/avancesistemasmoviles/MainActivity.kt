package com.example.avancesistemasmoviles

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editPeso = findViewById<EditText>(R.id.editPeso)
        val editAltura = findViewById<EditText>(R.id.editAltura)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val textResultado = findViewById<TextView>(R.id.textResultado)

        btnCalcular.setOnClickListener {
            val pesoStr = editPeso.text.toString()
            val alturaStr = editAltura.text.toString()

            if (pesoStr.isNotEmpty() && alturaStr.isNotEmpty()) {
                val peso = pesoStr.toFloat()
                val altura = alturaStr.toFloat()
                val imc = peso / (altura * altura)

                val clasificacion = when {
                    imc < 18.5 -> "Bajo peso"
                    imc < 24.9 -> "Normal"
                    imc < 29.9 -> "Sobrepeso"
                    else -> "Obesidad"
                }

                val resultado = "Tu IMC es %.2f\nClasificación: %s".format(imc, clasificacion)
                textResultado.text = resultado
            } else {
                textResultado.text = "Por favor, ingresa peso y altura."
            }
        }
    }
}
