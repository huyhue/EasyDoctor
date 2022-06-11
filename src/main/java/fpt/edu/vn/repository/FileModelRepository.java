package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.FileModel;

public interface FileModelRepository extends JpaRepository<FileModel, Integer> {
	
	@Query("select f from FileModel f where f.name = :name and f.user.id = :userId")
	FileModel findByNameAndUserId(@Param("name") String name, @Param("userId") int userId);
	
	@Query("select f from FileModel f where f.user.id = :userId and f.contentType = 'application/pdf'")
	FileModel findCertificationByUserId(@Param("userId") int userId);
	
	@Query("select f from FileModel f where f.user.id = :userId and f.contentType = 'image/png'")
	FileModel findImageByUserId(@Param("userId") int userId);
}
