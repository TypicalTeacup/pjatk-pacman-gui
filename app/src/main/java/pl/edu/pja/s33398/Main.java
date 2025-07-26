package pl.edu.pja.s33398;

import pl.edu.pja.s33398.Controller.MainMenuController;
import pl.edu.pja.s33398.View.MainMenuView;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            SynthLookAndFeel synth = new SynthLookAndFeel();
            InputStream is = new FileInputStream("src/main/resources/synthlookandfeel.xml");
            synth.load(is, Main.class);
            UIManager.setLookAndFeel(synth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        SwingUtilities.invokeLater(() -> {
            MainMenuView view = new MainMenuView();
            MainMenuController controller = new MainMenuController(view);
        });
    }
}
