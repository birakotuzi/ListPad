package br.edu.ifsp.scl.sdm.listpad.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R

class CadastroListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_lista)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cadastro_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var db = DatabaseHelper(this)
        if(item.itemId==R.id.action_salvarLista) {
            val nome = findViewById<EditText>(R.id.eTLisNome).text.toString()
            val descricao = findViewById<EditText>(R.id.eTLisDescricao).text.toString()
            val urgente = findViewById<EditText>(R.id.eTLisUrgente).text.toString()
            val categoria = findViewById<EditText>(R.id.eTLisCategoria).text.toString()

            val l= Lista(null, nome, descricao, urgente.toInt(), categoria.toInt())
            if(db.inserirLista(l)>0) {
                Toast.makeText(this, "Lista inserida", Toast.LENGTH_LONG).show()
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}