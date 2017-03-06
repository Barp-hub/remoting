package io.github.riwcwt.grpc.client;

import com.google.common.collect.Lists;
import io.github.riwcwt.grpc.annotation.GrpcClient;
import io.github.riwcwt.grpc.command.GrpcServerRunner;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michael on 2017-03-06.
 */
public class GrpcClientBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientBeanPostProcessor.class);

    private ConcurrentHashMap<String, List<Class>> beansToProcess = new ConcurrentHashMap<>();

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @Autowired
    private GrpcServerRunner serverRunner;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("before : " + beanName + " - " + bean.getClass().getCanonicalName());

        Class clazz = bean.getClass();
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(GrpcClient.class)) {
                    if (!beansToProcess.containsKey(beanName)) {
                        beansToProcess.put(beanName, new ArrayList<>());
                    }
                    beansToProcess.get(beanName).add(clazz);
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("after : " + beanName + " - " + bean.getClass().getCanonicalName());
        if (beansToProcess.containsKey(beanName)) {
            Object target = getTargetBean(bean);
            for (Class clazz : beansToProcess.get(beanName)) {
                for (Field field : clazz.getDeclaredFields()) {
                    GrpcClient annotation = AnnotationUtils.getAnnotation(field, GrpcClient.class);
                    if (null != annotation) {

                        List<ClientInterceptor> list = Lists.newArrayList();
                        for (Class<? extends ClientInterceptor> clientInterceptorClass : annotation.interceptors()) {
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
                        }

                        Channel channel = this.serverRunner.channel(annotation.value()); //channelFactory.createChannel(annotation.value(), list);
                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, target, channel);
                    }
                }
            }
        }
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
