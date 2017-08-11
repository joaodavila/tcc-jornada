import java.util.Arrays;

public class TestaEquili {

	public static void main(String[] args) {
		int arr[] = {-1,3,-4,5,1,-6,2,1};
		System.out.println(equi(arr));
	}
	
	static int equi(int arr[]) {
		int n = arr.length;
	    if (n==0) return -1;
	    
	    long sum = 0;
	    
	    int i; 
	    sum =  Arrays.stream(arr)
                .sum();
	    
	    long sum_left = 0;  
        
	    for(i=0;i<n;i++) {
	        long sum_right = sum - sum_left - (long) arr[i];
	        if (sum_left == sum_right) return i;
	        sum_left += (long) arr[i];
	    }
	    
	    return -1; 
	} 



}
