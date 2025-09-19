package com.example.demo.domain.post.dto;

import com.example.demo.core.generic.AbstractDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostDTO extends AbstractDTO {
    
    private UUID authorId;
    private String authorName;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private int likeCount;
    private Set<UUID> likedByUserIds;
    private boolean likedByCurrentUser;
}