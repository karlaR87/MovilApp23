package com.example.esespi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.sql.*
import android.renderscript.*
import android.widget.TextView
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

private lateinit var connSQL: conexionSQL
private lateinit var Usuario: EditText
private lateinit var Contraseña: EditText
private lateinit var Login: Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Usuario=findViewById(R.id.txtUsuarioLogIn)
        Contraseña=findViewById(R.id.txtContraseñaLogIn)
        Login=findViewById(R.id.btnIniciarSesiónLogIn)
        connSQL = conexionSQL()


        Login.setOnClickListener {
            val usuario = Usuario.text.toString()
            val contraseña = Contraseña.text.toString()
            val conn = connSQL.dbConn()

            if (conn != null) {
                val credentialsValid = verifyCredentials(conn, usuario, contraseña)
                if (credentialsValid) {
                    showToast("Inicio de sesión exitoso")

                    //Abrir la activity principal de la aplicacion
                    /*
                    val login = Intent(this, NavigatorMainScreen::class.java)
                    startActivity(login)
                    */

                    // Realiza alguna acción adicional después de iniciar sesión exitosamente
                } else {
                    showToast("Credenciales inválidas")
                    // Realiza alguna acción adicional cuando las credenciales son inválidas
                }

                conn.close()
            } else {
                showToast("Error de conexión")
                // Realiza alguna acción adicional cuando no se pudo establecer la conexión con la base de datos
            }
        }

    }

    private fun verifyCredentials(conn: Connection, usuario: String, contraseña: String): Boolean {
        val query = "SELECT COUNT(*) FROM usuarios WHERE Usuario = ? AND Contraseña = ?"
        val preparedStatement = conn.prepareStatement(query)
        preparedStatement.setString(1, usuario)
        preparedStatement.setString(2, contraseña)

        val resultSet = preparedStatement.executeQuery()
        resultSet.next()
        val count = resultSet.getInt(1)

        resultSet.close()
        preparedStatement.close()

        return count > 0
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}