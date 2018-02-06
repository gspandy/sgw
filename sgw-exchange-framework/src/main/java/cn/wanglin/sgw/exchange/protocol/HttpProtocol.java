package cn.wanglin.sgw.exchange.protocol;

import cn.wanglin.sgw.exchange.Exchanger;
import cn.wanglin.sgw.exchange.EntityType;
import cn.wanglin.sgw.exchange.Protocol;
import cn.wanglin.sgw.exchange.exception.ServerException;
import cn.wanglin.sgw.exchange.exception.TimeoutException;
import cn.wanglin.sgw.exchange.utils.WebUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpProtocol<REQ, RSP> implements Protocol<REQ, RSP> {
    Logger logger = LoggerFactory.getLogger(getClass());
    Exchanger<REQ, RSP> exchanger;


    @Override
    public void send(String exchangerSequenceId, REQ request) throws TimeoutException, IOException {
        logger.info("发送交易{}报文:{}", exchangerSequenceId, request);
        String                         resp       = null;
        HttpClient                     httpClient = null;
        HttpEntityEnclosingRequestBase method     = null;
        try {
            method = new HttpPost(exchanger.config.url);
            httpClient = getHttpClient();
            method.setEntity(toHttpEntity(request));
            HttpResponse response       = httpClient.execute(method);
            HttpEntity   responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new IOException(response.getStatusLine().toString() + "|" + resp);
            }
            if (exchanger.config.sync) {
                RSP responseBody = toResponse(responseEntity);
                exchanger.setExchangerResponse(exchangerSequenceId, responseBody);
                logger.info("发送交易{}报文回执:{}", exchangerSequenceId, responseBody);
            } else {
                //do nothing todo
            }
        } catch (Exception e) {
            logger.info("发送交易{}报文失败:{}", exchangerSequenceId, e);
            throw new IOException(e);
        }
    }

    private HttpClient getHttpClient() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        HttpClient httpClient = null;

        PoolingHttpClientConnectionManager poolManger = null;
        InputStream                        in         = null;
        if (exchanger.config.url.contains("https") && null != exchanger.config.certFile) {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            in = new FileInputStream(exchanger.config.certFile);
            keyStore.load(in, exchanger.config.certPassword.toCharArray());

            SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keyStore, exchanger.config.certPassword.toCharArray()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            poolManger = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        } else {
            poolManger = new PoolingHttpClientConnectionManager();
        }
        poolManger.setMaxTotal(exchanger.config.coreSize);
        poolManger.setMaxTotal(exchanger.config.maxSize);
        poolManger.setDefaultMaxPerRoute(20);
        httpClient = HttpClients.custom().setConnectionManager(poolManger).build();

        return httpClient;

    }

    private RSP toResponse(HttpEntity entity) throws IOException {
//        String  mimeType = ContentType.parse(entity.getContentType().getValue()).getMimeType();
        Charset charset = ContentType.parse(entity.getContentType().getValue()).getCharset();
        if (exchanger.config.rspEntityType.equals(EntityType.BYTE)) {
            //转byte
            return (RSP) WebUtils.streamAsByte(entity.getContent(), entity.getContentLength(), charset.toString());
        } else {
            //转String
            return (RSP) WebUtils.streamAsString(entity.getContent(), charset.toString());
        }
    }

    private HttpEntity toHttpEntity(REQ request) throws ServerException {
        //String or byte
        HttpEntity requestBody = null;
        if (exchanger.config.reqEntityType.equals(EntityType.TEXT)) {
            if (request instanceof String) {
                requestBody = new StringEntity(request.toString(), exchanger.config.charset);
            } else {
                throw new ServerException("不支持的转换");
            }
        } else if (exchanger.config.reqEntityType.equals(EntityType.JSON)) {
            //tojson
        } else if (exchanger.config.reqEntityType.equals(EntityType.XML)) {
            if (request instanceof String) {
                requestBody = new StringEntity(request.toString(), exchanger.config.charset);
            } else {
                throw new ServerException("不支持的转换");
            }
        } else if (exchanger.config.reqEntityType.equals(EntityType.FROM)) {
            if (request instanceof Map) {
                List<NameValuePair> params = new ArrayList<>();
                ((Map) request).forEach((k, v) -> {
                    params.add(new BasicNameValuePair(k.toString(), v.toString()));
                });
                requestBody = new UrlEncodedFormEntity(params, exchanger.config.charset);
            } else {
                throw new ServerException("不支持的转换");
            }
        } else if (exchanger.config.reqEntityType.equals(EntityType.BYTE)) {
            if (request instanceof String) {
                requestBody = new ByteArrayEntity(request.toString().getBytes(exchanger.config.charset));
            } else if (request instanceof byte[]) {
                requestBody = new ByteArrayEntity(request.toString().getBytes(exchanger.config.charset));
            } else {
                throw new ServerException("不支持的转换");
            }
        }
        return requestBody;
    }


}
