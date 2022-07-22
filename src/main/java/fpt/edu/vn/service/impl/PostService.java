package fpt.edu.vn.service.impl;

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

import fpt.edu.vn.component.CommentDTO;
import fpt.edu.vn.component.PostDTO;
import fpt.edu.vn.model.Post;
import fpt.edu.vn.repository.PostRepository;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.util.Utils;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @PersistenceContext
    EntityManager entityManager;

    public List<PostDTO> getListPost(String keyword, Integer special_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        List<PostDTO> ls = new ArrayList<>();
        String condition = "";
        if (special_id != null) {
            condition += "special_id=" + special_id;
        }
        if (keyword != null) {
            if (condition.length() > 0)
                condition += " and ";
            condition += "message like '%" + keyword + "%'";
        }
        if (condition.length() > 0)
            condition = " where " + condition;
        String sql = "SELECT p.id,u.fullname,u.profile_img,s.name,p.message,p.img,likes,total_like,update_at FROM easydoctor.posts p join specialties s on s.id=p.special_id join users u on p.user_id=u.id "
                + condition;
        List<Object[]> reList = entityManager.createNativeQuery(sql).getResultList();
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
            return p;
        }).collect(Collectors.toList());
        return ls;
    }

    private List<CommentDTO> getComment(long id) {
        String sql = "SELECT c.id,message,fullname,update_at,u.profile_img,c.user_id FROM easydoctor.comments c join users u on u.id=c.user_id where post_id="
                + id+" order by update_at desc";
        List<Object[]> ls = entityManager.createNativeQuery(sql).getResultList();
        List<CommentDTO> lsCom = ls.stream().map(obj -> {
            CommentDTO c = new CommentDTO(Utils.objToLong(obj[0]), Utils.objToString(obj[1]), Utils.objToString(obj[2]),
                    Utils.objToString(obj[3]), Utils.objToString(obj[4]), Utils.objToLong(obj[5]));
            return c;
        }).collect(Collectors.toList());
        return lsCom;
    }

    public boolean handleLike(long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        try {
            Post p = postRepository.getById(postId);
            String lsLike = p.getLikes();
            String newLs = "";
            if (lsLike.contains(user.getId().toString())) {
                if (lsLike.length() > 1) {
                    newLs = lsLike.replace("," + user.getId(), "");
                }
                p.setLikes(newLs);
                postRepository.save(p);
                return true;
            }
            if (lsLike.length() > 1) {
                newLs = lsLike + "," + user.getId();
            } else {
                newLs = user.getId() + "";
            }
            p.setLikes(newLs);
            postRepository.save(p);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isLike(String likes, int id) {
        List<String> ls = Arrays.asList(likes.split(","));
        return ls.contains(String.valueOf(id));
    }
}
