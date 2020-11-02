package task2;

import java.util.Arrays;

public class TimeMeasure {
    private Double[] timesAvg;
    private Integer[] numOfSurveys;

    public TimeMeasure(int M){
        timesAvg = new Double[M/2];
        numOfSurveys = new Integer[M/2];
        Arrays.fill(timesAvg, 0.0);
        Arrays.fill(numOfSurveys,0);
    }

    public double getTimeAtIndex (int i){
        return timesAvg[i];
    }

    public void modifyNumberOfSurveys(int number, int i){
        numOfSurveys[number] += i;
    }

    public void countNewAvgTime(int numOfPortion, long time) {
        double lastAvg = timesAvg[numOfPortion - 1];
        if (numOfSurveys[numOfPortion - 1] == 1) timesAvg[numOfPortion - 1] = (double) time;
        else {
            timesAvg[numOfPortion - 1] =
                    ((numOfSurveys[numOfPortion - 1] - 1) * lastAvg + time) / numOfSurveys[numOfPortion - 1];
        }
    }
}
