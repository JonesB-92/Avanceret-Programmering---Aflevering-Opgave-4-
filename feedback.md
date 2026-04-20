# Feedback til Jonas

Rigtig flot aflevering! Din implementering er både teknisk korrekt og viser en god forståelse for detaljerne i hash-tabeller med åben adressering.

### Positive observationer
- **Optimering i `put`:** Det er meget stærkt, at du holder styr på den første `DELETED`-plads (`firstDeleted`), du møder under din probing. Det gør din indsættelse mere effektiv, da du genbruger "huller" i tabellen uden at bryde søgestien.
- **Robust Hash-håndtering:** Du håndterer `Integer.MIN_VALUE` og negative hash-koder manuelt på en sikker måde. Det sikrer, at dit indeks altid er inden for arrayets grænser.
- **Dynamisk Resizing:** Din `rehash()`-logik er korrekt implementeret. Du fordobler kapaciteten ved en load factor på 0.5 og genindsætter kun de aktive elementer (springer `DELETED` over), hvilket er præcis efter bogen.
- **Læsbarhed:** Din brug af `for`-loops i stedet for komplekse `while`-konstruktioner gør koden meget læsbar, præcis som du selv nævner i din README.

### Småting til overvejelse
- **Hash-funktion (Tip):** Selvom din manuelle håndtering af negative tal fungerer fint, kan man i Java ofte bruge bitwise AND: `(key.hashCode() & 0x7fffffff) % table.length`. Det er en meget hurtig og robust standardmåde at tvinge et tal til at være positivt på.
- **Null-tjek i `get`:** I dit loop i `get` har du linjen `if (entry != null && entry != DELETED)`. Da du allerede har et `if (entry == null) { return null; }` lige ovenover, er `entry != null` tjekket teknisk set overflødigt, men det skader selvfølgelig ikke sikkerheden.

### Svar på dine spørgsmål i README
1. **Count i while-loops:** Du har helt ret – hvis du rehasher ved en load factor på 0.5 (og sikrer at tabellen aldrig bliver 100% fuld), vil linear probing altid finde en `null`-plads før eller siden, så en `count` er ikke strengt nødvendig for at undgå uendelige løkker. Det er dog en god "forsvarsmekanisme" at have, hvis man arbejder med tabeller, der kan blive meget fyldte.

Alt i alt en toppræstation!

Venlig hilsen,
Instruktøren