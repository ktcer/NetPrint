package com.demo.app.interceptor;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TimeCostInterceptor implements HandlerInterceptor {

    // before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    // after the handler is executed
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("-----------------------").append(df.format(new Date()))
                    .append("-------------------------------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName())
                    .append("\n");
            sb.append("Method    : ").append(h.getMethod().getName())
                    .append("\n");
            sb.append("Params    : ")
                    .append(getParamString(request.getParameterMap()))
                    .append("\n");
            sb.append("URI       : ").append(request.getRequestURI())
                    .append("\n");
            sb.append("CostTime  : ").append(executeTime).append("ms")
                    .append("\n");
            sb.append("-------------------------------------------------------------------------------");
            System.out.println(sb.toString());
        }
        // logger.debug("[" + handler + "] executeTime : " + executeTime +
        // "ms");
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
}
