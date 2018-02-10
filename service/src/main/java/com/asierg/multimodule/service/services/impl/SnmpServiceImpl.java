package com.asierg.multimodule.service.services.impl;

import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.enums.SnmpField;
import com.asierg.multimodule.service.exceptions.SnmpException;
import com.asierg.multimodule.service.services.SnmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SnmpServiceImpl implements SnmpService {

    private static final Logger logger = LoggerFactory.getLogger(SnmpServiceImpl.class);

    @Value("${snmp.retries:2}")
    private int snmpRetries;
    @Value("${snmp.timeout.milliseconds:1000}")
    private int snmpTimeoutMilliSeconds;
    @Value("${snmp.port:161}")
    private int snmpPort;

    public DeviceDTO getDevice(String ip) {
        CommunityTarget communityTarget = new CommunityTarget();
        communityTarget.setCommunity(new OctetString("public"));
        communityTarget.setAddress(GenericAddress.parse(String.format("udp:%s/%s", ip, this.snmpPort)));
        communityTarget.setRetries(this.snmpRetries);
        communityTarget.setTimeout((long) this.snmpTimeoutMilliSeconds);
        communityTarget.setVersion(1);
        Snmp snmp = null;

        DeviceDTO deviceDTO;
        try {
            TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            PDU pdu = new PDU();
            pdu.addAll(this.getOIDVariableBindings());
            pdu.setType(-96);
            ResponseEvent event = snmp.send(pdu, communityTarget);
            deviceDTO = this.getSnmp(event);
            deviceDTO.setIpAddress(ip);
        } catch (IOException ioe) {
            logger.error("An error occurred while accesing snmp. Ip: {}", ip);
            throw new SnmpException(ioe);
        } finally {
            this.closeSnmpConnection(snmp, ip);
        }

        return deviceDTO;
    }

    private DeviceDTO getSnmp(ResponseEvent event) {
        DeviceDTO deviceDTO = null;
        if (event != null && event.getResponse() != null) {
            PDU response = event.getResponse();
            if (response != null) {
                deviceDTO = new DeviceDTO();
                deviceDTO.setSerialNumber(this.getValueFromOIDVariable(response, SnmpField.SERIAL_NUMBER.getOid()));
                deviceDTO.setProductIdentifier(this.getValueFromOIDVariable(response, SnmpField.PRODUCT_IDENTIFIER.getOid()));
                deviceDTO.setFirmwareVersion(this.getValueFromOIDVariable(response, SnmpField.FIRMWARE_VERSION.getOid()));
                deviceDTO.setManufacturer(this.getValueFromOIDVariable(response, SnmpField.MANUFACTURER.getOid()));
                deviceDTO.setSysDescription(this.getValueFromOIDVariable(response, SnmpField.SYS_DESCRIPTION.getOid()));
            }
        }
        return deviceDTO;
    }

    private String getValueFromOIDVariable(PDU response, OID oid) {
        return response.getVariable(oid) != null ? response.getVariable(oid).toString() : null;
    }

    private void closeSnmpConnection(Snmp snmp, String ip) {
        if (snmp != null) {
            try {
                snmp.close();
            } catch (IOException var4) {
                logger.error("An error occurred while closing snmp. Ip: {}", ip);
                throw new SnmpException(var4);
            }
        }
    }

    public VariableBinding[] getOIDVariableBindings() {
        List<VariableBinding> variableBindingList = Arrays.stream(SnmpField.values()).map((snmpField) -> {
            return new VariableBinding(snmpField.getOid());
        }).collect(Collectors.toList());
        return variableBindingList.toArray(new VariableBinding[variableBindingList.size()]);
    }
}