package br.edu.ifsp.scl.sdm.listpad.Model

import java.io.Serializable

class Lista  (var id_lista: Int? = null,
              var nome: String = "",
              var descricao: String = "",
              var urgente: Int? = null,
              var categoria: Int? = null
): Serializable