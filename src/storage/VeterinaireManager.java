package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Veterinaire;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VeterinaireManager {
    private static final String FILE_PATH = "data/veterinaire.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Veterinaire> chargerVeterinaires() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Veterinaire>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void sauvegarderVeterinaires(List<Veterinaire> liste) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(liste, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
