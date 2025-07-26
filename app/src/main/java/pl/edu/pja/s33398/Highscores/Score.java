package pl.edu.pja.s33398.Highscores;

import java.io.Serializable;

public record Score(int score, String name) implements Serializable {
}
