package Laba2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

abstract class Appliance {
    private String name;
    private int power;
    private boolean isPluggedIn;

    public Appliance(String name, int power) {
        this.name = name;
        this.power = power;
        this.isPluggedIn = false;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public boolean isPluggedIn() {
        return isPluggedIn;
    }

    public void plugIn() {
        isPluggedIn = true;
    }

    public void unplug() {
        isPluggedIn = false;
    }

    @Override
    public String toString() {
        return String.format("Appliance{name='%s', power=%dW, pluggedIn=%s}", name, power, isPluggedIn ? "Yes" : "No");
    }
}

class KitchenAppliance extends Appliance {
    public KitchenAppliance(String name, int power) {
        super(name, power);
    }
}

class HomeAppliance extends Appliance {
    public HomeAppliance(String name, int power) {
        super(name, power);
    }
}

// Менеджер электроприборов
class ApplianceManager {
    private ArrayList<Appliance> appliances;

    public ApplianceManager() {
        this.appliances = new ArrayList<>();
    }

    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    public int calculateTotalPower() {
        int totalPower = 0;
        for (Appliance appliance : appliances) {
            if (appliance.isPluggedIn()) {
                totalPower += appliance.getPower();
            }
        }
        return totalPower;
    }

    public void sortAppliancesByPower() {
        appliances.sort(Comparator.comparingInt(Appliance::getPower));
    }

    public ArrayList<Appliance> findAppliancesByPowerRange(int minPower, int maxPower) {
        ArrayList<Appliance> result = new ArrayList<>();
        for (Appliance appliance : appliances) {
            if (appliance.getPower() >= minPower && appliance.getPower() <= maxPower) {
                result.add(appliance);
            }
        }
        return result;
    }

    public void displayAppliances() {
        for (Appliance appliance : appliances) {
            System.out.println(appliance);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ApplianceManager manager = new ApplianceManager();

        manager.addAppliance(new KitchenAppliance("Холодильник", 150));
        manager.addAppliance(new KitchenAppliance("Микроволновка", 1200));
        manager.addAppliance(new HomeAppliance("Пылесос", 800));
        manager.addAppliance(new HomeAppliance("Телевизор", 300));

        manager.findAppliancesByPowerRange(0, 500).get(0).plugIn();
        manager.findAppliancesByPowerRange(700, 900).get(0).plugIn();
        manager.findAppliancesByPowerRange(700, 900).get(0).unplug();

        System.out.println("Total power consumption of plugged-in appliances: " + manager.calculateTotalPower() + "W");

        manager.sortAppliancesByPower();
        System.out.println("\nSorted appliances by power:");
        manager.displayAppliances();

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter min power: ");
        int minPower = scanner.nextInt();
        System.out.print("Enter max power: ");
        int maxPower = scanner.nextInt();

        ArrayList<Appliance> foundAppliances = manager.findAppliancesByPowerRange(minPower, maxPower);
        System.out.println("\nAppliances in the specified power range:");
        for (Appliance appliance : foundAppliances) {
            System.out.println(appliance);
        }

        scanner.close();
    }
}
