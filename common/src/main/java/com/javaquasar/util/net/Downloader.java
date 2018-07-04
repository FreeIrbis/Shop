package com.javaquasar.util.net;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Java Quasar on 04.08.17.
 */
public class Downloader {

    private static final String EQUAL_SIGN = "=";
    private static final String AMPERSAND = "&";
    private static final String QUESTION_SIGN = "?";


    public static void download(String url, String pathToFile) throws MalformedURLException, IOException {
        URL website = new URL(url);
        File file = new File(pathToFile);

        if (file.exists()) {
            throw new IOException("File \"" + pathToFile + "\" exists");
        }
        try (InputStream is = website.openStream();
             ReadableByteChannel rbc = Channels.newChannel(is);
             FileOutputStream fos = new FileOutputStream(file);) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("83.69.245.150", 	53281));
        downloadUsingNIO(urlStr, file, proxy);
    }

    public static void downloadUsingNIO(String urlStr, String file, Proxy proxy) throws IOException {
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection(proxy);
//        conn.setConnectTimeout(5*60*1000);
//        conn.setReadTimeout(5*60*1000);
        try (InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file)) {
            ReadableByteChannel rbc = Channels.newChannel(is);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public static void downloadUsingNIO(String urlStr, String file, String hostName, int port) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));
        downloadUsingNIO(urlStr, file, proxy);
    }

    public static HttpURLConnection getHttpURLConnection(String requestUrl, Method method, Map<String, String> urlParameters) throws IOException {
        //System.out.println(requestUrl + urlParameters);
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("charset", "utf-8");
        conn.setUseCaches(false);

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

    public static int getPostDataLength(String urlParameters) {
        if (urlParameters != null) {
            byte[] postData = urlParameters.getBytes();
            return postData.length;
        } else {
            return 0;
        }
    }

    public static String createUrl(String baseUrl, Map<String, String> urlParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append(QUESTION_SIGN);
        sb.append(mapToUrlParameter(urlParameters));
        return baseUrl + mapToUrlParameter(urlParameters);
    }


    public static String mapToUrlParameter(Map<String, String> urlParameters) {
        StringBuilder sb = new StringBuilder();

//        Iterator it = urlParameters.entrySet().iterator();
//        while(it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            //получить ключ
//            String key = entry.getKey();
//            //получить значение
//            String value = entry.getValue();
//        }

        int size = urlParameters.size();
        for(Map.Entry<String, String> me : urlParameters.entrySet()) {
            try {
                size--;
                sb.append(me.getKey() + EQUAL_SIGN + URLEncoder.encode(me.getValue(), StandardCharsets.UTF_8.name()));
                if(size > 0) {
                    sb.append(AMPERSAND);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }

}
