package com.example.esespi

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

private lateinit var connSQL: conexionSQL
private lateinit var btnIdiomaAceptar: Button
private lateinit var linearLayout: LinearLayout
private lateinit var Busqueda: EditText
private lateinit var Buscar: Button

class RegistroUsuarioSeleccionIdioma : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario_seleccion_idioma)

        val tarjetasSeleccionadas = mutableListOf<String>() // Cambio: Declarar lista de cadenas
        linearLayout = findViewById(R.id.linearLayoutIdiomaPicker) // Reemplaza "linearLayout" con el ID de tu LinearLayout
        connSQL = conexionSQL()
        Busqueda = findViewById(R.id.txtBusqudaIdiomaSelector)
        Buscar = findViewById(R.id.btnBuscar)


        val conn = connSQL.dbConn()
        if (conn != null) {
            val statement = conn.createStatement()
            val query = "select * from tbIdiomas" // Reemplaza "tu_tabla" con el nombre de tu tabla en la base de datos
            btnIdiomaAceptar = findViewById(R.id.btnIdiomaSelectorAceptar)

            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val lblIdioma = resultSet.getString("Idioma") // Reemplaza "nombre_columna" con el nombre de la columna que contiene los elementos

                // Crea la tarjeta (card) para cada elemento
                val card = layoutInflater.inflate(R.layout.registro_usuario_card_idioma, null)
                val selector = card.findViewById<View>(R.id.selectedBooleanCircle)
                val textView = card.findViewById<TextView>(R.id.lblIdiomaOnCard)
                textView.text = lblIdioma

                card.setOnClickListener {
                    // Verifica si la tarjeta ya está seleccionada
                    val estaSeleccionada = tarjetasSeleccionadas.contains(lblIdioma) // Cambio: Verificar con lblIdioma en lugar de card

                    // Cambia el fondo de la tarjeta según su estado de selección
                    if (estaSeleccionada) {
                        selector.setBackgroundResource(R.drawable.registro_ususario_circulo_seleccion_falso) // Establece el fondo deseado cuando no está seleccionada
                        tarjetasSeleccionadas.remove(lblIdioma) // Cambio: Eliminar lblIdioma en lugar de card
                    } else {
                        selector.setBackgroundResource(R.drawable.registro_ususario_circulo_seleccion_verdadero) // Establece el fondo deseado cuando está seleccionada
                        tarjetasSeleccionadas.add(lblIdioma) // Cambio: Agregar lblIdioma en lugar de card
                    }
                }
                // Agrega la tarjeta al contenedor
                linearLayout.addView(card)
            }

            Buscar.setOnClickListener {
                
            }

            btnIdiomaAceptar.setOnClickListener {
                val texto = tarjetasSeleccionadas.joinToString(" ")
                println(texto)
            }

            conn.close()
        }
    }
}