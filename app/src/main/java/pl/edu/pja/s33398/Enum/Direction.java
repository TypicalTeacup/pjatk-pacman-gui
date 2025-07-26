package pl.edu.pja.s33398.Enum;

import pl.edu.pja.s33398.Coordinates;

public enum Direction {
    UP {
        @Override
        public String toString() {
            return "↑";
        }

        @Override
        public Coordinates getRelativeCoordinates(int x, int y, int scale) {
            return new Coordinates(x - scale, y);
        }

        @Override
        public boolean isOpposite(Direction direction) {
            return direction == DOWN;
        }
    },

    DOWN {
        @Override
        public String toString() {
            return "↓";
        }

        @Override
        public Coordinates getRelativeCoordinates(int x, int y, int scale) {
            return new Coordinates(x + scale, y);
        }

        @Override
        public boolean isOpposite(Direction direction) {
            return direction == UP;
        }
    },

    LEFT {
        @Override
        public String toString() {
            return "←";
        }

        @Override
        public Coordinates getRelativeCoordinates(int x, int y, int scale) {
            return new Coordinates(x, y - scale);
        }

        @Override
        public boolean isOpposite(Direction direction) {
            return direction == RIGHT;
        }
    },

    RIGHT {
        @Override
        public String toString() {
            return "→";
        }

        @Override
        public Coordinates getRelativeCoordinates(int x, int y, int scale) {
            return new Coordinates(x, y + scale);
        }

        @Override
        public boolean isOpposite(Direction direction) {
            return direction == LEFT;
        }
    };

    public abstract boolean isOpposite(Direction direction);

    public Coordinates getRelativeCoordinates(Coordinates coordinates, int scale) {
        return this.getRelativeCoordinates(coordinates.row(), coordinates.column(), scale);
    }

    public abstract Coordinates getRelativeCoordinates(int x, int y, int scale);
}
