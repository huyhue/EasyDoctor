package fpt.edu.vn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fpt.edu.vn.component.CommentDTO;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.PostService;
import fpt.edu.vn.service.UserService;
import fpt.edu.vn.service.impl.CommentService;

@Controller
@RequestMapping("/forums")
public class ForumController {

	private final UserService userService;
	private final PostService postService;
	@Autowired
	private CommentService commentService;

	public ForumController(UserService userService, PostService postService, CommentService commentService) {
		super();
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping("/list")
	public String showListPost(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(required = false) Integer specialId,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer page) {
		
		model.addAttribute("currentId", currentUser.getId());
		model.addAttribute("currentImg", currentUser.getProfileImage());
//		model.addAttribute("page", page);
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("specialId", specialId);	
//		model.addAttribute("listPost", postService.getListPost(keyword, specialId));
		model.addAttribute("listPost", postService.getListPosts());

		model.addAttribute("specialties", userService.getAllSpecialty());
		return "forum/home";
	}
	
//	@GetMapping("/comment")
//	@ResponseBody
//	public CommentDTO comment(@RequestParam Long postid,
//			@RequestParam String message) {
//		return commentService.addComment(postid, message);
//	}

//	@GetMapping("/comment/delete")
//	@ResponseBody
//	public boolean deleteComment(@RequestParam Long cid) {
//		return commentService.deleteComment(cid);
//	}

}
