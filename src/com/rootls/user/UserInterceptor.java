package com.rootls.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        boolean needLogin = hasLoginAnotation(method.getDeclaringClass(), method);

        //是否需要登录
        if (!needLogin) {
            return true;
        }else {

        }
        return super.preHandle(request, response, handler);
    }

    private boolean hasLoginAnotation(Class clazz, Method method) {

        NeedLogin methodAnnotation = method.getAnnotation(NeedLogin.class);
        if (method.isAnnotationPresent(NeedLogin.class)) {
            return methodAnnotation.value();
        }

        Annotation clazzAnnotation = clazz.getAnnotation(NeedLogin.class);
        if (clazz.isAnnotationPresent(NeedLogin.class)) {
            return ((NeedLogin) clazzAnnotation).value();
        } else {
            return false;
        }
    }

    @Target({ElementType.TYPE,ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface NeedLogin {
        boolean value() default true;
    }

}
