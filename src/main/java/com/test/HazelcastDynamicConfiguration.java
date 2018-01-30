package com.test;

import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.ReplicatedMapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jeveloper (Sergey Trembach) on 30.01.18.
 */
@Configuration
public class HazelcastDynamicConfiguration {

    @Bean
    public ReplicatedMap<String, Compilation> compilations(HazelcastInstance hazelcastInstance) {

        ReplicatedMapConfig config = new ReplicatedMapConfig("compilation.map");
        config.setInMemoryFormat(InMemoryFormat.OBJECT);
        config.setAsyncFillup(false);
        hazelcastInstance.getConfig().addReplicatedMapConfig(config);

        return hazelcastInstance.getReplicatedMap("compilation.map");
    }



}
