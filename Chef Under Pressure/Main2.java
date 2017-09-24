import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    private static int kPlusOne;
    private static int[][] bestCity;

    private static int[] product;
    private static ArrayList<Integer>[] neighbors;
    private static boolean[] visited;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        int nPlusOne = scanner.nextInt() + 1;
        product = new int[nPlusOne];
        neighbors = new ArrayList[nPlusOne];
        visited = new boolean[nPlusOne];

        kPlusOne = scanner.nextInt() + 1;
        bestCity = new int[nPlusOne][kPlusOne];
        for (int i = 1; i < nPlusOne; i++)
        {
            neighbors[i] = new ArrayList<>();
            for (int j = 1; j < kPlusOne; j++)
            {
                bestCity[i][j] = Integer.MAX_VALUE;
            }
        }

        int b = scanner.nextInt();

        for (int i = 2; i < nPlusOne; i++)
        {
            int city1 = scanner.nextInt();
            int city2 = scanner.nextInt();
            neighbors[city1].add(city2);
            neighbors[city2].add(city1);
        }

        for (int i = 1; i < nPlusOne; i++)
        {
            product[i] = scanner.nextInt();
        }

        determineBestCitiesInSubtrees(b);

        // mark products that are not available
        for (int i = 1; i < kPlusOne; i++)
        {
            if (bestCity[b][i] == Integer.MAX_VALUE)
            {
                bestCity[b][i] = -1;
            }
        }
        Arrays.fill(visited, false);
        determineBestCitiesNotInSubtrees(b);

        int q = scanner.nextInt();
        for (int i = 0; i < q; i++)
        {
            int a = scanner.nextInt();
            int p = scanner.nextInt();
            System.out.println(bestCity[a][p]);
        }
    }

    /*
    depth first search to determine the best city for each product that is
    available in each subtree
     */
    private static void determineBestCitiesInSubtrees(int root)
    {
        visited[root] = true;
        bestCity[root][product[root]] = root;
        for (int neighbor : neighbors[root])
        {
            if (!visited[neighbor])
            {
                determineBestCitiesInSubtrees(neighbor);
                for (int i = 1; i < kPlusOne; i++)
                {
                    if (bestCity[neighbor][i] < bestCity[root][i])
                    {
                        bestCity[root][i] = bestCity[neighbor][i];
                    }
                }
            }
        }
    }

    /*
    depth first search to determine the best city for each product that is
    not available in each subtree
     */
    private static void determineBestCitiesNotInSubtrees(int root)
    {
        visited[root] = true;
        for (int neighbor : neighbors[root])
        {
            if (!visited[neighbor])
            {
                for (int i = 1; i < kPlusOne; i++)
                {
                    if (bestCity[neighbor][i] == Integer.MAX_VALUE)
                    {
                        bestCity[neighbor][i] = bestCity[root][i];
                    }
                }
                determineBestCitiesNotInSubtrees(neighbor);
            }
        }
    }
}
