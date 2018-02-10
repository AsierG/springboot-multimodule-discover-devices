package com.asierg.multimodule.service.utils;

import com.asierg.multimodule.service.exceptions.IpRangeFormatException;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

    public IpUtils() {
    }

    public static Set<String> translateIpRange(String ipStart, String ipEnd) throws IpRangeFormatException {
        Long ipStartLong = ip2long(ipStart);
        Long ipEndLong = ip2long(ipEnd);
        if (ipStartLong > ipEndLong) {
            throw new IpRangeFormatException("The starting point of IP range is bigger than the ending point");
        }
        Set<String> ipList = new LinkedHashSet<>();
        for (Long currentIp = ipStartLong; currentIp <= ipEndLong; currentIp++) {
            ipList.add(long2ip(currentIp));
        }
        return ipList;
    }

    public static List<String> iprange2cidr(String ipStart, String ipEnd) {
        long start = ip2long(ipStart);
        long end = ip2long(ipEnd);

        List<String> result;
        byte maxSize;
        for (result = new ArrayList<>(); end >= start; start = (long) ((double) start + Math.pow(2.0D, (double) (32 - maxSize)))) {
            for (maxSize = 32; maxSize > 0; --maxSize) {
                long mask = iMask(maxSize - 1);
                long maskBase = start & mask;
                if (maskBase != start) {
                    break;
                }
            }

            double x = Math.log((double) (end - start + 1L)) / Math.log(2.0D);
            byte maxDiff = (byte) ((int) (32.0D - Math.floor(x)));
            if (maxSize < maxDiff) {
                maxSize = maxDiff;
            }

            String ip = long2ip(start);
            result.add(ip + "/" + maxSize);
        }

        return result;
    }


    /**
     * Operación para obtener ips a partir de un rango de ips representado
     * en un String. Los rangos estaran unidos mediante "-" y separados con ";"
     * Por ejemplo: "192.168.5.0-192.168.5.100;192.168.8.50-192.168.8.100;192.168.9.15"
     *
     * @param ipRangePinput Rango de ips
     * @param Set<String>   Listado de ips
     * @return Datos del dispositivo
     */
    public static Set<String> getIpsFromInputRange(String ipRangePinput) {
        Set<String> ips = new LinkedHashSet<>();
        InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
        String[] ranges = ipRangePinput.split(";");
        logger.info("ipRangePinput {}", ipRangePinput);
        for (String range : ranges) {
            String[] ipRange = range.split("-");
            if (ipRange.length == 2) {
                if (inetAddressValidator.isValid(ipRange[0]) && inetAddressValidator.isValid(ipRange[1])) {
                    ips.addAll(IpUtils.translateIpRange(ipRange[0], ipRange[1]));
                } else {
                    logger.error(ipRange[0] + " " + inetAddressValidator.isValid(ipRange[0]) + " , "
                            + ipRange[0] + " " + inetAddressValidator.isValid(ipRange[0]));
                }
            } else {
                if (inetAddressValidator.isValid(ipRange[0])) {
                    ips.add(ipRange[0]);
                } else {
                    logger.error(ipRange[0] + " " + inetAddressValidator.isValid(ipRange[0]));
                }
            }
        }
        logger.info("ips size {}", ips.size());
        return ips;
    }


    private static long iMask(int s) {
        return Math.round(Math.pow(2.0D, 32.0D) - Math.pow(2.0D, (double) (32 - s)));
    }

    private static long ip2long(String ipstring) {
        String[] ipAddressInArray = ipstring.split("\\.");
        long num = 0L;
        long ip;

        for (int x = 3; x >= 0; --x) {
            ip = Long.parseLong(ipAddressInArray[3 - x]);
            num |= ip << (x << 3);
        }

        return num;
    }

    private static String long2ip(long longIP) {
        StringBuffer sbIP = new StringBuffer("");
        sbIP.append(String.valueOf(longIP >>> 24));
        sbIP.append(".");
        sbIP.append(String.valueOf((longIP & 16777215L) >>> 16));
        sbIP.append(".");
        sbIP.append(String.valueOf((longIP & 65535L) >>> 8));
        sbIP.append(".");
        sbIP.append(String.valueOf(longIP & 255L));
        return sbIP.toString();
    }

    public static List<String> translateCIDR(String cidr) throws IpRangeFormatException {
        SubnetUtils subnetUtils = new SubnetUtils(cidr);
        subnetUtils.setInclusiveHostCount(true);
        return Arrays.asList(subnetUtils.getInfo().getAllAddresses());
    }
}