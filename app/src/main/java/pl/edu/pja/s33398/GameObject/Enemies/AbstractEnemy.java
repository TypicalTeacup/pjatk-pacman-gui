package pl.edu.pja.s33398.GameObject.Enemies;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Enum.Direction;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.GameObject.Upgrades.*;
import pl.edu.pja.s33398.GameObject.Wall;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GridCell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractEnemy extends GameObject {
    private static final List<Class<? extends AbstractUpgrade>> droppableUpgrades = List.of(
            ConfusionUpgrade.class,
            FreezeUpgrade.class,
            MagnetUpgrade.class,
            PowerPelletUpgrade.class,
            SpeedUpgrade.class
    );
    private final Random random = new Random();
    public boolean isEatable = false;
    private List<String> leftImages;
    private List<String> rightImages;
    private List<String> upImages;
    private List<String> downImages;
    private int animationFrame;
    private int ticksUntilUpgradeDrop = 10;
    private Coordinates target = new Coordinates(0, 0);
    private Direction moveDirection = Direction.DOWN;
    private Direction preferredDirection = Direction.LEFT;
    private boolean isFrozen = false;
    private String frozenImage;
    private boolean isConfused = false;
    private String confusedImage;
    private List<String> eatableImages;

    protected void setImages(String folderName) {
        rightImages = List.of(
                "src/main/resources/ghost/" + folderName + "/right/1.png",
                "src/main/resources/ghost/" + folderName + "/right/2.png"
        );
        leftImages = List.of(
                "src/main/resources/ghost/" + folderName + "/left/1.png",
                "src/main/resources/ghost/" + folderName + "/left/2.png"
        );
        upImages = List.of(
                "src/main/resources/ghost/" + folderName + "/up/1.png",
                "src/main/resources/ghost/" + folderName + "/up/2.png"
        );
        downImages = List.of(
                "src/main/resources/ghost/" + folderName + "/down/1.png",
                "src/main/resources/ghost/" + folderName + "/down/2.png"
        );
        frozenImage = "src/main/resources/upgrade/freeze/2.png";
        confusedImage = "src/main/resources/upgrade/confusion/ghost_confusion.png";
        eatableImages = List.of(
                "src/main/resources/ghost/eatable/1.png",
                "src/main/resources/ghost/eatable/2.png"
        );
    }

    @Override
    public List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();

        if (this.isConfused) {
            imagePaths.add(confusedImage);
        }

        if (this.isFrozen) {
            imagePaths.add(frozenImage);
        }

        String drawPath = switch (this.moveDirection) {
            case UP -> upImages.get(this.animationFrame);
            case DOWN -> downImages.get(this.animationFrame);
            case LEFT -> leftImages.get(this.animationFrame);
            case RIGHT -> rightImages.get(this.animationFrame);
        };

        if (!this.isEatable) {
            imagePaths.add(drawPath);
        } else {
            imagePaths.add(eatableImages.get(this.animationFrame));
        }

        return imagePaths;
    }

    @Override
    public void update(GameGrid grid, Coordinates coordinates) {
        Coordinates newTarget = this.updateTarget(grid, coordinates);
        if (this.isConfused) {
            newTarget = new Coordinates(random.nextInt(0, grid.getRowCount()), random.nextInt(0, grid.getColumnCount()));
        }
        if (newTarget != null) this.target = newTarget;

        this.tryDroppingUpgrade(grid, coordinates);

        if (this.isFrozen) {
            return;
        }

        // update preferred direction
        int minDistanceSquared = Integer.MAX_VALUE;
        Direction newPreferredDirection = this.preferredDirection;
        for (Direction direction : Direction.values()) {
            if (direction.isOpposite(this.moveDirection)) continue;

            Coordinates checkCoords = direction.getRelativeCoordinates(coordinates, 1);
            int distanceSquared = checkCoords.getDistanceSquared(this.target);
            if (distanceSquared < minDistanceSquared && !(grid.getGridCell(checkCoords).getWallLayer() instanceof Wall)) {
                minDistanceSquared = distanceSquared;
                newPreferredDirection = direction;
            }
        }
        this.preferredDirection = newPreferredDirection;

        // update move direction
        GridCell preferredCell = grid.getGridCell(this.preferredDirection.getRelativeCoordinates(coordinates, 1));
        if (!(preferredCell.getWallLayer() instanceof Wall)) {
            this.moveDirection = this.preferredDirection;
        }

        // move
        Coordinates targetCoordinates = this.moveDirection.getRelativeCoordinates(coordinates, 1);
        GridCell targetCell = grid.getGridCell(targetCoordinates);
        if (!(targetCell.getWallLayer() instanceof Wall)) {
            GridCell currentCell = grid.getGridCell(coordinates);
            switch (this) {
                case Blinky blinky -> {
                    targetCell.setBlinkyLayer(this);
                    currentCell.setBlinkyLayer(null);
                }
                case Inky inky -> {
                    targetCell.setInkyLayer(this);
                    currentCell.setInkyLayer(null);
                }
                case Pinky pinky -> {
                    targetCell.setPinkyLayer(this);
                    currentCell.setPinkyLayer(null);
                }
                case Clyde clyde -> {
                    targetCell.setClydeLayer(this);
                    currentCell.setClydeLayer(null);
                }
                default -> {
                }
            }
        }
    }

    //@Override
    //public void render(Graphics g, int width, int height) {
    //    Graphics2D g2d = (Graphics2D) g;
    //    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    //
    //    Image drawImage = switch (this.moveDirection) {
    //        case UP -> upImages.get(this.animationFrame);
    //        case DOWN -> downImages.get(this.animationFrame);
    //        case LEFT -> leftImages.get(this.animationFrame);
    //        case RIGHT -> rightImages.get(this.animationFrame);
    //    };
    //
    //    if (!this.isEatable) {
    //        g2d.drawImage(drawImage, 0, 0, width, height, null);
    //    } else {
    //        g2d.drawImage(eatableImages.get(this.animationFrame), 0, 0, width, height, null);
    //    }
    //
    //    if (this.isFrozen) {
    //        g2d.drawImage(frozenImage, 0, 0, width, height, null);
    //    }
    //
    //    if (this.isConfused) {
    //        g2d.drawImage(confusedImage, 0, 0, width, height, null);
    //    }
    //}

    private void tryDroppingUpgrade(GameGrid grid, Coordinates coordinates) {
        ticksUntilUpgradeDrop--;
        if (ticksUntilUpgradeDrop <= 0) {
            ticksUntilUpgradeDrop = 10;
            // 25% chance
            if (random.nextInt(4) == 0) {
                try {
                    Class<? extends AbstractUpgrade> upgradeClass = droppableUpgrades.get(random.nextInt(droppableUpgrades.size()));
                    AbstractUpgrade upgradeInstance = upgradeClass.getDeclaredConstructor().newInstance();
                    grid.getGridCell(coordinates).setUpgradeLayer(upgradeInstance);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void animate() {
        this.animationFrame++;
        this.animationFrame = this.animationFrame % this.rightImages.size();
    }

    protected abstract Coordinates updateTarget(GameGrid grid, Coordinates coordinates);

    public void freeze() {
        this.isFrozen = true;
    }

    public void unfreeze() {
        this.isFrozen = false;
    }

    public void confuse() {
        this.isConfused = true;
    }

    public void unconfuse() {
        this.isConfused = false;
    }

    public void setEatable(boolean eatable) {
        this.isEatable = eatable;
    }
}
