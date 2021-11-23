package br.edu.ifsp.scl.sdm.listpad.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.R

class ItemAdapter (val itensLista:ArrayList<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    var listener:ItemListener?=null

    fun setClickListener(listener:ItemListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_celula, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.descricaoVH.text = itensLista[position].descricao
    }

    override fun getItemCount(): Int {
        return itensLista.size
    }

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val descricaoVH = view.findViewById<TextView>(R.id.tVItemDescricao)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface ItemListener {
        fun onItemClick(pos: Int)
    }
}