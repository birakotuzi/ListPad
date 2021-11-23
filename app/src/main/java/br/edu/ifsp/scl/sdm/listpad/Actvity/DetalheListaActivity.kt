package br.edu.ifsp.scl.sdm.listpad.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.R
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Lista

class DetalheListaActivity : AppCompatActivity() {

    private var lista = Lista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_lista)

        lista = this.intent.getSerializableExtra("lista") as Lista
        val nome = findViewById<EditText>(R.id.eTLisNome)
        val descricao = findViewById<EditText>(R.id.eTLisDescricao)
        val urgente = findViewById<EditText>(R.id.eTLisUrgente)
        val categoria = findViewById<EditText>(R.id.eTLisCategoria)

        nome.setText(lista.nome)
        descricao.setText(lista.descricao)
        urgente.setText(lista.urgente.toString())
        categoria.setText(lista.categoria.toString())
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
            val urgente = findViewById<EditText>(R.id.eTLisUrgente).text.toString()
            val categoria = findViewById<EditText>(R.id.eTLisCategoria).text.toString()

            lista.nome = nome
            lista.descricao = descricao
            lista.urgente = urgente.toInt()
            lista.categoria = categoria.toInt()


            if(db.atualizarLista(lista)>0) {
                Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
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