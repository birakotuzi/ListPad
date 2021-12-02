package br.edu.ifsp.scl.sdm.listpad.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Model.Item
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
        if(listasLista[position].urgente == 1){
            holder.urgenteVH.setImageResource(R.drawable.ic_baseline_error_24)
        } else {
            holder.urgenteVH.setImageResource(R.drawable.ic_baseline_error_outline_24)
        }
        holder.editarVH.setImageResource(R.drawable.ic_baseline_edit_24)
        holder.deletarVH.setImageResource(R.drawable.ic_baseline_delete_forever_24)
    }

    override fun getItemCount(): Int {
        return listasLista.size
    }

    inner class ListaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nomeVH = view.findViewById<TextView>(R.id.tVLisnome)
        val urgenteVH = view.findViewById<ImageView>(R.id.iVUrgente)
        val editarVH = view.findViewById<ImageView>(R.id.iVEditar)
        val deletarVH = view.findViewById<ImageView>(R.id.iVDeletar)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
            view.findViewById<ImageView>(R.id.iVUrgente).setOnClickListener {
                listener?.onImageClick(adapterPosition)
            }
            view.findViewById<ImageView>(R.id.iVEditar).setOnClickListener {
                listener?.onImageEditClick(adapterPosition)
            }
            view.findViewById<ImageView>(R.id.iVDeletar).setOnClickListener {
                listener?.onImageDeleteClick(adapterPosition)
            }
        }
    }

    interface ListaListener {
        fun onItemClick(pos: Int)
        fun onImageClick(pos: Int)
        fun onImageEditClick(pos: Int)
        fun onImageDeleteClick(pos: Int)
    }

}