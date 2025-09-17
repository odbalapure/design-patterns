package mvc;

public class MvcAppDemo {
    public static void main(String[] args) {
        // 1. Fetch student record from a "database" (a helper method here).
        StudentModel model = retrieveStudentFromDatabase();

        // 2. Create a View to write student details on the console.
        StudentView view = new StudentView();

        // 3. Create a Controller to manage the Model and View.
        StudentController controller = new StudentController(model, view);

        // 4. Display the initial data.
        System.out.println("Initial student details:");
        controller.updateView();

        System.out.println("\n----------------------------\n");

        // 5. Update the model data using the controller.
        System.out.println("Updating student name to 'John Doe'...");
        controller.setStudentName("John Doe");

        // 6. Display the updated data.
        System.out.println("Updated student details:");
        controller.updateView();
    }

    // Helper method to simulate fetching data.
    private static StudentModel retrieveStudentFromDatabase() {
        StudentModel student = new StudentModel();
        student.setName("Jane Smith");
        student.setRollNo("15UCS100");
        return student;
    }
}
