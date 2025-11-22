package com.example.avancesistemasmoviles

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AQUI VA EL MODO OSCURO
        val prefsDark = getSharedPreferences("temaIMC", MODE_PRIVATE)
        val darkModeAct = prefsDark.getBoolean("modoOscuro", false)

        if (darkModeAct) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //  DESPUÉS SIGUE TU CÓDIGO NORMAL
        val editPeso = findViewById<EditText>(R.id.editPeso)
        val editAltura = findViewById<EditText>(R.id.editAltura)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiar)
        val btnHistorial = findViewById<Button>(R.id.btnHistorial)
        val textResultado = findViewById<TextView>(R.id.textResultado)
        val btnModoOscuro = findViewById<Button>(R.id.btnModoOscuro)

        // Cargar el ultimo IMC guardado
        val prefs = getSharedPreferences("appIMC", MODE_PRIVATE)
        val ultimo = prefs.getString("ultimoResultado", "")

        if (!ultimo.isNullOrEmpty()) {
            textResultado.text = ultimo
        }

        // Cargar peso y altura guardados
        editPeso.setText(prefs.getString("ultimoPeso", ""))
        editAltura.setText(prefs.getString("ultimoAltura", ""))

        // Guardar automáticamente cuando el usuario escribe
        editPeso.addTextChangedListener {
            prefs.edit().putString("ultimoPeso", it.toString()).apply()
        }

        editAltura.addTextChangedListener {
            prefs.edit().putString("ultimoAltura", it.toString()).apply()
        }

        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }



        //termina cargar ultimo IMC guardado

        //Boton calcular IMC
        btnCalcular.setOnClickListener {

            val pesoStr = editPeso.text.toString()
            val alturaStr = editAltura.text.toString()

            if (pesoStr.isNotEmpty() && alturaStr.isNotEmpty()) {
                val peso = pesoStr.toFloat()
                // Convierte cm a m
                var altura = alturaStr.toFloat()
                if (altura > 3) {
                    altura /= 100
                }

                if (altura <= 0 || peso <= 0) {
                    textResultado.text = "Por favor ingresa valores válidos."
                    return@setOnClickListener
                }

                val imc = peso / (altura * altura)

                val clasificacion: String
                val color: Int

                when {
                    imc < 18.5 -> {
                        clasificacion = "Bajo peso \uD83E\uDEB6"
                        color = android.graphics.Color.parseColor("#3FA9F5") // azul
                    }
                    imc < 24.9 -> {
                        clasificacion = "Normal \uD83D\uDCAA"
                        color = android.graphics.Color.parseColor("#4CAF50") // verde
                    }
                    imc < 29.9 -> {
                        clasificacion = "Sobrepeso \uD83C\uDF54"
                        color = android.graphics.Color.parseColor("#FFC107") // amarillo
                    }
                    else -> {
                        clasificacion = "Obesidad ⚠\uFE0F"
                        color = android.graphics.Color.parseColor("#F44336") // rojo
                    }
                }
                val recomendacion = when {
                    imc < 18.5 -> "Recomendación: Aumenta tu ingesta calórica y consulta un nutriólogo."
                    imc < 24.9 -> "Recomendación: ¡Sigue así! Mantén hábitos saludables."
                    imc < 29.9 -> "Recomendación: Aumenta tu actividad física y cuida tu alimentación."
                    else -> "Recomendación: Es recomendable acudir con un especialista en salud."
                }


                val resultado = "Tu IMC es %.2f\nClasificación: %s\n\n%s"
                    .format(imc, clasificacion, recomendacion)
                textResultado.text = resultado
                val anim = android.view.animation.AlphaAnimation(0f, 1f)
                anim.duration = 700
                textResultado.startAnimation(anim)

                // Cargar el ultimo IMC guardado
                val prefs = getSharedPreferences("appIMC", MODE_PRIVATE)
                //Guardar el último resultado calculado
                prefs.edit().putString("ultimoResultado", resultado).apply()
                textResultado.setTextColor(color)
                // Guardar en historial
                val historial = prefs.getStringSet("historialIMC", mutableSetOf())!!.toMutableSet()
                historial.add(resultado)
                prefs.edit().putStringSet("historialIMC", historial).apply()


            } else {
                textResultado.text = "Por favor, ingresa peso y altura."
            }
        }
        btnLimpiar.setOnClickListener {
            editPeso.text.clear()
            editAltura.text.clear()
            textResultado.text = "Tu IMC aparecerá aquí"
            textResultado.setTextColor(android.graphics.Color.BLACK)
        }

        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }

        val btnTablaIMC = findViewById<Button>(R.id.btnTablaIMC)

        btnTablaIMC.setOnClickListener {
            val intent = Intent(this, TablaImcActivity::class.java)
            startActivity(intent)
        }

        btnModoOscuro.setOnClickListener {
            val prefsDark = getSharedPreferences("temaIMC", MODE_PRIVATE)
            val editor = prefsDark.edit()

            val modoActual = prefsDark.getBoolean("modoOscuro", false)

            if (modoActual) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("modoOscuro", false)
                btnModoOscuro.text = "Modo Oscuro"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("modoOscuro", true)
                btnModoOscuro.text = "Modo Claro"
            }
            editor.apply()
        }



    }

}
