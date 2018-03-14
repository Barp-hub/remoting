package io.github.riwcwt;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ServiceRun {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRun.class);

    /*服务类型  webser*/
    public static final int SERVER_TYPE_WEBSER = 1;
    /*服务类型  文件服务*/
    public static final int SERVER_TYPE_FILE = 2;

    private final static String DEVICE_UUID = "WEB_CLIENT_SERVICE_RUN";

    private Map<String, Object> xssWhiteListApiMap = new HashMap<String, Object>();

    private PoolingHttpClientConnectionManager cm = null;

    //    private String token;
    //
    //    private Gson antiXssGson = GsonUtil.createAntiXssGson();
    //
    //    public String getToken() {
    //        return token;
    //    }
    //
    //    public ServiceRun() {
    //        String apis = IBSConfigurer.get("xss.whiteList.webserApi");
    //        logger.debug("load xss api whitelist:" + apis);
    //        if (StringUtil.isNotBlank(apis)) ;
    //        String[] apis_arr = apis.split(",");
    //        for (int i = 0; i < apis_arr.length; i++) {
    //            if (StringUtil.isNotBlank(apis_arr[i])) {
    //                xssWhiteListApiMap.put(apis_arr[i], 1);
    //            }
    //        }
    //    }
    //
    //    public String getServer(int type) {
    //        if (SERVER_TYPE_FILE == type) {
    //            return PropConfig.SERVER_FILE;
    //        } else {
    //            return PropConfig.SERVER;
    //        }
    //    }

    //    public void init() {
    //
    //        if (token != null) return;
    //
    //        String param = "&device=" + DEVICE_UUID;
    //        DeviceInfo deviceData = new DeviceInfo();
    //        deviceData.setDH(1024);
    //        deviceData.setDW(1000);
    //        deviceData.setChannel(6000);
    //        AuthRes ret = runService(param, deviceData, IAuth.APIPATH_AUTH, AuthRes.class, false);
    //        token = ret.getToken();
    //
    //    }

    private HttpClient getHttpClient() {
        if (cm == null) { //need create the cm
            cm = new PoolingHttpClientConnectionManager();
        }
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        return httpClient;
    }

    //    public <T> T runService(Object param, String api, Class<T> classOfT) {
    //        return runService(param, api, (Type) classOfT);
    //    }
    //
    //    public <T> T runService(Object param, String api, Type typeOfT) {
    //        return runService(param, api, typeOfT, false);
    //    }
    //
    //    public <T> T runService(String api, Type typeOfT) {
    //        return runService(null, api, typeOfT, true);
    //    }
    //
    //    public <T> T runService(String requestParam, Object requestBody, String api, Type resClass, boolean isGet) {
    //        return this.runService(requestParam, requestBody, api, resClass, isGet, SERVER_TYPE_WEBSER);
    //    }

    public String runService(String url, Object requestBody, /*String api,*/ /*Type resClass,*/ boolean isGet/*, int serverType*/) {

        String res = null;
        String responseBody = null;
        String url_s = null;
        try {

            HttpRequestBase httpReq = null;

            //            url_s = this.getServer(serverType) + api /*+ "?" + calParamsUri(requestParam)*/;
            //            URL url = new URL(url_s);
            //            URI uri = new URI(url.getProtocol(), url.getHost() + ":" + url.getPort(), url.getPath(), url.getQuery(), null);
            URI uri = new URI(url);

            if (isGet) {
                httpReq = new HttpGet(uri);
            } else {

                httpReq = new HttpPost(uri);

                String requestBodyJson = null;
                if (requestBody != null) {
                    //                    requestBodyJson = GsonUtil.toJson(requestBody);
                    requestBodyJson = JSON.toJSONString(requestBody);
                }

                if (requestBodyJson != null) {
                    HttpPost httpPost = (HttpPost) httpReq;
                    httpPost.setEntity(new StringEntity(requestBodyJson, "UTF-8"));
                }
            }

            //            httpReq.setHeader("Accept", "application/json");
            //            httpReq.setHeader("Content-type", "application/json");

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = getHttpClient().execute(httpReq, responseHandler);

            //            if (xssWhiteListApiMap.get(api) == null) {
            //                res = JSON.parseObject(responseBody, resClass);
            //            } else {
            //            res = JSON.parseObject(responseBody, resClass);
            //            }
            res = responseBody;
        } catch (Exception e) {

            //            if (e instanceof ScanException) {
            //                logger.error("antiSamy error, url: " + url_s, e);
            //                logger.error(responseBody, e);
            //            }

            logger.error("api invoke error:", e);
            e.printStackTrace();
            res = null;
        }

        return res;
    }

    //    private String calParamsUri(String requestParam) {
    //
    //        ArrayList<String> paramNames = new ArrayList<String>();
    //        ArrayList<String> params = new ArrayList<String>();
    //
    //        if (requestParam != null) {
    //            String[] paramList = requestParam.split("&");
    //            for (String item : paramList) {
    //                String temp[] = item.split("=");
    //                if (temp.length == 2) {
    //                    paramNames.add(temp[0]);
    //                    params.add(temp[1]);
    //                }
    //            }
    //        }
    //
    //        paramNames.add("appKey");
    //        params.add(PropConfig.APP_KEY);
    //
    //        paramNames.add("versionCode");
    //        params.add(PropConfig.VERSION_CODE);
    //
    //        Date curDate = new Date();
    //        paramNames.add("timestamp");
    //        params.add(new Long(curDate.getTime()).toString());
    //
    //        if (token != null) {
    //            paramNames.add("token");
    //            params.add(token);
    //        }
    //
    //        String uri = null;
    //        for (int i = 0; i < paramNames.size(); i++) {
    //            if (uri == null) {
    //                uri = paramNames.get(i) + "=" + params.get(i);
    //            } else {
    //                uri += "&" + paramNames.get(i) + "=" + params.get(i);
    //            }
    //        }
    //
    //        uri += "&sign=" + calSignature(params);
    //
    //        return uri;
    //    }

    //    private String calSignature(ArrayList<String> params) {
    //
    //        Collections.sort(params, new Comparator<String>() {
    //
    //            public int compare(String o1, String o2) {
    //                return o1.compareTo(o2);
    //            }
    //        });
    //
    //        String sData = PropConfig.APP_SECRET;
    //
    //        for (String paramName : params) {
    //            sData += Util.paramURIDecode(paramName);
    //        }
    //
    //        sData += PropConfig.APP_SECRET;
    //
    //        //		sData = sData.replaceAll("%25", "%") ;
    //        try {
    //            sData = Signature.calculateRFC2104HMAC(sData, PropConfig.APP_SECRET);
    //        } catch (SignatureException e) {
    //            logger.error(e);
    //            e.printStackTrace();
    //        }
    //
    //        return sData;
    //    }

    //    public <T> T runService(Object param, String api, Type resClass, boolean isGet) {
    //
    //        String requestParam = null;
    //        Object requestBody = null;
    //
    //        if (param != null) {
    //            if (param.getClass().equals(String.class)) {
    //                //if it is a string object, add the parameter to end of URL
    //                requestParam = (String) param;
    //            } else if (!isGet) {
    //                requestBody = param;
    //            }
    //        }
    //        return runService(requestParam, requestBody, api, resClass, isGet);
    //    }

    //    /**
    //     * 文件上传
    //     *
    //     * @param api
    //     * @param resType
    //     * @param file
    //     * @param farm
    //     * @param id
    //     * @param type
    //     * @param <T>
    //     * @return
    //     */
    //    public <T> T uploadFileToFarm(String api, Type resType, MultipartFile file, String farm, Integer id, String type, String token) {
    //
    //        T res = null;
    //
    //        try {
    //            String url = this.getServer(SERVER_TYPE_FILE) + api + "?" + "appKey=" + PropConfig.APP_KEY;
    //
    //            Date curDate = new Date();
    //            String timestamp = new Long(curDate.getTime()).toString();
    //
    //            url = url + "&token=" + token + "&timestamp=" + timestamp;
    //
    //            ArrayList<String> params = new ArrayList<String>();
    //            params.add(timestamp);
    //            params.add(PropConfig.APP_KEY);
    //            params.add(token);
    //            params.add(file.getName());
    //            params.add(type);
    //            params.add(file.getOriginalFilename());
    //            params.add(farm);
    //            params.add("1001");
    //            if (id != null) {
    //                params.add(id.toString());
    //            }
    //
    //            url += "&sign=" + calSignature(params);
    //
    //            HttpPost httppost = new HttpPost(url);
    //
    //            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    //
    //            entity.addPart("type", strBody(type));
    //            if (id != null) {
    //                entity.addPart("id", strBody(id.toString()));
    //            }
    //            entity.addPart("name", strBody(file.getName()));
    //            entity.addPart("file", new InputStreamBody(file.getInputStream(), file.getContentType(), file.getOriginalFilename()));
    //            entity.addPart("fileName", strBody(file.getOriginalFilename()));
    //            entity.addPart("farm", strBody(farm));
    //            entity.addPart("fileType", strBody("1001"));
    //            httppost.setEntity(entity.build());
    //            httppost.setHeader("Accept", "application/json");
    //
    //            ResponseHandler<String> responseHandler = new BasicResponseHandler();
    //            String responseBody = getHttpClient().execute(httppost, responseHandler);
    //            res = GsonUtil.fromJson(responseBody, resType);
    //
    //        } catch (Exception e) {
    //            res = null;
    //        }
    //
    //        return res;
    //    }

    //    private StringBody strBody(String value) throws UnsupportedEncodingException {
    //        return new StringBody(value, Charset.forName("utf-8"));
    //    }

}
