package com.example.demo.domain.post;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Post extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"roles", "password"})
    private User author;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({"roles", "password"})
    private Set<User> likedBy = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Post(UUID id, User author, String description, String imageUrl) {
        super(id);
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.likedBy = new HashSet<>();
    }

    public void toggleLike(User user) {
        if (likedBy.contains(user)) {
            likedBy.remove(user);
        } else {
            likedBy.add(user);
        }
    }

    public int getLikeCount() {
        return likedBy.size();
    }

    public boolean isLikedByUser(User user) {
        return likedBy.contains(user);
    }
}

