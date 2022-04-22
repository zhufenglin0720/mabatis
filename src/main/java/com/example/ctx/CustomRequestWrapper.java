package com.example.ctx;


import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 168
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    private CustomRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtils.toByteArray(request.getInputStream());
    }

    /**
     * New instance custom request wrapper.
     *
     * @param request the request
     * @return the custom request wrapper
     * @throws IOException the io exception
     */
    public static CustomRequestWrapper newInstance(HttpServletRequest request) throws IOException {
        // 创建前要调用一次getParameterMap，否则参数会丢失
        request.getParameterMap();
        return new CustomRequestWrapper(request);
    }

    /**
     * Get body byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
