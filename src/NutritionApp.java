import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NutritionApp extends JFrame implements ActionListener {

    // GUI components
    private JLabel heightLabel, weightLabel, ageLabel, sexLabel, activityLevelLabel, goalLabel;
    private JTextField heightField, weightField, ageField;
    private JComboBox<String> sexCombo, activityLevelCombo, goalCombo;
    private JButton calculateButton;
    private JTextArea outputArea;

    public NutritionApp() {
        super("Calorie Calculator");
        setLayout(new FlowLayout());

        // Initialize GUI components
        heightLabel = new JLabel("Height (inches):");
        weightLabel = new JLabel("Weight (lbs):");
        ageLabel = new JLabel("Age:");
        sexLabel = new JLabel("Sex:");
        activityLevelLabel = new JLabel("Activity Level:");
        goalLabel = new JLabel("Goal:");

        heightField = new JTextField(10);
        weightField = new JTextField(10);
        ageField = new JTextField(10);

        String[] sexOptions = {"Male", "Female"};
        sexCombo = new JComboBox<>(sexOptions);

        String[] activityLevelOptions = {"Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extremely Active"};
        activityLevelCombo = new JComboBox<>(activityLevelOptions);

        String[] goalOptions = {"Maintain Weight", "Lose Weight", "Gain Weight"};
        goalCombo = new JComboBox<>(goalOptions);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        outputArea = new JTextArea(15, 35);
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);

        // Add components to frame
        add(heightLabel);
        add(heightField);

        add(weightLabel);
        add(weightField);

        add(ageLabel);
        add(ageField);

        add(sexLabel);
        add(sexCombo);

        add(activityLevelLabel);
        add(activityLevelCombo);

        add(goalLabel);
        add(goalCombo);

        add(calculateButton);

        add(outputScroll);

        setSize(450, 500);
        setVisible(true);
    }

    // Action listener for calculate button
    public void actionPerformed(ActionEvent e) {
        String heightText = heightField.getText();
        String weightText = weightField.getText();
        String ageText = ageField.getText();
        String sex = (String) sexCombo.getSelectedItem();
        String activityLevel = (String) activityLevelCombo.getSelectedItem();
        String goal = (String) goalCombo.getSelectedItem();

        try {
            // Parse input values
            double height = Double.parseDouble(heightText);
            double weight = Double.parseDouble(weightText);
            int age = Integer.parseInt(ageText);

            // Calculate maintenance calories
            double maintenanceCalories = calculateMaintenanceCalories(height, weight, age, sex, activityLevel);

            // Calculate calories for goal
            double goalCalories = maintenanceCalories;
            if (goal.equals("Lose Weight")) {
                goalCalories = maintenanceCalories - 500;
            } else if (goal.equals("Gain Weight")) {
                goalCalories = maintenanceCalories + 500;
            }

            // Display results
            outputArea.setText(String.format("Maintenance Calories: %.2f%n", maintenanceCalories));
            if(goal.equals("Lose Weight"))
                outputArea.append(String.format("To lose 1 lb/week: Calories: %.2f%n", goalCalories));
            else
                outputArea.append(String.format("To gain 1 lb/week: Calories: %.2f%n", goalCalories));
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: please enter a number for height, weight, and age.");
        }
    }

    private double calculateMaintenanceCalories(double height, double weight, int age, String sex, String activityLevel) {
        double bmr;
        double maintenanceCalories;

        // Calculate basal metabolic rate (BMR) using Mifflin-St Jeor equation
        if (sex.equals("Male")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        // Adjust BMR based on activity level
        switch (activityLevel) {
            case "Sedentary":
                maintenanceCalories = bmr * 1.2;
                break;
            case "Lightly Active":
                maintenanceCalories = bmr * 1.375;
                break;
            case "Moderately Active":
                maintenanceCalories = bmr * 1.55;
                break;
            case "Very Active":
                maintenanceCalories = bmr * 1.725;
                break;
            case "Extremely Active":
                maintenanceCalories = bmr * 1.9;
                break;
            default:
                maintenanceCalories = 0;
                break;
        }

        return maintenanceCalories;
    }

    public static void main(String[] args) {
        new NutritionApp();
    }
}