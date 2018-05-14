import java.util.*;

public class Kruskal {
    Map<Integer, Integer> parent = new HashMap<>();
    public static int minCosteDeA=Integer.MAX_VALUE;
    public static int minCosteDeB=Integer.MAX_VALUE;

    // perform MakeSet operation
    public void makeSet(int N) {
        // create N disjoint sets (one for each vertex)
        for (int i = 0; i < N; i++)
            parent.put(i, i);
    }

    // Find the root of the set in which element k belongs
    private int Find(int k) {
        // if k is root
        if (parent.get(k) == k)
            return k;

        // recurse for parent until we find root
        return Find(parent.get(k));
    }

    // Perform Union of two subsets
    private void Union(int a, int b) {
        // find root of the sets in which elements
        // x and y belongs
        int x = Find(a);
        int y = Find(b);

        parent.put(x, y);
    }

    // construct MST using Kruskal's algorithm
    public static List<Edge> KruskalAlgo(List<Edge> edges, int numC, int numVertices) {
        // stores edges present in MST
        List<Edge> MST = new ArrayList<>();

        // initialize DisjointSet class
        // create singleton set for each element of universe
        Kruskal ds = new Kruskal();
        ds.makeSet(numC);

        int index = 0;

        // MST contains exactly V-1 edges
        Edge next_edge;
        while (index < (numVertices - 1) && (next_edge = edges.get(index)) != null) {
            // consider next edge with minimum weight from the graph


            // find root of the sets to which two endpoint
            // vertices of next_edge belongs
            int x = ds.Find(next_edge.src);
            int y = ds.Find(next_edge.dest);

            // if both endpoints have different parents, they belong to
            // different connected components and can be included in MST
            if (x != y) {
                MST.add(next_edge);
                ds.Union(x, y);
                index++;
            }else edges.remove(index);
        }
        return MST;
    }

    public static int Krusk(int[][] cost, int[] visited, int numOfCities, int B) {
        List<Edge> edges = new ArrayList<>(1);
        for(int i = 0; i < numOfCities; i++){
            for(int j = i + 1; j < numOfCities; j++) {
                if(visited[i] == 0 && visited[j] == 0){
                    if (cost[i][j]<Integer.MAX_VALUE){
                        if (cost[0][j] < minCosteDeA && visited[j]==0){
                            minCosteDeA = cost[0][j];
                        }
                        if (cost[B][j] < minCosteDeB && visited[j]==0){
                            minCosteDeB = cost[B][j];
                        }
                        edges.add(new Edge(i, j, cost[i][j]));
                    }
                }
            }
        }
        if (edges.size() == 0) return 0;
        if (edges.size() == 1) return edges.get(0).weight;
        // sort edges by increasing weight
        Collections.sort(edges, (a, b) -> a.weight - b.weight);

        //System.out.println(Arrays.toString(edges.toArray()));
        int tempNum = numOfCities;
        for(int i = 0; i < tempNum; i++){
            if(visited[i] > 0)
                numOfCities--;
        }
        //System.out.println(numOfCities);
        // construct graph
        List<Edge> e = KruskalAlgo(edges, tempNum, numOfCities);

        int peso = 0;
        for (Edge edge : e) {
            peso += edge.weight;
        }
        return peso;
    }

    // Data structure to store graph edges
    static class Edge {
        int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + src + ", " + dest + ", " + weight + ")";
        }
    }

}
