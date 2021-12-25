package xyz.crearts.iot.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortCodeMapping {
    @Id
    private Long id;
    private Integer deviceId;
    private String portCode;
    private Integer destination;
}
