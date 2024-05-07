
# Rapport

En layout skapades med tre olika EditText som var och en ansvarar för inmatning av data i tre olika kolumner
i databasen. Två knappar "Read" och "Write" läggs till, "Write" ska ansvara för att skriva de inmatade fälten
till databasen och "Read" ansvarar för att läsa in databasen och skriva ut innehållet i databastabellerna 
i en lista i en TextView.

```
private EditText titleInput, artistInput, yearInput;
private TextView listRecords;
private Button readButton, writeButton;

titleInput = findViewById(R.id.titleField);
artistInput = findViewById(R.id.artistField);
yearInput = findViewById(R.id.yearField);
readButton = findViewById(R.id.readButton);
writeButton = findViewById(R.id.writeButton);
```

För att skapa och hantera SQLLite databas så skapas två klasser. En klass som extendar SQLiteOpenHelper
och som skapar och hanterar databasen (DatabaseHelper) och en klass som specar upp hur databasens tabeller 
ska se ut (DatabaseTables).

För att lägga till data i databasen så lagras det som skrivs in i varje EditText till variabler där
används också trim() för att ta bort whitespace i början och slutet av det som skrivs in.

```
String title = titleInput.getText().toString().trim();
String artist = artistInput.getText().toString().trim();
String yearString = yearInput.getText().toString().trim();
```

Detta för att underlätta en kontroll så att fälten inte är tomma eller innehåller endast mellanslag:
```
if (title.isEmpty() || artist.isEmpty() || yearString.isEmpty()) {
    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
    return;
}
```
Den inmatade datan skickas in i databaseHelper klassen där den läggs in i en databstabell.
```
databaseHelper.addRecord(title, artist, year);
```

För att läsa datan används en metod som läser in datan i tabellen i en arraylist och genom en for loop
skrivs arrayen ut rad för rad i en TextView.

```
private List<Record> getRecords() {
    List<Record> records = new ArrayList<>();
    Cursor cursor = null;
    try {
        cursor = database.query(DatabaseTables.Record.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Record record = new Record(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_ARTIST)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_YEAR))
                );
                records.add(record);
            } while (cursor.moveToNext());
        }
    } catch (Exception e) {
        Log.d("Marazp", "Error reading records", e);
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
    Toast.makeText(MainActivity.this, "Successfully read database!", Toast.LENGTH_SHORT).show();
    return records;
}
```

Denna delen stötte jag på stora bekymmer i början då appen kraschade när jag anropade getRecords metoden.
Jag lade då till en undantags hanterare, i form av en try/catch funktion för att kunna felsöka. Det visade
sig då att felet berodde på att jag hade ändrat namnen på kolumnerna i DatabaseTables efter att 
databasen hade skapats första gången. Vilket resulterade i att några kolumner som refererades till inte fanns.
Det jag hade glömt var att ändra version på databasen. Så efter att versionsnumret på databasen ändrades
så fungerade till slut.


<img src="screenshot1.png" alt="Main Activity" style="width:300px;height:600px;"> <img src="screenshot2.png" alt="Second Activity" style="width:300px;height:600px;">
