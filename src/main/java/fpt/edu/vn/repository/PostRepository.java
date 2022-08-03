package fpt.edu.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpt.edu.vn.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
    List<Post> findByUserId(Long userId);
    
    @Query("select COUNT(p) from Post p where MONTH(p.updateAt) =:month ")
    int countAllPostByMonth(@Param("month") int month);
}
