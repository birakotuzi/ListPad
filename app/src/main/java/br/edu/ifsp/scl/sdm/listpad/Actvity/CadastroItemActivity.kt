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

    private var lista = Lista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_item)

        lista = this.intent.getSerializableExtra("lista") as Lista
        val listaId = findViewById<EditText>(R.id.eTItemLista)
        val listaNome = findViewById<EditText>(R.id.eTItemListaNome)


        listaId.setText(lista.id_lista.toString())
        listaNome.setText(lista.nome.toString())
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

            val existe = db.pesquisarItensPorDescricao(null, descricao)
            if (existe) {
                Toast.makeText(this, "Não é possível inserir. Descrição já existe", Toast.LENGTH_LONG).show()
            } else {
                val l = Item(null, descricao, flag.toInt(), lista.toInt())
                if (db.inserirItem(l) > 0) {
                    Toast.makeText(this, "Item inserido", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}