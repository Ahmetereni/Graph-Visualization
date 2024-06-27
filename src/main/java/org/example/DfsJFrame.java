package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Set;

public class DfsJFrame {


    public static void main(String[] args) {
        // Create nodes
        Birey A = new Birey("A");
        Birey B = new Birey("B");
        Birey C = new Birey("C");
        Birey D = new Birey("D");
        Birey E = new Birey("E");
        Birey F = new Birey("F");
        Birey G = new Birey("G");

        // Create edges
        A.komsuEkle(E);
        A.komsuEkle(F);
        C.komsuEkle(F);
        E.komsuEkle(B);
        E.komsuEkle(D);
        B.komsuEkle(F);
        B.komsuEkle(D);
        B.komsuEkle(G);

        // Set up the frame
        JFrame frame = new JFrame("DFS Visualization");
        DFSLinearVisualizationPanel panel = new DFSLinearVisualizationPanel();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Perform DFS starting from node A
        Set<Birey> visited = new HashSet<>();
        panel.setNodes(A, B, C, D, E, F);
        panel.dfs(A, visited);
    }





}
class DFSLinearVisualizationPanel extends JPanel {
    private Set<Birey> visited = new HashSet<>();
    private Birey[] nodes;
    private int delay = 1000; // 1 second delay for visualization

    public void setNodes(Birey... nodes) {
        this.nodes = nodes;
    }

    public void dfs(Birey node, Set<Birey> visited) {
        if (node == null || visited.contains(node)) {
            return;
        }

        // Mark the current node as visited
        visited.add(node);
        this.visited.add(node);

        // Repaint the panel to update the visualization
        repaint();

        // Delay for visualization
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Recur for all the neighbors
        for (Birey neighbor : node.getKomsulari()) {
            dfs(neighbor, visited);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw nodes
        int radius = 25;
        int[][] positions = {
                {100, 100}, {300, 100}, {500, 100},
                {100, 300}, {300, 300}, {500, 300}
        };

        if (nodes != null) {
            for (int i = 0; i < nodes.length; i++) {
                Birey node = nodes[i];
                int x = positions[i][0];
                int y = positions[i][1];

                // Draw edges
                for (Birey neighbor : node.getKomsulari()) {
                    int neighborIndex = -1;
                    for (int j = 0; j < nodes.length; j++) {
                        if (nodes[j].equals(neighbor)) {
                            neighborIndex = j;
                            break;
                        }
                    }
                    if (neighborIndex != -1) {
                        int nx = positions[neighborIndex][0];
                        int ny = positions[neighborIndex][1];
                        drawArrowLine(g2, x, y, nx, ny, radius, 10, 5);
                    }
                }

                // Draw nodes
                if (visited.contains(node)) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.BLUE);
                }
                g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                g2.setColor(Color.BLACK);
                g2.drawString(node.getAd(), x - 5, y + 5);
            }
        }
    }

    private void drawArrowLine(Graphics2D g2, int x1, int y1, int x2, int y2, int radius, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double sin = dy / D, cos = dx / D;

        // Shorten the line by the radius of the circle
        double x1Adjusted = x1 + radius * cos;
        double y1Adjusted = y1 + radius * sin;
        double x2Adjusted = x2 - radius * cos;
        double y2Adjusted = y2 - radius * sin;


        g2.drawLine((int) x1Adjusted, (int) y1Adjusted, (int) x2Adjusted, (int) y2Adjusted);
    }
}