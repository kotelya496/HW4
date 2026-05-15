package org.example;

import org.example.controller.DTO.UserDto;
import org.example.controller.UserControllerConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static org.example.exception.ConsoleExeptionHandler.*;

@Component
public class ConsoleClient {

    static UserControllerConsole userController;

    @Autowired
    public void setUserController(UserControllerConsole userController) {
        ConsoleClient.userController = userController;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu(){
        while (true) {
            System.out.println("\nМеню");
            System.out.println("\n1. создать User");
            System.out.println("2. получить всех User");
            System.out.println("3. поиск по ID");
            System.out.println("4. обновить User");
            System.out.println("5. удалить User");
            System.out.println("0. Выод");
            System.out.print("\nВыберите операцию: ");

            int numberOperation;
            try {
                numberOperation = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nВведите номер операции.");
                continue;
            }

            switch (numberOperation) {
                case 1:
                    createUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    getUserById();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nВыберите операцию из предложенных");
            }
        }
    }

    private static void createUser() {

        execute(()->{

            System.out.print("\nВведите имя: ");
            String name = scanner.nextLine();

            System.out.print("Введите email: ");
            String email = scanner.nextLine();

            System.out.print("Введите возраст: ");
            int age = Integer.parseInt(scanner.nextLine());

            var user1 = new UserDto(null,name,email,age, null);
            UserDto user = userController.createUser(user1);
            System.out.println("\nUser сохране");

        });
    }

    private static void viewAllUsers() {
        System.out.println("\n--- Все User ---");
        List<UserDto> users = userController.allUsers();

        if (users.isEmpty()) {
            System.out.println("Список Users пуст.");
        } else {
            users.forEach(System.out::println);
            System.out.println("\nКоличество User: " + users.size());
        }
    }

    private static void getUserById() {

        execute(()->{

            System.out.print("\nВведите user ID: ");
            Long id = Long.parseLong(scanner.nextLine());

            UserDto user = userController.getUserById(id);

        });
    }

    private static void updateUser() {

        execute(()->{

            System.out.print("\nВведите ID для обновления user: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Введите новое имя: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = null;

            System.out.print("Введите новый email: ");
            String email = scanner.nextLine();
            if (email.trim().isEmpty()) email = null;

            System.out.print("Введите новый возраст: ");
            String ageStr = scanner.nextLine();
            int age = Integer.parseInt(ageStr);

            UserDto user = userController.updateUserById(id, new UserDto(null, name, email, age, null));
            System.out.println("User обновден");

        });
    }

    private static void deleteUser() {

        execute(()->{

            System.out.print("\nВведите ID для удаления user: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Подтвердить операцию (y/n): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                userController.deleteUserById(id);
                System.out.println("User удален");
            } else {
                System.out.println("Удаление не удалось.");
            }

        });

    }
}
