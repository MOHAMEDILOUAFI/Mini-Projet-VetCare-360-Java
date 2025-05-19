package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Proprietaire;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FichierManager {
    private static final String FILE_PATH = "data/proprietaires.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static List<Proprietaire> chargerProprietaires() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Proprietaire>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void sauvegarderProprietaires(List<Proprietaire> proprietaires) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(proprietaires, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
