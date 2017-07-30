/* KC
 * 7/21/2017
 * RandomizedQueue.java
 */

import java.util.Comparator;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private Point[] toSort;
    private int numberOfSegments = 0;
    private LineSegment[] segments;
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        Comparator<Point> slope = points[0].slopeOrder();
        toSort = points;
        Arrays.sort(toSort, slope);
        
        int[] checker = new int[points.length];
        int count = 0;
        for (int i = 0; i < toSort.length - 1; i++) {
            if (toSort[i] != toSort[i+1]) {
                if (count >= 3) {
                    checker[i] = -1;
                    checker[i-count] = 1;
                    numberOfSegments++;
                }
                count = 0;
            } else {
                count++;
            }
        }
        
        segments = new LineSegment[numberOfSegments];
        
     // then find the smallest and the largest points
        int first = 0;
        count = 0;
        for (int i = 0; i < toSort.length; i++) {
            if (checker[i] == 1) {first = i;}
            else if (checker[i] == -1) {
                Point large = toSort[i-1];
                Point small = toSort[i];
                for (int j = first; j <= i; j++) {
                    if (toSort[j].compareTo(large) == 1) { 
                        //if the first point is greater than already set
                        large = toSort[j];
                    }
                    if (toSort[j].compareTo(small) == -1) {
                        small = toSort[j];
                    }
                }
                
                segments[count] = new LineSegment(large, small);
                count++;
                
                
            }
        }
    }
    
    
    public int numberOfSegments() {        // the number of line segments
        return numberOfSegments;
    }
    
    public LineSegment[] segments() {               // the line segments
        return segments;
    }
    
    public static void main(String[] args) {
        // read the n points from a file
        int num = StdIn.readInt();
        Point[] points = new Point[num];
        for (int i = 0; i < num; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x,y);
        }

           // draw the points
           StdDraw.enableDoubleBuffering();
           StdDraw.setXscale(0, 32768);
           StdDraw.setYscale(0, 32768);
           for (Point p : points) {
               p.draw();
           }
           StdDraw.show();

           // print and draw the line segments
           FastCollinearPoints collinear = new FastCollinearPoints(points);
           for (LineSegment segment : collinear.segments()) {
               StdOut.println(segment);
               segment.draw();
           }
           StdDraw.show();
    }
 }