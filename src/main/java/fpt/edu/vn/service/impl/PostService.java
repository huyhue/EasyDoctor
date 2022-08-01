package fpt.edu.vn.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.component.CommentDTO;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.PostDTO;
import fpt.edu.vn.model.Post;
import fpt.edu.vn.repository.CommentRepository;
import fpt.edu.vn.repository.PostRepository;
import fpt.edu.vn.repository.SpecialtyRepository;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.util.Utils;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentRepository commentRepository;
	private final SpecialtyRepository specialtyRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	public PostService(PostRepository postRepository, CommentRepository commentRepository,
			SpecialtyRepository specialtyRepository, EntityManager entityManager) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.specialtyRepository = specialtyRepository;
		this.entityManager = entityManager;
	}

	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	private final int pageSize = 5;

	public int getTotalPost(long doctorId) {
		return postRepository.findByUserId(doctorId).size();
	}

	public List<PostDTO> getAllForum() {
		List<PostDTO> ls = new ArrayList<>();
		String sql = "SELECT p.id,u.fullname,s.name,p.message,p.img,total_like,update_at FROM posts p join specialties s on s.id=p.special_id join users u on p.user_id=u.id";
		List<Object[]> reList = entityManager.createNativeQuery(sql).getResultList();

		ls = reList.stream().map(obj -> {
			PostDTO p = new PostDTO();
			p.setId(Utils.objToLong(obj[0]));
			p.setUsername(Utils.objToString(obj[1]));
			p.setSpecial(Utils.objToString(obj[2]));
			p.setMessage(Utils.objToString(obj[3]));
			p.setImg(Utils.objToString(obj[4]));
			p.setTotalLike(Utils.objToInt(obj[5]));
			p.setTime(Utils.objToString(obj[6]));
			return p;
		}).collect(Collectors.toList());
		return ls;
	}

	public List<PostDTO> getListPost(Integer doctorId, String keyword, Integer special_id, Integer page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		List<PostDTO> ls = new ArrayList<>();
		String condition = "";
		if (special_id != null) {
			condition += "special_id=" + special_id;
		}
		if (doctorId != null) {
			if (condition.length() > 0)
				condition += " and ";
			condition += "user_id=" + doctorId;
		}
		if (keyword != null) {
			if (condition.length() > 0)
				condition += " and ";
			condition += "message like '%" + keyword + "%'";
		}
		if (condition.length() > 0)
			condition = " where " + condition;
		String sql = "SELECT p.id,u.fullname,u.profile_img,s.name,p.message,p.img,likes,total_like,update_at,p.user_id,p.special_id FROM posts p join specialties s on s.id=p.special_id join users u on p.user_id=u.id "
				+ condition + " order by update_at desc";

		List<Object[]> reList = entityManager.createNativeQuery(sql).setFirstResult((page - 1) * pageSize)
				.setMaxResults(pageSize).getResultList();
		ls = reList.stream().map(obj -> {
			PostDTO p = new PostDTO();
			p.setId(Utils.objToLong(obj[0]));
			p.setUsername(Utils.objToString(obj[1]));
			p.setUserImg(Utils.objToString(obj[2]));
			p.setSpecial(Utils.objToString(obj[3]));
			p.setMessage(Utils.objToString(obj[4]));
			p.setImg(Utils.objToString(obj[5]));
			p.setLiked(isLike(Utils.objToString(obj[6]), user.getId()));
			p.setTotalLike(Utils.objToInt(obj[7]));
			p.setComments(getComment(p.getId()));
			p.setTime(Utils.objToString(obj[8]));
			p.setUserid(Utils.objToLong(obj[9]));
			p.setSpecialId(Utils.objToLong(obj[10]));
			return p;
		}).collect(Collectors.toList());
		return ls;
	}

	public long getTotalPage(Integer doctorId, String keyword, Integer special_id) {
		String condition = "";
		if (special_id != null) {
			condition += "special_id=" + special_id;
		}
		if (doctorId != null) {
			if (condition.length() > 0)
				condition += " and ";
			condition += "user_id=" + doctorId;
		}
		if (keyword != null) {
			if (condition.length() > 0)
				condition += " and ";
			condition += "message like '%" + keyword + "%'";
		}
		if (condition.length() > 0)
			condition = " where " + condition;
		String sql = "SELECT count(*) FROM posts p join specialties s on s.id=p.special_id join users u on p.user_id=u.id "
				+ condition;
		long res = Utils.objToLong(entityManager.createNativeQuery(sql).getSingleResult());
		return (long) Math.ceil((double) res / pageSize);
	}

	public boolean deletePost(long postid) {
		try {
			postRepository.deleteById(postid);
			commentRepository.removeByPostId(postid);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updatePost(long postid, String message, MultipartFile image, long specialId) throws IOException {
		Post p = postRepository.getById(postid);

		if (!image.isEmpty()) {
			Path staticPath = Paths.get("src/main/resources/static");
			Path imagePath = Paths.get("img/forum");
			if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
				Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
			}
			Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image.getOriginalFilename());
			try (OutputStream os = Files.newOutputStream(file)) {
				os.write(image.getBytes());
			}
			p.setImg(image.getOriginalFilename());
		}

		p.setSpecialId(specialId);
		p.setMessage(message);
//		p.setUpdateAt(Utils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		postRepository.save(p);
		return true;

	}

	public CommonMsg saveForum(PostDTO post) throws IOException {
		CommonMsg commonMsg = new CommonMsg();
//		if (post.getId() == null) {
//			addPost(post.getMessage(), post.getSpecial(), null);
//		}else {
//			updatePost(post.getId(), post.getMessage(), null, post.getSpecial());
//		}
		addPost(post.getMessage(), post.getSpecial(), null);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}

	public boolean addPost(String message, String special, MultipartFile image) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		Post p = new Post();
		try {
			if (!image.isEmpty()) {
				Path staticPath = Paths.get("src/main/resources/static");
				Path imagePath = Paths.get("img/forum");
				if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
					Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
				}

				Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image.getOriginalFilename());
				try (OutputStream os = Files.newOutputStream(file)) {
					os.write(image.getBytes());
				}
				p.setImg(image.getOriginalFilename());
			}
		} finally {
			p.setSpecialId((long) specialtyRepository.findByName(special).getId());
			p.setUserId(Long.parseLong(user.getId().toString()));
			p.setMessage(message);
//			p.setUpdateAt(Utils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			postRepository.save(p);
		}
		return true;
	}

	private List<CommentDTO> getComment(long id) {
		String sql = "SELECT c.id,message,fullname,update_at,u.profile_img,c.user_id FROM comments c join users u on u.id=c.user_id where post_id="
				+ id + " order by update_at desc";
		List<Object[]> ls = entityManager.createNativeQuery(sql).getResultList();
		List<CommentDTO> lsCom = ls.stream().map(obj -> {
			CommentDTO c = new CommentDTO(Utils.objToLong(obj[0]), Utils.objToString(obj[1]), Utils.objToString(obj[2]),
					Utils.objToString(obj[3]), Utils.objToString(obj[4]), Utils.objToLong(obj[5]));
			return c;
		}).collect(Collectors.toList());
		return lsCom;
	}

	public PostDTO handleLike(long postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

		Post p = postRepository.getById(postId);
		String lsLike = p.getLikes();
		String newLs = "";
		if (lsLike == null || lsLike.isEmpty()) {
			p.setLikes("" + user.getId());
			p.setTotalLike(1L);
			postRepository.save(p);
			PostDTO pdto = new PostDTO();
			pdto.setLiked(true);
			pdto.setTotalLike(p.getTotalLike());
			return pdto;
		}
		if (lsLike.contains(user.getId().toString())) {
			if (lsLike.length() > 1) {
				newLs = lsLike.replace("," + user.getId(), "");
			}
			p.setLikes(newLs);
			p.setTotalLike(p.getTotalLike() - 1);
			postRepository.save(p);
			PostDTO pdto = new PostDTO();
			pdto.setLiked(isLike(p.getLikes(), user.getId()));
			pdto.setTotalLike(p.getTotalLike());
			return pdto;
		}
		if (lsLike.length() > 1) {
			newLs = lsLike + "," + user.getId();
		} else {
			newLs = user.getId() + "";
		}
		p.setTotalLike(p.getTotalLike() + 1);

		p.setLikes(newLs);
		postRepository.save(p);
		PostDTO pdto = new PostDTO();
		pdto.setLiked(isLike(p.getLikes(), user.getId()));
		pdto.setTotalLike(p.getTotalLike());
		return pdto;
	}

	public boolean isLike(String likes, int id) {
		if (likes.isEmpty())
			return false;
		List<String> ls = Arrays.asList(likes.split(","));
		return ls.contains(String.valueOf(id));
	}

	public CommonMsg deleteForum(long postId) {
		CommonMsg commonMsg = new CommonMsg();
		deletePost(postId);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}
}