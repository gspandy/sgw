package cn.wanglin.sgw.protocol;

import cn.wanglin.sgw.*;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.TimeoutException;
import cn.wanglin.sgw.utils.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpProtocol<REQ, RSP> implements Protocol<REQ, RSP> {
    Logger logger = LoggerFactory.getLogger(getClass());
    Channel<REQ, RSP> channel;


    @Override
    public void send(String channelSequenceId, REQ request) throws TimeoutException, IOException {
        logger.info("发送交易{}报文:{}", channelSequenceId, request);
        String                         resp       = null;
        HttpClient                     httpClient = null;
        HttpEntityEnclosingRequestBase method     = null;
        try {
            method = new HttpPost(channel.config.url);
            httpClient = getHttpClient();
            method.setEntity(toHttpEntity(request));
            HttpResponse response       = httpClient.execute(method);
            HttpEntity   responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new IOException(response.getStatusLine().toString() + "|" + resp);
            }
            if (channel.config.sync) {
                RSP responseBody = toResponse(responseEntity);
                channel.setChannelResponse(channelSequenceId, responseBody);
                logger.info("发送交易{}报文回执:{}", channelSequenceId, responseBody);
            } else {
                //do nothing todo
            }
        } catch (Exception e) {
            logger.info("发送交易{}报文失败:{}", channelSequenceId, e);
            throw new IOException(e);
        }
    }

    private HttpClient getHttpClient() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        HttpClient httpClient = null;

        PoolingHttpClientConnectionManager poolManger = null;
        InputStream                        in         = null;
        if (channel.config.url.contains("https") && null != channel.config.certFile) {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            in = new FileInputStream(channel.config.certFile);
            keyStore.load(in, channel.config.certPassword.toCharArray());

            SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keyStore, channel.config.certPassword.toCharArray()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            poolManger = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        } else {
            poolManger = new PoolingHttpClientConnectionManager();
        }
        poolManger.setMaxTotal(channel.config.coreSize);
        poolManger.setMaxTotal(channel.config.maxSize);
        poolManger.setDefaultMaxPerRoute(20);
        httpClient = HttpClients.custom().setConnectionManager(poolManger).build();

        return httpClient;

    }

    private RSP toResponse(HttpEntity entity) throws IOException {
//        String  mimeType = ContentType.parse(entity.getContentType().getValue()).getMimeType();
        Charset charset = ContentType.parse(entity.getContentType().getValue()).getCharset();
        if (channel.config.rspEntityType.equals(EntityType.BYTE)) {
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
        if (channel.config.reqEntityType.equals(EntityType.TEXT)) {
            if (request instanceof String) {
                requestBody = new StringEntity(request.toString(), channel.config.charset);
            } else {
                throw new ServerException("不支持的转换");
            }
        } else if (channel.config.reqEntityType.equals(EntityType.JSON)) {
            //tojson
        } else if (channel.config.reqEntityType.equals(EntityType.XML)) {
            if (request instanceof String) {
                requestBody = new StringEntity(request.toString(), channel.config.charset);
            } else {
                throw new ServerException("不支持的转换");
            }
        } else if (channel.config.reqEntityType.equals(EntityType.FROM)) {
            if (request instanceof Map) {
                List<NameValuePair> params = new ArrayList<>();
                ((Map) request).forEach((k, v) -> {
                    params.add(new BasicNameValuePair(k.toString(), v.toString()));
                });
                requestBody = new UrlEncodedFormEntity(params, channel.config.charset);
            } else {
                throw new ServerException("不支持的转换");
            }
        } else if (channel.config.reqEntityType.equals(EntityType.BYTE)) {
            if (request instanceof String) {
                requestBody = new ByteArrayEntity(request.toString().getBytes(channel.config.charset));
            } else if (request instanceof byte[]) {
                requestBody = new ByteArrayEntity(request.toString().getBytes(channel.config.charset));
            } else {
                throw new ServerException("不支持的转换");
            }
        }
        return requestBody;
    }


}
