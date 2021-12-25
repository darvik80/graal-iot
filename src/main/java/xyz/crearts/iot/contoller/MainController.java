package xyz.crearts.iot.contoller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.crearts.iot.persistence.repository.PortCodeMappingRepository;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final JsonMapper mapper = new JsonMapper();
    private final PortCodeMappingRepository repository;

    @GetMapping
    public String index() throws JsonProcessingException {
        var res = repository.findAll();

        return mapper.writeValueAsString(res);
    }
}
