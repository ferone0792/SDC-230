/* HeistInfoStorage.java
 * This class stores stores momey and crewCount*/
public class HeistInfoStorage {
    public double money = 0.0;
    public int crewCount = 0;    
    public boolean unexpectedCostsApplied = false; //Keeps track of only once choices
    public boolean crewCutCalculated = false;
    // Prints current money and crew 
    public void printStatus() {
        System.out.println("----------------------------------------------------------");
        System.out.printf("Current Money: $%.2f%n", money);
        System.out.println("Crew Members: " + crewCount);
        System.out.println("----------------------------------------------------------");
    }
}