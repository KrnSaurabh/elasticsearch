/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.http;

import org.elasticsearch.common.network.NetworkModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.transport.MockTcpTransport;
import org.elasticsearch.transport.MockTcpTransportPlugin;
import org.elasticsearch.transport.NettyPlugin;

import java.util.Collection;

public abstract class HttpSmokeTestCase extends ESIntegTestCase {

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        return Settings.builder()
            .put(super.nodeSettings(nodeOrdinal))
            .put(NetworkModule.TRANSPORT_TYPE_KEY, randomFrom(NettyPlugin.NETTY_TRANSPORT_NAME,
                MockTcpTransportPlugin.MOCK_TCP_TRANSPORT_NAME))
            .put(NetworkModule.HTTP_ENABLED.getKey(), true).build();
    }

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return pluginList(MockTcpTransportPlugin.class, NettyPlugin.class);
    }

    @Override
    protected Collection<Class<? extends Plugin>> transportClientPlugins() {
        return pluginList(MockTcpTransportPlugin.class, NettyPlugin.class);
    }

    @Override
    protected Settings transportClientSettings() {
        return Settings.builder()
            .put(super.transportClientSettings())
            .put(NetworkModule.TRANSPORT_TYPE_KEY, randomFrom(NettyPlugin.NETTY_TRANSPORT_NAME,
                MockTcpTransportPlugin.MOCK_TCP_TRANSPORT_NAME)).build();
    }

    @Override
    protected boolean ignoreExternalCluster() {
        return true;
    }
}
