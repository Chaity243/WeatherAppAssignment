package com.msil.sharedmobility.subscribe.presentation.ui.home.adaptor

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msil.sharedmobility.subscribe.R
import kotlinx.android.synthetic.main.city_list_item_grid.view.*
import kotlinx.android.synthetic.main.city_list_item_row.view.*
import android.R.attr.top
import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.left
import android.graphics.Rect
import android.widget.TextView


public interface OnCityClickListeners {
    public fun onCitySelected(view: View, city:String,position: Int)
}

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space

    }
}

class OtherCityListAdaptor(val items: Array<String>, val context: Context,private val cityClickListeners: OnCityClickListeners):RecyclerView.Adapter<OtherCityListAdaptor.ListHolder>(){
    class ListHolder(v:View) :RecyclerView.ViewHolder(v) {
        val cityText: TextView = v.cityText
        val view = v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        return ListHolder(LayoutInflater.from(context).inflate(R.layout.city_list_item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.cityText.text = items.get(position)
        holder.view.setOnClickListener{
            cityClickListeners.onCitySelected(it,items[position],position)
        }
    }

}

class PopularCityGridAdaptor(val items: Array<String>, val context: Context, private val cityClickListeners: OnCityClickListeners):RecyclerView.Adapter<PopularCityGridAdaptor.GridHolder>(){

    var positionSelected = -1
    class GridHolder(v:View) :RecyclerView.ViewHolder(v) {

        val city_text = v.city_text
        val city_logo = v.city_logo
        val view =v;

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
        return GridHolder(LayoutInflater.from(context).inflate(R.layout.city_list_item_grid, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setPosition(position: Int){
        if(position == -1)
            return
        positionSelected = position
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: GridHolder, position: Int) {
        holder.city_text.text = items[position]
        //holder.city_logo
        //enable disable text
        holder.city_text.isEnabled = positionSelected==position
        holder.view.setOnClickListener{

            //holder.city_text.isEnabled = true
            positionSelected = holder.adapterPosition
            notifyDataSetChanged()
            cityClickListeners.onCitySelected(it,items[position],position)
        }
    }




}