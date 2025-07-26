package pl.edu.pja.s33398.GameObject.Upgrades;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.Model.GameModel;

public class MagnetUpgrade extends AbstractUpgrade {
    public MagnetUpgrade() {
        this.setImages("magnet", 6);
    }

    @Override
    public void activate(GameModel model, Coordinates coordinates) {
        Player player = model.getGrid().getPlayer();
        if (player == null) return;

        new Thread(() -> {
            try {
                player.activateMagnet();
                Thread.sleep(10_000);
                player.deactivateMagnet();
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}
