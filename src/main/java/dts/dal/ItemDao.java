package dts.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import dts.data.ItemEntity;

//extends all the basic mongo actions: findById, create, delete,update
public interface ItemDao extends MongoRepository<ItemEntity, String> {

	public List<ItemEntity> findAllByParent_id(@Param("parentId") String parentId, Pageable pageable);
	public List<ItemEntity> findByChildren_id(@Param("childId") String childId, Pageable pageable);
	public List<ItemEntity> findAllByNameLike(@Param("pattern") String pattern, Pageable pageable);
	public List<ItemEntity> findAllByTypeLike(@Param("type") String type, Pageable pageable);
	public List<ItemEntity> findAllByLngBetweenAndLatBetween(
			@Param("minLng") double minLng ,
			@Param("maxLng") double maxLng,
			@Param("minLat") double minLat,
			@Param("maxLat") double maxLat,
			Pageable pageable);
	
	// INSERT - Create
	// SELECT - Read
	// UPDATE - Update
	// DELETE - Delete
}
