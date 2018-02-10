package com.asierg.multimodule.service.enums;

import org.snmp4j.smi.OID;

public enum SnmpField {

    SERIAL_NUMBER(new OID("1.3.6.1.4.1.15732.10.2.0")),
    PRODUCT_IDENTIFIER(new OID("1.3.6.1.4.1.15732.10.6.0")),
    FIRMWARE_VERSION(new OID("1.3.6.1.4.1.15732.10.3.0")),
    MANUFACTURER(new OID("1.3.6.1.4.1.15732.10.1.0")),
    SYS_DESCRIPTION(new OID("1.3.6.1.2.1.1.1.0"));

    private final OID oid;

    SnmpField(OID oid) {
        this.oid = oid;
    }

    public OID getOid() {
        return this.oid;
    }
}