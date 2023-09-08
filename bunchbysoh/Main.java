package bunchbysoh;

public class Main {
  static class CountsBySoH {
    public int healthy = 0;
    public int exchange = 0;
    public int failed = 0;
  };

  static CountsBySoH countBatteriesByHealth(int[] presentCapacities) {
    CountsBySoH counts = new CountsBySoH();
    int rated_capacity = 120;
    
    for (int capacity : presentCapacities) {
        double soh = (capacity / rated_capacity) * 100; 
        if (soh > 80) {
            counts.healthy++;
        } else if (soh >= 65) {
            counts.exchange++;
        } else {
            counts.failed++;
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
    System.out.println("Done counting :)\n");
  }
  
  static void testBoundaryConditions() {
	    System.out.println("Testing boundary conditions...\n");

	    // Test batteries right at the boundary (80% SoH)
	    int[] boundaryCapacities = {96, 100, 80};  // 80% of 120 is 96
	    CountsBySoH boundaryCounts = countBatteriesByHealth(boundaryCapacities);
	    assert(boundaryCounts.healthy == 1);
	    assert(boundaryCounts.exchange == 2);
	    assert(boundaryCounts.failed == 0);

	    // Test batteries below 65% SoH
	    int[] failedCapacities = {64, 50, 30};  // Below 65% of 120
	    CountsBySoH failedCounts = countBatteriesByHealth(failedCapacities);
	    assert(failedCounts.healthy == 0);
	    assert(failedCounts.exchange == 0);
	    assert(failedCounts.failed == 3);

	    System.out.println("Boundary condition tests complete :)\n");
	}


  public static void main(String[] args) {
    testBucketingByHealth();
    //testBoundaryConditions();
  }
}