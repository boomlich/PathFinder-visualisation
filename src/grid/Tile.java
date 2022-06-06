package grid;

public class Tile {

    private TileState tileState;

    public Tile(TileState tileState) {
        this.tileState = tileState;
    }

    public TileState getTileState(){
        return tileState;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileState=" + tileState +
                '}';
    }
}
