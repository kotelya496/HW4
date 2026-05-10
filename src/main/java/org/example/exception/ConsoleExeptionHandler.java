package org.example.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleExeptionHandler {

    @FunctionalInterface
    public interface ExceptionThrowingRunnable {
        void run() throws Exception;
    }

    public static void execute(ExceptionThrowingRunnable action) {
        try {
            action.run();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка введиете числового значения возраста");
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
            log.error(e.getMessage());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            log.error(e.getMessage());
        }
    }
}
