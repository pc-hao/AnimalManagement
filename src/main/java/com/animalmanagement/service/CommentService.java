package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final static Integer PAGE_SIZE = 10;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    CommentLikeMapper commentLikeMapper;

    @Autowired
    MessageMapper messageMapper;

    public Map<String, Object> adminGetComments(AdminGetCommentsBo adminGetCommentsBo) {
        CommentExample example = new CommentExample();
        example.createCriteria().andCensoredEqualTo(CensorStatusEnum.UNREVIEWED.getCode());

        List<Comment> commentList = commentMapper.selectByExample(example);

        commentList.sort(Comparator.comparing(Comment::getTime));

        List<AdminCommentGetVo> voList = commentList
                .stream()
                .map(e -> {
                    AdminCommentGetVo vo = new AdminCommentGetVo();
                    BeanUtils.copyProperties(e, vo);
                    UserInfo userInfo = userInfoMapper.selectByPrimaryKey(e.getUserId());
                    vo.setUsername(userInfo.getUsername());
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();

        map.put("sumNum", voList.size());

        int start = adminGetCommentsBo.getPage() * adminGetCommentsBo.getPageNum();
        if (start >= voList.size()) {
            map.put("comments", null);
        } else {
            int end = Math.min(start + adminGetCommentsBo.getPageNum(), voList.size());
            map.put("comments", voList.subList(start, end));
        }
        return map;
    }

    public void adminCommentCensor(CommentCensorBo commentCensorBo) {
        Comment comment = commentMapper.selectByPrimaryKey(commentCensorBo.getCommentId());
        Message message;
        if (comment == null) {
            throw new RuntimeException("Comment ID Does Not Exist");
        }

        if (commentCensorBo.getOperate() == 0) {
            comment.setCensored(CensorStatusEnum.PASS.getCode());
            message = Message.builder()
            .userId(comment.getUserId())
            .content("您的评论：“" + comment.getContent() +"”已通过")
            .build();
        } else {
            comment.setCensored(CensorStatusEnum.REJECT.getCode());
            message = Message.builder()
            .userId(comment.getUserId())
            .content("您的评论：“" + comment.getContent() +"”未能通过，理由如下：\n" + commentCensorBo.getReason())
            .build();
        }
        commentMapper.updateByPrimaryKeySelective(comment);
        messageMapper.insertSelective(message);
    }

    public void addComment(AddCommentBo addCommentBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(addCommentBo.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(addCommentBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
        if (addCommentBo.getComment().isEmpty()) {
            throw new RuntimeException("The Content Is Empty");
        }

        Comment insertComment;
        SysRoleUserExample sysRoleUserExample = new SysRoleUserExample();
        sysRoleUserExample.createCriteria().andUserIdEqualTo(addCommentBo.getUserId());
        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectByExample(sysRoleUserExample);
        SysRoleUser sysRoleUser = sysRoleUserList.get(0);
        if(sysRoleUser.getRoleId() == 1) {
            insertComment = Comment.builder()
            .userId(addCommentBo.getUserId())
            .tweetId(addCommentBo.getTweetId())
            .time(LocalDateTime.now())
            .content(addCommentBo.getComment())
            .censored(1)
            .build();
        } else {
            insertComment = Comment.builder()
            .userId(addCommentBo.getUserId())
            .tweetId(addCommentBo.getTweetId())
            .time(LocalDateTime.now())
            .content(addCommentBo.getComment())
            .build();
        }
        
        commentMapper.insertSelective(insertComment);

        tweet.setComments(tweet.getComments() + 1); //todo 感觉这个帖子的评论数可以删了，我看了一下整个项目只有这里用了，因为添加地评论需要过审，没过审的评论拿不到
        tweetMapper.updateByPrimaryKeySelective(tweet);
    }

    public Boolean commentLike(CommentLikeBo commentLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(commentLikeBo.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentLikeBo.getCommentId());
        if (Objects.isNull(comment)) {
            throw new RuntimeException("CommentId Does Not Exist");
        }

        CommentLikeExample example = new CommentLikeExample();
        example.createCriteria()
                .andUserIdEqualTo(commentLikeBo.getUserId())
                .andCommentIdEqualTo(commentLikeBo.getCommentId());
        CommentLikeKey commentLike = commentLikeMapper.selectOneByExample(example);
        if (Objects.isNull(commentLike)) {
            CommentLikeKey insertCommentLike = CommentLikeKey.builder()
                    .userId(commentLikeBo.getUserId())
                    .commentId(commentLikeBo.getCommentId())
                    .build();
            commentLikeMapper.insertSelective(insertCommentLike);
            comment.setLikes(comment.getLikes() + 1);
            commentMapper.updateByPrimaryKeySelective(comment);
            return true;
        } else {
            commentLikeMapper.deleteByPrimaryKey(commentLike);
            comment.setLikes(comment.getLikes() - 1);
            commentMapper.updateByPrimaryKeySelective(comment);
            return false;
        }
    }

    public List<CommentVo> getCommentVoListByTweetId(Integer tweetId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTweetIdEqualTo(tweetId);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        commentList = commentList.stream().filter(this::checkCommentValid).collect(Collectors.toList());

        Map<Integer, UserInfo> userInfoMap = userService.getUserInfoByIdList(
                commentList.stream().map(Comment::getUserId).distinct().toList());
        Map<Integer, UserInfo> adminMap = userService.getAllAdminMap();

        return commentList
                .stream()
                .map(e -> {
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(e, commentVo);
                    UserInfo userInfo = userInfoMap.get(e.getUserId());
                    commentVo.setAvatar(userInfo.getAvatar());
                    commentVo.setUsername(userInfo.getUsername());
                    commentVo.setIsAdmin(adminMap.containsKey(e.getUserId()));
                    commentVo.setLikeNum(e.getLikes());
                    return commentVo;
                }).sorted((o1, o2) -> {
                    if ((o1.getIsAdmin() && o2.getIsAdmin()) || (!o1.getIsAdmin() && !o2.getIsAdmin())) {
                        return o1.getTime().compareTo(o2.getTime());
                    } else if (o1.getIsAdmin()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }).toList();
    }

    public void fillInIsLike(List<CommentVo> commentVoList, Integer userId) {
        /** 用户查询自己所点赞的评论列表，下面流操作时填写CommentVo的isLike字段 */
        CommentLikeExample example = new CommentLikeExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Integer> userCommentLikeList = commentLikeMapper.selectByExample(example)
                .stream().map(CommentLikeKey::getCommentId).toList();
        commentVoList.forEach(e -> {
            e.setIsLike(userCommentLikeList.contains(e.getId()));
        });
    }

    public Map<String, Object> getComments(UserGetCommentsBo getCommentsBo) {
        List<CommentVo> commentVoList = getCommentVoListByTweetId(getCommentsBo.getTweetId());
        fillInIsLike(commentVoList, getCommentsBo.getUserId());
        List<CommentVo> commentVoListSorted = new ArrayList<>();
        for(CommentVo commentVo:commentVoList) {
            if(commentVo.getIsAdmin()) {
                commentVoListSorted.add(commentVo);
            }
        }
        for(CommentVo commentVo:commentVoList) {
            if(!commentVo.getIsAdmin()) {
                commentVoListSorted.add(commentVo);
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        int start = getCommentsBo.getCommentPage() * PAGE_SIZE;
        if (start >= commentVoListSorted.size()) {
            resultMap.put("comments", null);
        } else {
            int end = Math.min(start + PAGE_SIZE, commentVoListSorted.size());
            resultMap.put("comments", commentVoListSorted.subList(start, end));
        }
        return resultMap;
    }

    /**
     * 校验帖子是否过审、被删除
     */
    private boolean checkCommentValid(Comment comment) {
        return Objects.equals(comment.getCensored(), CensorStatusEnum.PASS.getCode()) && !comment.getDeleted();
    }

    /**
     * 通过id获取评论，id不存在直接报错
     */
    public Comment getCommentById(Integer id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (Objects.isNull(comment)) {
            throw new RuntimeException("Comment Id Does Not Exist");
        }
        return comment;
    }

    public void deleteComment(Integer userId, Integer commentId) {
        userService.getUserInfoById(userId);
        Comment comment = getCommentById(commentId);

        if(!Objects.equals(comment.getUserId(), userId)) {
            throw new RuntimeException("不能删除他人帖子");
        }

        comment.setDeleted(true);
        commentMapper.updateByPrimaryKeySelective(comment);
    }
}
