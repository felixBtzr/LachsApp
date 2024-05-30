package com.example.lachsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

// Adapterklasse, die die Daten in der ListView darstellt
class DrinkAdapter(private val context: Context, private val dataList: MutableList<MainActivity.Data>) : BaseAdapter() {

    // Gibt die Anzahl der Elemente in der Liste zurück
    override fun getCount(): Int {
        return dataList.size
    }

    // Gibt das Element an einer bestimmten Position zurück
    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    // Gibt die ID des Elements an einer bestimmten Position zurück
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Erstellt die Ansicht für jedes Element in der ListView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        // Wenn convertView null ist, wird eine neue Ansicht erstellt
        if (convertView == null) {
            // Inflates (lädt) das Layout für ein Listenelement
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            // Erstellt einen neuen ViewHolder
            viewHolder = ViewHolder(view)
            // Setzt den ViewHolder als Tag der Ansicht, um später darauf zugreifen zu können
            view.tag = viewHolder
        } else {
            // Wiederverwendet eine bestehende Ansicht
            view = convertView
            // Holt den ViewHolder aus der wiederverwendeten Ansicht
            viewHolder = view.tag as ViewHolder
        }

        // Holt die Daten für das aktuelle Listenelement
        val data = dataList[position]
        // Setzt die Texte der TextViews mit den Daten
        viewHolder.drinkName.text = data.drinkName
        viewHolder.field1.text = data.field1
        viewHolder.field2.text = data.field2
        viewHolder.field3.text = data.field3

        // Prüft, ob ein Durchschnittswert vorhanden ist, und zeigt ihn an, wenn ja
        if (data.average.isNotEmpty()) {
            viewHolder.averageDrink.visibility = View.VISIBLE
            viewHolder.averageDrink.text = data.average
        } else {
            viewHolder.averageDrink.visibility = View.GONE
        }

        // Gibt die fertige Ansicht zurück
        return view
    }

    // ViewHolder-Klasse zur effizienten Wiederverwendung von Views
    private class ViewHolder(view: View) {
        // Deklariert die TextViews, die die Daten anzeigen
        val drinkName: TextView = view.findViewById(R.id.drinkName)
        val field1: TextView = view.findViewById(R.id.field1)
        val field2: TextView = view.findViewById(R.id.field2)
        val field3: TextView = view.findViewById(R.id.field3)
        val averageDrink: TextView = view.findViewById(R.id.averageDrink)
    }
}
