package parallel2;
import java.util.Scanner;

public class PracticeProgram2 {
    public static void main(String[] arg){
        int inputValue;
        int result=0;
        int fin=0;
        Scanner sc = new Scanner(System.in);
        inputValue = sc.nextInt();

        TProgram tprogram = new TProgram(inputValue);
        Thread thread = new Thread(tprogram);
        thread.start();

        if(inputValue%2==0) {
            for (int i = 1; i <= inputValue/2; i++) {
                result+=i;
                System.out.println("    "+i + "합 : " + result);
            }
        }
        else{
            for (int i = 1; i <= (inputValue/2)+1; i++) {
                result+=i;
                System.out.println("    "+i + "합 : " + result);
            }
        }

       while(!tprogram.check);
           fin = result + tprogram.result;
           System.out.println("총 합 : " + fin);
    }
}
