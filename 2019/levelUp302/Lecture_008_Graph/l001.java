import java.util.ArrayList;

public class l001 {

    public static class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    static int N = 7;
    static ArrayList<Edge>[] graph = new ArrayList[N];

    public static void addEdge(int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }

    public static int findEdge(int u, int v) {
        for (int i = 0; i < graph[u].size(); i++) {
            Edge e = graph[u].get(i);
            if (e.v == v)
                return i;
        }

        return -1;
    }

    public static void removeEdge(int u, int v) {
        int idx1 = findEdge(u, v);
        graph[u].remove(idx1);

        int idx2 = findEdge(v, u);
        graph[v].remove(idx2);
    }

    public static void removeVtx(int u) {
        while (graph[u].size() != 0) {
            int v = graph[u].get(graph[u].size() - 1).v;
            removeEdge(u, v);
        }
    }

    public static void display() {
        for (int i = 0; i < N; i++) {
            System.out.print(i + " -> ");
            for (Edge e : graph[i]) {
                System.out.print("(" + e.v + ", " + e.w + ") ");
            }
            System.out.println();
        }
    }

    public static boolean hasPath(int src, int desti, boolean[] vis) {
        if (src == desti)
            return true;

        vis[src] = true;
        boolean res = false;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                res = res || hasPath(e.v, desti, vis);
        }

        return res;
    }

    public static int allPath(int src, int desti, int weight, String ans, boolean[] vis) {
        if (desti == src) {
            System.out.println(ans + src + "@" + weight);
            return 1;
        }

        vis[src] = true;
        int count = 0;
        // System.out.println(src + " -> " + ans + src + "@" + weight);

        for (Edge e : graph[src]) {
            if (!vis[e.v])
                count += allPath(e.v, desti, weight + e.w, ans + src + " ", vis);
        }

        vis[src] = false;
        return count;
    }

    public static void preOrder(int src, int weight, String ans, boolean[] vis) {
        System.out.println(src + " -> " + ans + src + "@" + weight);

        vis[src] = true;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                preOrder(e.v, weight + e.w, ans + src + " ", vis);
        }
        vis[src] = false;
    }

    public static class pair {
        String path = "";
        int cost = 0;

        pair(int cost, String path) {
            this.cost = cost;
            this.path = path;
        }
    }

    public static pair heavyWeightPath(int src, int desti, boolean[] vis) {
        if (src == desti) {
            return new pair(0, src + "");
        }

        vis[src] = true;
        pair myAns = new pair(0, "");

        for (Edge e : graph[src]) {
            if (!vis[e.v]) {
                pair recAns = heavyWeightPath(e.v, desti, vis);
                if (recAns.cost + e.w > myAns.cost) {
                    myAns.cost = recAns.cost + e.w;
                    myAns.path = src + " " + recAns.path;
                }
            }
        }

        vis[src] = false;
        return myAns;
    }

    public static int hamintonianPathAndCycle(int src, int osrc, int edgeCount, boolean[] vis, String path) {
        if (edgeCount == N - 1) {
            int idx = findEdge(src, osrc);
            if (idx != -1) {
                System.out.println("Cycle: " + path + src);
            } else {
                System.out.println("Path: " + path + src);
            }
            return 1;
        }

        vis[src] = true;
        int count = 0;
        for (Edge e : graph[src]) {
            if (!vis[e.v])
                count += hamintonianPathAndCycle(e.v, osrc, edgeCount + 1, vis, path + src + " ");
        }

        vis[src] = false;
        return count;
    }

    public static void GCC_DFS(int src, boolean[] vis) {
        vis[src] = true;
        for (Edge e : graph[src])
            if (!vis[e.v])
                GCC_DFS(e.v, vis);
    }

    public static int GCC() {
        boolean[] vis = new boolean[N];
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (!vis[i]) {
                GCC_DFS(i, vis);
                count++;
            }
        }
        return count;
    }

    // ============================================================================

    public static void constructGraph() {
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<Edge>();
        }

        addEdge(0, 1, 10);
        addEdge(0, 3, 10);
        addEdge(1, 2, 10);
        addEdge(2, 3, 40);
        addEdge(3, 4, 2);
        addEdge(4, 5, 2);
        addEdge(4, 6, 8);
        addEdge(5, 6, 3);

        addEdge(2, 5, 10);

        display();

    }

    public static void solve() {
        // removeVtx(3);
        boolean[] vis = new boolean[N];
        // allPath(0, 6, 0, "", vis);
        // preOrder(0, 0, "", vis);

        // pair ans = heavyWeightPath(0, 6, vis);
        // System.out.println(ans.path + " @ " + ans.cost);

        System.out.println(hamintonianPathAndCycle(0, 0, 0, vis, ""));
        // display();

    }

    public static void main(String[] args) {
        constructGraph();
        solve();

    }
}