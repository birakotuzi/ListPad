package br.edu.ifsp.scl.sdm.listpad.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Data.CategoriaAdapter
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaCategoriaActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var categoriasLista = ArrayList<Categoria>()
    lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_categoria)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext,CadastroCategoriaActivity::class.java)
            startActivity(intent)
        }

        updateUI()
    }

    fun updateUI() {
        categoriasLista = db.listarCategorias()
        categoriaAdapter = CategoriaAdapter(categoriasLista)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = categoriaAdapter

        var listener = object :CategoriaAdapter.CategoriaListener{
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext, DetalheCategoriaActivity::class.java)
                val c = categoriaAdapter.categoriasLista[pos]
                intent.putExtra("categoria", c)
                startActivity(intent)
            }
        }
        categoriaAdapter.setClickListener(listener)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}