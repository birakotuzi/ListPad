package br.edu.ifsp.scl.sdm.listpad.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R

class CadastroListaActivity : AppCompatActivity() {

    private var categoriasLista = ArrayList<Categoria>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_lista)
        this.setTitle("Cadastrar lista")

        val db = DatabaseHelper(this)
        categoriasLista = db.listarCategorias()
        val arraySpinner: MutableList<String> = ArrayList()
        categoriasLista.forEach {
            arraySpinner.add(it.id_categoria.toString() + " - " + it.descricao)
        }

        val spinnerCategoria = findViewById<Spinner>(R.id.sPLisCategoria)

        val arrayAdapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraySpinner)
        spinnerCategoria.adapter = arrayAdapter

        val spinnerUrgente = findViewById<Spinner>(R.id.sPLisUrgente)

        val arrayUrgenteSpinner = listOf<String>("Sim","Não")
        val arrayUrgenteAdapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayUrgenteSpinner)
        spinnerUrgente.adapter = arrayUrgenteAdapter
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
            //val urgente = findViewById<EditText>(R.id.eTLisUrgente).text.toString()
            //val categoria = findViewById<EditText>(R.id.eTLisCategoria).text.toString()
            val spinnerCategoria = findViewById<Spinner>(R.id.sPLisCategoria).selectedItem.toString()
            val categoria = spinnerCategoria.substringBefore(" - ")

            val spinnerUrgente = findViewById<Spinner>(R.id.sPLisUrgente).selectedItem.toString()
            var urgente = 0
            if (spinnerUrgente == "Sim") {
                urgente = 1
            }

            val existe = db.pesquisarListasPorNome(null, nome)
            if (existe) {
                Toast.makeText(this, "Não é possível inserir. Nome já existe na lista", Toast.LENGTH_LONG).show()
            } else {
                val l = Lista(null, nome, descricao, urgente, categoria.toInt())
                if (db.inserirLista(l) > 0) {
                    Toast.makeText(this, "Lista inserida", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}