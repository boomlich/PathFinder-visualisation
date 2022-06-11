# Pathfinding and maze generation visualization

### Program for visualizing pathfinding and maze-generation algorithms.

## Pathfinding

Algorithms for finding the shortest path between two points.

### Dijkstra's algorithm

This is a greedy weighted algorithm, 

The basic steps of the algorithm are as follows (pseudocode):
1. Set the length of all potential cells to infinite L(cell) = infty, except the start cell, which is initialized as 0
2. Create an empty set that stores the visited cells
3. while the end goal is not present in the visited set: 
  4. set the current cell to be the cell with the lowest distance from the start cell
  5. add the current cell to the visited set
  6. for all unvisited neighbors of the current cell:
    7. if the length of current cell + neighbor offset < L(neighbour), then L(neighbour) = L(current cell) + neighbor offset

### A* algorithm

A* is a variant of Dijkstra's algorithm. It functions similarly but includes another heuristic value for selecting the next cell to visit. This value is the distance from the cell to the goal. i.e. This means that the next cell to visit is based on the G-cost (length from start to current cell), H-cost (length from the current cell to end cell), where G-cost + H-cost = F-cost. The cell with the lowest F-cost is selected. Otherwise everything is the same as Dijkstra's.

Source:
- Rosen. (2019). Discrete mathematics and its applications (Eighth edition, international student edition.). McGraw-Hill Education.
- https://www.youtube.com/watch?v=-L-WgKMFuhE&t=309s&ab_channel=SebastianLague
- https://www.youtube.com/watch?v=ySN5Wnu88nE&ab_channel=Computerphile

## Maze Generation

Algorithms for generating solvable mazes.

### Randomized Depth First Search


### Randomized Kruskal's

### Randomized Prim's

### Recurisve Division

Source:
- https://en.wikipedia.org/wiki/Maze_generation_algorithm
- https://weblog.jamisbuck.org/2011/1/12/maze-generation-recursive-division-algorithm
