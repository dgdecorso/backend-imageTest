package com.example.demo.domain.post;

import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl extends AbstractServiceImpl<Post> implements PostService {
    
    private final PostRepository postRepository;
    
    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        super(postRepository);
        this.postRepository = postRepository;
    }
    
    @Override
    public List<Post> findByAuthorId(UUID authorId) {
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);
    }
    
    @Override
    @Transactional
    public Post createPost(Post post, User author) {
        post.setAuthor(author);
        return postRepository.save(post);
    }
    
    @Override
    @Transactional
    public Post updatePost(UUID postId, Post updatedPost, User currentUser) {
        Post post = findById(postId);
        
        // Check if user is owner or admin
        if (!post.getAuthor().getId().equals(currentUser.getId()) && 
            !hasAdminRole(currentUser)) {
            throw new AccessDeniedException("You can only edit your own posts");
        }
        
        post.setDescription(updatedPost.getDescription());
        post.setImageUrl(updatedPost.getImageUrl());
        
        return postRepository.save(post);
    }
    
    @Override
    @Transactional
    public void deletePost(UUID postId, User currentUser) {
        Post post = findById(postId);
        
        // Check if user is owner or admin
        if (!post.getAuthor().getId().equals(currentUser.getId()) && 
            !hasAdminRole(currentUser)) {
            throw new AccessDeniedException("You can only delete your own posts");
        }
        
        postRepository.deleteById(postId);
    }
    
    @Override
    @Transactional
    public Post toggleLike(UUID postId, User user) {
        Post post = findById(postId);
        post.toggleLike(user);
        return postRepository.save(post);
    }
    
    @Override
    public boolean isOwner(UUID postId, UUID userId) {
        Post post = findById(postId);
        return post.getAuthor().getId().equals(userId);
    }
    
    @Override
    public List<Post> findAllOrderedByDate() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
    
    private boolean hasAdminRole(User user) {
        return user.getRoles().stream()
            .anyMatch(role -> role.getName().equals("ADMIN"));
    }
}
