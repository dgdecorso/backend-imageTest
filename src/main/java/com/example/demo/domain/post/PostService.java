package com.example.demo.domain.post;

import com.example.demo.core.generic.AbstractService;
import com.example.demo.domain.user.User;
import java.util.List;
import java.util.UUID;

public interface PostService extends AbstractService<Post> {
    
    List<Post> findByAuthorId(UUID authorId);
    
    Post createPost(Post post, User author);
    
    Post updatePost(UUID postId, Post updatedPost, User currentUser);
    
    void deletePost(UUID postId, User currentUser);
    
    Post toggleLike(UUID postId, User user);
    
    boolean isOwner(UUID postId, UUID userId);
    
    List<Post> findAllOrderedByDate();
}
