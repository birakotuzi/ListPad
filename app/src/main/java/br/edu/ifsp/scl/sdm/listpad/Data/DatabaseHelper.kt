package br.edu.ifsp.scl.sdm.listpad.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.Model.Lista

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "listpad.db"
        private val DATABASE_VERSION = 1

        private val CAT_TABLE_NAME = "categorias"
        private val ID_CATEGORIA = "id_categoria"
        private val CAT_DESCRICAO = "descricao"

        private val LIS_TABLE_NAME = "listas"
        private val ID_LISTA = "id_lista"
        private val LIS_NOME = "nome"
        private val LIS_DESCRICAO = "descricao"
        private val LIS_URGENTE = "urgente"
        private val LIS_CATEGORIA = "categoria"

        private val ITEM_TABLE_NAME = "itens"
        private val ID_ITEM = "id_item"
        private val ITEM_DESCRICAO = "descricao"
        private val ITEM_FLAG = "flag"
        private val ITEM_LISTA = "lista"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_CAT = "CREATE TABLE $CAT_TABLE_NAME ($ID_CATEGORIA INTEGER PRIMARY KEY AUTOINCREMENT, $CAT_DESCRICAO TEXT);"
        p0?.execSQL(CREATE_TABLE_CAT)
        val CREATE_TABLE_LIS = "CREATE TABLE $LIS_TABLE_NAME ($ID_LISTA INTEGER PRIMARY KEY AUTOINCREMENT, $LIS_NOME TEXT, $LIS_DESCRICAO TEXT, $LIS_URGENTE INTEGER, $LIS_CATEGORIA INTEGER, FOREIGN KEY ($LIS_CATEGORIA) REFERENCES $CAT_TABLE_NAME ($ID_CATEGORIA))"
        p0?.execSQL(CREATE_TABLE_LIS)
        val CREATE_TABLE_ITEM = "CREATE TABLE $ITEM_TABLE_NAME ($ID_ITEM INTEGER PRIMARY KEY AUTOINCREMENT, $ITEM_DESCRICAO TEXT, $ITEM_FLAG INTEGER, $ITEM_LISTA INTEGER, FOREIGN KEY ($ITEM_LISTA) REFERENCES $LIS_TABLE_NAME ($ID_LISTA))"
        p0?.execSQL(CREATE_TABLE_ITEM)

        val INSERT_CAT1 = "INSERT INTO $CAT_TABLE_NAME values (1, 'tarefas')"
        p0?.execSQL(INSERT_CAT1)
        val INSERT_CAT2 = "INSERT INTO $CAT_TABLE_NAME values (2, 'compras')"
        p0?.execSQL(INSERT_CAT2)
        val INSERT_CAT3 = "INSERT INTO $CAT_TABLE_NAME values (3, 'compromissos ')"
        p0?.execSQL(INSERT_CAT3)
        val INSERT_CAT4 = "INSERT INTO $CAT_TABLE_NAME values (4, 'geral')"
        p0?.execSQL(INSERT_CAT4)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun inserirCategoria(categoria: Categoria): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_CATEGORIA, categoria.id_categoria)
        values.put(CAT_DESCRICAO, categoria.descricao)
        val result = db.insert(CAT_TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun inserirLista(lista: Lista): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_LISTA, lista.id_lista)
        values.put(LIS_NOME, lista.nome)
        values.put(LIS_DESCRICAO, lista.descricao)
        values.put(LIS_URGENTE, lista.urgente)
        values.put(LIS_CATEGORIA, lista.categoria)
        val result = db.insert(LIS_TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun inserirItem(item: Item): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(ITEM_DESCRICAO, item.descricao)
        values.put(ITEM_FLAG, item.flag)
        values.put(ITEM_LISTA, item.lista)
        val result = db.insert(ITEM_TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun atualizarCategoria(categoria: Categoria): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_CATEGORIA, categoria.id_categoria)
        values.put(CAT_DESCRICAO, categoria.descricao)
        val result = db.update(CAT_TABLE_NAME, values, "$ID_CATEGORIA=?", arrayOf(categoria.id_categoria.toString()))
        db.close()
        return result
    }

    fun atualizarLista(lista: Lista): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_LISTA, lista.id_lista)
        values.put(LIS_NOME, lista.nome)
        values.put(LIS_DESCRICAO, lista.descricao)
        values.put(LIS_URGENTE, lista.urgente)
        values.put(LIS_CATEGORIA, lista.categoria)
        val result = db.update(LIS_TABLE_NAME, values, "$ID_LISTA=?", arrayOf(lista.id_lista.toString()))
        db.close()
        return result
    }

    fun atualizarListaUrgente(id: Int?, urgente: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_LISTA, id)
        values.put(LIS_URGENTE, urgente)
        val result = db.update(LIS_TABLE_NAME, values, "$ID_LISTA=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun atualizarItemFlag(id: Int?, flag: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, id)
        values.put(ITEM_FLAG, flag)
        val result = db.update(ITEM_TABLE_NAME, values, "$ID_ITEM=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun atualizarItem(item: Item): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(ITEM_DESCRICAO, item.descricao)
        values.put(ITEM_FLAG, item.flag)
        values.put(ITEM_LISTA, item.lista)
        val result = db.update(ITEM_TABLE_NAME, values, "$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }

    fun apagarCategoria(categoria: Categoria): Int {
        val db = this.writableDatabase
        val result = db.delete(CAT_TABLE_NAME,"$ID_CATEGORIA=?", arrayOf(categoria.id_categoria.toString()))
        db.close()
        return result
    }

    fun apagarLista(lista: Lista): Int {
        val db = this.writableDatabase
        val result = db.delete(LIS_TABLE_NAME,"$ID_LISTA=?", arrayOf(lista.id_lista.toString()))
        db.close()
        return result
    }

    fun apagarItemPorIdLista(lista: Lista): Int {
        val db = this.writableDatabase
        val result = db.delete(ITEM_TABLE_NAME,"$ITEM_LISTA=?", arrayOf(lista.id_lista.toString()))
        db.close()
        return result
    }

    fun apagarItem(item: Item): Int {
        val db = this.writableDatabase
        val result = db.delete(ITEM_TABLE_NAME,"$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }

    fun listarCategorias():ArrayList<Categoria> {
        val listaCategorias = ArrayList<Categoria>()
        val query = "SELECT * FROM $CAT_TABLE_NAME ORDER BY $ID_CATEGORIA"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            var c = Categoria (cursor.getInt(0),
                                cursor.getString(1)
                                )
            listaCategorias.add(c)
        }
        cursor.close()
        db.close()
        return listaCategorias
    }

    fun listarListas():ArrayList<Lista> {
        val listaListas = ArrayList<Lista>()
        val query = "SELECT * FROM $LIS_TABLE_NAME ORDER BY $LIS_NOME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            var c = Lista (cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
            )
            listaListas.add(c)
        }
        cursor.close()
        db.close()
        return listaListas
    }

    fun listarListasPorCategoria(categoria: Categoria):ArrayList<Lista> {
        var id_categoria = categoria.id_categoria.toString()
        val listaListas = ArrayList<Lista>()
        val query = "SELECT * FROM $LIS_TABLE_NAME WHERE $LIS_CATEGORIA = $id_categoria"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            var c = Lista (cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4)
            )
            listaListas.add(c)
        }
        cursor.close()
        db.close()
        return listaListas
    }

    fun listarItensPorIdLista(id_lista: String):ArrayList<Item> {
        val listaItens = ArrayList<Item>()
        val query = "SELECT * FROM $ITEM_TABLE_NAME WHERE $ITEM_LISTA = $id_lista ORDER BY $ITEM_DESCRICAO"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            var c = Item (cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
            )
            listaItens.add(c)
        }
        cursor.close()
        db.close()
        return listaItens
    }

    fun listarItens():ArrayList<Item> {
        val listaItens = ArrayList<Item>()
        val query = "SELECT * FROM $ITEM_TABLE_NAME ORDER BY $ITEM_DESCRICAO"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            var c = Item (cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
            )
            listaItens.add(c)
        }
        cursor.close()
        db.close()
        return listaItens
    }

    fun buscarListaPorId(id: String):Lista {
        var lista= Lista()
        var query = "SELECT * FROM $LIS_TABLE_NAME WHERE $ID_LISTA = '$id'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            lista = Lista (cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4)
            )
        }
        cursor.close()
        db.close()
        return lista
    }


    fun pesquisarListasPorNome(id: String?, nome: String):Boolean {
        var existe = false
        var query = "SELECT * FROM $LIS_TABLE_NAME WHERE $LIS_NOME = '$nome'"
        if (id != null) {
            query += " AND $ID_LISTA <> $id"
        }
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
            existe = true
        }
        cursor.close()
        db.close()
        return existe
    }

    fun pesquisarItensPorDescricao(id: String?, descricao: String):Boolean {
        var existe = false
        var query = "SELECT * FROM $ITEM_TABLE_NAME WHERE $ITEM_DESCRICAO = '$descricao'"
        if (id != null) {
            query += " AND $ID_ITEM <> $id"
        }
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
            existe = true
        }
        cursor.close()
        db.close()
        return existe
    }
}