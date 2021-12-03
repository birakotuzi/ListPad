package br.edu.ifsp.scl.sdm.listpad.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.R

class CadastroCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_categoria)

        this.setTitle("Cadastrar categoria")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cadastro_categoria, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var db = DatabaseHelper(this)
        if(item.itemId==R.id.action_salvarCategoria) {
            val descricao = findViewById<EditText>(R.id.editTextDescricao).text.toString()
            if( descricao.trim().equals(""))
            {
                Toast.makeText(this, "Descrição não pode ser vazia", Toast.LENGTH_LONG).show()
            } else {
                val c = Categoria(null, descricao)
                if (db.inserirCategoria(c) > 0) {
                    Toast.makeText(this, "Categoria inserida", Toast.LENGTH_LONG)
                }
                finish()
            }
        }
        if(item.itemId==R.id.action_listaCategoria) {
            val intent = Intent(applicationContext,ListaCategoriaActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}