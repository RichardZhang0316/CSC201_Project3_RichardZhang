import java.util.*;
public class HashTables {

    // Problem 4: a function that find and print a pair of number in the array that add up to target.
    public static void findSumPair(int target, int[] array){
        // an empty hash table
        Hashtable<Integer,Integer> map=new Hashtable<>();

        for(int i=0; i<array.length; i++){
            // check if target-array[i] exists in the hash table
            if (map.containsKey(target - array[i])){
                System.out.printf("Pair found: %d , %d",array[map.get(target - array[i])] , array[i]);
                return;
            }
           // store the integer we get in the hash table
            map.put(array[i],i);
        }
        // if we don't find a pair whose sum is equal to the target, we print the following message
        System.out.println("Pair not found");
    }

    // Problem 5: a function that takes an array of integers as input
    // and returns the number of distinct absolute values in the input array.
    public static int DistinctValues(int[] array){
        // by inserting the absolute value of all the elements in the array into a hash set, because
        // we know a hash set doesn't allow duplicate elements, we can get the number of
        // distinct absolute values in the input array by simply counting the size of the hash set.
        Set<Integer> distinctAbsoluteValues=new HashSet<>();
        for(int i=0;i< array.length;i++){
            distinctAbsoluteValues.add(Math.abs(array[i]));
        }
        return distinctAbsoluteValues.size();
    }

    public static void main(String[] args){
        // Problem 4 test case
        int[] nums = { 5, 7, 2, 5, 3, 9, -6};
        int target = 10;
        findSumPair(target,nums);

        // Problem 5 test case
        System.out.println("\nThe number of distinct absolute values in the input array is "+DistinctValues(nums));
    }
}
