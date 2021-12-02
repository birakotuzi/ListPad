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
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R

class DetalheItemActivity : AppCompatActivity() {

    private var item = Item()
    private var lista = Lista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_item)

        item = this.intent.getSerializableExtra("item") as Item
        val db = DatabaseHelper(this)
        lista = db.buscarListaPorId(item.lista.toString())

        val listaId = findViewById<EditText>(R.id.etItemLista)
        val listaNome = findViewById<EditText>(R.id.etItemListaNome)

        val descricao = findViewById<EditText>(R.id.etItemDescricao)

        val spinnerCumprido = findViewById<Spinner>(R.id.sPItemFlag)
        val arrayCumpridoSpinner = listOf<String>("Sim","Não")
        val arrayCumpridoAdapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayCumpridoSpinner)
        spinnerCumprido.adapter = arrayCumpridoAdapter


        var cumpridoSelecionado = "Não"
        if (item.flag == 1) {
            cumpridoSelecionado = "Sim"
        }
        spinnerCumprido.setSelection(getIndex(spinnerCumprido, cumpridoSelecionado))

        descricao.setText(item.descricao)
        listaId.setText(lista.id_lista.toString())
        listaNome.setText(lista.nome)
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
        menuInflater.inflate(R.menu.menu_detalhe_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if(menuItem.itemId==R.id.action_salvarItem) {
            val descricao = findViewById<EditText>(R.id.etItemDescricao).text.toString()
            val lista = findViewById<EditText>(R.id.etItemLista).text.toString()

            val spinnerCumprido = findViewById<Spinner>(R.id.sPItemFlag).selectedItem.toString()
            var flag = 0
            if (spinnerCumprido == "Sim") {
                flag = 1
            }

            item.descricao = descricao
            item.flag = flag
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