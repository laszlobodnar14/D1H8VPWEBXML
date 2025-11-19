package JSOND1H8VP;

import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONWrite {
    public static void main(String[] args) {
        JSONArray lessons = new JSONArray();
        lessons.add(createLesson("Elektronika Elektrotechnika", "hétfő", "8", "10", "E3", "Dr. Szabó Norbert", "Mernokinformatikus"));
        lessons.add(createLesson("Integralt Vallalti rendszerek", "hétfő", "10", "12", "Inf. 103", "Kulcsárné Dr. Forrai Mónika", "MernokInformatikus"));
        lessons.add(createLesson("Korszeru Informacios Techonlogiak", "Kedd", "8", "10", "Inf. 103", "Dr. Árvai László", "Mernokinformatikus"));
        lessons.add(createLesson("Korszeru Informacios Technologiak", "Kedd", "10", "12", "Inf. 103", "Dr. Árvai László", "Mernokinformatikus"));
        lessons.add(createLesson("Integralt Vallalati rendszerek", "Kedd", "12", "14", "E12", "Dr. Samad Davandipuor", "Mernokinformatikus"));
        lessons.add(createLesson("Adatkezelés XML-ben", "Szerda", "10", "12", "Inf. 103", "Dr. Bendarik László", "Mernokinformatikus"));
        lessons.add(createLesson("Adatkezelés XML-ben", "Szerda", "8", "10", "E12", "Dr. Kovács László", "Mernokinformatikus"));
        lessons.add(createLesson("Elektronika Elektrotechnika", "Szerda", "12", "14", "E11", "Dr. Szabó Norbert", "Mernokinformatikus"));
        lessons.add(createLesson("Fizika II", "Csutortok", "8", "10", "Fizika Könyvtár", "Dr. Nagy Ádám", "Mernokinformatikus"));


        JSONObject root = new JSONObject();
        root.put("ora", lessons);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("D1H8VP_orarend", root);

        fileWrite(jsonObject, "OrarendD1H8VP.json");
        consoleWrite(jsonObject);
    }

    private static void fileWrite(JSONObject jsonObject, String fileName) {
        try(FileWriter writer = new FileWriter(fileName)){
            writer.write(indentJson(jsonObject.toJSONString()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void consoleWrite(JSONObject jsonObject) {
        System.out.println("A felépített JSON dokumentum tartalma:\n");
        JSONObject root = (JSONObject) jsonObject.get("D1H8VP_orarend");
        JSONArray lessons = (JSONArray) root.get("ora");
        for(int i=0; i<lessons.size(); i++) {
            JSONObject lesson = (JSONObject) lessons.get(i);
            JSONObject time = (JSONObject) lesson.get("idopont");
            System.out.println("Tárgy: "+lesson.get("targy"));
            System.out.println("Időpont: "+time.get("nap")+" "+time.get("tol")+"-"+time.get("ig"));
            System.out.println("Helyszín: "+lesson.get("helyszin"));
            System.out.println("Oktató: "+lesson.get("oktato"));
            System.out.println("Szak: "+lesson.get("szak")+"\n");
        }
    }

    private static String indentJson(String json) {
        String out = "";
        int indent = 0;
        for (int i = 0; i < json.length()-1; i++) {
            out += json.charAt(i);
            if (json.charAt(i) == ',') {
                out += "\n" + "  ".repeat(indent>0 ? indent : 0);
            } else if (json.charAt(i) == '{' | json.charAt(i) == '[') {
                indent++;
                out += "\n" + "  ".repeat(indent>0 ? indent : 0);
            }else if ((json.charAt(i+1) == '}' || json.charAt(i+1) == ']')) {
                indent--;
                out += "\n" + "  ".repeat(indent>0 ? indent : 0);
            }
        }
        out+=json.charAt(json.length()-1);
        return out;
    }

    private static JSONObject createLesson(String subject, String day, String from, String to, String place, String teacher, String major) {
        JSONObject lesson = new JSONObject();
        JSONObject time = new JSONObject();
        time.put("nap", day);
        time.put("tol", from);
        time.put("ig", to);
        lesson.put("targy", subject);
        lesson.put("idopont", time);
        lesson.put("helyszin", place);
        lesson.put("oktato", teacher);
        lesson.put("szak", major);
        return lesson;
    }

}