package com.example.demo.domain.post;

import com.example.demo.domain.post.dto.PostDTO;
import com.example.demo.domain.post.dto.PostMapper;
import com.example.demo.domain.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/posts")
public class PostController {
    
    private final PostService postService;
    private final PostMapper postMapper;
    
    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = postService.findAllOrderedByDate();
        return ResponseEntity.ok(postMapper.toDTOs(posts));
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable UUID userId) {
        List<Post> posts = postService.findByAuthorId(userId);
        return ResponseEntity.ok(postMapper.toDTOs(posts));
    }
    
    @GetMapping("/my-posts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostDTO>> getMyPosts(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        List<Post> posts = postService.findByAuthorId(userDetails.user().getId());
        return ResponseEntity.ok(postMapper.toDTOs(posts));
    }
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Post post = postMapper.fromDTO(postDTO);
        Post createdPost = postService.createPost(post, userDetails.user());
        return new ResponseEntity<>(postMapper.toDTO(createdPost), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDTO> updatePost(@PathVariable UUID id, 
                                              @Valid @RequestBody PostDTO postDTO, 
                                              Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Post post = postMapper.fromDTO(postDTO);
        Post updatedPost = postService.updatePost(id, post, userDetails.user());
        return ResponseEntity.ok(postMapper.toDTO(updatedPost));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id, Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        postService.deletePost(id, userDetails.user());
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/like")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDTO> toggleLike(@PathVariable UUID id, Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Post post = postService.toggleLike(id, userDetails.user());
        return ResponseEntity.ok(postMapper.toDTO(post));
    }
}
