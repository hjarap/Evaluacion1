package com.example.evaluacion1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var cantCazuela: EditText
    private lateinit var cantPastel: EditText
    private lateinit var totalCazuela: EditText
    private lateinit var totalPastel: EditText
    private lateinit var totalComida: EditText
    private lateinit var totalPropina: EditText
    private lateinit var totalFinal: EditText
    private lateinit var switchPropina: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cantCazuela = findViewById(R.id.CantCazuela)
        cantPastel = findViewById(R.id.CantPastel)
        totalCazuela = findViewById(R.id.TotalCazuela)
        totalPastel = findViewById(R.id.TotalPastel)
        totalComida = findViewById(R.id.totalComida)
        totalPropina = findViewById(R.id.totalPropina)
        totalFinal = findViewById(R.id.totalFinal)
        switchPropina = findViewById(R.id.switchPropina)

        cantCazuela.addTextChangedListener(textWatcher)
        cantPastel.addTextChangedListener(textWatcher)

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            calculartotal()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            calculartotal()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun calcularPropina(totalComida: Int): Int {
        return if (switchPropina.isChecked) {
            (totalComida * 0.1).toInt()
        } else {
            0
        }
    }

    private fun calculartotal() {
        val cazuela = cantCazuela.text.toString().toIntOrNull() ?: 0
        val pastel = cantPastel.text.toString().toIntOrNull() ?: 0

        val totalCazuelaPrecio = cazuela * 12000
        val totalPastelPrecio = pastel * 10000

        val precioComida = totalCazuelaPrecio + totalPastelPrecio
        val propina = calcularPropina(precioComida)

        val totalFinalConPropina = precioComida + propina

        totalCazuela.setText(formatoMoneda(totalCazuelaPrecio))
        totalPastel.setText(formatoMoneda(totalPastelPrecio))
        totalComida.setText(formatoMoneda(precioComida))
        totalPropina.setText(formatoMoneda(propina))
        totalFinal.setText(formatoMoneda(totalFinalConPropina))
    }

    private fun formatoMoneda(valor: Int): String {
        val formato = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return formato.format(valor)
    }
}
