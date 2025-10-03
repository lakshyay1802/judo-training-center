import java.util.Scanner;

public class JudoTraining {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Welcome to Judo Training Center =====");

        // Student basic details
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        // Weight in kg
        System.out.print("Enter your weight (kg): ");
        double weightKg = sc.nextDouble();

        // Height in feet and inches
        System.out.print("Enter your height - feet: ");
        int feet = sc.nextInt();
        System.out.print("Enter inches: ");
        int inches = sc.nextInt();
        double heightMeters = feet * 0.3048 + inches * 0.0254;

        // Weekly hours validation
        int weeklyHours;
        while (true) {
            System.out.print("Enter weekly training hours: ");
            weeklyHours = sc.nextInt();

            if (weeklyHours > 21) {
                System.out.print("❌ Too much training! Are you an athlete? (yes=1 / no=0): ");
                int athleteChoice = sc.nextInt();
                if (athleteChoice == 1) {
                    if (weeklyHours > 50) {
                        System.out.println("❌ Even athletes cannot train more than 50 hours. Please re-enter.");
                        continue;
                    }
                    break;
                } else {
                    System.out.println("Please re-enter hours (must be <= 21 for non-athletes).");
                    continue;
                }
            } else {
                break;
            }
        }

        double hourlyFee = 20.0;
        Trainee trainee = new Trainee(name, age, weightKg, heightMeters, weeklyHours, hourlyFee);

        // Display Report
        trainee.displayTrainingReport();

        // Competition eligibility
        if (trainee.isEligibleForCompetition()) {
            System.out.println("\n✅ " + trainee.getName() + " is eligible for the Judo Competition!");
            System.out.println("👉 Competition Category: " + trainee.getCompetitionCategory());
        } else {
            System.out.println("\n❌ " + trainee.getName() + " is NOT eligible yet.");
        }

        // BMI Suggestions
        System.out.println("\n===== Health & Training Suggestions =====");
        System.out.printf("Your BMI: %.2f%n", trainee.calculateBMI());
        System.out.println(trainee.getBMISuggestion());

        // Calculate burned calories if weeklyHours > 10
        if (weeklyHours > 10) {
            int burnedCalories = weeklyHours * 500; // 500 kcal per hour
            System.out.println("🔥 Estimated calories burned per week: " + burnedCalories + " kcal");

            // Protein needs: 1.5g per kg bodyweight
            double protein = trainee.getWeight() * 1.5;
            System.out.printf("🍗 Recommended protein intake per week: %.1f grams%n", protein);

            System.out.println("\n🥦 Suggested Veg Diet:");
            System.out.println("- Lentils, Chickpeas, Tofu, Paneer");
            System.out.println("- Whole grains (Brown rice, Quinoa)");
            System.out.println("- Vegetables, Nuts, Seeds, Milk products");

            System.out.println("\n🍗 Suggested Non-Veg Diet:");
            System.out.println("- Chicken, Fish, Eggs");
            System.out.println("- Milk, Yogurt");
            System.out.println("- Protein shakes or supplements if needed");
        }

        sc.close();
    }
}

// Trainee Class
class Trainee {
    private String name;
    private int age;
    private double weight; // kg
    private double height; // meters
    private int weeklyTrainingHours;
    private double hourlyFee;

    public Trainee(String name, int age, double weight, double height, int weeklyTrainingHours, double hourlyFee) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.weeklyTrainingHours = weeklyTrainingHours;
        this.hourlyFee = hourlyFee;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getCompetitionCategory() {
        if (weight < 45) return "Below minimum competition weight";

        int start = 45;
        int end = 65;
        while (start <= 150) {
            if (weight >= start && weight <= end) return start + "kg - " + end + "kg";
            start += 20;
            end += 20;
        }
        return "Above 150kg (Heavyweight Open)";
    }

    public double calculateWeeklyFee() {
        return weeklyTrainingHours * hourlyFee;
    }

    public double calculateMonthlyFee() {
        return calculateWeeklyFee() * 4;
    }

    public double calculateBMI() {
        return weight / (height * height);
    }

    public String getBMISuggestion() {
        double bmi = calculateBMI();
        if (bmi < 18.5) return "Underweight → Suggestion: Gain weight with calorie surplus.";
        else if (bmi < 25) return "Normal BMI → Maintain weight and balanced diet.";
        else if (bmi < 30) return "Overweight → Suggestion: Lose weight with calorie deficit.";
        else return "Obese → Strongly advised weight reduction & lifestyle changes.";
    }

    public boolean isEligibleForCompetition() {
        return (weeklyTrainingHours >= 3 && weight >= 45);
    }

    public void displayTrainingReport() {
        System.out.println("\n===== Judo Training Report =====");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.printf("Weight: %.1f kg\n", weight);
        System.out.printf("Height: %.2f m\n", height);
        System.out.println("Weekly Training Hours: " + weeklyTrainingHours);
        System.out.println("Hourly Fee: $" + hourlyFee);
        System.out.println("Weekly Fee: $" + calculateWeeklyFee());
        System.out.println("Monthly Fee: $" + calculateMonthlyFee());
        System.out.println("Competition Category: " + getCompetitionCategory());
        System.out.println("================================");
    }
}
