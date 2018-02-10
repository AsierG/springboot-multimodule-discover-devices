package com.asierg.multimodule.service.services;

import com.asierg.multimodule.service.utils.IpUtils;
import org.apache.commons.net.util.SubnetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class PruebaService {

    private static final Logger logger = LoggerFactory.getLogger(PruebaService.class);

    @Value("${name:unknownName}")
    private String name;

    public PruebaService() {
    }

    @Autowired
    private ProcessService processService;

    public String getMessage() {
        return this.getMessage(this.name);
    }

    public String getMessage(String name) {
        return "Hello " + name;
    }

    private void pruebasIps() throws UnknownHostException {
        SubnetUtils utils = new SubnetUtils("192.168.1.0/15");
        String[] allIps = utils.getInfo().getAllAddresses();
        int ipsSize = allIps.length;
        String var10000 = allIps[allIps.length - 1];
        InetAddress[] addr = InetAddress.getAllByName("192.168.1.0");
        List<String> ipRange = IpUtils.iprange2cidr("192.168.0.0", "192.168.255.0");
        utils = new SubnetUtils((String) ipRange.get(0));
        SubnetUtils utils2 = new SubnetUtils((String) ipRange.get(0));
        allIps = utils.getInfo().getAllAddresses();
        String[] allIps1 = utils2.getInfo().getAllAddresses();
        ipsSize = allIps.length;
        int ipsSize2 = allIps1.length;
        SubnetUtils utilsPrueba = new SubnetUtils("192.168.224.0/17");
        String[] allIpsPrueba = utilsPrueba.getInfo().getAllAddresses();
        int allIpsPruebaSize = allIpsPrueba.length;
        var10000 = allIpsPrueba[allIpsPrueba.length - 1];
        SubnetUtils utilsPrueba2 = new SubnetUtils("192.168.224.0/24");
        String[] allIpsPrueba2 = utilsPrueba2.getInfo().getAllAddresses();
        int allIpsPrueba2Size = allIpsPrueba2.length;
        var10000 = allIpsPrueba[allIpsPrueba2.length - 1];
    }

    /*private void pruebaSnmp() {
        long start = System.currentTimeMillis();
        SubnetUtils utils = new SubnetUtils("192.168.1.1/15");
        String[] allIps = utils.getInfo().getAllAddresses();
        int ipsSize = allIps.length;
        List<CompletableFuture<DeviceDTO>> listCompletableFuture = new ArrayList();
        String[] var7 = allIps;
        int var8 = allIps.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            String ip = var7[var9];
            CompletableFuture<DeviceDTO> snmpDeviceCompletableFuture = this.processService.discoverIp(ip);
            listCompletableFuture.add(snmpDeviceCompletableFuture);
        }

        CompletableFuture.allOf((CompletableFuture[]) listCompletableFuture.toArray(new CompletableFuture[0])).join();
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
    }*/

}
