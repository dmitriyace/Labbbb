import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

public class Output {
    static Gson gson = new Gson();
    static String json = "";

    /**
     * <p>Сохраняет коллекцию в текстовый файл в формате задания объектов json</p>
     *  @param path  Путь, по которому будет сохранена коллекция
     * @param pjeys Коллекция, элементы которой будут сохраняться в текстовый файл
     */
    public static void save(String path, CopyOnWriteArrayList<Pj> pjeys) {

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path))) {

            json = "";
            for (Pj pj : pjeys) {
                json = json + "\r\n" + gson.toJson(pj);
            }
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
