/**
 *
 */

package all;

import javax.swing.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.hwpf.extractor.WordExtractor;
import java.io.*;
import java.util.ArrayList;

public class CourseController {
    /**
     * Tárgyak között keres az alapján, hogy a JComboBox-ban melyik elemet jelöltük ki
     * @param courses a Course objektumokat tartalmazó lista
     * @param transform the function that will transform this whole thing
     * @param searchValue a felhasználó által beírt String, amire keres
     * @return egy lista, amiben az összes találat szerepel
     */
    public static <T> List<Course> searchList(List<Course> courses, Function<Course, T> transform, String searchValue) {
        List<Course> result = courses.stream().filter(item -> transform.apply(item).toString().toLowerCase().contains(searchValue.toLowerCase())).collect(Collectors.toList());
        return result;
    }



    /**
     * A bemenő .doc kiterjesztésű fájlból kinyeri a szöveget.
     * @param fileName a kiválasztott előzetes órarend fájl
     * @return egy WordExtractor objektum, ami az egész beolvasott doc fájlból kinyert szöveget tartalmazza
     */
    public static WordExtractor readDoc(String fileName) {
        String filePath = fileName;
        FileInputStream fileInputStream;

        WordExtractor extractor = null;
        try {
            fileInputStream = new FileInputStream(new File(filePath));
            extractor = new WordExtractor(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractor;
    }

    /**
     * Inputként kap egy WordExtractor objektumot, melyet String-ekből álló listává alakít át.
     * Ezek után az eredeti dokumentum fájl egy sora az ArrayList-nek egy eleme lesz.
     * @param extractor az a WordExtractor objektum, melyet konvertálni kell
     * @throws IOException dobódik, ha a BufferReader-nél bármilyen IO hiba keletkezik
     */
    public static List<String> docToArrayList(WordExtractor extractor) throws IOException {

        List<String> wordDoc = new ArrayList<>();
        BufferedReader reader;
        reader = new BufferedReader(new StringReader(extractor.getText()));

        String line = reader.readLine();
        while (line != null) {
            line = reader.readLine();
            wordDoc.add(line);

        }
        reader.close();
        return wordDoc;
    }

    /**
     * Az előzetes órarend minden oldala tartalmaz olyan sorokat, melyek nem egy tantárgyat képviselnek (fejléc, lábléc, stb.)
     * Ez a metódus eltávolít minden ilyen elemet a listából, végeredményül van egy "kész lista"
     * @param inputList a "docToArrayList" által kapott String-ek listája
     */
    public static void removeUnnecessaryLines(List<String> inputList) {
        for (int i = 0; i < inputList.size() - 1; i++) {

            if (inputList.get(i).contains(".lap") ||
                    inputList.get(i).contains("Ti.Tantárgy") ||
                    inputList.get(i).contains("tanszék órái") ||
                    inputList.get(i).contains("─────") ||
                    inputList.get(i).isEmpty() ||
                    inputList.get(i).length() < 90 //minden ténylegesen tantárgyról szóló oszlop fix hosszúságú, ha nem éri el ezt a karakterszámot a sor akkor törölni kell
            ){
                inputList.remove(i);
                i--;
            } else
                continue;
        }
    }

    /**
     * A korábban készült listából készít Course objektumokat.
     * Mivel minden oszlop fix hosszúságú, így a String-ekből egyszerűen létre lehet hozni a Course objektumokat tartalmazó listát
     * @param list egy olyan String-ekből álló lista, ami már nem tartalmaz felesleges karaktereket, sorokat, minden eleme egy-egy tantárgy
     * @return a Course list
     */
    public static List<Course> stringListToCourseList(List<String> list) {
        int felev;
        List<Course> courses = new ArrayList<>();

        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();

        for (String targy : list) {
            try {
                String[] egyTargy = {
                        targy.substring(2, 4).trim(), //0. oszlop - félév
                        targy.substring(4, 7).trim(), //1. oszlop - kar
                        targy.substring(8, 10).trim(), //2. oszlop - szki
                        targy.substring(10, 12).trim(), //3. oszlop - ti
                        targy.substring(16, 52).trim(), //4. oszlop - tantárgy
                        targy.substring(52, 55).trim(), //5. oszlop - tanszék
                        targy.substring(56, 62).trim(), //6. oszlop - tanár
                        targy.substring(63, 69).trim().replaceAll("\\s+", ""), //7. oszlop - csoport
                        targy.substring(70, 72).trim(), //8. oszlop - fő
                        targy.substring(76, 78).trim(), //9. oszlop - nap
                        targy.substring(79, 81).trim(), //10. oszlop - kezdés
                        targy.substring(82, 84).trim(), //11. oszlop - hossz
                        targy.substring(85, 87).trim(), //12. oszlop - tipus
                        targy.substring(87, 93).trim() //13. oszlop - terem
                };

                try {
                    felev = Integer.parseInt(egyTargy[0]);
                } catch (NumberFormatException e) {
                    felev = 0;
                }

                Course temp = new Course();
                temp.setFelev(felev);
                temp.setKar(egyTargy[1]);
                temp.setSzki(egyTargy[2]);
                temp.setTi(egyTargy[3]);
                temp.setTantargy(egyTargy[4]);
                temp.setTanszek(egyTargy[5]);
                temp.setEloado(egyTargy[6]);
                temp.setCsoport(egyTargy[7]);
                temp.setFo(Integer.parseInt(egyTargy[8]));
                temp.setNap(egyTargy[9]);
                temp.setKezdes(Integer.parseInt(egyTargy[10]));
                temp.setHossz(Integer.parseInt(egyTargy[11]));
                temp.setTipus(egyTargy[12]);
                temp.setTerem(egyTargy[13]);

                courseDatabaseManager.create(temp);
            } catch (NullPointerException e) {
                continue;
            }
        }
        return courses;
    }

    public static void createDatabase(List<Course> courseList) {
        try {
            CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
            for (Course c : courseList) {
                courseDatabaseManager.create(c);
            }
        } catch (NullPointerException e1) {
            e1.printStackTrace();
        }
    }
}