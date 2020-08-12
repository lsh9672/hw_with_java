package parallel;

import java.util.Scanner;

//Thread를 이용한 1~10 까지의 합,곱,제곱을 구하는 프로그램 - 합을 구하는 부분
public class PracticeProg {
    public static void main(String[] arg){

        int result=0;

        ThreadProgram1 thread1 = new ThreadProgram1();
        ThreadProgram2 thread2 = new ThreadProgram2();
        thread1.start();
        thread2.start();

        for(int i=1;i<=10;i++){
            result+=i;
            System.out.println("    "+i + "합 : " + result);
        }
        System.out.println("    "+"합의 결과 : "+ result);

    }
}
