package observer;

public class FitnessAppObserverDemo {
    public static void main(String[] args) {
        // Subject
        FitnessData fitnessData = new FitnessData();

        // Observers
        LiveActivityDisplay display = new LiveActivityDisplay();
        ProgressLogger logger = new ProgressLogger();
        GoalNotifier notifier = new GoalNotifier();

        // Register observers
        fitnessData.registerObserver(display);
        fitnessData.registerObserver(logger);
        fitnessData.registerObserver(notifier);

        // Simulate new fitness data being pushed
        fitnessData.newFitnessDataPushed(500, 5, 20);

        // Remove notifier
        fitnessData.removeObserver(notifier);

        // Simulate new fitness data being pushed
        fitnessData.newFitnessDataPushed(100, 5, 20);

        // Daily reset
        fitnessData.dailyReset();
    }
}
