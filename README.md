# LachsApp

LachsApp ist eine Android-Anwendung zur Verwaltung und Berechnung von Getränkedaten. Die App ermöglicht es, Getränke hinzuzufügen, Durchschnittswerte zu berechnen und dynamische Zähler zu erstellen.

## Funktionen

- **Hinzufügen von Getränken**: Eingabe von drei Werten und dem Getränkenamen, um einen Durchschnittswert zu berechnen.
- **Berechnung des Durchschnitts**: Berechnung des Durchschnitts der drei eingegebenen Werte und Anzeige des Ergebnisses.
- **Dynamische Zähler**: Hinzufügen von dynamischen Zählern, um verschiedene Werte zu zählen und zu verwalten.
- **Speicherung der Daten**: Speicherung der eingegebenen Daten und Zähler in `SharedPreferences`, um die Daten bei erneutem Starten der App wiederherzustellen.

## Installation

1. **Clone das Repository**:
    ```sh
    git clone https://github.com/username/LachsApp.git
    ```
2. **Öffne das Projekt in IntelliJ IDEA**:
    - Wähle `File > Open` und navigiere zum geklonten Repository.
3. **Baue das Projekt**:
    - Klicke auf `Build > Rebuild Project` in IntelliJ IDEA.
4. **Starte die App**:
    - Klicke auf das grüne Play-Symbol oder wähle `Run > Run 'app'`.

## Verwendung

1. **Getränk hinzufügen**:
    - Fülle die Felder `editTextNumber1`, `editTextNumber2`, `editTextNumber3` und `editTextDrink` aus.
    - Klicke auf den `calculateButton`, um den Durchschnitt zu berechnen und das Getränk zur Liste hinzuzufügen.

2. **Dynamischen Zähler hinzufügen**:
    - Klicke auf den `addCounterButton`.
    - Gib im erscheinenden Dialog einen Namen für den Zähler ein und bestätige.

3. **Daten löschen**:
    - Klicke auf den `clearButton`, um alle gespeicherten Getränke zu löschen.

## Projektstruktur

```plaintext
LachsApp/
├── .gitignore
├── README.md
├── LICENSE
├── build.gradle
├── settings.gradle
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── app/
    ├── build.gradle
    ├── src/
    │   ├── main/
    │   │   ├── AndroidManifest.xml
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── example/
    │   │   │           └── lachsapp/
    │   │   │               ├── MainActivity.kt
    │   │   │               ├── DrinkAdapter.kt
    │   │   └── res/
    │   │       ├── layout/
    │   │       │   ├── activity_main.xml
    │   │       │   └── list_item.xml
    │   │       ├── values/
    │   │       │   ├── strings.xml
    │   │       │   ├── colors.xml
    │   │       │   ├── styles.xml
    │   │       │   ├── dimens.xml
    │   │       │   └── themes.xml
    │   │       └── drawable/
    │   │           └── (deine Bilddateien und Zeichnungen)
    └── proguard-rules.pro
```
## Danksagungen
* Dank an alle, die zur Android-Entwickler-Community beitragen
* Besonderer Dank an die Autoren von Bibliotheken und Tools, die in diesem Projekt verwendet wurden

## Quellcode
MainActivity.kt
```kotlin
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
    private val counterList = mutableListOf<CounterData>()

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
        restoreCounters()

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
                saveCounters()
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
    private fun addCounter(counterName: String) {
        val counterView = layoutInflater.inflate(R.layout.counter_item, counterContainer, false)
        val counterText = counterView.findViewById<TextView>(R.id.counterText)
        val incrementButton = counterView.findViewById<Button>(R.id.incrementButton)
        val decrementButton = counterView.findViewById<Button>(R.id.decrementButton)

        counterText.text = counterName
        var count = 0

        incrementButton.setOnClickListener {
            count++
            counterText.text = "$counterName: $count"
        }

        decrementButton.setOnClickListener {
            if (count > 0) {
                count--
                counterText.text = "$counterName: $count"
            }
        }

        counterContainer.addView(counterView)
        counterList.add(CounterData(counterName, count))
    }

    // Funktion zum Speichern der Getränkedaten in SharedPreferences
    private fun saveData() {
        val editor = sharedPreferences.edit()
        val jsonData = gson.toJson(dataList)
        editor.putString("dataList", jsonData)
        editor.apply()
    }

    // Funktion zum Wiederherstellen der Getränkedaten aus SharedPreferences
    private fun restoreData() {
        val jsonData = sharedPreferences.getString("dataList", null)
        if (!jsonData.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Data>>() {}.type
            val restoredData = gson.fromJson<MutableList<Data>>(jsonData, type)
            dataList.clear()
            dataList.addAll(restoredData)
        }
    }

    // Funktion zum Löschen aller Getränkedaten und Leeren der Anzeige
    private fun clearData() {
        dataList.clear()
        listAdapter.notifyDataSetChanged()
        textViewAverage.text = ""
        showSnackbar("Alle gespeicherten Getränke wurden gelöscht!")
        saveData()
    }

    // Funktion zum Speichern der Counter-Daten in SharedPreferences
    private fun saveCounters() {
        val editor = sharedPreferences.edit()
        val jsonData = gson.toJson(counterList)
        editor.putString("counterList", jsonData)
        editor.apply()
    }

    // Funktion zum Wiederherstellen der Counter-Daten aus SharedPreferences
    private fun restoreCounters() {
        val jsonData = sharedPreferences.getString("counterList", null)
        if (!jsonData.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<CounterData>>() {}.type
            val restoredCounters = gson.fromJson<MutableList<CounterData>>(jsonData, type)
            counterList.clear()
            counterList.addAll(restoredCounters)
            counterList.forEach { counterData ->
                addCounter(counterData.name)
            }
        }
    }

    // Funktion zum Anzeigen eines Snackbar mit einer Nachricht
    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    // Datenklasse für Getränke
    data class Data(
        val drinkName: String,
        val field1: String,
        val field2: String,
        val field3: String,
        val average: String // Durchschnittswert hinzufügen
    )

    // Datenklasse für Counter
    data class CounterData(
        val name: String,
        val count: Int
    )
}
```
**DrinkAdapter.kt**
```kotlin
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
```
**activity_main.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/editTextNumber1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Erster Wert" />

    <EditText
        android:id="@+id/editTextNumber2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Zweiter Wert" />

    <EditText
        android:id="@+id/editTextNumber3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Dritter Wert" />

    <EditText
        android:id="@+id/editTextDrink"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Getränkename" />

    <Button
        android:id="@+id/calculateButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Berechnen" />

    <TextView
        android:id="@+id/textViewAverage"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Durchschnitt: "
        android:textSize="18sp" />

    <Button
        android:id="@+id/clearButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Löschen" />

    <ListView
        android:id="@+id/getraenkeListe"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <Button
        android:id="@+id/addCounterButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Counter hinzufügen" />

    <LinearLayout
        android:id="@+id/counterContainer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical" />
</LinearLayout>
```

**list_item.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:orientation="horizontal"
              android:padding="8dp" >

    <TextView
            android:id="@+id/drinkName"
            android:layout_width="50dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@string/getraenk"
            android:textSize="18sp"
            android:padding="4dp" />

    <TextView
            android:id="@+id/field1"
            android:layout_width="50dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@string/f"
            android:textSize="18sp"
            android:padding="4dp" />

    <TextView
            android:id="@+id/field2"
            android:layout_width="50dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@string/d"
            android:textSize="18sp"
            android:padding="4dp" />

    <TextView
            android:id="@+id/field3"
            android:layout_width="50dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="@string/n"
            android:textSize="18sp"
            android:padding="4dp" />

    <TextView
            android:id="@+id/averageDrink"
            android:layout_width="50dp"
            android:layout_height="wrap_content"
            android:text="@string/durchschnitt"
            android:textSize="18sp"
            android:padding="4dp"
            android:visibility="gone" />

</LinearLayout>
```