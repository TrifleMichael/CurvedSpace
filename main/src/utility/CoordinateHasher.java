package utility;

public class CoordinateHasher {


    public static long hashIntegers(int x, int y) {
        long combined = ((long) x << 32) | (y & 0xFFFFFFFFL);

        combined = ((combined >>> 33) ^ combined) * 0xFF51AFD7ED558CCDL;
        combined = ((combined >>> 33) ^ combined) * 0xC4CEB9FE1A85EC53L;
        combined = (combined >>> 33) ^ combined;

        return combined;
    }

    public static void main(String[] args) {
        int chance = 100;
        long countPositive = 0;
        long countNegative = 0;
        for(int x = -1000; x < 1000; x++) {
            for(int y = -1000; y < 1000; y++) {
                if (hashIntegers(x, y) % chance == 0) {
                    countPositive++;
                } else {
                    countNegative++;
                }
            }
        }
        System.out.println((double)countPositive / (countNegative + countPositive));
    }
}
