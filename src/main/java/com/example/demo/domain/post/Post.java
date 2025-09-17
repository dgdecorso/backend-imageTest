package com.example.demo.domain.post;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.role.Role;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Post extends AbstractEntity {

    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "likes")
    private int likes;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_tags", joinColumns = @JoinColumn(name = "posts_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();


    public Post(UUID id, UUID userId, String description, String imageUrl, String createdAt, int likes, Set<Role> roles) {
        super(id);
        this.userId = userId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.likes = likes;
        this.roles = roles;
    }



}

