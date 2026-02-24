// Subtracts mostly randomcosts  and allows user to subtract one time only; Prevents money from going negative

import java.util.Random;
public class Option2UnexpectedCosts {
    public void run(Random random, HeistContext ctx) {
        if (ctx.unexpectedCostsApplied) {
            System.out.println("You already applied unexpected costs. You can only do that once.");
            return;
        }

        ctx.printStatus();
        System.out.println("=== Unexpected Costs ==="); //Range of costs for each category. Some are fixed
        double vaultDrillRepairs = randomIntInRange(random, 1000, 3500);
        double vehicleDamage = randomIntInRange(random, 250, 1800);
        double disguises = randomIntInRange(random, 12, 70);
        double bankMap = 800.0;
        double cctvHack = 700.0;
        double sleepingGas = randomIntInRange(random, 50, 500);
        System.out.printf("1) Vault drill repairs: $%.2f%n", vaultDrillRepairs);
        System.out.printf("2) Vehicle damage:      $%.2f%n", vehicleDamage);
        System.out.printf("3) Disguises:           $%.2f%n", disguises);
        System.out.printf("4) Bank map:            $%.2f%n", bankMap);
        System.out.printf("5) CCTV hack:           $%.2f%n", cctvHack);
        System.out.printf("6) Sleeping gas:        $%.2f%n", sleepingGas);
        double total = vaultDrillRepairs + vehicleDamage + disguises + bankMap + cctvHack + sleepingGas;
        System.out.println("----------------------------------------------------------");
        System.out.printf("Total unexpected costs: $%.2f%n", total);
        if (ctx.money - total < 0) {
            System.out.println("Error: This would make money negative. Costs will NOT be applied.");
            return;
        }

        ctx.money -= total;
        ctx.unexpectedCostsApplied = true;
        System.out.printf("Updated Money: $%.2f%n", ctx.money);
    }

    private int randomIntInRange(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}