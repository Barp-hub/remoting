package io.github.riwcwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import io.github.riwcwt.register.RemotingBeanRegister;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import(RemotingBeanRegister.class)
public @interface EnableRemoting {
	public String name();
}
