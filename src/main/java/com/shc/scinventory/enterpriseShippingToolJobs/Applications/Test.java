package com.shc.scinventory.enterpriseShippingToolJobs.Applications;

import java.util.LinkedList;
import java.util.List;

public class Test {
	public static void main(String [] args) {
		List<Integer> list1 = new LinkedList<Integer> ();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		List<Integer> list2 = new LinkedList<Integer> ();
		list2.add(4);
		list2.add(5);
		list2.add(6);
		List<Integer> list3 = new LinkedList<Integer> ();
		list3.add(7);
		list3.add(8);
		list3.add(9);
		List<List<Integer>> matrix = new LinkedList<List<Integer>>();
		matrix.add(list1);
		matrix.add(list2);
		matrix.add(list3);
		
		System.out.println(matrix.get(0));
		System.out.println(matrix.get(1));
		System.out.println(matrix.get(2));

		//recursive
		//findEdge(matrix, 0, 0);
		int x = 0,  y = 0;
		for (; y< matrix.size(); y++ ){
			//printVertical(matrix, y, x);

			printVerticalLoop(matrix, y, x);
		}
		
		for (; x < matrix.get(0).size(); x++) {
			//printVertical(matrix, y-1, x);
			printVerticalLoop(matrix, y-1, x+1);

		}
	}
	
	public static void printVerticalLoop (List<List<Integer>> matrix, int y, int x) {
//		System.out.println("y = " + y + ",x = " + x);
		for (int i = y, j = x; i>-1 && matrix.get(0).size()>j; i--, j++) {
			System.out.print(matrix.get(i).get(j) + ",");
		}
	}
	
	public static void findEdge (List<List<Integer>> matrix, int y, int x) {
		 if(matrix.size() == y || matrix.get(0).size() == x 
				 || (x!=0 && y!=(matrix.get(0).size() -1)))
			 return;
		 
		 printVertical(matrix, y , x);
		 System.out.println();
		 findEdge(matrix, y +1, x);
		 findEdge(matrix, y , x+1);
		
	}
	
	public static void printVertical(List<List<Integer>> matrix, int y, int x) {
		if(y < 0 || matrix.get(0).size() == x ) {
			return;
		}
		
		System.out.print(matrix.get(y).get(x) + ",");
		printVertical(matrix, y -1, x+1);

	}
}
