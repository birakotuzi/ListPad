package br.edu.ifsp.scl.sdm.listpad.Data

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.listpad.Model.Item
import br.edu.ifsp.scl.sdm.listpad.R

class ItemAdapter (var itemsLista:ArrayList<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    var listener:ItemListener?=null

    fun setClickListener(listener:ItemListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_celula, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.descricaoItemVH.text = itemsLista[position].descricao
        if(itemsLista[position].flag == 1){
            holder.flagItemVH.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            holder.flagItemVH.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
    }

    override fun getItemCount(): Int {
        return itemsLista.size
    }

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val descricaoItemVH = view.findViewById<TextView>(R.id.tvItemDescricao)
        val flagItemVH = view.findViewById<ImageView>(R.id.ivItemFlag)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
            view.findViewById<ImageView>(R.id.ivItemFlag).setOnClickListener {
                listener?.onImageClick(adapterPosition)
            }
        }
    }

    interface ItemListener {
        fun onItemClick(pos: Int)
        fun onImageClick(pos: Int)
    }

}