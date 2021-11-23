package br.edu.ifsp.scl.sdm.listpad.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Model.Lista
import br.edu.ifsp.scl.sdm.listpad.R

class ListaAdapter (val listasLista:ArrayList<Lista>): RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    var listener:ListaListener?=null

    fun setClickListener(listener:ListaListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaAdapter.ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_celula, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaAdapter.ListaViewHolder, position: Int) {
        holder.nomeVH.text = listasLista[position].nome
    }

    override fun getItemCount(): Int {
        return listasLista.size
    }

    inner class ListaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nomeVH = view.findViewById<TextView>(R.id.tVLisnome)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface ListaListener {
        fun onItemClick(pos: Int)
    }
}