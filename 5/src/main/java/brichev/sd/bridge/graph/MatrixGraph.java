package brichev.sd.bridge.graph;

import brichev.sd.bridge.draw.DrawingApi;
import brichev.sd.bridge.figure.Edge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixGraph extends Graph {
    private final List<Edge> edges = new ArrayList<>();
    private final int vertexesNumber;

    public MatrixGraph(DrawingApi drawingApi, String filename) throws FileNotFoundException {
        super(drawingApi);
        try (var scanner = new Scanner(new File(filename))) {
            int n = scanner.nextInt();
            this.vertexesNumber = n;
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (scanner.nextInt() == 1) {
                        this.edges.add(new Edge(r, c));
                    }
                }
            }
        }
    }

    @Override
    public void drawGraph() {
        super.drawListGraph(vertexesNumber, edges);
    }
}
