package bunchbysoh;

public class Main {
	static final double HEALTHY_THRESHOLD = 80.0;
	static final double EXCHANGE_THRESHOLD = 65.0;
	static final double RATED_CAPACITY = 120.0;

	
  static class CountsBySoH {
    public int healthy = 0;
    public int exchange = 0;
    public int failed = 0;
  };

  static CountsBySoH countBatteriesByHealth(int[] boundaryCapacities) {
    CountsBySoH counts = new CountsBySoH();
   
    for (double capacity : boundaryCapacities) {
    	 if (capacity <= 0) {
    		 System.err.println("Invalid capacity detected: " + capacity);
             continue;
    	    } else {
    	        double soh = ((double) capacity / RATED_CAPACITY) * 100;
    	        if (soh > HEALTHY_THRESHOLD) {
    	            counts.healthy++;
    	        } else if (soh >= EXCHANGE_THRESHOLD) {
    	            counts.exchange++;
    	        } else {
    	            counts.failed++;
    	        }
    	    }
    }

    return counts;
  }

  static void testBucketingByHealth() {
	  System.out.println("Counting batteries by SoH...\n");
	    int[] presentCapacities = {115, 118, 80, 95, 91, 77};
	    CountsBySoH counts = countBatteriesByHealth(presentCapacities);
	    assert(counts.healthy == 2);
	    assert(counts.exchange == 3);
	    assert(counts.failed == 1);
	    System.out.println("Actual counts: Healthy=" + counts.healthy + ", Exchange=" + counts.exchange + ", Failed=" + counts.failed);
	    System.out.println("Done counting :)\n");
  }
  
  static void testBoundaryConditions() {
	    System.out.println("Testing boundary conditions...\n");

	    // Test batteries right at the boundary (80% SoH)
	    int[] boundaryCapacities = {96, 100, 80};  // 80% of 120 is 96
	    CountsBySoH boundaryCounts = countBatteriesByHealth(boundaryCapacities);
	    System.out.println("Boundary Counts (80% SoH):");
	    System.out.println("Healthy: " + boundaryCounts.healthy);
	    System.out.println("Exchange: " + boundaryCounts.exchange);
	    System.out.println("Failed: " + boundaryCounts.failed);
	    
	    assert(boundaryCounts.healthy == 1);
	    assert(boundaryCounts.exchange == 2);
	    assert(boundaryCounts.failed == 0);

	    // Test batteries below 65% SoH
	    int[] failedCapacities = {64, 50, 30};  // Below 65% of 120
	    CountsBySoH failedCounts = countBatteriesByHealth(failedCapacities);
	    System.out.println("\nBoundary Counts (Below 65% SoH):");
	    System.out.println("Healthy: " + failedCounts.healthy);
	    System.out.println("Exchange: " + failedCounts.exchange);
	    System.out.println("Failed: " + failedCounts.failed);
	    
	    assert(failedCounts.healthy == 0);
	    assert(failedCounts.exchange == 0);
	    assert(failedCounts.failed == 3);

	    System.out.println("Boundary condition tests complete :)\n");
	}


  
  public static void main(String[] args) {
	    testBucketingByHealth();
	    testBoundaryConditions();
  }
}