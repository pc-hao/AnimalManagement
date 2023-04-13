package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import org.springframework.stereotype.Service;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    CommentLikeMapper commentLikeMapper;

    public Map<String, Object> adminGetComments(AdminGetCommentsBo adminGetCommentsBo) {
        List<Comment> commentList = commentMapper.selectByExample(new CommentExample());

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", commentList.size());

        commentList.sort(Comparator.comparing(Comment::getTime));
        int start = (adminGetCommentsBo.getPage() - 1) * adminGetCommentsBo.getPageNum();
        if (start >= commentList.size()) {
            map.put("users", null);
        } else {
            int end = Math.min(start + adminGetCommentsBo.getPageNum(), commentList.size());
            map.put("users", commentList.subList(start, end));
        }
        return map;
    }

    public void adminCommentCensor(CommentCensorBo commentCensorBo) {
        Integer commentId = commentCensorBo.getCommentId();
        checkIdExists(commentId);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if(commentCensorBo.getOperate() == 0) {
            comment.setCensored(true);
        } else {
            comment.setDeleted(true);
        }
    }

    public void checkIdExists(Integer commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment == null) {
            throw new RuntimeException("CommentId Does Not Exist");
        }
    }

    public void addComment(AddCommentBo addCommentBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(addCommentBo.getUserId());
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(addCommentBo.getTweetId());
        if(Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
        if(addCommentBo.getComment().isEmpty()) {
            throw new RuntimeException("The Content Is Empty");
        }
        Comment insertComment = Comment.builder()
                                .userId(addCommentBo.getUserId())
                                .tweetId(addCommentBo.getTweetId())
                                .time(LocalDateTime.now())
                                .isHelp(false)
                                .content(addCommentBo.getComment())
                                .build();
        commentMapper.insertSelective(insertComment);
    }

    public void commentLike(CommentLikeBo commentLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(commentLikeBo.getUserId());
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentLikeBo.getCommentId());
        if(Objects.isNull(comment)) {
            throw new RuntimeException("CommentId Does Not Exist");
        }
        
        CommentLikeExample example = new CommentLikeExample();
        example.createCriteria().andUserIdEqualTo(commentLikeBo.getUserId());
        example.createCriteria().andCommentIdEqualTo(commentLikeBo.getCommentId());
        CommentLikeKey commentLike = commentLikeMapper.selectOneByExample(example);
        if(Objects.isNull(commentLike)) {
            CommentLikeKey insertCommentLike =  CommentLikeKey.builder()
                                            .userId(commentLikeBo.getUserId())
                                            .commentId(commentLikeBo.getCommentId())
                                            .build();
            commentLikeMapper.insertSelective(insertCommentLike);
            comment.setLikes(comment.getLikes() + 1);
            commentMapper.updateByPrimaryKeySelective(comment);
        }
    }

    public void commentUnlike(CommentLikeBo commentLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(commentLikeBo.getUserId());
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentLikeBo.getCommentId());
        if(Objects.isNull(comment)) {
            throw new RuntimeException("CommentId Does Not Exist");
        }
        
        CommentLikeExample example = new CommentLikeExample();
        example.createCriteria().andUserIdEqualTo(commentLikeBo.getUserId());
        example.createCriteria().andCommentIdEqualTo(commentLikeBo.getCommentId());
        CommentLikeKey commentLike = commentLikeMapper.selectOneByExample(example);
        if(!Objects.isNull(commentLike)) {
            commentLikeMapper.deleteByPrimaryKey(commentLike);
            comment.setLikes(comment.getLikes() - 1);
            commentMapper.updateByPrimaryKeySelective(comment);
        }
    }
}
