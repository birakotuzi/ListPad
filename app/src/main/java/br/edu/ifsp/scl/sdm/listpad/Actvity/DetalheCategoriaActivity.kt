package br.edu.ifsp.scl.sdm.listpad.Actvity

import android.content.Intent
import android.database.DatabaseErrorHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.ifsp.scl.sdm.listpad.Data.DatabaseHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R

class DetalheCategoriaActivity : AppCompatActivity() {

    private var categoria = Categoria()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_categoria)
        this.setTitle("Editar categoria")

        categoria = this.intent.getSerializableExtra("categoria") as Categoria
        val descricao = findViewById<EditText>(R.id.editTextDescricao)

        descricao.setText(categoria.descricao)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_categoria, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if(item.itemId==R.id.action_alterarCategoria) {
            val descricao = findViewById<EditText>(R.id.editTextDescricao).text.toString()
            if( descricao.trim().equals(""))
            {
                Toast.makeText(this, "Descrição não pode ser vazia", Toast.LENGTH_LONG).show()
            } else {
                categoria.descricao = descricao

                if (db.atualizarCategoria(categoria) > 0) {
                    Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }

        if(item.itemId==R.id.action_excluirCategoria){
            var exclusao_ref = true
            var listasLista = db.listarListasPorCategoria(categoria)
            listasLista.forEach {
                if (db.apagarItemPorIdLista(it) > 0) {
                    if (db.apagarLista(it) > 0) {
                        exclusao_ref = true
                    }
                    else {
                        exclusao_ref = false
                    }
                }
                else {
                    exclusao_ref = false
                }
            }

            if (exclusao_ref == true)
                if(db.apagarCategoria(categoria)>0) {
                    Toast.makeText(this, "Categoria excluída", Toast.LENGTH_LONG).show()
                }
            else {
                    Toast.makeText(this, "Erro ao excluir categoria", Toast.LENGTH_LONG).show()
            }
            finish()
        }
        if(item.itemId==R.id.action_listaCategoria) {
            val intent = Intent(applicationContext,ListaCategoriaActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}