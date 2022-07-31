package fpt.edu.vn.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import fpt.edu.vn.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	List<Question> findAll();

}