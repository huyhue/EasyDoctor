package fpt.edu.vn.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.component.CommentDTO;
import fpt.edu.vn.component.PostDTO;
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
			@RequestParam(required = false) Integer did,
			@RequestParam(required = false) Integer specialId,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer page) {
		if (currentUser == null) {
			return "users/login";
		}
		if (page == null)
			page = 1;
		model.addAttribute("currentId", currentUser.getId());
		model.addAttribute("currentImg", currentUser.getProfileImage());
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("specialId", specialId);
		model.addAttribute("did", did);
		model.addAttribute("maxpage", postService.getTotalPage(did, keyword, specialId));
		model.addAttribute("ls", postService.getListPost(did, keyword, specialId, page));
		model.addAttribute("specialties", userService.getAllSpecialty());
		return "forum/home";
	}

	@GetMapping("/comment")
	@ResponseBody
	public CommentDTO comment(@RequestParam Long postid,
			@RequestParam String message) {
		return commentService.addComment(postid, message);
	}

	@PostMapping("/comment/update")
	@ResponseBody
	public CommentDTO updatComment(@RequestParam Long commentid,
			@RequestParam String message) {
		return commentService.updateComment(commentid, message);
	}

	@GetMapping("/comment/delete")
	@ResponseBody
	public boolean deleteComment(@RequestParam Long cid) {
		return commentService.deleteComment(cid);
	}

	@GetMapping("/post/like")
	@ResponseBody
	public PostDTO likePost(@RequestParam Long pid) {
		return postService.handleLike(pid);
	}

	@PostMapping("/post/add")
	public String addNewPost(@RequestParam String message, @RequestParam(required = false) long specialId,
			@RequestParam(required = false) MultipartFile image)
			throws IOException {
		postService.addPost(message, specialId, image);
		return "redirect:/forum/list";
	}

	@PostMapping("/post/update")
	public String updatePost(@RequestParam Long pid, @RequestParam String message, @RequestParam long specialId,
			@RequestParam(required = false) MultipartFile image)
			throws IOException {
		postService.updatePost(pid, message, image, specialId);
		return "redirect:/forum/list";
	}

	@GetMapping("/post/delete")
	public String deletePost(@RequestParam Long pid) {
		postService.deletePost(pid);
		return "redirect:/forum/list";
	}

}