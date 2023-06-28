package io.github.panxiaochao.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * {@code RequestWrapper}
 * <p> description: 重新包装HttpServletRequest，解决是让其输入流可重复读
 *
 * @author Lypxc
 * @since 2023-06-26
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(RequestWrapper.class);

    /**
     * 存储body数据的容器
     */
    private final byte[] bodyBytes;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            request.setCharacterEncoding(DEFAULT_CHARSET.name());
            bodyBytes = getBodyBytes(request);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("getBodyString is error", e);
        }
        return null;
    }

    /**
     * 获取请求BodyBytes
     *
     * @param request request
     * @return String
     */
    public byte[] getBodyBytes(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream()).getBytes(DEFAULT_CHARSET);
        } catch (IOException e) {
            log.error("getBodyBytes is error", e);
        }
        return null;
    }

    /**
     * 获取请求Body
     *
     * @return String
     */
    public String getBodyString() {
        return inputStream2String(new ByteArrayInputStream(bodyBytes));
    }

    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, DEFAULT_CHARSET));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("inputStream2String is error", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("reader close is error", e);
                }
            }
        }

        return sb.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyBytes);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public int available() throws IOException {
                return bodyBytes.length;
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}