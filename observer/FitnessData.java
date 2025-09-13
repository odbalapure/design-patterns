package observer;

import java.util.ArrayList;
import java.util.List;

public class FitnessData implements FitnessDataSubject {
    private int steps;
    private int activeMinutes;
    private int calories;
    List<FitnessDataObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(FitnessDataObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(FitnessDataObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (FitnessDataObserver observer : observers) {
            observer.update(this);
        }
    }

    public void newFitnessDataPushed(int steps, int activeMinutes, int calories) {
        this.steps = steps;
        this.activeMinutes = activeMinutes;
        this.calories = calories;

        System.out.println("\nFitnessData: New data received – Steps: " + steps +
                ", Active Minutes: " + activeMinutes + ", Calories: " + calories);
        notifyObservers();
    }

    public void dailyReset() {
        this.steps = 0;
        this.activeMinutes = 0;
        this.calories = 0;

        System.out.println("\nFitnessData: Daily reset - Steps: " + steps + ", Active Minutes: " + activeMinutes
                + ", Calories: " + calories);
        notifyObservers();
    }

    public int getSteps() {
        return steps;
    }

    public int getActiveMinutes() {
        return activeMinutes;
    }

    public int getCalories() {
        return calories;
    }
}
