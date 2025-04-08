import java.io.*;
import java.util.*;

public class GymManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginPage loginPage = new LoginPage(scanner);

        loginPage.run();
    }

    public static class LoginPage {
        private Scanner scanner;
        private String username;
        private String password;

        public LoginPage(Scanner scanner) {
            this.scanner = scanner;
        }

        public void run() {
            while (true) {
                System.out.println("Welcome to the Gym Management System!");
                System.out.print("Enter Username: ");
                username = scanner.nextLine();
                System.out.print("Enter Password: ");
                password = scanner.nextLine();

                if (validateLogin(username, password)) {
                    System.out.println("Login successful!");

                    if (username.equals("admin")) {
                        AdminPage adminPage = new AdminPage(scanner);
                        adminPage.run();
                    } else if (username.equals("trainer")) {
                        TrainerPage trainerPage = new TrainerPage(scanner);
                        trainerPage.run();
                    } else {
                        UserPage userPage = new UserPage(scanner);
                        userPage.run();
                    }
                } else {
                    System.out.println("Invalid username or password. Try again.");
                }
            }
        }

        private boolean validateLogin(String username, String password) {
            try (BufferedReader br = new BufferedReader(new FileReader("credentials.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] credentials = line.split(":");
                    if (credentials.length == 2) {
                        String storedUsername = credentials[0].trim();
                        String storedPassword = credentials[1].trim();

                        if (username.equals(storedUsername) && password.equals(storedPassword)) {
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading credentials file.");
            }
            return false;
        }
    }

    public static class AdminPage {
        private Scanner scanner;
        private List<String> trainers = new ArrayList<>();
        private List<String> members = new ArrayList<>();

        public AdminPage(Scanner scanner) {
            this.scanner = scanner;
        }

        public void run() {
            while (true) {
                System.out.println("\n--- Admin Page ---");
                System.out.println("1. Add Trainer");
                System.out.println("2. Manage Memberships");
                System.out.println("3. View Reports & Analytics");
                System.out.println("4. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addTrainer();
                        break;
                    case 2:
                        manageMemberships();
                        break;
                    case 3:
                        viewReports();
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }

        private void addTrainer() {
            System.out.print("Enter Trainer Name: ");
            String trainerName = scanner.nextLine();
            trainers.add(trainerName);
            System.out.println("Trainer " + trainerName + " added successfully!");
        }

        private void manageMemberships() {
            System.out.println("Managing Memberships...");
            System.out.println("Enter Member Name to Register: ");
            String memberName = scanner.nextLine();
            members.add(memberName);
            System.out.println("Member " + memberName + " registered successfully!");
        }

        private void viewReports() {
            System.out.println("Total Trainers: " + trainers.size());
            System.out.println("Total Members: " + members.size());
        }
    }

    public static class TrainerPage {
        private Scanner scanner;
        private Map<String, String> assignedPlans = new HashMap<>();

        public TrainerPage(Scanner scanner) {
            this.scanner = scanner;
        }

        public void run() {
            while (true) {
                System.out.println("\n--- Trainer Page ---");
                System.out.println("1. Assign Workout & Diet Plan");
                System.out.println("2. View Feedback");
                System.out.println("3. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        assignPlan();
                        break;
                    case 2:
                        viewFeedback();
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }

        private void assignPlan() {
            System.out.print("Enter Member Name: ");
            String memberName = scanner.nextLine();
            System.out.print("Enter Workout & Diet Plan for " + memberName + ": ");
            String plan = scanner.nextLine();
            assignedPlans.put(memberName, plan);
            System.out.println("Workout & Diet Plan assigned to " + memberName);
        }

        private void viewFeedback() {
            System.out.println("Viewing Feedback...");
        }
    }

    public static class UserPage {
        private Scanner scanner;
        private Map<String, String> feedback = new HashMap<>();
        private List<String> transactions = new ArrayList<>();
        private Map<String, String> sessions = new HashMap<>();

        public UserPage(Scanner scanner) {
            this.scanner = scanner;
        }

        public void run() {
            while (true) {
                System.out.println("\n--- User Page ---");
                System.out.println("1. Register/Subscribe for Membership");
                System.out.println("2. Book a Training Session");
                System.out.println("3. Make Payment");
                System.out.println("4. Leave Feedback");
                System.out.println("5. View Transaction History");
                System.out.println("6. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerMembership();
                        break;
                    case 2:
                        bookTrainingSession();
                        break;
                    case 3:
                        makePayment();
                        break;
                    case 4:
                        leaveFeedback();
                        break;
                    case 5:
                        viewTransactionHistory();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }

        private void registerMembership() {
            System.out.println("Registering for Membership...");
        }

        private void bookTrainingSession() {
            System.out.print("Enter Session Type (Personal/Group): ");
            String sessionType = scanner.nextLine();
            sessions.put("Session", sessionType);
            System.out.println("Training session booked successfully!");
        }

        private void makePayment() {
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            transactions.add("Payment: " + amount);
            System.out.println("Payment successful!");
        }

        private void leaveFeedback() {
            System.out.print("Enter your feedback: ");
            String feedbackText = scanner.nextLine();
            feedback.put("User Feedback", feedbackText);
            System.out.println("Feedback submitted successfully!");
        }

        private void viewTransactionHistory() {
            System.out.println("Transaction History: ");
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}
