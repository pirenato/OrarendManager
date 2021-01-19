/**
 * Osztaly a Course objektumok kezelesehez
 */

package coursemanagement;

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
    public static WordExtractor readDocFile(String fileName) {
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
        List<String> wordDoc = new ArrayList<String>();
        BufferedReader reader;
        reader = new BufferedReader(new StringReader(extractor.getText()));

        String line = reader.readLine();
        System.out.println("This is the first line niggaz: \n" + line);
        while (line != null) {
            line = reader.readLine();
            if(line == null || line.contains(".lap") || line.contains("Ti.Tantargy") || line.contains("tanszek orai") || line.contains("─────")  || line.length() < 93)
                continue; //a program nem adja hozza a nem tantargyakat tartalmazo sorokat
            else
                wordDoc.add(line);
        }
        reader.close();
        return wordDoc;
    }

    /**
     * A korabban keszült listabol keszit Course objektumokat.
     * Mivel minden oszlop fix hosszusagu, igy a String-ekbol egyszeruen ki lehet nyerni az adatokat
     * @param list egy olyan String-ekbol allo lista, ami mar nem tartalmaz felesleges karaktereket, sorokat, minden eleme egy-egy tantargy
     * @return a Course list
     */
    public static List<Course> stringListToCourseList(List<String> list) {
        int semester;
        List<Course> courseList = new ArrayList<>();

        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();

        for (String course : list) {
            try {
                String[] courseAsString = {
                        course.substring(2, 4).trim(), //0. oszlop - felev
                        course.substring(4, 7).trim(), //1. oszlop - kar
                        course.substring(8, 10).trim(), //2. oszlop - szki.
                        course.substring(10, 12).trim(), //3. oszlop - ti.
                        course.substring(16, 52).trim(), //4. oszlop - tantargy
                        course.substring(52, 55).trim(), //5. oszlop - tanszek
                        course.substring(56, 62).trim(), //6. oszlop - oktato
                        course.substring(63, 69).trim().replaceAll("\\s+", ""), //7. oszlop - csoport
                        course.substring(70, 72).trim(), //8. oszlop - fo
                        course.substring(76, 78).trim(), //9. oszlop - nap
                        course.substring(79, 81).trim(), //10. oszlop - kezdes idopontja
                        course.substring(82, 84).trim(), //11. oszlop - ora hossza
                        course.substring(85, 87).trim(), //12. oszlop - ora tipusa
                        course.substring(87, 93).trim() //13. oszlop - terem
                };

                try {
                    semester = Integer.parseInt(courseAsString[0]);
                } catch (NumberFormatException e) {
                    semester = 0;
                }

                Course tempCourse = new Course();
                tempCourse.setFelev(semester);
                tempCourse.setKar(courseAsString[1]);
                tempCourse.setSzki(courseAsString[2]);
                tempCourse.setTi(courseAsString[3]);
                tempCourse.setTantargy(courseAsString[4]);
                tempCourse.setTanszek(courseAsString[5]);
                tempCourse.setEloado(courseAsString[6]);
                tempCourse.setCsoport(courseAsString[7]);
                tempCourse.setFo(Integer.parseInt(courseAsString[8]));
                tempCourse.setNap(courseAsString[9]);
                tempCourse.setKezdes(Integer.parseInt(courseAsString[10]));
                tempCourse.setHossz(Integer.parseInt(courseAsString[11]));
                tempCourse.setTipus(courseAsString[12]);
                tempCourse.setTerem(courseAsString[13]);

                courseDatabaseManager.create(tempCourse);
            } catch (NullPointerException e) {
                continue;
            }
        }
        return courseList;
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
     * Tantargyakban valo kereses
     * @param inputList a lista amiben keres, ami az osszes tantargyban keres
     * @param selectedIndex a JComboBox-ban kivalasztott oszlop indexe, az a mezo, amiben keresni akarunk
     * @param seachValue az ertek, amit keresunk
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