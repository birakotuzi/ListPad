package br.edu.ifsp.scl.sdm.listpad.Actvity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Data.ItemAdapter
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaItemActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var itemsLista = ArrayList<Item>()
    lateinit var itemAdapter: ItemAdapter
    private var lista = Lista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_item)
        this.setTitle("Itens")

        lista = this.intent.getSerializableExtra("lista") as Lista

        val fab = findViewById<FloatingActionButton>(R.id.fabItem)
        fab.setOnClickListener {
            val intent = Intent(applicationContext,CadastroItemActivity::class.java)
            intent.putExtra("lista", lista)
            startActivity(intent)
        }

        updateUI()
    }

    fun updateUI() {
        itemsLista = db.listarItensPorIdLista(lista.id_lista.toString())
        itemAdapter = ItemAdapter(itemsLista)

        val recyclerview = findViewById<RecyclerView>(R.id.rvItem)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = itemAdapter

        var listener = object : ItemAdapter.ItemListener{
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext, DetalheItemActivity::class.java)
                val i = itemAdapter.itemsLista[pos]
                intent.putExtra("item", i)
                startActivity(intent)
            }

            override fun onImageClick(pos: Int) {
                val id: Int? = itemAdapter.itemsLista[pos].id_item
                var flag:Int? = itemAdapter.itemsLista[pos].flag

                if (flag == null ) {
                    flag = 0
                }

                if (flag == 0) {
                    flag = 1
                } else {
                    flag = 0
                }

                if(db.atualizarItemFlag(id, flag)>0) {
                    updateUI()
                }
            }

            override fun onImageDeleteClick(pos: Int) {
                val i = itemAdapter.itemsLista[pos]
                confirmaExclusao(i)
            }
        }
        itemAdapter.setClickListener(listener)
    }

    private fun confirmaExclusao(i: Item) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alerta") // O Titulo da notificação
        alertDialog.setMessage("Confirma a exclusão?") // a mensagem ou alerta

        alertDialog.setPositiveButton("Sim", { _, _ ->
            if(db.apagarItem(i)>0) {
                updateUI()
            }
        })
        alertDialog.setNegativeButton("Não", { dialog, _ ->
            dialog.dismiss()
        })
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_categoria, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // listar Lista
        if (item.itemId == R.id.action_listarLista) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}