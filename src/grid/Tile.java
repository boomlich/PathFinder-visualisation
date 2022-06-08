package grid;

public class Tile {

    private boolean visited;
    private TileState tileState;

    public Tile(TileState tileState) {
        this.tileState = tileState;
        visited = false;
    }

    public TileState getTileState(){
        return tileState;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileState=" + tileState +
                '}';
    }
}
