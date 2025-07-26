package pl.edu.pja.s33398.GameObject.Upgrades;


import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.Model.GameModel;

public class SpeedUpgrade extends AbstractUpgrade {
    public SpeedUpgrade() {
        this.setImages("speed", 7);
    }

    @Override
    public void activate(GameModel model, Coordinates coordinates) {
        Player player = model.getGrid().getPlayer();
        if (player == null) return;

        new Thread(() -> {
            try {
                player.activateSpeed();
                Thread.sleep(10_000);
                player.deactivateSpeed();
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}
