//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        FindScore fs = new FindScore();
        //Example 1
        int[] num1 = {2,1,3,4,5,2};

        //Example 2
        int[] num2 = {2,3,5,1,3,2};

        System.out.println("Example 1: "+ fs.findScore(num1));
        System.out.println("Example 2: "+ fs.findScore(num2));
    }
}