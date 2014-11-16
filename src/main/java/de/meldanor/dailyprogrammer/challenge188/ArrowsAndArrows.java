package de.meldanor.dailyprogrammer.challenge188;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ArrowsAndArrows {

    public static void main(String[] args) {
        challenge("simpledata");
        challenge("challengeData");
    }

    private static void challenge(String fileName) {
        try {
            Instant start = Instant.now();
            List<String> strings = Files.readAllLines(new File("src/main/resources/challenge188/" + fileName + ".txt").toPath());
            String head = strings.get(0);
            // Skip head
            List<String> lines = strings.subList(1, strings.size());
            ArrowsAndArrows arrowsAndArrows = new ArrowsAndArrows(head, lines);

            List<Node> largestCircle = arrowsAndArrows.findLargestCircle();
            Instant finish = Instant.now();
            System.out.println("Duration: " + Duration.between(start, finish));
            String s = arrowsAndArrows.printPath(largestCircle);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int TOP = '^';
    public static final int RIGHT = '>';
    public static final int BOTTOM = 'v';
    public static final int LEFT = '<';

    private Node[][] matrix;
    private int width;
    private int height;

    public ArrowsAndArrows(String head, List<String> lines) {
        String[] split = head.split(" ");
        this.width = Integer.valueOf(split[0]);
        this.height = Integer.valueOf(split[1]);
        this.matrix = getNodes(lines);
    }

    private Node[][] getNodes(List<String> lines) {
        Node[][] matrix;

        matrix = new Node[height][width];
        if (lines.size() != height) {
            throw new IllegalStateException("Lines != height");
        }
        for (int i = 0; i < height; ++i) {
            String line = lines.get(i);
            char[] chars = line.toCharArray();
            if (chars.length != width) {
                throw new IllegalStateException("CharCount != width");
            }
            for (int j = 0; j < width; ++j) {
                matrix[i][j] = new Node(j, i, chars[j]);
            }
        }
        return matrix;
    }

    public List<Node> searchCircle(Node start) {
        List<Node> path = new ArrayList<>();
        Set<Node> alreadyVisited = new HashSet<>();
        Node cur = start;
        do {
            int dir = cur.direction;
            int xDiff = 0;
            int yDiff = 0;
            switch (dir) {
                case TOP:
                    yDiff = -1;
                    break;
                case RIGHT:
                    xDiff = 1;
                    break;
                case BOTTOM:
                    yDiff = 1;
                    break;
                case LEFT:
                    xDiff = -1;
                    break;
            }

            int x = (cur.x + xDiff);
            x = (x < 0) ? (width - (Math.abs(x) % width)) % width : (x % width);
            int y = (cur.y + yDiff);
            y = (y < 0) ? (height - (Math.abs(y) % height)) % height : (y % height);
            path.add(cur);
            alreadyVisited.add(cur);
            cur = matrix[y][x];
        } while (!alreadyVisited.contains(cur));
        path = path.subList(path.indexOf(cur), path.size());
        return path;
    }

    public List<Node> findLargestCircle() {
        List<Node> maxPath = Collections.emptyList();
        int maxPathLength = maxPath.size();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Node start = matrix[y][x];
                List<Node> curPath = searchCircle(start);
                if (curPath.size() > maxPathLength) {
                    maxPathLength = curPath.size();
                    maxPath = curPath;
                }
            }
        }

        return maxPath;
    }

    public String printPath(List<Node> path) {
        StringBuilder sBuilder = new StringBuilder(width * height);
        sBuilder.append("Length: ").append(path.size()).append(System.lineSeparator());
        Set<Node> tmpSet = new HashSet<>(path);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Node cur = matrix[y][x];
                if (tmpSet.contains(cur)) {
                    sBuilder.append((char) cur.direction);
                } else {
                    sBuilder.append(' ');
                }

            }
            sBuilder.append(System.lineSeparator());
        }
        return sBuilder.toString();
    }

    private class Node {
        final int x;
        final int y;
        final int direction;


        private Node(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", direction=" + direction +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return direction == node.direction && x == node.x && y == node.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + direction;
            return result;
        }
    }
}
