package com.asierg.multimodule.service.config;

import com.asierg.multimodule.service.enums.RcpCode;
import com.asierg.multimodule.service.enums.RcpField;
import com.asierg.multimodule.service.enums.RcpJoin;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Configuration
public class RcpConfig {

    @Value("${rcp.asierg.user:admin}")
    private String rcpUser;

    @Value("${rcp.asierg.password:admin}")
    private String rcpPassword;

    @Bean
    public HttpClient httpClient() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(rcpUser, rcpPassword);
        provider.setCredentials(AuthScope.ANY, credentials);
        return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }

    @Bean(name = "productIdentifierRequest")
    public Byte[] productIdentifierRequest() throws IOException {
        byte[] bytes = getBytesToSendInRequestField(RcpField.MAIN_PRODUCT);
        Byte[] result = ArrayUtils.toObject(bytes);
        return result;
    }

    @Bean(name = "serialNumberRequest")
    public Byte[] serialNumberRequest() throws IOException {
        byte[] bytes = getBytesToSendInRequestField(RcpField.SERIAL_NUMBER);
        Byte[] result = ArrayUtils.toObject(bytes);
        return result;
    }

    @Bean(name = "firmwareVersionRequest")
    public Byte[] firmwareVersionRequest() throws IOException {
        byte[] bytes = getBytesToSendInRequestField(RcpField.MAIN_VERSION);
        Byte[] result = ArrayUtils.toObject(bytes);
        return result;
    }

    @Bean(name = "allFieldsRequest")
    public Byte[] allFieldsRequest() throws IOException {
        byte[] productIdentifierMessage = getBytesToSendInRequestField(RcpField.MAIN_PRODUCT);
        byte[] serialNumberMessage = getBytesToSendInRequestField(RcpField.SERIAL_NUMBER);
        byte[] firmwareVersionMessage = getBytesToSendInRequestField(RcpField.MAIN_VERSION);
        ByteBuffer byteBuffer = ByteBuffer.allocate(productIdentifierMessage.length + serialNumberMessage.length + firmwareVersionMessage.length);
        byteBuffer
                .put(productIdentifierMessage)
                .put(serialNumberMessage)
                .put(firmwareVersionMessage);
        Byte[] result = ArrayUtils.toObject(byteBuffer.array());
        return result;
    }

    private byte[] getBytesToSendInRequestField(RcpField rcpField) throws IOException {
        byte[] fieldDataByteArray = rcpField.getRequestString().getBytes(StandardCharsets.UTF_8);
        byte[] length = ByteBuffer.allocate(4).putInt(fieldDataByteArray.length).array();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + length.length + 1 + fieldDataByteArray.length);
        byteBuffer
                .put(RcpCode.REQUEST_MESSAGE.getCode())
                .put(length)
                .put(RcpJoin.LAST_FRAGMENT.getFragement())
                .put(fieldDataByteArray);
        Byte[] result = ArrayUtils.toObject(byteBuffer.array());
        ArrayUtils.toPrimitive(result);
        return byteBuffer.array();
    }

}
