/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest_drone_distance;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author argeu
 */
public class CityGraph {

    private City list[];
    private static final int DIS_MAX = 250;
    private int dis;

    public CityGraph(City adjList[]) {
        list = adjList;
        dis = 0;
    }

    public void createGraph() {
        for (int i = 0; i < 800; i++) {
            for (int n = 0; n < 800; n++) {
                dis = list[i].getDistance(list[n]);
                if (dis < DIS_MAX && dis != 0) {
                    list[i].getList().add(new Edge(list[n], dis));
                }
            }
        }
    }

    public Stack<City> dijsktraAlg(City first, City second) {
        PriorityQueue<Edge> q = new PriorityQueue<>(new edgeComp());
        LinkedList<City> visited = new LinkedList<>();
        Edge u;
        int source = 0;
        int alt;

        for (int i = 0; i < 800; i++) {
            list[i].setPathDisMax();
            list[i].setPrev(null);
            if (first.toString().equals(list[i].toString())) {
                source = i;
                list[source].setPathDis(0);
                q.add(new Edge(list[source], 0));
            }
        }
       
        while (!q.isEmpty()) {
            u = q.remove();
            visited.add(u.getCity());
            Iterator<Edge> iter = u.getCity().getList().iterator();
            while (iter.hasNext()) {
                Edge eTemp = iter.next();
                if (eTemp.getCity().equals(second)) {
                    eTemp.getCity().incPathNum();
                }
                alt = u.getWeight() + eTemp.getWeight();
                if (alt < eTemp.getCity().getPathDis()) {
                    eTemp.getCity().setPathDis(alt);
                    eTemp.getCity().setPrev(u.getCity());
                    q.add(new Edge(eTemp.getCity(), alt));
                }
            }
        }

        Iterator<City> iter2 = visited.iterator();
        while (iter2.hasNext()) {
            City temp2 = iter2.next();
            if (temp2.equals(second)) {
                Stack<City> stack = new Stack<>();
                if (temp2.getPrev() != null || temp2 == list[source]) {
                    while (temp2 != null) {
                        stack.push(temp2);
                        temp2 = temp2.getPrev();
                    }
                    return stack;
                }
            }
        }

        return null;
    }

    public class edgeComp implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            Edge first, second;
            first = o1;
            second = o2;
            if (second.getWeight() < first.getWeight()) {
                return -1;
            }
            if (second.getWeight() > first.getWeight()) {
                return 1;
            } else {
                return 0;
            }
        }

    }
}
