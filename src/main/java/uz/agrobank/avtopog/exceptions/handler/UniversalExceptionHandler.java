package uz.agrobank.avtopog.exceptions.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uz.agrobank.avtopog.exceptions.UniversalException;


@ControllerAdvice
public class UniversalExceptionHandler  {
    @ExceptionHandler(UniversalException.class)
    public String exceptionHandler(UniversalException e, WebRequest request, Model model) {
        model.addAttribute("message",e.getMessage());
    return "error";
    }


}
