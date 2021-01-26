package dts.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import dts.data.ItemEntity;
import dts.data.OperationEntity;

public interface OperationDao extends MongoRepository<OperationEntity, String> {
//	public List<OperationEntity> getAllOperationsBoundaries(@Param("operations") String operationId, Pageable pageable);
//	public List<OperationEntity> findAllByOperationBoundary(@Param("operationId") String operationId, Pageable pageable);
}
