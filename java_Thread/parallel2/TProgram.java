package parallel2;

public class TProgram implements Runnable {
    int inputValue;
    int result=0;
    boolean check=false;

    public TProgram(int inputValue) {
        this.inputValue = inputValue;
    }

    @Override
    public void run() {
        if(inputValue%2==0){
            for(int i=(inputValue/2) +1;i<=inputValue;i++){
                result+=i;
                System.out.println("    "+i + "합 : " + result);
            }
            check = true;
        }
        else{
            for (int i = (inputValue/2)+2; i <= inputValue; i++) {
                result+=i;
                System.out.println("    "+i + "합 : " + result);
            }
            check = true;
        }

    }
}
