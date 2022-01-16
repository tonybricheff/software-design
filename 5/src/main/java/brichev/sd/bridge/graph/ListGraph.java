package brichev.sd.bridge.graph;

import brichev.sd.bridge.draw.DrawingApi;
import brichev.sd.bridge.figure.Edge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListGraph extends Graph {
    private final List<Edge> edges = new ArrayList<>();
    private final int vertexesNumber;

    public ListGraph(DrawingApi drawingApi, String filename) throws FileNotFoundException {
        super(drawingApi);
        try (var scanner = new Scanner(new File(filename))) {
            this.vertexesNumber = scanner.nextInt();
            var size = scanner.nextInt();
            for (int i = 0; i < size; i++) {
                edges.add(new Edge(scanner.nextInt(), scanner.nextInt()));
            }
        }
    }

    @Override
    public void drawGraph() {
        super.drawListGraph(vertexesNumber, edges);
    }
}
