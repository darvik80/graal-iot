package xyz.crearts.iot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("mqtt")
public class MqttProperties {
    private String clientId = "graalvm-iot";
    private String accessToken;
    private String uri;
    private String telemetryTopic;
}
