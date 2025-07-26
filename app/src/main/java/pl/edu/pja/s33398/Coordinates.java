package pl.edu.pja.s33398;

public record Coordinates(int row, int column) {
    public int getDistanceSquared(Coordinates other) {
        int dx = this.column - other.column;
        int dy = this.row - other.row;
        return dx * dx + dy * dy;
    }
}
