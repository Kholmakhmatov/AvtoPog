package uz.agrobank.avtopog.annotation;

import uz.agrobank.avtopog.model.enums.RoleEnum;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRole {
    RoleEnum[] value();
}
