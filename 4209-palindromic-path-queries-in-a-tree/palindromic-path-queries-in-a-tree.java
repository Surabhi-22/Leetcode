import java.util.*;

class Solution {

    private int[] tin, tout, depth;
    private int[][] up;
    private Fenwick bit;
    private int LOG;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[LOG][n];

        int[] parent = new int[n];
        int[] next = new int[n];
        int[] stack = new int[n];
        int[] chars = new int[n];

        for (int i = 0; i < n; i++) {
            chars[i] = s.charAt(i) - 'a';
        }

        int[] baseMask = new int[n];

        int top = 0;
        int timer = 0;

        stack[0] = 0;
        parent[0] = 0;

        baseMask[0] = 1 << chars[0];
        tin[0] = timer++;

        while (top >= 0) {
            int u = stack[top];

            if (next[u] < graph[u].size()) {
                int v = graph[u].get(next[u]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                up[0][v] = u;

                baseMask[v] =
                        baseMask[u] ^ (1 << chars[v]);

                tin[v] = timer++;
                stack[++top] = v;

            } else {
                tout[u] = timer;
                top--;
            }
        }

        up[0][0] = 0;

        for (int k = 1; k < LOG; k++) {
            for (int v = 0; v < n; v++) {
                up[k][v] =
                        up[k - 1][up[k - 1][v]];
            }
        }

        bit = new Fenwick(n);

        for (int v = 0; v < n; v++) {
            bit.rangeXor(
                    tin[v],
                    tin[v] + 1,
                    baseMask[v]
            );
        }

        List<Boolean> answers = new ArrayList<>();

        for (String query : queries) {

            String[] parts = query.trim().split("\\s+");

            if (parts[0].equals("update")) {

                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << chars[u];
                int newChar = c - 'a';
                int newMask = 1 << newChar;

                int delta = oldMask ^ newMask;

                bit.rangeXor(tin[u], tout[u], delta);

                chars[u] = newChar;

            } else if (parts[0].equals("query")) {

                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int maskU = bit.pointQuery(tin[u]);
                int maskV = bit.pointQuery(tin[v]);

                int ancestorMask = 1 << chars[ancestor];

                int pathMask =
                        maskU ^ maskV ^ ancestorMask;

                boolean possible =
                        (pathMask & (pathMask - 1)) == 0;

                answers.add(possible);
            }
        }

        return answers;
    }

    private int lca(int u, int v) {

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    private static class Fenwick {

        private final int[] tree;
        private final int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        private void update(int index, int value) {

            while (index <= n + 1) {
                tree[index] ^= value;
                index += index & -index;
            }
        }

        private int query(int index) {

            int result = 0;

            while (index > 0) {
                result ^= tree[index];
                index -= index & -index;
            }

            return result;
        }

        void rangeXor(int left, int right, int value) {

            update(left + 1, value);
            update(right + 1, value);
        }

        int pointQuery(int index) {
            return query(index + 1);
        }
    }
}