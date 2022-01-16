package brichev.sd.bridge;

import brichev.sd.bridge.draw.AwtDrawingApi;
import brichev.sd.bridge.draw.DrawingApi;
import brichev.sd.bridge.draw.FxDrawingApi;
import brichev.sd.bridge.graph.Graph;
import brichev.sd.bridge.graph.ListGraph;
import brichev.sd.bridge.graph.MatrixGraph;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        DrawingApi api;
        switch (args[0]) {
            case "fx":
                api = new FxDrawingApi();
                break;
            case "awt":
                api = new AwtDrawingApi();
                break;
            default:
                throw new IllegalArgumentException("Invalid api");
        }
        Graph graph;
        switch (args[1]) {
            case "list":
                graph = new ListGraph(api, "src/main/resources/list.txt");
                break;
            case "matrix":
                graph = new MatrixGraph(api, "src/main/resources/matrix.txt");
                break;
            default:
                throw new IllegalArgumentException("Invalid graph");
        }
        graph.drawGraph();
    }
}
