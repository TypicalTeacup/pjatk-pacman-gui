package pl.edu.pja.s33398.GameObject;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Enum.Direction;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GridCell;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private final List<String> leftImages;
    private final List<String> rightImages;
    private final List<String> upImages;
    private final List<String> downImages;
    public boolean isMagnetActive = false;
    public boolean isSpedUp = false;
    private int animationFrame = 0;
    private Direction moveDirection = Direction.RIGHT;
    private Direction preferredDirection = Direction.RIGHT;
    private String magnetImage;

    public Player() {
        rightImages = List.of(
                "src/main/resources/pacman/right/1.png",
                "src/main/resources/pacman/right/2.png",
                "src/main/resources/pacman/right/3.png",
                "src/main/resources/pacman/right/4.png"
        );
        leftImages = List.of(
                "src/main/resources/pacman/left/1.png",
                "src/main/resources/pacman/left/2.png",
                "src/main/resources/pacman/left/3.png",
                "src/main/resources/pacman/left/4.png"
        );
        upImages = List.of(
                "src/main/resources/pacman/up/1.png",
                "src/main/resources/pacman/up/2.png",
                "src/main/resources/pacman/up/3.png",
                "src/main/resources/pacman/up/4.png"
        );
        downImages = List.of(
                "src/main/resources/pacman/down/1.png",
                "src/main/resources/pacman/down/2.png",
                "src/main/resources/pacman/down/3.png",
                "src/main/resources/pacman/down/4.png"
        );
        magnetImage = "src/main/resources/upgrade/magnet/player_magnet.png";
    }

//    @Override
//    public void render(Graphics g, int width, int height) {
//        Image drawImage = switch (this.moveDirection) {
//            case LEFT -> this.leftImages.get(this.animationFrame);
//            case RIGHT -> this.rightImages.get(this.animationFrame);
//            case UP -> this.upImages.get(this.animationFrame);
//            case DOWN -> this.downImages.get(this.animationFrame);
//        };
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
//
//        g2d.drawImage(drawImage, 0, 0, width, height, null);
//
//        if (this.isMagnetActive) {
//            g2d.drawImage(magnetImage, 0, 0, width, height, null);
//        }
//    }

    @Override
    public List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();

        if (this.isMagnetActive) {
            imagePaths.add(magnetImage);
        }

        String drawLabel = switch (this.moveDirection) {
            case LEFT -> leftImages.get(this.animationFrame);
            case RIGHT -> rightImages.get(this.animationFrame);
            case UP -> upImages.get(this.animationFrame);
            case DOWN -> downImages.get(this.animationFrame);
        };

        imagePaths.add(drawLabel);

        return imagePaths;
    }

    @Override
    public void update(GameGrid grid, Coordinates coordinates) {
        Coordinates playerCoordinates = grid.getPlayerCoordinates();
        Coordinates preferredCoordinates = this.preferredDirection.getRelativeCoordinates(playerCoordinates, 1);
        GridCell preferredCell = grid.getGridCell(preferredCoordinates);
        this.updateMoveDirection(preferredCell);

        this.tryMoving(grid, playerCoordinates);
    }

    @Override
    public void animate() {
        this.animationFrame++;
        this.animationFrame = this.animationFrame % this.rightImages.size();
    }

    private void tryMoving(GameGrid grid, Coordinates playerCoordinates) {
        Coordinates targetCoordinates = this.moveDirection.getRelativeCoordinates(playerCoordinates, 1);
        GridCell targetCell = grid.getGridCell(targetCoordinates);
        if (!(targetCell.getWallLayer() instanceof Wall)) {
            grid.getGridCell(targetCoordinates).setPlayerLayer(this);
            grid.getGridCell(playerCoordinates).setPlayerLayer(null);
        }
    }


    private void updateMoveDirection(GridCell preferredCell) {
        if (preferredCell.getWallLayer() instanceof Wall) {
            return;
        }
        this.moveDirection = this.preferredDirection;
    }

    public Direction getMoveDirection() {
        return this.moveDirection;
    }

    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    public Direction getPreferredDirection() {
        return this.preferredDirection;
    }

    public void setPreferredDirection(Direction preferredDirection) {
        this.preferredDirection = preferredDirection;
    }

    public void activateMagnet() {
        this.isMagnetActive = true;
    }

    public void deactivateMagnet() {
        this.isMagnetActive = false;
    }

    public void activateSpeed() {
        this.isSpedUp = true;
    }

    public void deactivateSpeed() {
        this.isSpedUp = false;
    }
}
