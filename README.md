# LachsApp

LachsApp ist eine Android-Anwendung zur Verwaltung und Berechnung von Getränkedaten. Die App ermöglicht es, Getränke hinzuzufügen, Durchschnittswerte zu berechnen und dynamische Zähler zu erstellen.

## Funktionen

- **Hinzufügen von Getränken**: Eingabe von drei Werten und dem Getränkenamen, um einen Durchschnittswert zu berechnen.
- **Berechnung des Durchschnitts**: Berechnung des Durchschnitts der drei eingegebenen Werte und Anzeige des Ergebnisses.
- **Dynamische Zähler**: Hinzufügen von dynamischen Zählern, um verschiedene Werte zu zählen und zu verwalten.
- **Speicherung der Daten**: Speicherung der eingegebenen Daten und Zähler in `SharedPreferences`, um die Daten bei erneutem Starten der App wiederherzustellen.

## Installation

1. **Repository klonen**:
    ```sh
    git clone https://github.com/felix.Btzr/LachsApp.git
    ```
2. **Projekt in IntelliJ IDEA öffnen**:
   - Wähle `File > Open` und navigiere zum geklonten Repository.
3. **Projekt bauen**:
   - Klicke auf `Build > Rebuild Project` in IntelliJ IDEA.
4. **App starten**:
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
├── app/
│   ├── libs/
│   ├── src/
│   │   ├── androidTest/
│   │   │   └── java/
│   │   │       └── com.example.lachsapp/
│   │   │           └── ExampleInstrumentedTest.kt
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com.example.lachsapp/
│   │   │   │       ├── ui.theme/
│   │   │   │       │   ├── Color.kt
│   │   │   │       │   ├── Theme.kt
│   │   │   │       │   └── Type.kt
│   │   │   │       ├── DrinkAdapter.kt
│   │   │   │       ├── MainActivity.kt
│   │   │   │       ├── Data.kt
│   │   │   │       └── CounterData.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_launcher_background.xml
│   │   │   │   │   ├── ic_launcher_foreground.xml
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   └── list_item.xml
│   │   │   │   ├── mipmap-anydpi-v26/
│   │   │   │   │   ├── ic_launcher.xml
│   │   │   │   │   ├── ic_launcher_round.xml
│   │   │   │   ├── mipmap-hdpi/
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   ├── ic_launcher_round.webp
│   │   │   │   │   └── lachs.png
│   │   │   │   ├── mipmap-mdpi/
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   ├── ic_launcher_round.webp
│   │   │   │   │   └── lachs.png
│   │   │   │   ├── mipmap-xhdpi/
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   ├── ic_launcher_round.webp
│   │   │   │   │   └── lachs.png
│   │   │   │   ├── mipmap-xxhdpi/
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   ├── ic_launcher_round.webp
│   │   │   │   │   └── lachs.png
│   │   │   │   ├── mipmap-xxxhdpi/
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   ├── ic_launcher_round.webp
│   │   │   │   │   └── lachs.png
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── ids.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   └── xml/
│   │   │   │       ├── backup_rules.xml
│   │   │   │       └── data_extraction_rules.xml
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── proguard-rules.pro
│   │   ├── test/
│   │   │   └── java/
│   │   │       └── com.example.lachsapp/
│   │   │           └── ExampleUnitTest.kt
│   ├── build.gradle.kts
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md
