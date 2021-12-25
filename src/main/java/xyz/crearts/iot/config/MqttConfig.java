package xyz.crearts.iot.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig implements MqttCallback {
    private IMqttAsyncClient client;
    private final MqttProperties props;

    public MqttConfig(MqttProperties props) {
        this.props = props;
        log.info("MQTT: {} {}", props.getUri(), props.getAccessToken());
    }

    @Bean
    IMqttAsyncClient mqttAsyncClient(MqttProperties props) throws MqttException {
        var client = new MqttAsyncClient(props.getUri(), props.getClientId(), null);
        var options = new MqttConnectOptions();
        options.setUserName(props.getAccessToken());
        options.setAutomaticReconnect(true);

        client.connect(options).waitForCompletion();

        this.client = client;
        return client;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Scheduled(fixedRate = 5000)
    void onMessage() throws MqttException {
        if (client != null) {
            String data = String.format("{\"date\": \"%s\"}", Instant.now().toString());
            client.publish(props.getTelemetryTopic(), new MqttMessage(data.getBytes()));
            log.info("publish: {}", data);
        }
    }
}
