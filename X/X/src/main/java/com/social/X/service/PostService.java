package com.social.X.service;

import com.social.X.model.Post;
import com.social.X.model.User;
import com.social.X.repo.PostRepository;
import com.social.X.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public String createPost(int userId, String postBody) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User does not exist for ID: " + userId); // Logging statement
            return "User does not exist";
        }

        Post post = new Post();
        post.setUser(user);
        post.setPostBody(postBody);
        post.setDate(new Date());
        postRepository.save(post);

        return "Post created successfully";
    }

    public List<Post> getAllPostsSortedByDate() {
        // Retrieve all posts from the repository sorted by date in descending order
        return postRepository.findAllByOrderByDateDesc();
    }

    public Post getPostById(int postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public String editPost(int postId, String postBody) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "Post does not exist";
        }

        post.setPostBody(postBody);
        postRepository.save(post);

        return "Post edited successfully";
    }

    public String deletePost(int postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "Post does not exist";
        }

        postRepository.delete(post);

        return "Post deleted";
    }
}
