package com.test;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;


@Configuration
public class HazelcastConfiguration {

    @Value("${hazelcast.group_name:test}")
    private String groupName;

    @Value("${hazelcast.group_password:password}")
    private String groupPassword;

    @Value("${hazelcast.port:5307}")
    private int port;

    @Value("${hazelcast.port_auto-increment:true}")
    private boolean portAutoIncrement;

    @Value("${hazelcast.multicast_enabled:false}")
    private boolean multicast;

    @Value("${hazelcast.tcp-ip_enabled:true}")
    private boolean tcpIp;

    @Value("${hazelcast.tcp-ip_members:127.0.0.1}")
    private String tcpIpMembers;

    @Bean
    public HazelcastInstance hazelcastInstance() {

        Config cfg = new Config();

        cfg.setProperty("hazelcast.logging.type", "slf4j");

        cfg.getGroupConfig().setName(groupName);
        cfg.getGroupConfig().setPassword(groupPassword);

        NetworkConfig network = cfg.getNetworkConfig();
        network.setPort(port);
        network.setPortAutoIncrement(portAutoIncrement);

        JoinConfig join = network.getJoin();

        join.getTcpIpConfig().setEnabled(tcpIp);
        if (tcpIp)
            join.getTcpIpConfig().setMembers(Arrays.asList(tcpIpMembers.split(",")));

        join.getAwsConfig().setEnabled(false);

        join.getMulticastConfig().setEnabled(multicast);

        return Hazelcast.newHazelcastInstance(cfg);
    }


}
