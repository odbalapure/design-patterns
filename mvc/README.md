## MVC

Imagine building a car, we would organize it into distinct systems -- the engine (core logic making the car move), dashboard (displays info. to the driver), controls like steering wheel and pedals (take inputs). Each system has a clear responsibility, they communicate through well defined interfaces.

Separation of concerns is the heart of MVC pattern. It is not just a pattern, its a foundational architecture philosophy for designing software, especially apps with user interfaces.

## What is it

It divides the logic into 3 main logical components: Model, view and controller.

- **Model (the brains)**: This is the core; manages the data, logic and rules of the application. It is responsbile for business logic -- processing data, interacting with the database, enforcing application rules. It does not know about the UI. Its mainly concerned with state and behavior of application's data.

- **View (the face)**: This is what the user sees and interacts with. Its job is to present data from the model to the user. The view is supposed to be dumb - no application logic. It simply recievs data from the model and displays it. There can be multiple views for a single model (eg: web page, mobile app, desktop app can display the same date).

- **The controller (traffic cop)**: Acts as the intermediary b/w the model and the view. It listens for user inputs from the view (eg: button clicks, form submission), processes it and tells the model what to do. After the model updates the state, the controller is responsible for telling the view to update its display.

Consider an example where we update our profile name. Once the model state has changed the UI needs to reflect the changes:

1. **Passive model**: The controller after updating the model, explicitly tells the view to refresh itself. The view asks the model for the latest data (eg: `model.getUsername()`); redraws part of the screen showing the name.

2. **Active model**: The model, after updating itself, fires an event or notification (eg: observer pattern). The view is registered as a listener to the model and automatically receives this notification. Upon receiving it, the view queries the model for new data and updates itself.

## Why use it

- **Separation of concerns**: Keeping the UI code separate from business logic, application becomes easier to understand, debug and modify.

- **Parallel development**: Components are decoupled, different teams can work on them simultaneously.

- **Code reusability**: The same model can be used with different views. Eg: Web interface (view 1) and REST API (view 2) that both use the same underlying model and controller logic to manage data.

- **Testability**: Separating logic into model and controller makes them easier to unit test. We can test the business rules and data logic w/o needing to render a UI or simulate button clicks.
