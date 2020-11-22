/**
 * Osztaly a Course objektumok kezelesehez
 */

package all;

import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CourseController {
    /**
     * A bemeno .doc kiterjesztesu fajlbol kinyeri a szöveget.
     * @param fileName a kivalasztott elozetes orarend fajl
     * @return egy WordExtractor objektum, ami az egesz beolvasott doc fajlbol kinyert szöveget tartalmazza
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
     * Inputkent kap egy WordExtractor objektumot, melyet String-ekbol allo listava alakit at.
     * Ezek utan az eredeti dokumentum fajl egy sora az ArrayList-nek egy eleme lesz.
     * @param extractor az a WordExtractor objektum, melyet konvertalni kell
     * @throws IOException dobodik, ha a BufferReader-nel barmilyen IO hiba keletkezik
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
     * Az elozetes orarend minden oldala tartalmaz olyan sorokat, melyek nem egy tantargyat kepviselnek (fejlec, lablec, stb.)
     * Ez a metodus eltavolit minden ilyen elemet a listabol, a kimenete a vegleges lista, ami mar csak targykbol all
     * @param inputList a "docToArrayList" altal kapott String-ek listaja
     */
    public static void removeUnnecessaryLines(List<String> inputList) {
        for (int i = 0; i < inputList.size() - 1; i++) {

            if (inputList.get(i).contains(".lap") ||
                    inputList.get(i).contains("Ti.Tantargy") ||
                    inputList.get(i).contains("tanszek orai") ||
                    inputList.get(i).contains("─────") ||
                    inputList.get(i).isEmpty() ||
                    inputList.get(i).length() < 90 //minden tenylegesen tantargyrol szolo oszlop fix hosszusagu, ha nem eri el ezt a karakterszamot a sor akkor törölni kell
            ) {
                inputList.remove(i);
                i--;
            } else
                continue;
        }
    }

    /**
     * Targyak kozott keres az alapjan, hogy a JComboBox-ban melyik elemet jeloltuk ki
     * @param courses a Course objektumokat tartalmazo lista
     * @param transform
     * @param searchValue a felhasznalo altal beirt String, amire keres
     * @return Course objektumokbol allo lista, amiben az összes talalat szerepel
     */
    public static <T> List<Course> searchList(List<Course> courses, Function<Course, T> transform, String searchValue) {
        List<Course> result = courses.stream().filter(item -> transform.apply(item).toString().toLowerCase().contains(searchValue.toLowerCase())).collect(Collectors.toList());
        return result;
    }


    /**
     * A korabban keszült listabol keszit Course objektumokat.
     * Mivel minden oszlop fix hosszusagu, igy a String-ekbol egyszeruen ki lehet nyerni az adatokat
     * @param list egy olyan String-ekbol allo lista, ami mar nem tartalmaz felesleges karaktereket, sorokat, minden eleme egy-egy tantargy
     * @return a Course list
     */
    public static List<Course> stringListToCourseList(List<String> list) {
        int felev;
        List<Course> courses = new ArrayList<>();

        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();

        for (String targy : list) {
            try {
                String[] courseString = {
                        targy.substring(2, 4).trim(), //0. oszlop - felev
                        targy.substring(4, 7).trim(), //1. oszlop - kar
                        targy.substring(8, 10).trim(), //2. oszlop - szki
                        targy.substring(10, 12).trim(), //3. oszlop - ti
                        targy.substring(16, 52).trim(), //4. oszlop - tantargy
                        targy.substring(52, 55).trim(), //5. oszlop - tanszek
                        targy.substring(56, 62).trim(), //6. oszlop - tanar
                        targy.substring(63, 69).trim().replaceAll("\\s+", ""), //7. oszlop - csoport
                        targy.substring(70, 72).trim(), //8. oszlop - fo
                        targy.substring(76, 78).trim(), //9. oszlop - nap
                        targy.substring(79, 81).trim(), //10. oszlop - kezdes
                        targy.substring(82, 84).trim(), //11. oszlop - hossz
                        targy.substring(85, 87).trim(), //12. oszlop - tipus
                        targy.substring(87, 93).trim() //13. oszlop - terem
                };

                try {
                    felev = Integer.parseInt(courseString[0]);
                } catch (NumberFormatException e) {
                    felev = 0;
                }

                Course tempCourse = new Course();
                tempCourse.setFelev(felev);
                tempCourse.setKar(courseString[1]);
                tempCourse.setSzki(courseString[2]);
                tempCourse.setTi(courseString[3]);
                tempCourse.setTantargy(courseString[4]);
                tempCourse.setTanszek(courseString[5]);
                tempCourse.setEloado(courseString[6]);
                tempCourse.setCsoport(courseString[7]);
                tempCourse.setFo(Integer.parseInt(courseString[8]));
                tempCourse.setNap(courseString[9]);
                tempCourse.setKezdes(Integer.parseInt(courseString[10]));
                tempCourse.setHossz(Integer.parseInt(courseString[11]));
                tempCourse.setTipus(courseString[12]);
                tempCourse.setTerem(courseString[13]);

                courseDatabaseManager.create(tempCourse);
            } catch (NullPointerException e) {
                continue;
            }
        }
        return courses;
    }

    /**
     *
     * @param inputList
     * @param selectedIndex
     * @param seachValue
     * @return
     */
    public static List<Course> searchCourse(List<Course> inputList, int selectedIndex, String seachValue) {
        List<Course> list = null;

        switch (selectedIndex) {
            case 0:
                list = CourseController.searchList(inputList, Course::getFelev, seachValue);
                break;
            case 1:
                list = CourseController.searchList(inputList, Course::getKar, seachValue);
                break;
            case 2:
                list = CourseController.searchList(inputList, Course::getSzki, seachValue);
                break;
            case 3:
                list = CourseController.searchList(inputList, Course::getTi, seachValue);
                break;
            case 4:
                list = CourseController.searchList(inputList, Course::getTantargy, seachValue);
                break;
            case 5:
                list = CourseController.searchList(inputList, Course::getTanszek, seachValue);
                break;
            case 6:
                list = CourseController.searchList(inputList, Course::getEloado, seachValue);
                break;
            case 7:
                list = CourseController.searchList(inputList, Course::getCsoport, seachValue);
                break;
            case 8:
                list = CourseController.searchList(inputList, Course::getFo, seachValue);
                break;
            case 9:
                list = CourseController.searchList(inputList, Course::getKezdes, seachValue);
                break;
            case 10:
                list = CourseController.searchList(inputList, Course::getHossz, seachValue);
                break;
            case 11:
                list = CourseController.searchList(inputList, Course::getTerem, seachValue);
                break;
            case 12:
                list = CourseController.searchList(inputList, Course::getNap, seachValue);
                break;
            case 13:
                list = CourseController.searchList(inputList, Course::getTipus, seachValue);
                break;
        }

        return list;
    }

    /**
     * Egy targy tobbszor szerepel a listaban, ha egy adott targy tobb tankornek is van.
     * Az orarend generalasahoz csak egy orara van szukseg, ez a metodus nem jeleniti meg a tablazatban
     * a duplikalt orakat, de az adatbazisbol nem torli.
     * @param courseList
     */
    public static void removeDuplicateCourses(List<Course> courseList) {
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = i + 1; j < courseList.size(); j++) {
                if(courseList.get(j).getTantargy().equals(courseList.get(i).getTantargy())
                        && courseList.get(j).getKezdes() == courseList.get(i).getKezdes()
                        && courseList.get(j).getTipus().equals(courseList.get(i).getTipus())
                        && courseList.get(j).getTerem().equals(courseList.get(i).getTerem())) {
                    courseList.remove(j);
                    j--;
                }
            }
        }
    }
}