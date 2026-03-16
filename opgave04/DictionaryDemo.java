package opgave04;

public class DictionaryDemo {

    public static void main(String[] args) {
        Dictionary<Integer, String> dictionary = new DictionaryOpenAddressing<>(13);

        System.out.println(dictionary.isEmpty());
        System.out.println(dictionary.size());
        System.out.println("-----------------");

        dictionary.put(8, "hans");
        dictionary.put(3, "viggo");
        System.out.println(dictionary.isEmpty());
        System.out.println(dictionary.size());
        System.out.println(dictionary.get(8));

        System.out.println("-----------------");
        dictionary.put(7, "bent");
        dictionary.put(2, "lene");
        System.out.println(dictionary.isEmpty());
        System.out.println(dictionary.size());
        System.out.println("-----------------");

        System.out.println(dictionary.remove(3));
        System.out.println(dictionary.get(3));
        System.out.println(dictionary.size());


        System.out.println("-----------------");
        System.out.println(dictionary.put(8, "Viggo"));
        System.out.println(dictionary.size());
        System.out.println(dictionary.get(8));


    }

}
