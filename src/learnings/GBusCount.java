package learnings;

import java.io.*;
import java.util.*;

public class GBusCount {
    private int checkCount(int cities[], int city) {
        int count = 0;
        for (int i = 0; i < cities.length; i = i+2) {
            int city1 = cities[i];
            int city2 = cities[i+1];
            if (city >= city1 && city <= city2) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream("sample.txt"))));
        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            System.out.println(n*n);
        }
    }
}
