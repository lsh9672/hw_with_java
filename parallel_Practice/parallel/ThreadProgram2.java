package parallel;
//Thread를 이용한 1~10 까지의 합,곱,제곱을 구하는 프로그램 - 2^10을 구하는 부분
public class ThreadProgram2 extends Thread {

        @Override
        public void run() {
            int sq=1;
            int count=0;
           while(count!=10){
                sq*=2;
                count++;
                System.out.println("      " +count + "제곱 : " + sq);
            }
            System.out.println("      " +"제곱의 결과 : "+ sq);
        }

}
