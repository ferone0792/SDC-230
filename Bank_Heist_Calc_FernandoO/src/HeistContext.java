/*This class stores shared info that all menu options need for money and crew count
 *Has flags that enforce "only once" rules*/
public class HeistContext {
    public double money = 0.0;
    public int crewCount = 0;   
    public boolean unexpectedCostsApplied = false;//Keeps track of the only once choices
    public boolean crewCutCalculated = false;
    public void printStatus() { //Prints current crew count and moemny on top of menu
        System.out.println("----------------------------------------------------------");
        System.out.printf("Current Money: $%.2f%n", money);
        System.out.println("Crew Members: " + crewCount);
        System.out.println("----------------------------------------------------------");
    }
}