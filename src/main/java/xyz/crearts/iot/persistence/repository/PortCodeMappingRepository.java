package xyz.crearts.iot.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.crearts.iot.persistence.entity.PortCodeMapping;

@Repository
public interface PortCodeMappingRepository extends CrudRepository<PortCodeMapping, Long> {
}
