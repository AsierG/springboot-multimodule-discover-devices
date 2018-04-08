package com.asierg.multimodule.service.services.impl;

import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.enums.*;
import com.asierg.multimodule.service.services.RcpService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.ByteBuffer;

@Service
public class RcpServiceImpl implements RcpService {

    private static final Logger logger = LoggerFactory.getLogger(RcpServiceImpl.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("audit-log");

    private static final String REBOOT_SIGNAL = "REBOOTING";

    @Value("${rcp.asierg.port:80}")
    private int rcpPort;

    @Value("${rcp.asierg.user:admin}")
    private String rcpUser;

    @Value("${rcp.asierg.password:admin}")
    private String rcpPassword;

    private Byte[] allFieldsRequest;

    @Autowired
    public RcpServiceImpl(Byte[] allFieldsRequest) {
        this.allFieldsRequest = allFieldsRequest;
    }

    private String getValueFromData(String fieldName, String data) {
        final String init = fieldName + " = ";
        final String end = "\n";
        int startingPosition = data.indexOf(init);
        return data.substring(startingPosition + init.length(), data.indexOf(end, startingPosition));
    }

    private URL getConnectionUrl(String ip) throws MalformedURLException {
        String CONNECTION_URL = String.format("http://%s:%s", ip, rcpPort);
        return new URL(CONNECTION_URL);
    }

    public DeviceDTO getDeviceData(String ip, RcpContentType rcpContentType) {
        DeviceDTO deviceDTO = null;
        try {

            HttpURLConnection conn = getConnection(getConnectionUrl(ip), rcpContentType.getType());

            conn.connect();

            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(ArrayUtils.toPrimitive(allFieldsRequest));
            }

            ReflashResultCode result = ReflashResultCode.getReflashResultCode(conn.getResponseCode());
            if (result.equals(ReflashResultCode.OK_RESPONSE)) {

                StringBuilder sb = new StringBuilder();

                String line;

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                deviceDTO = buildDevice(ip, sb.toString());
            }
            conn.disconnect();
        } catch (IOException ioe) {
            logger.error("An error occurred while accesing via rcp. Ip: {}", ip, ioe);
        }
        return deviceDTO;
    }

    public ReflashResultCode reflashDevice(String ip, RcpContentType rcpContentType, Firmware firmware) {
        ReflashResultCode result;
        try {
            HttpURLConnection conn = getConnection(getConnectionUrl(ip), rcpContentType.getType());
            conn.connect();
            byte[] postData = getBytesToSendInReflash(firmware.getImageFile());

            auditLogger.info("IP {} image upload start", ip);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }

            result = ReflashResultCode.getReflashResultCode(conn.getResponseCode());
            auditLogger.info("IP {} image upload end", ip);

            if (result.equals(ReflashResultCode.OK_RESPONSE)) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    logger.debug(line);
                    if (line.toUpperCase().contains(REBOOT_SIGNAL)) {
                        auditLogger.info("IP {} device will reboot", ip);
                        break;
                    }
                }
            }

            conn.disconnect();
        } catch (IOException ioe) {
            logger.error("An error occurred while accesing via rcp. Ip: {}", ip, ioe);
            result = ReflashResultCode.NO_CONNECTION_RESPONSE;
        }
        return result;
    }

    private DeviceDTO buildDevice(String ip, String rcpResponseData) {
        String modelIdentifier = getValueFromData(RcpField.MAIN_PRODUCT.getFieldName(), rcpResponseData);
        String serialNumber = getValueFromData(RcpField.SERIAL_NUMBER.getFieldName(), rcpResponseData);
        String firmwareVersion = getValueFromData(RcpField.MAIN_VERSION.getFieldName(), rcpResponseData);
        DeviceDTO deviceDTO = null;
        if (serialNumber != null) {
            deviceDTO = new DeviceDTO();
            deviceDTO.setIpAddress(ip);
            deviceDTO.setSerialNumber(serialNumber);
            deviceDTO.setProductIdentifier(modelIdentifier);
            deviceDTO.setFirmwareVersion(firmwareVersion);
        }
        return deviceDTO;
    }

    private HttpURLConnection getConnection(URL url, String contentType) throws IOException, ProtocolException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, contentType);
        String userPassword = rcpUser + ":" + rcpPassword;
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        conn.setRequestProperty("Authorization", "Basic " + encoding);
        conn.setUseCaches(false);
        return conn;
    }

    private byte[] getBytesToSendInReflash(byte[] firmwareData) throws IOException {
        byte[] type = ByteBuffer.allocate(2).put((byte) 0).array();
        //En este tipo de peticiones, a la longitud de los datos hay que sumar la longitud del campo "type"
        byte[] length = ByteBuffer.allocate(4).putInt(firmwareData.length + 2).array();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + length.length + 1 + type.length + firmwareData.length);
        byteBuffer
                .put(RcpCode.REFLASH_MESSAGE.getCode())
                .put(length)
                .put(RcpJoin.LAST_FRAGMENT.getFragement())
                .put(type)
                .put(firmwareData);
        Byte[] result = ArrayUtils.toObject(byteBuffer.array());
        ArrayUtils.toPrimitive(result);
        return byteBuffer.array();
    }


}
