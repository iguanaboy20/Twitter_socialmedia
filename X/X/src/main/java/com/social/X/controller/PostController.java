package com.social.X.controller;

import com.social.X.model.Post;
import com.social.X.model.User;
import com.social.X.service.PostService;
import com.social.X.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService; // Assuming you have a UserService to fetch user details

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequestBody requestBody) {
        // Check if the user exists
        User user = userService.getUserByID(requestBody.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        // User exists, proceed to create post
        String response = postService.createPost(requestBody.getUserId(), requestBody.getPostBody());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getPostById(@RequestParam("postID") int postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserFeed() {
        List<Post> posts = postService.getAllPostsSortedByDate(); // Retrieve all posts sorted by date
        return ResponseEntity.ok(posts);
    }

    @PatchMapping
    public ResponseEntity<String> editPost(@RequestParam("postID") int postId, @RequestParam("postBody") String postBody) {
        String response = postService.editPost(postId, postBody);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(@RequestParam("postID") int postId) {
        String response = postService.deletePost(postId);
        return ResponseEntity.ok(response);
    }

    public static class PostRequestBody {
        private int userID;
        private String postBody;

        // Getters and setters
        public int getUserId() {
            return userID;
        }

        public void setUserId(int userId) {
            this.userID = userId;
        }

        public String getPostBody() {
            return postBody;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }
    }
}
