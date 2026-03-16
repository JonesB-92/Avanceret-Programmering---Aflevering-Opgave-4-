package opgave04;

public class DictionaryOpenAddressing<K, V> implements Dictionary<K, V> {
    private Entry<K, V>[] table;
    private int size;
    private Entry<K, V> DELETED = new Entry<>(null, null);

    /**
     * Studies show you should maintain the
     * load factor under 0.5 for the open addressing scheme - (YL)
     */
    private static final double loadFactorThreshold = 0.5;
    private double threshold;

    public DictionaryOpenAddressing(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        table = (Entry<K, V>[]) new Entry[capacity];
        size = 0;
        threshold = table.length * loadFactorThreshold;
    }

    /**
     * 1. Kollision håndteres ved lineær probing.
     * Hashfunktionen er givet ved
     * ––> h(x) = x (mod N).
     */
    public int hashFunction(K k) {
        int index = k.hashCode();
        if (index == Integer.MIN_VALUE) {
            index = Integer.MAX_VALUE;
        } else if (index < 0) {
            index = -index;
        }
        return index % table.length;
    }


    @Override
//    public V get(K key) {
//        int index = hashFunction(key);
//
//        int count = 0;
//        while (table[index] != null && count < n) {
//            if (table[index] != DELETED && table[index].key.equals(key)) {
//                return table[index].value;
//            } else {
//                index = (index + 1) % n;
//                count++;
//            }
//        }
//        return null;
//    }

    public V get(K key) {
        int index = hashFunction(key);

        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[index];

            //Hurtig break, hvis vi finder null == key findes ikke.
            if (entry == null) {
                return null;
            }

            //Tjek om entry er den samme, hvis ikke null
            if (entry != null && entry != DELETED) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
            //ellers søg videre
            index = (index + 1) % table.length;

        }
        return null;
    }

    /**
     * Når du indsætter (put), skal du beregne index = key.hashCode() % N.
     * Hvis pladsen er optaget af en anden nøgle, skal du prøve (index + 1) % N, så (index + 2) % N og så videre,
     * indtil du finder en ledig plads eller den samme nøgle.
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Må ikke være null!");
        }
        //(Rehash funktionen tjekker selv, om der er behov for at udvide table)
        rehash();

        //HOLDE STYR PÅ FØRSTE DELETED vi møder, da den højst sandsynligt er tættere på entry's oprindelige plads,
        // end den første null, vi finder --> hurtigere søgning
        int firstDeleted = -1;
        int index = hashFunction(key);

        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[index];

            // 3 muligheder:
            //null, deleted eller optaget

            // Hvis null indsætter vi, da vi så kan være sikre på, at samme nøgle ikke findes længere fremme
            if (entry == null) {
                if (firstDeleted != -1) {
                    index = firstDeleted;
                }
                table[index] = new Entry<>(key, value);
                size++;
                return null;
            }
            //Hvis støder på deleted
            else if (entry == DELETED) {
                //gemmer vi index på den første deleted entry
                if (firstDeleted == -1) {
                    firstDeleted = index;
                }
                //Hvis ikke kan vi sikkert undgå nullpointer ved equals()
            } else if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            index = (index + 1) % table.length;
        }

        //Hvis ingen nulls har vi været hele array igennem og sikret os for,
        // at entry ikke allerede findes. Nu indsætter vi på deleted, hvis vi har.
        if (firstDeleted != -1) {
            table[firstDeleted] = new Entry<>(key, value);
            size++;
        }

        return null;
    }

    //* Ved sletning (remove) er det ikke nok bare at sætte pladsen til null, da det kan ødelægge "stien" for fremtidige søgninger.
    //* Du skal bruge en speciel "DELETED" markør (en "sentinel value") for at indikere, at en plads tidligere har været optaget.
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int index = hashFunction(key);

        //Start fra hashIndex og gå videre, så længe vi ikke har været hele table igennem.
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[index];

            //Hvis null findes key ikke i vores table
            if (entry == null) {
                return null;
            }
            //Undgå nullpointer først og tjek derefter om key er er den rigtige
            else if (entry != DELETED && entry.key.equals(key)) {
                V remValue = entry.value;
                table[index] = DELETED;
                size--;
                return remValue;
            }
            index = (index + 1) % table.length;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Implementér en metode til at udvide tabellen, når den når en vis fyldningsgrad
     */
    public void rehash() {
        //Tjekke behov
        if (size >= threshold) {
            Entry<K, V>[] oldTable = table;

            //Lave ny array med n*2 pladser
            table = (Entry<K, V>[]) new Entry[table.length * 2];
            size = 0;

            for (int i = 0; i < oldTable.length; i++) {
                Entry<K, V> entry = oldTable[i];

                //KUN interesseret i faktiske entries
                if (entry != null && entry != DELETED) {
                    // Hvis faktisk entry --> lægge ind i nye table
                    this.put(entry.key, entry.value);
                }
            }
            threshold = table.length * loadFactorThreshold;
        }
    }


    private static class Entry<K, V> {
        final K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
