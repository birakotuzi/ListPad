package br.edu.ifsp.scl.sdm.listpad.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Data.ListaAdapter
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var listasLista = ArrayList<Lista>()
    lateinit var listaAdapter: ListaAdapter
    private var lista = Lista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext,CadastroListaActivity::class.java)
            startActivity(intent)
        }

        updateUI()
    }

    fun updateUI() {
        listasLista = db.listarListas()
        listaAdapter = ListaAdapter(listasLista)

        val recyclerview = findViewById<RecyclerView>(R.id.rVLista)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = listaAdapter

        var listener = object :ListaAdapter.ListaListener{
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext,DetalheListaActivity::class.java)
                val l = listaAdapter.listasLista[pos]
                intent.putExtra("lista", l)
                startActivity(intent)
            }
        }
        listaAdapter.setClickListener(listener)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if(item.itemId==R.id.action_alterarLista) {
            val nome = findViewById<EditText>(R.id.eTLisNome).text.toString()

            lista.nome = nome

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
        // listar Categoria
        if(item.itemId==R.id.action_listarCategoria){
            val intent = Intent(applicationContext,ListaCategoriaActivity::class.java)
            startActivity(intent)
        }
        //listar Item
        if(item.itemId==R.id.action_listarItem){
            val intent = Intent(applicationContext,ListaItemActivity::class.java)
            startActivity(intent)
        }
        //

        return super.onOptionsItemSelected(item)
    }
}