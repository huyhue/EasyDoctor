package fpt.edu.vn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fpt.edu.vn.component.CommentDTO;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.UserService;
import fpt.edu.vn.service.impl.CommentService;
import fpt.edu.vn.service.impl.PostService;

@Controller
@RequestMapping("/forum")
public class ForumController {

	private final UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;

	public ForumController(UserService userService) {
		super();
		this.userService = userService;

	}

	@GetMapping("/list")
	public String showListPost(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(required = false) Integer specialId,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer page) {

		model.addAttribute("currentId", currentUser.getId());
		model.addAttribute("currentImg", currentUser.getProfileImage());
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("specialId", specialId);
		model.addAttribute("ls", postService.getListPost(keyword, specialId));

		model.addAttribute("specialties", userService.getAllSpecialty());
		return "forum/home";
	}

	@GetMapping("/comment")
	@ResponseBody
	public CommentDTO comment(@RequestParam Long postid,
			@RequestParam String message) {

		return commentService.addComment(postid, message);
	}

	@GetMapping("/comment/delete")
	@ResponseBody
	public boolean deleteComment(@RequestParam Long cid) {

		return commentService.deleteComment(cid);
	}

}
