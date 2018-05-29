import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

public class Output {
    static Gson gson = new Gson();
    static String json = "";

    /**
     * <p>��������� ��������� � ��������� ���� � ������� ������� �������� json</p>
     *  @param path  ����, �� �������� ����� ��������� ���������
     * @param collection ���������, �������� ������� ����� ����������� � ��������� ����
     */
    public static void save(String path, CopyOnWriteArrayList<Pj> collection) {

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path))) {

            json = "";
            for (Pj pj : collection) {
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
