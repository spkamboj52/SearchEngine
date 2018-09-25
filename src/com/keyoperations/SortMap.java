package com.keyoperations;

import java.util.List;
import java.util.Map.Entry;

public class SortMap {
	int partition(List<Entry<String, Integer>> list, int low, int high)
    {
        int pivot = list.get(high).getValue(); 
//        System.out.println("Pivot: "+pivot);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (list.get(j).getValue() <= pivot)
            {
                i++;
 
                // swap arr[i] and arr[j]
                Entry<String, Integer> temp = list.get(i);
                
                
                
                
                list.set(i, list.get(j));
                list.set(j,temp);
//                System.out.println(temp+"  "+list.get(i)+"  "+list.get(j));
                //System.out.println(temp);
            
            }
        }
 
        // swap arr[i+1] and arr[high] (or pivot)
        Entry<String, Integer> temp = list.get(i+1);
        list.set(i+1, list.get(high));        
        list.set(high, temp);
        return i+1;
    }
 
 
    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(List<Entry<String, Integer>> list, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is 
              now at right place */
            int pi = partition(list, low, high);
 
            // Recursively sort elements before
            // partition and after partition
            sort(list, low, pi-1);
            sort(list, pi+1, high);
        }
    }
}
