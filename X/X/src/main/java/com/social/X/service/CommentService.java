package com.social.X.service;

import com.social.X.model.Comment;
import com.social.X.model.Post;
import com.social.X.model.User;
import com.social.X.repo.CommentRepository;
import com.social.X.repo.PostRepository;
import com.social.X.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public String createComment(int userId, int postId, String commentBody) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User does not exist";
        }

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "Post does not exist";
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentBody(commentBody);
        commentRepository.save(comment);

        return "Comment created successfully";
    }

    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public String editComment(int commentId, String commentBody) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return "Comment does not exist";
        }

        comment.setCommentBody(commentBody);
        commentRepository.save(comment);

        return "Comment edited successfully";
    }

    public String deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return "Comment does not exist";
        }

        commentRepository.delete(comment);

        return "Comment deleted";
    }
}