package com.monstersinc.stock101.community.model.mapper;

import com.monstersinc.stock101.community.model.vo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {
    void insertPost(Post post); // XML에서 insertPost는 꼭 useGeneratedKeys="true" keyProperty="postId" 설정!

    Post selectPostById(@Param("id") int id);

    List<Post> selectPostsByStockId(@Param("stockId") int stockId);
}

