import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        City[] kingdom = new City[n + 1];
        for (int i = 1; i <= n; i++)
        {
            kingdom[i] = new City(i);
        }

        int k = scanner.nextInt();
        int b = scanner.nextInt();
        for (int i = 1; i < n; i++)
        {
            City city1 = kingdom[scanner.nextInt()];
            City city2 = kingdom[scanner.nextInt()];
            city1.paths.add(city2);
            city2.paths.add(city1);
        }
        calculateDistancesFromCapital(kingdom[b]);

        for (int i = 1; i <= n; i++)
        {
            kingdom[i].product = scanner.nextInt();
        }
        int q = scanner.nextInt();
        for (int i = 0; i < q; i++)
        {
            int a = scanner.nextInt();
            int p = scanner.nextInt();
            System.out.println(searchCities(kingdom[a], p));
        }
    }

    private static class City
    {
        int number;
        int product;
        ArrayList<City> paths;
        int distanceFromCapital;

        City(int number)
        {
            this.number = number;
            this.paths = new ArrayList<>();
        }
    }

    /*
    breadth first search to determine how far each city is from the capital
     */
    private static void calculateDistancesFromCapital(City capital)
    {
        Set<City> visited = new HashSet<>();
        Queue<City> toVisit = new LinkedList<>();
        Queue<City> visitNext;

        visited.add(capital);
        toVisit.add(capital);

        int distance = 0;
        while (!toVisit.isEmpty()) {
            visitNext = new LinkedList<>();
            for (City current : toVisit)
            {
                current.distanceFromCapital = distance;
                for (City adjacent : current.paths)
                {
                    if (!visited.contains(adjacent))
                    {
                        visited.add(adjacent);
                        visitNext.add(adjacent);
                    }
                }
            }
            toVisit = visitNext;
            distance += 1;
        }
    }

    /*
    breadth first search with a constraint: search all reachable cities that
    don't take you closer to the capital, then, if the product wasn't found,
    expand the search to cities that are one unit closer to the capital
     */
    private static int searchCities(City start, int product)
    {
        Set<City> visited = new HashSet<>();
        Queue<City> toVisit = new LinkedList<>();
        Queue<City> visitNext;

        visited.add(start);
        toVisit.add(start);

        int threshold = start.distanceFromCapital;
        int bestCity = Integer.MAX_VALUE;

        while (threshold >= 0)
        {
            visitNext = new LinkedList<>();
            while (!toVisit.isEmpty())
            {
                City current = toVisit.poll();
                if (current.product == product && current.number < bestCity)
                {
                    bestCity = current.number;
                }
                for (City adjacent : current.paths)
                {
                    if (!visited.contains(adjacent))
                    {
                        visited.add(adjacent);
                        if (adjacent.distanceFromCapital < threshold)
                        {
                            visitNext.add(adjacent);
                        }
                        else
                        {
                            toVisit.add(adjacent);
                        }
                    }
                }
            }
            if (bestCity < Integer.MAX_VALUE)
            {
                return bestCity;
            }
            toVisit = visitNext;
            threshold -= 1;
        }
        return -1;
    }
}
