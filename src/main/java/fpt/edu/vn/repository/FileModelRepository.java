package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.FileModel;

public interface FileModelRepository extends JpaRepository<FileModel, Integer> {
	
	@Query("select f from FileModel f where f.name = :name and f.type = :type")
	FileModel findByNameAndHistoryId(@Param("name") String name, @Param("type") String type);
}
