package com.javaquasar.util.net;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Java Quasar on 06.09.17.
 */
public class NetClient {

    private static final String EQUAL_SIGN = "=";
    private static final String AMPERSAND = "&";
    private static final String QUESTION_SIGN = "?";

    private Proxy proxy = null;
    private boolean useProxy = false;
    private int connectTimeout = 15000; //milliseconds
    private int readTimeout = 60000; //milliseconds

    public NetClient() {

    }

    public NetClient(Proxy proxy) {
        this.proxy = proxy;
        this.useProxy = true;
    }

    public NetClient(Proxy proxy, int connectTimeout, int readTimeout) {
        this.proxy = proxy;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public void download(String urlStr, String file) throws IOException {
        System.out.println(urlStr);
        download(urlStr, file, Method.GET, null);
    }

    public void download(String urlStr, String file, Method method, Map<String, String> urlParameters) throws IOException {
        HttpURLConnection conn = getHttpURLConnection(urlStr, method, urlParameters);

        try (InputStream is = conn.getInputStream();
             FileOutputStream fos = new FileOutputStream(file)) {
            ReadableByteChannel rbc = Channels.newChannel(is);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public Response getResponse(String url) throws IOException {
        return getResponse(url, Method.GET, null);
    }

    public Response getResponse(String urlStr, Method method, Map<String, String> urlParameters) throws IOException {
        HttpURLConnection conn = getHttpURLConnection(urlStr, method, urlParameters);
        byte[] result = null;
        int responseCode = conn.getResponseCode();

        Response response = new Response(responseCode);
        try (InputStream is = conn.getInputStream()) {
           // String result =  br.lines().collect(Collectors.joining("\n"));
            result = new byte[is.available()];
            is.read(result);
            switch (responseCode) {
                case 200: {

                }
                break;
                default: {
                    throw new RuntimeException("responseCode: " + responseCode + "\nresult: " + result);
                }
            }
            response.setBody(result);
        } finally {
            conn.disconnect();
        }
        return response ;
    }

    public HttpURLConnection getHttpURLConnection(String requestUrl, Method method, Map<String, String> urlParameters) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = null;

        if(useProxy) {
            conn = (HttpURLConnection) url.openConnection(proxy);
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setDoOutput(true);
        //conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("charset", "utf-8");
        conn.setUseCaches(false);
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        String urlParametersString = mapToUrlParameter(urlParameters);

        switch (method) {
            case POST: {
                int postDataLength = getPostDataLength(urlParametersString);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            }
            break;
            case GET: {

            }
            break;
        }

        if (urlParameters != null) {
            conn.getOutputStream().write(urlParametersString.getBytes());
        }

        //conn.disconnect();
        return conn;
    }

    private int getPostDataLength(String urlParameters) {
        if (urlParameters != null) {
            byte[] postData = urlParameters.getBytes();
            return postData.length;
        } else {
            return 0;
        }
    }

    public String createUrl(String baseUrl, Map<String, String> urlParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append(QUESTION_SIGN);
        sb.append(mapToUrlParameter(urlParameters));
        return sb.toString();
    }


    private String mapToUrlParameter(Map<String, String> urlParameters) {
        if(urlParameters != null) {
            StringBuilder sb = new StringBuilder();
            int size = urlParameters.size();
            for (Map.Entry<String, String> me : urlParameters.entrySet()) {
                try {
                    size--;
                    sb.append(me.getKey() + EQUAL_SIGN + URLEncoder.encode(me.getValue(), StandardCharsets.UTF_8.name()));
                    if (size > 0) {
                        sb.append(AMPERSAND);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            return sb.toString();
        }
        return "";
    }


}
