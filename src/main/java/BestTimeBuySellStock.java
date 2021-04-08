public class BestTimeBuySellStock {
    // we want to remember the min price seen so far, do a diff with the current price,
    // and save the largest diff seen so far
    public static int compute(int[] prices) {
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int price : prices) {
            minPrice = Math.min(price, minPrice);
            int profit = price - minPrice;
            maxProfit = Math.max(profit, maxProfit);
        }
        return maxProfit;
    }
}
