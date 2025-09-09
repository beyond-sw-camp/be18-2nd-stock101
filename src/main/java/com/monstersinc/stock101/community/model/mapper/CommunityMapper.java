package com.monstersinc.stock101.community.model.mapper;

import com.monstersinc.stock101.community.model.vo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface CommunityMapper {
    int insertPost(Post post); // XML에서 insertPost는 꼭 useGeneratedKeys="true" keyProperty="postId" 설정!

    Post selectPostById(@Param("id") int id);

    List<Post> selectPostsByStock(@Param("stockId") int stockId, RowBounds rowBounds);

    long countPostsByStock(@Param("stockId") int stockId);
}

