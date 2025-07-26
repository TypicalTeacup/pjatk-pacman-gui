package pl.edu.pja.s33398.Highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighscoresSingleton implements Serializable {
    private static final String fileName = "highscores.ser";
    private static HighscoresSingleton instance;
    private List<Score> highscores;

    private HighscoresSingleton() {
        this.highscores = new ArrayList<>();
    }

    public static synchronized HighscoresSingleton getInstance() {
        if (instance == null) {
            // read from file
            instance = loadFromFile();
        }
        return instance;
    }

    private static HighscoresSingleton loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return new HighscoresSingleton();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            return (HighscoresSingleton) obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void addScore(int score, String name) {
        this.highscores.add(new Score(score, name));
        this.highscores.sort(Comparator.comparingInt(Score::score).reversed());
        try {
            this.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void saveToFile() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }

    public List<Score> getScores() {
        return this.highscores;
    }

}
