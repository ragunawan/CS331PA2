import java.util.*;

public class PA2 {

	public static void main(String[] args) {
		System.out.println("Find the k'th smallest value in an array.");
		Random generator = new Random();
		long timer, alg1, timer2, alg2;
		int[] input = new int[1000];
		int k = 404;
		for (int i = 0; i < input.length-1; i++){
			input[i] = generator.nextInt();
		}
		System.out.println("Array Size: " + input.length);
		System.out.println("K: " + k);

		timer = System.nanoTime();
		System.out.println("Algorithm1 Value: " + kth1(k, input));
		alg1 = System.nanoTime() - timer;
		System.out.println("Time: " + alg1);

		timer2 = System.nanoTime();
		System.out.println("Algorithm2 Value: " + kth2(k, input));
		alg2 = System.nanoTime() - timer2;
		System.out.println("Time: " + alg2);
	}

	public static int kth1 (int k, int[] set){
		int[] newSet = new int[set.length];
		// if |S| = 1, return single element
		if (set.length == 1) return set[0];
		else {
			// else, choose an element randomly from set
			int selected = set[set.length/2];
			// let s1 be less than, s2 equal to, s3 greater than m
			int s1 = 0, s2 = 0, s3 = 0;
			// fill in single partitioned array with tracked indices
			for (int a = 0; a < set.length; a++){
				if(set[a] < selected){
					newSet[s1] = set[a];
					s1++;
				} else if (set[a] > selected){
					newSet[(newSet.length-1)-s3] = set[a];
					s3++;
				} else {
					s2++;
				}
			}
			int sTemp = s1;
			if (s2 > 0){
				for (int b = 0; b < s2; b++){
					newSet[sTemp] = selected;
					sTemp++;
				}
			}
			// if s1 >= k, return SELECT(k, S1)
			if (s1 >= k){
				return kth1(k, Arrays.copyOfRange(newSet, 0, s1));
			} else {
				// else; if (|S1|+|S2| >= K), return m
				if ((s1 + s2) >= k){
					return selected;
				} else {
					// else, return SELECT(k-|S1|-|S2|, S3)
					return kth1(k - s1 - s2, Arrays.copyOfRange(newSet, s1+s2, newSet.length));
				}
			}
		}

	}
// need to switch sort method with decision tree
	public static int kth2 (int k, int[] set){
		// if array is relatively small, use Java Sort instead
		if (set.length < 50){
			Arrays.sort(set);
			return set[k];
		} else {
			int[] newSet = new int[set.length];
			// divide into sequences of five elements and store medians into M
			int[] M = new int[set.length/5];
			for(int i = 0; i < set.length/5; i++){
				int[] temp = Arrays.copyOfRange(set, i*5, (i+1)*5-1);
				Arrays.sort(temp);
				M[i] = temp[temp.length/2];
			}
			Arrays.sort(M);
			// find median of medians as the pivot value
			int selected = M[M.length/2];
			int s1 = 0, s2 = 0, s3 = 0;
			for (int a = 0; a < set.length; a++){
				if(set[a] < selected){
					newSet[s1] = set[a];
					s1++;
				} else if (set[a] > selected){
					newSet[(newSet.length-1)-s3] = set[a];
					s3++;
				} else {
					s2++;
				}
			}
			int sTemp = s1;
			if (s2 > 0){
				for (int b = 0; b < s2; b++){
					newSet[sTemp] = selected;
					sTemp++;
				}
			}
			// if s1 >= k, return SELECT(k, S1)
			if (s1 >= k){
				return kth2(k, Arrays.copyOfRange(newSet, 0, s1));
			} else {
				if ((s1 + s2) >= k){
					// else; if (|S1|+|S2| >= K), return m
					return selected;
				} else {
					// else, return SELECT(k-|S1|-|S2|, S3)
					return kth2(k - s1 - s2, Arrays.copyOfRange(newSet, s1+s2, newSet.length));
				}
			}

		}



	}

}
