package dts.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dts.data.ItemEntity;
import dts.data.OperationEntity;
import dts.data.UserEntity;

public interface UserDao extends MongoRepository<UserEntity, String>{

}
