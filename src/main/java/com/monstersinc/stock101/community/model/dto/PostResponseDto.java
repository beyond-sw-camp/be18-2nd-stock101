package com.monstersinc.stock101.community.model.dto;

import com.monstersinc.stock101.community.model.vo.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private int postId;      // required
    private int stockId;     // required
    private int userId;      // required
    private String opinion;  // required (<= 100 chars)
    private String content;  // required
    private String createdAt;// required (string <date-time>)
    private boolean isDeleted;// required

    public static PostResponseDto of(Post p) {
        return PostResponseDto.builder()
                .postId(p.getPostId())
                .opinion(p.getOpinion())
                .content(p.getContent())
                .createdAt(p.getCreatedAt())
                .isDeleted(p.isDeleted())
                .stockId(p.getStockId())
                .userId(p.getUserId())
                .build();
    }

    public static List<PostResponseDto> of(List<Post> posts) {
        return posts.stream().map(PostResponseDto::of).toList();
    }
}

