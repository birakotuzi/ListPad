package br.edu.ifsp.scl.sdm.listpad.Data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Model.Categoria
import br.edu.ifsp.scl.sdm.listpad.R

class CategoriaAdapter(var categoriasLista:ArrayList<Categoria>):RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    var listener:CategoriaListener?=null

    fun setClickListener(listener:CategoriaListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriaAdapter.CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoria_celula, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaAdapter.CategoriaViewHolder, position: Int) {
        holder.descricaoVH.text = categoriasLista[position].descricao
    }

    override fun getItemCount(): Int {
        return categoriasLista.size
    }

    inner class CategoriaViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val descricaoVH = view.findViewById<TextView>(R.id.descricao)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface CategoriaListener {
        fun onItemClick(pos: Int)
    }
}