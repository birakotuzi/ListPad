package br.edu.ifsp.scl.sdm.listpad.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.R
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.Model.Lista

class DetalheListaActivity : AppCompatActivity() {

    private var lista = Lista()
    private var categoriasLista = ArrayList<Categoria>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_lista)
        this.setTitle("Editar lista")

        lista = this.intent.getSerializableExtra("lista") as Lista
        val nome = findViewById<EditText>(R.id.eTLisNome)
        val descricao = findViewById<EditText>(R.id.eTLisDescricao)
        val urgente = findViewById<EditText>(R.id.eTLisUrgente)
        //val categoria = findViewById<EditText>(R.id.eTLisCategoria)

        val db = DatabaseHelper(this)
        categoriasLista = db.listarCategorias()
        val arraySpinner: MutableList<String> = ArrayList()
        var categoriaSelecionada = lista.categoria.toString()
        categoriasLista.forEach {
            arraySpinner.add(it.id_categoria.toString() + " - " + it.descricao)
            if (categoriaSelecionada == it.id_categoria.toString()) {
                categoriaSelecionada += " - " + it.descricao
            }
        }

        val spinnerCategoria = findViewById<Spinner>(R.id.sPLisCategoria)

        val arrayAdapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraySpinner)
        spinnerCategoria.adapter = arrayAdapter

        spinnerCategoria.setSelection(getIndex(spinnerCategoria, categoriaSelecionada))

        val spinnerUrgente = findViewById<Spinner>(R.id.sPLisUrgente)
        val arrayUrgenteSpinner = listOf<String>("Sim","Não")
        val arrayUrgenteAdapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayUrgenteSpinner)
        spinnerUrgente.adapter = arrayUrgenteAdapter


        var urgenteSelecionado = "Não"
        if (lista.urgente == 1) {
            urgenteSelecionado = "Sim"
        }
        spinnerUrgente.setSelection(getIndex(spinnerUrgente, urgenteSelecionado))


        nome.setText(lista.nome)
        descricao.setText(lista.descricao)
        //urgente.setText(lista.urgente.toString())
        //categoria.setText(lista.categoria.toString())
    }

    // função para pegar o índice do item selecionado do Spinner
    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if(item.itemId==R.id.action_alterarLista) {
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

            lista.nome = nome
            lista.descricao = descricao
            lista.urgente = urgente
            lista.categoria = categoria.toInt()

            val existe = db.pesquisarListasPorNome(lista.id_lista.toString(), nome)
            if (existe) {
                Toast.makeText(this, "Não é possível atualizar. Já existe outro registro com esse nome na lista", Toast.LENGTH_LONG).show()
            } else {
                if (db.atualizarLista(lista) > 0) {
                    Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }

        if(item.itemId==R.id.action_excluirLista){
            if(db.apagarLista(lista)>0) {
                Toast.makeText(this, "Lista excluída", Toast.LENGTH_LONG).show()
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}