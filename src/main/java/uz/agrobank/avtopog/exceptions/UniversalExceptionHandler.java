package uz.agrobank.avtopog.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class UniversalExceptionHandler  {
    @ExceptionHandler(UniversalException.class)
    public String exceptionHandler(UniversalException e, WebRequest request, Model model) {
        model.addAttribute("message",e.getMessage());
        int value = e.getStatus().value();
        return "/error/"+value;
    }
    @ExceptionHandler(Exception.class)
    public String exception(Exception e, WebRequest request, Model model) {
        model.addAttribute("message","Server error call us");
        return "/error/error";
    }


}
