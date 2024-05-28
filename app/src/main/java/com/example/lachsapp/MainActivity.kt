package com.example.lachsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var addCounterButton: Button
    private lateinit var counterContainer: LinearLayout
    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var editTextNumber3: EditText
    private lateinit var editTextDrink: EditText
    private lateinit var textViewAverage: TextView
    private lateinit var calculateButton: Button
    private lateinit var listView: ListView
    private lateinit var clearButton: Button
    private lateinit var listAdapter: DrinkAdapter

    private val dataList = mutableListOf<Data>()
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("LachsAppPrefs", Context.MODE_PRIVATE)

        // Initialisieren der Views
        addCounterButton = findViewById(R.id.addCounterButton)
        counterContainer = findViewById(R.id.counterContainer)
        editTextNumber1 = findViewById(R.id.editTextNumber1)
        editTextNumber2 = findViewById(R.id.editTextNumber2)
        editTextNumber3 = findViewById(R.id.editTextNumber3)
        editTextDrink = findViewById(R.id.editTextDrink)
        calculateButton = findViewById(R.id.calculateButton)
        listView = findViewById(R.id.getraenkeListe)
        clearButton = findViewById(R.id.clearButton)
        textViewAverage = findViewById(R.id.textViewAverage)

        // Daten wiederherstellen
        restoreData()

        // Adapter für die ListView erstellen und setzen
        listAdapter = DrinkAdapter(this, dataList)
        listView.adapter = listAdapter

        calculateButton.setOnClickListener {
            // Validierung und Hinzufügen der Daten zur Liste
            if (editTextDrink.text.isNullOrEmpty() || editTextNumber1.text.isNullOrEmpty() || editTextNumber2.text.isNullOrEmpty() || editTextNumber3.text.isNullOrEmpty()) {
                showSnackbar("Alle Felder müssen ausgefüllt sein!")
            } else {
                // Werte aus den EditText-Feldern auslesen und Durchschnitt berechnen
                val number1 = editTextNumber1.text.toString().toFloatOrNull() ?: 0f
                val number2 = editTextNumber2.text.toString().toFloatOrNull() ?: 0f
                val number3 = editTextNumber3.text.toString().toFloatOrNull() ?: 0f
                val average = (number1 + number2 + number3) / 3

                // Durchschnittswert anzeigen
                textViewAverage.text = "Durchschnitt: %.2f".format(average).replace('.', ',')

                // Daten zur Liste hinzufügen
                val data = Data(
                    editTextDrink.text.toString(),
                    editTextNumber1.text.toString(),
                    editTextNumber2.text.toString(),
                    editTextNumber3.text.toString(),
                    "%.2f".format(average).replace('.', ',') // Durchschnittswert hinzufügen
                )
                dataList.add(data)

                // Adapter aktualisieren
                listAdapter.notifyDataSetChanged()

                // Inhalte der Felder löschen
                editTextNumber1.setText("")
                editTextNumber2.setText("")
                editTextNumber3.setText("")
                editTextDrink.setText("")

                // Daten speichern
                saveData()
            }
        }

        clearButton.setOnClickListener {
            // Datenliste und Anzeige löschen
            clearData()
        }

        addCounterButton.setOnClickListener {
            // Dialog zum Hinzufügen eines Counters anzeigen
            showAddCounterDialog()
        }
    }

    // Funktion zum Anzeigen eines Dialogs zum Hinzufügen eines Counters
    private fun showAddCounterDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Neuen Counter hinzufügen")

        val input = EditText(this)
        input.hint = "Name des Counters"
        builder.setView(input)

        builder.setPositiveButton("Bestätigen") { dialog, _ ->
            val counterName = input.text.toString()
            if (counterName.isNotEmpty()) {
                addCounter(counterName)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Abbrechen") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    // Funktion zum Hinzufügen eines neuen Counters
    @SuppressLint("SetTextI18n")
    private fun addCounter(name: String) {
        val newCounterLayout = LinearLayout(this)
        newCounterLayout.orientation = LinearLayout.HORIZONTAL
        newCounterLayout.setPadding(16, 16, 16, 16)

        val counterName = TextView(this)
        counterName.text = name
        counterName.textSize = 18f

        val counterValue = TextView(this)
        counterValue.text = "0"
        counterValue.textSize = 18f
        counterValue.setPadding(16, 0, 16, 0)

        val incrementButton = Button(this)
        incrementButton.text = "+"
        incrementButton.setOnClickListener {
            val currentValue = counterValue.text.toString().toInt()
            counterValue.text = (currentValue + 1).toString()
        }

        val decrementButton = Button(this)
        decrementButton.text = "-"
        decrementButton.setOnClickListener {
            val currentValue = counterValue.text.toString().toInt()
            if (currentValue > 0) {
                counterValue.text = (currentValue - 1).toString()
            } else {
                showSnackbar("Ne.")
            }
        }

        val deleteButton = Button(this)
        deleteButton.text = "Löschen"
        deleteButton.setOnClickListener {
            counterContainer.removeView(newCounterLayout)
        }

        newCounterLayout.addView(counterName)
        newCounterLayout.addView(decrementButton)
        newCounterLayout.addView(counterValue)
        newCounterLayout.addView(incrementButton)
        newCounterLayout.addView(deleteButton)

        counterContainer.addView(newCounterLayout)
    }

    // Funktion zum Anzeigen der Datenliste
    private fun displayDataList() {
        listAdapter.notifyDataSetChanged()
    }

    // Funktion zum Anzeigen einer Snackbar mit einer Nachricht
    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    // Funktion zum Speichern der Daten in SharedPreferences
    private fun saveData() {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(dataList)
        editor.putString("dataList", json)
        editor.apply()
    }

    // Funktion zum Wiederherstellen der Daten aus SharedPreferences
    private fun restoreData() {
        val json = sharedPreferences.getString("dataList", null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Data>>() {}.type
            val restoredList: MutableList<Data> = gson.fromJson(json, type)
            dataList.clear()
            dataList.addAll(restoredList)
        }
    }

    // Funktion zum Löschen der Daten
    private fun clearData() {
        dataList.clear()
        saveData()
        listAdapter.notifyDataSetChanged()
        showSnackbar("Liste wurde gelöscht.")
    }

    // Datenklasse zur Speicherung der Daten für die Liste
    data class Data(
        val drinkName: String,
        val field1: String,
        val field2: String,
        val field3: String,
        var average: String
    )
}
