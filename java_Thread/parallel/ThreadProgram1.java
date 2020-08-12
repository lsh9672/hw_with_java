package parallel;
//Thread를 이용한 1~10 까지의 합,곱,제곱을 구하는 프로그램 - 곱을 구하는 부분

public class ThreadProgram1 extends Thread{

    @Override
    public void run() {
        int mul=1;
        for(int i=1;i<=10;i++){
            mul*=i;
            System.out.println(i + "곱 : " + mul);
        }
        System.out.println("곱의 결과 : "+ mul);
    }
}
