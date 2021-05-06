import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomGraphGenerator {
    public static void generate (int maxNodes, String outputFileName) throws IOException {
        FileWriter file = new FileWriter(outputFileName);

        int nodes = (new Random()).nextInt(maxNodes);
        int edges = (new Random()).nextInt(nodes * (nodes - 1) / 2);

        file.write(nodes + " " + edges + "\n");

        HashMap<Integer, List<Integer>> edgesMap = new HashMap<>();

        for (int i = 0; i < edges; ++i) {
            int source = (new Random()).nextInt(nodes);
            int destination = (new Random()).nextInt(nodes);

            while (source == 0 || destination == 0 || source == destination) {
                source = (new Random()).nextInt(nodes);
                destination = (new Random()).nextInt(nodes);
            }

            file.write(source + " " + destination + "\n");
        }

        file.close();
    }
}
