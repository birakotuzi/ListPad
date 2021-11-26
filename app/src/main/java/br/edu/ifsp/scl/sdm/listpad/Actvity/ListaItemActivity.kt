package br.edu.ifsp.scl.sdm.listpad.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Data.ItemAdapter
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaItemActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var itensLista = ArrayList<Item>()
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_item)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext,CadastroListaActivity::class.java)
            startActivity(intent)
        }

        updateUI()
    }

    fun updateUI() {
        itensLista = db.listarItens()
        itemAdapter = ItemAdapter(itensLista)

        val recyclerview = findViewById<RecyclerView>(R.id.rVItem)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = itemAdapter

        var listener = object :ItemAdapter.ItemListener{
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext, DetalheItemActivity::class.java)
                val i = itemAdapter.itensLista[pos]
                intent.putExtra("item", i)
                startActivity(intent)
            }
        }
        itemAdapter.setClickListener(listener)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}