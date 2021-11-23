package br.edu.ifsp.scl.sdm.listpad.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R

class CadastroItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cadastro_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        var db = DatabaseHelper(this)
        if(menuItem.itemId==R.id.action_salvarItem) {
            val descricao = findViewById<EditText>(R.id.eTItemDescricao).text.toString()
            val flag = findViewById<EditText>(R.id.eTItemFlag).text.toString()
            val lista = findViewById<EditText>(R.id.eTItemLista).text.toString()

            val l= Item(null, descricao, flag.toInt(), lista.toInt())
            if(db.inserirItem(l)>0) {
                Toast.makeText(this, "Item inserido", Toast.LENGTH_LONG).show()
            }
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}