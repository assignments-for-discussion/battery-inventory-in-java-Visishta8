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
    // Test when SoH is exactly 80%
    int[] capacities80 = {96}; // 80% of 120
    CountsBySoH counts80 = countBatteriesByHealth(capacities80);
    assert(counts80.healthy == 0);
    assert(counts80.exchange == 1);
    assert(counts80.failed == 0);

    // Test when SoH is exactly 65%
    int[] capacities65 = {78}; // 65% of 120
    CountsBySoH counts65 = countBatteriesByHealth(capacities65);
    assert(counts65.healthy == 0);
    assert(counts65.exchange == 1);
    assert(counts65.failed == 0);

    // Test when present capacity is equal to rated capacity
    int[] capacitiesEqual = {120};
    CountsBySoH countsEqual = countBatteriesByHealth(capacitiesEqual);
    assert(countsEqual.healthy == 1);
    assert(countsEqual.exchange == 0);
    assert(countsEqual.failed == 0);
    
    assert(counts.healthy == 2);
    assert(counts.exchange == 3);
    assert(counts.failed == 1);
    System.out.println("Done counting :)\n");
  }

  public static void main(String[] args) {
    testBucketingByHealth();
  }
}