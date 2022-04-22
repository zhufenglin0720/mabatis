package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.example.ctx.CustomRequestWrapper;
import com.example.ctx.client.RequestClientInfo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 168
 */
public class RequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    private static final String[] IP_HEADER_CANDIDATES = {"X-real-ip", "X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    /**
     * 获取客户端信息
     *
     * @param request
     * @return
     */
    public static RequestClientInfo clientInfo(HttpServletRequest request) {
        RequestClientInfo info = new RequestClientInfo();
        // 解析 agent 字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(browserAgent(request));
        // 获取浏览器对象
        Browser browser = userAgent.getBrowser();
        // 获取操作系统对象
        OperatingSystem os = userAgent.getOperatingSystem();

        info.setBrowser(browser.getGroup().getName());
        info.setDeviceType(os.getDeviceType().getName());
        info.setOs(os.getGroup().getName());
        info.setClientIp(parseClientIp(request));
        return info;
    }

    /**
     * 获取访问的Host
     *
     * @param request the request
     * @return the string
     */
    public static String parseHost(HttpServletRequest request) {
        return request.getHeader("Host");
    }

    /**
     * 获取访问的Host，不要端口号
     *
     * @param request the request
     * @return the string
     */
    public static String parseHostWithoutPort(HttpServletRequest request) {
        return parseHost(request).replaceAll(":.*", "");
    }

    /**
     * 获取客户端IP
     *
     * @param request the request
     * @return the string
     */
    public static String parseClientIp(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (StringUtils.isNoneBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private static String browserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isBlank(userAgent)) {
            return "";
        }
        return userAgent;
    }

    /**
     * Parse device code string.
     *
     * @param request the request
     * @return the string
     */
    public static String parseDeviceCode(HttpServletRequest request) {
        try {
            if (request instanceof CustomRequestWrapper) {
                CustomRequestWrapper wrapper = (CustomRequestWrapper) request;
                String body = new String(wrapper.getBody());
                if (StringUtils.isNotBlank(body)) {
                    Map dataMap = JSON.parseObject(body, Map.class);
                    String deviceCode = (String) dataMap.get("uuid");
                    if (StringUtils.isBlank(deviceCode)) {
                        return "";
                    }
                    return deviceCode;
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

    /**
     * Parse method string.
     *
     * @param request the request
     * @return the string
     */
    public static String parseMethod(HttpServletRequest request) {
        return request.getMethod().toUpperCase();
    }

    /**
     * Parse payload string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    public static String parsePayload(HttpServletRequest request) throws Exception {
        byte[] byteBody = IOUtils.toByteArray(request.getInputStream());
        String playload = "";
        if (byteBody != null) {
            playload = new String(byteBody);
        }
        return playload;
    }

    /**
     * Parse query string string.
     *
     * @param request the request
     * @return the string
     */
    public static String parseQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }

    /**
     * Parse headers map.
     *
     * @param request the request
     * @return the map
     */
    public static Map<String, String> parseHeaders(HttpServletRequest request) {
        Map<String, String> headers = new TreeMap<>();
        Enumeration<String> er = request.getHeaderNames();
        while (er.hasMoreElements()) {
            String name = er.nextElement();
            String value = request.getHeader(name);
            if (StringUtils.isNotBlank(value)) {
                headers.put(name, value);
            }
        }
        return headers;
    }

    /**
     * Gets parameter string map.
     *
     * @param request the request
     * @return the parameter string map
     */
    public static Map<String, String> getParameterStringMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();
        Map<String, String> returnMap = new HashMap<>(10);
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length > 1) {
                for (String s : values) {
                    value = s + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];
            }
            returnMap.put(name, value);

        }
        return returnMap;
    }
}

