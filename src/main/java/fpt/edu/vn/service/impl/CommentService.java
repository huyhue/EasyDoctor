package fpt.edu.vn.service.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fpt.edu.vn.component.CommentDTO;
import fpt.edu.vn.model.Comment;
import fpt.edu.vn.repository.CommentRepository;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.util.Utils;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepo;
    @PersistenceContext
    EntityManager em;

    public CommentDTO getComment(long id) {
        String sql = "SELECT c.id,message,fullname,update_at,u.profile_img,c.user_id FROM easydoctor.comments c join users u on u.id=c.user_id where  c.id="
                + id;
        Object[] obj = (Object[]) em.createNativeQuery(sql).getSingleResult();
        CommentDTO c = new CommentDTO(Utils.objToLong(obj[0]), Utils.objToString(obj[1]), Utils.objToString(obj[2]),
                Utils.objToString(obj[3]), Utils.objToString(obj[4]), Utils.objToLong(obj[5]));
        return c;

    }

    public CommentDTO addComment(Long postid, String message) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
            Comment c = new Comment();
            c.setPostId(postid);
            c.setUserId(Long.parseLong(user.getId().toString()));
            c.setMessage(message);
            c.setCreateAt(Utils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            c.setUpdateAt(Utils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            commentRepo.save(c);
            return getComment(c.getId());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteComment(Long id) {
        try {
            commentRepo.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public CommentDTO updateComment(Long id, String message) {
        try {
            Comment c = commentRepo.getById(id);
            c.setMessage(message);
            c.setUpdateAt(Utils.formatDate(new Date(), Utils.DATETIME_FORMAT));
            commentRepo.save(c);
            return getComment(c.getId());
        } catch (Exception e) {
            return null;
        }
    }
}