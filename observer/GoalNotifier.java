package observer;

public class GoalNotifier implements FitnessDataObserver {
    private final int stepGoal = 10000;

    @Override
    public void update(FitnessData data) {
        if (data.getSteps() >= stepGoal) {
            System.out.println("Notifier → 🎉 Goal Reached! You've hit " + stepGoal + " steps!");
        } else {
            System.out.println("Notifier → Keep going! " + (stepGoal - data.getSteps()) + " steps to go.");
        }
    }
}
