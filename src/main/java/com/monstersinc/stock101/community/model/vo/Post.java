package com.monstersinc.stock101.community.model.vo;

import com.monstersinc.stock101.community.model.dto.PostRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private long postId;
    private String opinion;
    private String content;
    private String createdAt;
    private boolean isDeleted;
    private long stockId;
    private long userId;

    public void setPost(@Valid PostRequestDto requestDto){
        this.opinion = requestDto.getOpinion();
        this.content = requestDto.getContent();
        this.stockId = requestDto.getStockId();
        this.userId = requestDto.getUserId();
    }
}
