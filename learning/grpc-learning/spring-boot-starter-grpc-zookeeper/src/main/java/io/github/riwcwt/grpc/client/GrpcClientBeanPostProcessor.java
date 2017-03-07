package io.github.riwcwt.grpc.client;

import com.google.common.collect.Lists;
import io.github.riwcwt.grpc.annotation.GrpcClient;
import io.github.riwcwt.grpc.autoconfigure.Registry;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by michael on 2017-03-06.
 */
public class GrpcClientBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientBeanPostProcessor.class);

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @Autowired
    private Registry registry;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("before : " + beanName + " - " + bean.getClass().getCanonicalName());

        Class clazz = bean.getClass();
        do {
            Arrays.stream(clazz.getDeclaredFields()).forEach(field -> Optional.ofNullable(AnnotationUtils.getAnnotation(field, GrpcClient.class)).ifPresent(annotation -> {
                Object target = getTargetBean(bean);

                List<ClientInterceptor> list = Lists.newArrayList();
                Arrays.stream(annotation.interceptors()).forEach(clientInterceptorClass -> {
                    ClientInterceptor clientInterceptor;
                    if (beanFactory.getBeanNamesForType(ClientInterceptor.class).length > 0) {
                        clientInterceptor = beanFactory.getBean(clientInterceptorClass);
                    } else {
                        try {
                            clientInterceptor = clientInterceptorClass.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new BeanCreationException("Failed to create interceptor instance", e);
                        }
                    }
                    list.add(clientInterceptor);
                });

                Channel channel = this.registry.channel(annotation.value(), list);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, target, channel);
            }));
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("after : " + beanName + " - " + bean.getClass().getCanonicalName());
        return bean;
    }

    private Object getTargetBean(Object bean) {
        Object target = bean;
        try {
            while (AopUtils.isAopProxy(target)) {
                target = ((Advised) target).getTargetSource().getTarget();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return target;
    }
}
