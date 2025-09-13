package facade;

public class DeploymentAppFacade {
    public static void main(String[] args) {
        DeploymentFacade deploymentFacade = new DeploymentFacade();

        deploymentFacade.deployApplication("main", "prod.server.example.com");

        System.out.println("\n--- Deploying feature branch to staging ---");
        deploymentFacade.deployApplication("feature/new-ui", "staging.server.example.com");
    }
}
