package fpt.edu.vn.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import fpt.edu.vn.component.PostDTO;
import fpt.edu.vn.model.Post;
import fpt.edu.vn.repository.PostRepository;
import fpt.edu.vn.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	
    public PostServiceImpl(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}
    
    public List<PostDTO> getListPosts(){
    	 List<PostDTO> list = new ArrayList<>();
    	 List<Post> listPost =  postRepository.findAll();
    	 return list;
    }

}