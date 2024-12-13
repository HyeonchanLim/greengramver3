package com.code;

public class a {
    public int[] solution(int[] numbers, String direction) {
        int[] answer = {};
        int max = numbers.length-1;

        if (direction.equals("right")){
            answer[0] = numbers[max];
            for (int i=1; i<max; i++){
                answer[i+1] = answer[i];
            }
        }
        } else if (direction.equals("right")) {

        return answer;
    }
}
