package br.edu.ifsp.scl.sdm.listpad.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.R

class DetalheItemActivity : AppCompatActivity() {

    private var item = Item()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_item)

        item = this.intent.getSerializableExtra("item") as Item
        val descricao = findViewById<EditText>(R.id.etItemDescricao)
        val flag = findViewById<EditText>(R.id.etItemFlag)
        val lista = findViewById<EditText>(R.id.etItemLista)

        descricao.setText(item.descricao)
        flag.setText(item.flag.toString())
        lista.setText(item.lista.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if(menuItem.itemId==R.id.action_alterarLista) {
            val descricao = findViewById<EditText>(R.id.etItemDescricao).text.toString()
            val flag = findViewById<EditText>(R.id.etItemFlag).text.toString()
            val lista = findViewById<EditText>(R.id.etItemLista).text.toString()

            item.descricao = descricao
            item.flag = flag.toInt()
            item.lista = lista.toInt()

            val existe = db.pesquisarItensPorDescricao(item.id_item.toString(), descricao)
            if (existe) {
                Toast.makeText(this, "Não é possível atualizar. Já existe outro registro com essa descrição", Toast.LENGTH_LONG).show()
            } else {
                if (db.atualizarItem(item) > 0) {
                    Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }

        if(menuItem.itemId==R.id.action_excluirItem){
            if(db.apagarItem(item)>0) {
                Toast.makeText(this, "Item excluído", Toast.LENGTH_LONG).show()
            }
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}