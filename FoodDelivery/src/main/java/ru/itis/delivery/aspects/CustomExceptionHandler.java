package ru.itis.delivery.aspects;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.delivery.exceptions.NotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Произошла ошибочка, извини");
        return "error/error";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Неполадки с базой данных, извини");
        model.addAttribute("elseMessage", ex.getMessage());
        return "error/error";
    }

}
