package com.example.demo.config.interceptor;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class WrappedRequest extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream;

    public WrappedRequest(HttpServletRequest request) {
        super(request);
        byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(request.getInputStream(), byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomServletInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
    }

    public static final class CustomServletInputStream extends ServletInputStream {

        private final InputStream bis;

        public CustomServletInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.bis = byteArrayInputStream;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return bis.read(b, off, len);
        }

        @Override
        public byte[] readNBytes(int len) throws IOException {
            return bis.readNBytes(len);
        }

        @Override
        public int readNBytes(byte[] b, int off, int len) throws IOException {
            return bis.readNBytes(b, off, len);
        }

        @Override
        public long skip(long n) throws IOException {
            return bis.skip(n);
        }

        @Override
        public int available() throws IOException {
            return bis.available();
        }

        @Override
        public void close() throws IOException {
            bis.close();
        }

        @Override
        public synchronized void mark(int readlimit) {
            bis.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            bis.reset();
        }

        @Override
        public boolean markSupported() {
            return bis.markSupported();
        }

        @Override
        public long transferTo(OutputStream out) throws IOException {
            return bis.transferTo(out);
        }

        @Override
        public int read(byte[] b) throws IOException {
            return bis.read(b);
        }

        @Override
        public byte[] readAllBytes() throws IOException {
            return bis.readAllBytes();
        }
    }
}
