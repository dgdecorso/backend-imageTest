package com.example.demo.domain.post;

import com.example.demo.core.generic.AbstractRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends AbstractRepository<Post> {
    
    List<Post> findByAuthorId(UUID authorId);
    
    List<Post> findByAuthorIdOrderByCreatedAtDesc(UUID authorId);
    
    List<Post> findAllByOrderByCreatedAtDesc();
}
