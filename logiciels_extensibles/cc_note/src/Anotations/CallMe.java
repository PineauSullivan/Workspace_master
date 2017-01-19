package Anotations;

import java.lang.annotation.*;

@Target(value=ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CallMe {
	String param();
}