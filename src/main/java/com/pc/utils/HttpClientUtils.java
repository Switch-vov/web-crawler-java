package com.pc.utils;

import com.pc.pojo.HttpResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Switch
 * @version V1.0
 * @data 2017年2月12日
 */
public class HttpClientUtils {
    // http客户端
    private CloseableHttpClient httpClient;

    public HttpClientUtils() {
        // 创建默认Http客户端
        httpClient = HttpClients.createDefault();
    }

    /**
     * 带参数的get请求
     *
     * @param url    url地址
     * @param params 参数Map
     * @return httpClient返回结果对象
     */
    public HttpResult doGet(String url, Map<String, Object> params) {
        try {
            URI urlObject = createURLObject(url, params);
            HttpGet httpGet = createHttpGet(urlObject);
            return getHttpResultByRequest(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpGet createHttpGet(URI urlObject) {
        // 创建请求对象httpGet
        HttpGet httpGet = new HttpGet(urlObject);
        Map<String, String> heads = new HashMap<>();
        httpGet = setHeader(httpGet, heads);
        return httpGet;
    }

    private HttpGet setHeader(HttpGet httpGet, Map<String, String> heads) {
        setDefaultHeader(heads);
        for (Map.Entry<String, String> entry : heads.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue());
        }
        return httpGet;
    }

    private void setDefaultHeader(Map<String, String> heads) {
        if (!heads.containsKey("User-Agent")) {
            heads.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
        }
        if (!heads.containsKey("Accept")) {
            heads.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        }
    }

    private URI createURLObject(String url, Map<String, Object> params) throws URISyntaxException {
        // 创建URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);
        setRequestParams(params, uriBuilder);
        return uriBuilder.build();
    }

    private URI createURLObject(String url) throws URISyntaxException {
        return this.createURLObject(url, null);
    }

    private void setRequestParams(Map<String, Object> params, URIBuilder uriBuilder) {
        // 设置请求参数
        if (params != null && params.size() > 0) {
            // 遍历请求参数
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                // 封装请求参数
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
    }

    /**
     * 不带参数的get请求
     *
     * @param url url地址
     * @return httpClient返回结果对象
     */
    public HttpResult doGet(String url) {
        return this.doGet(url, null);
    }

    /**
     * 带参数的post请求
     *
     * @param url    url地址
     * @param params 参数Map
     * @return httpClient返回结果对象
     */
    public HttpResult doPost(String url, Map<String, Object> params) {
        try {
            // 创建请求对象httpPut
            HttpPost httpPost = new HttpPost(url);
            httpPost = (HttpPost) setEntity(params, httpPost);
            return getHttpResultByRequest(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpResult getHttpResultByRequest(HttpUriRequest request) throws IOException {
        // 使用Httpclient发起请求
        CloseableHttpResponse response = httpClient.execute(request);

        // 解析返回结果，封装返回对象httpResult
        // 获取状态码
        int code = response.getStatusLine().getStatusCode();

        // 获取响应体
        // 使用EntityUtils.toString方法必须保证entity不为空
        String body = null;
        if (response.getEntity() != null) {
            body = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return new HttpResult(code, body);
    }

    /**
     * 不带参数的post请求
     *
     * @param url url地址
     * @return httpClient返回结果对象
     */
    public HttpResult doPost(String url) {
        return this.doPost(url, null);
    }

    /**
     * 带参数的put请求
     *
     * @param url    url地址
     * @param params 参数Map
     * @return httpClient返回结果对象
     */
    public HttpResult doPut(String url, Map<String, Object> params) {
        try {
            // 创建请求对象httpPost
            HttpPut httpPut = new HttpPut(url);
            httpPut = (HttpPut) setEntity(params, httpPut);
            return getHttpResultByRequest(httpPut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpEntityEnclosingRequestBase setEntity(Map<String, Object> params, HttpEntityEnclosingRequestBase request) throws UnsupportedEncodingException {
        UrlEncodedFormEntity entity = null;
        entity = createUrlEncodedFormEntity(params, entity);
        // 把封装好的表单实体对象设置到HttpPut中
        if (entity != null) {
            request.setEntity(entity);
        }
        return request;
    }

    private UrlEncodedFormEntity createUrlEncodedFormEntity(Map<String, Object> params, UrlEncodedFormEntity entity) throws UnsupportedEncodingException {
        // 封装请求参数，请求数据是表单
        if (params != null && params.size() > 0) {
            // 声明封装表单数据的容器
            List<NameValuePair> pairs = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                // 封装请求参数到容器中
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 创建表单的Entity类
            entity = new UrlEncodedFormEntity(pairs);

        }
        return entity;
    }

    /**
     * 带参数的delete请求
     *
     * @param url    url地址
     * @param params 参数Map
     * @return httpClient返回结果对象
     */
    public HttpResult doDelete(String url, Map<String, Object> params) {
        try {
            URI urlObject = createURLObject(url, params);
            HttpDelete httpDelete = new HttpDelete(urlObject);
            return getHttpResultByRequest(httpDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络上下载图片
     */
    public void downloadPicture(String url, String dirPath, String fileName) {
        try {
            URI urlObject = createURLObject(url);
            HttpGet httpGet = createHttpGet(urlObject);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();

                InputStream in = entity.getContent();

                saveFileToDisk(in, dirPath, fileName);

                System.out.println("保存文件 " + fileName + " 成功....");
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件写到 硬盘指定目录下
     *
     * @param in
     * @param dirPath
     * @param fileName
     */
    private static void saveFileToDisk(InputStream in, String dirPath, String fileName) {
        try {
            File dir = new File(dirPath);
            if (dir == null || !dir.exists()) {
                dir.mkdirs();
            }

            //文件真实路径
            String realPath = dirPath.concat(fileName);
            File file = new File(realPath);
            if (file == null || !file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
