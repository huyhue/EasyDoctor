package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpt.edu.vn.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public long removeByPostId(long postid);
}