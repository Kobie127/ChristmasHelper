import java.io.*;

 class permutations {
     // Function prints all combinations of numbers 1, 2, ...MAX_POINT
     // that sum up to n.
     // i is used in recursion keep track of index in arr[] where next
     // element is to be added. Initital value of i must be passed as 0
     static void printCompositions(int arr[], int n, int i)
     {
         int MAX_POINT = 3;
         if (n == 0)
         {
             printArray(arr, i);
         }
         else if(n > 0)
         {
             for (int k = 1; k <= MAX_POINT; k++)
             {
                 arr[i]= k;
                 printCompositions(arr, n-k, i+1);
             }
         }
     }

     // Utility function to print array arr[]
     static void printArray(int arr[], int m)
     {
         for (int i = 0; i < m; i++)
             System.out.print(arr[i] + " ");
         System.out.println();
     }


     // Driver program
     public static void main (String[] args)
     {
         int n = 7;
         int size = 100;
         int[] arr = new int[size];
         System.out.println("Different compositions formed by 1, 2 and 3 of "+ n + " are");
         printCompositions(arr, n, 0);
     }
 }

// Contributed by Pramod Kumar