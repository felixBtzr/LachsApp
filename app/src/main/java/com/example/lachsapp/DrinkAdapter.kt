package com.example.lachsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DrinkAdapter(private val context: Context, private val dataList: MutableList<MainActivity.Data>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val data = dataList[position]
        viewHolder.drinkName.text = data.drinkName
        viewHolder.field1.text = data.field1
        viewHolder.field2.text = data.field2
        viewHolder.field3.text = data.field3

        // Prüfe, ob ein Durchschnittswert vorhanden ist, und zeige ihn an, wenn ja
        if (data.average.isNotEmpty()) {
            viewHolder.averageDrink.visibility = View.VISIBLE
            viewHolder.averageDrink.text = data.average
        } else {
            viewHolder.averageDrink.visibility = View.GONE
        }

        return view
    }


    private class ViewHolder(view: View) {

        val drinkName: TextView = view.findViewById(R.id.drinkName)
        val field1: TextView = view.findViewById(R.id.field1)
        val field2: TextView = view.findViewById(R.id.field2)
        val field3: TextView = view.findViewById(R.id.field3)
        val averageDrink: TextView = view.findViewById(R.id.averageDrink)
    }
}
