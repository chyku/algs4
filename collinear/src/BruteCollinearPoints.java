/* KC
 * 7/26/2017
 * BruteCollinearPoints.java
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments;
    
    
    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) {throw new java.lang.IllegalArgumentException();}
        
        
        int plength = points.length;
        // checking for repeats (not very time efficient)
        for (int i = 0; i < plength; i++) {
            for (int j = 0; j < plength; j++) {
                if (i != j) {
                    if (points[i] == points[j] || points[i] == null)
                        throw new java.lang.IllegalArgumentException();
                }
            }
        }
        
        double[] slopes = new double[(plength*(plength-1))/2];
        int[] lengths = new int[(plength*(plength-1))/2];
        Point[] notChecked = new Point[(plength*(plength-1))];
        
        int size = 1;
        
        slopes[0] = points[0].slopeTo(points[1]);
        notChecked[0] = points[0];
        notChecked[1] = points[1];
        lengths[0] = 1;
        
        for (int i = 0; i < plength; i++) {
            for (int j = i+1; j < plength; j++) {
                if (i != j) {
                    double newSlope = points[i].slopeTo(points[j]);
                    boolean isNew = true;
                    // check if new
                    for (int m = 0; m < size; m++) {
                        if (newSlope == slopes[m]) {
                            lengths[m]++;
                            isNew = false;
                            // change index??
                            
                            if (points[i].compareTo(notChecked[m*2]) == -1) { 
                               //if the first point is greater than already set
                                notChecked[m*2] = points[i];
                            }
                            if (points[i].compareTo(notChecked[m*2+1]) == 1) {
                                notChecked[m*2+1] = points[i];
                            }
                            if (points[j].compareTo(notChecked[m*2]) == -1) { 
                                //if the first point is greater than already set
                                notChecked[m*2] = points[j];
                            }
                            if (points[j].compareTo(notChecked[m*2+1]) == 1) {
                                notChecked[m*2+1] = points[j];
                            }
                            
                            //break;
                        }
                    }
                    
                    if (isNew) {
                        slopes[size] = newSlope;
                        lengths[size]++;
                        notChecked[size*2] = points[i];
                        notChecked[size*2+1] = points[j];
                        size++;
                    }
                }
            }
        }
        
        for (int i = 0; i < size; i++) {
            if (lengths[i] >= 6) {
                numberOfSegments++;
            }
        }
        
        segments = new LineSegment[numberOfSegments];
        
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (lengths[i] >= 4) {
                segments[j] = new LineSegment(notChecked[i*2], notChecked[i*2+1]);
                j++;
            }
        }
        
    }
    
    public int numberOfSegments() { // the number of line segments
        return numberOfSegments;
    }
    
    public LineSegment[] segments() { // the line segments
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
           BruteCollinearPoints collinear = new BruteCollinearPoints(points);
           for (LineSegment segment : collinear.segments()) {
               StdOut.println(segment);
               segment.draw();
           }
           StdDraw.show();
    }
}
