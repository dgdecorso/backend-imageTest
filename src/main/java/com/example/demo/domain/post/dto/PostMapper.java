package com.example.demo.domain.post.dto;

import com.example.demo.core.generic.AbstractMapper;
import com.example.demo.domain.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper extends AbstractMapper<Post, PostDTO> {
    
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", expression = "java(post.getAuthor() != null ? (post.getAuthor().getFirstName() + \" \" + post.getAuthor().getLastName()) : null)")
    @Mapping(target = "likeCount", expression = "java(post.getLikeCount())")
    @Mapping(target = "likedByUserIds", expression = "java(post.getLikedBy() != null ? post.getLikedBy().stream().map(u -> u.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())")
    PostDTO toDTO(Post post);
    
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "likedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post fromDTO(PostDTO dto);
}