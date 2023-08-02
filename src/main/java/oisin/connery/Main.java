package oisin.connery;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression: ");
        String answer = Computer.calculate(scanner.nextLine());
        System.out.println(answer);
    }
}