import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

public class GraphMatrix implements Graph {
    // adjacency matrix
    private int[][] matrix;

    // number of vertices
    private int numVertices;

    // number of edges
    private int numEdges;

    // check whether a vertex is visited before. We'll use it in the BFS and resetVisited methods
    private boolean[] Visited;

    // initialize a queue to help us trace vertices in the BFS method
    private Queue<Integer> queue;

    // initialize a graph with n vertices
    public void init(int n) {
        // a graph makes no sense when it has negative or zero vertex, so we need to print an error message
        // in such situation
        if(n<=0){
            System.out.println("Error: the number of vertices should be greater than 0.");
            System.exit(0);
        }
        numVertices=n;
        matrix=new int[n][n];
        numEdges=0;
        queue=new LinkedList<Integer>();
    }

    // return the number of vertices
    public int nodeCount(){
        return numVertices;
    }

    // return the current number of edges. This operation is a constant time operation: O(1).
    public int edgeCount(){
        return numEdges;
    }

    // adds a new edge from node v to node w with weight wgt
    public void addEdge(int v, int w, int wgt){
        if(v<0||v>=numVertices||w<0||w>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        // useless to store a weight of 0 or negative weight
        if (wgt <= 0) {
            System.out.println("Error: the weight should be greater than 0");
            System.exit(2);
        }
        if(matrix[v][w]==0){
            numEdges++;
        }
        matrix[v][w] = wgt;
    }

    // get the weight value for an edge
    public int getWeight(int v, int w){
        if(v<0||v>=numVertices||w<0||w>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        return matrix[v][w];
    }

    // set the weight of v and w.
    public void setWeight(int v, int w, int wgt){
        if(v<0||v>=numVertices||w<0||w>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        // useless to store a weight of 0 or negative weight
        if (wgt <= 0) {
            System.out.println("Error: the weight should be greater than 0");
            System.exit(2);
        }
        matrix[v][w]=wgt;
    }

    // remove an edge from the graph.
    public void removeEdge(int v, int w){
        if(v<0||v>=numVertices||w<0||w>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        if (matrix[v][w] != 0) {
            matrix[v][w] = 0;
            numEdges--;
        }
    }

    // return true if and only if the graph has an edge between v and w.
    public boolean hasEdge(int v, int w){
        if(v<0||v>=numVertices||w<0||w>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        return matrix[v][w] != 0;
    }

    // return an array containing vertex id's of the neighbors of v.
    public int[] neighbors(int v){
        if(v<0||v>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        int numNeighbors=0;
        int[] neighbors;
        // count the number of neighbors for vertex v
        for (int i=0; i<matrix[v].length; i++){
            if(matrix[v][i]!=0){
                numNeighbors++;
            }
        }
        // put the vertex v's neighbors into the array neighbors
        neighbors=new int[numNeighbors];
        int count=0;
        for (int i=0; i<matrix[v].length; i++){
            if(matrix[v][i]!=0){
                neighbors[count]=i;
                count++;
            }
        }
        return neighbors;
    }

    // resets the Visited array (required for BFS) to all false since no vertex is visited at the beginning.
    public void resetVisited(){
        Visited=new boolean[numVertices];
        Arrays.fill(Visited, false);
    }

    // Performs a Breadth-First-Search starting at vertex, v.
    // On PreVisit, the current vertex's label/id should be appended to the end of an ArrayList.
    // Do not perform a PostVisit operation.
    // Index 0 of the array should be the starting vertex, v. Index 1 should be one of v's neighbors and so on.
    // Once BFS is completed, the ArrayList is returned.
    public ArrayList<Integer> BFS(int v){
        if(v<0||v>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        ArrayList<Integer> result=new ArrayList<>();
        // queue the starting vertex
        queue.add(v);
        Visited[v]=true;
        // loop while queue is not empty
        while(queue.size()!=0){
            v= queue.poll();
            result.add(v);
//            System.out.print(v+" "); // use to test (aborted)
            // get vertex v's neighbors
            int[] adj=neighbors(v);
            for(int i=0;i< adj.length;i++){
                // only put new vertices into queue if they have not been visited
                if(!Visited[adj[i]]){
                    Visited[adj[i]]=true;
                    queue.add(adj[i]);
                }
            }
        }
        return result;
    }

    // Problem 2
    // Returns true if there is a path between v and w.
    // Otherwise, returns false. You may use the BFS method (above) for this method.
    public boolean hasPath(int v, int w){
        if(v<0||v>=numVertices||w<0||w>=numVertices){
            System.out.println("Error: the node is out of bound");
            System.exit(1);
        }
        // set all vertices to unvisited at the beginning
        resetVisited();
        ArrayList<Integer> isReachable=BFS(v);
        for(int i=0; i<isReachable.size();i++){
            if(isReachable.get(i)==w){
                return true;
            }
        }
        return false;
    }

    // Problem 3
    // Performs a topologicalSort of the graph
    // and returns an ArrayList that contains the vertex labels/id in topologically sorted order.
    public ArrayList<Integer> topologicalSort(){
        resetVisited();
        Queue<Integer> Q=new LinkedList<>();
        ArrayList<Integer> results=new ArrayList<>();
        int[] prerequisitesCount=new int[numVertices];
        // count the incoming vertex for a vertex
        int[] nList;
        for(int i=0; i<numVertices; i++){
            nList=neighbors(i);
            for (int j=0; j< nList.length; j++) {
                prerequisitesCount[nList[j]]++; // add to v's prerequisite count
            }
        }

        // put the vertices whose incoming vertices is equal to 0 into the queue
        for(int i=0; i<numVertices; i++){
            if(prerequisitesCount[i]==0){
                Q.add(i);
            }
        }

        while (!Q.isEmpty()) {
            int vertexToBeDisconnected=Q.poll();
            results.add(vertexToBeDisconnected);
            // disconnect this vertex from the graph
            // and decrease the prerequisite count of other vertices connected to this vertex by 1
            nList=neighbors(vertexToBeDisconnected);
            for (int i=0; i< nList.length; i++) {
                prerequisitesCount[nList[i]]--;
                if(prerequisitesCount[nList[i]]==0){
                    Q.add(nList[i]);
                }
            }
        }

        return results;
        }

        // the main class is used to test the problems 1-3
    public static void main (String[] args){

        // BFS test case
        GraphMatrix graph = new GraphMatrix();
        graph.init(6);
        graph.addEdge(0, 1,1);
        graph.addEdge(0, 3,1);
        graph.addEdge(0, 4,1);
        graph.addEdge(4, 5,1);
        graph.addEdge(3, 5,1);
        graph.addEdge(1, 2,1);
        graph.addEdge(1, 0,1);
        graph.addEdge(2, 1,1);
        graph.addEdge(4, 1,1);
        graph.addEdge(3, 1,1);
        graph.addEdge(5, 4,1);
        graph.addEdge(5, 3,1);
        graph.resetVisited();
        ArrayList<Integer> test=graph.BFS(0);
        System.out.println("The Breadth First Traversal of the graph is as follows :");
        for(int i=0; i< test.size();i++){
            System.out.print(test.get(i)+" ");
        }

        // Problem 2 test case
        GraphMatrix graph2 = new GraphMatrix();
        graph2.init(8);
        graph2.addEdge(0,3,1);
        graph2.addEdge(1,0,1);
        graph2.addEdge(1,2,1);
        graph2.addEdge(1,4,1);
        graph2.addEdge(2,7,1);
        graph2.addEdge(3,4,1);
        graph2.addEdge(3,5,1);
        graph2.addEdge(4,3,1);
        graph2.addEdge(4,6,1);
        graph2.addEdge(5,6,1);
        graph2.addEdge(6,7,1);
        if(graph2.hasPath(2,3)){
            System.out.println("\nPath exists");
        }
        else{
            System.out.println("\nPath don't exist");
        }

        // Problem 3 test case
        GraphMatrix graph3 = new GraphMatrix();
        graph3.init(8);
        graph3.addEdge(0,1,1);
        graph3.addEdge(1,2,1);
        graph3.addEdge(2,3,1);
        graph3.addEdge(3,4,1);
        graph3.addEdge(3,5,1);
        graph3.addEdge(3,6,1);
        graph3.addEdge(2,6,1);
        graph3.addEdge(6,7,1);
        graph.resetVisited();
        ArrayList<Integer> test3=graph3.topologicalSort();
        for(int i=0; i< test3.size();i++){
            System.out.print(test3.get(i)+" ");
        }
    }
}
