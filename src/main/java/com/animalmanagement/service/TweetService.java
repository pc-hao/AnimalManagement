package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.config.ImageConfig;
import com.animalmanagement.entity.*;
import com.animalmanagement.enums.CensorStatusEnum;
import com.animalmanagement.example.*;
import com.animalmanagement.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private static final String PICTURE_SAVE_PATH_FRONT = ImageConfig.frontPath + "/tweet/";

    private static final String PICTURE_SAVE_PATH = ImageConfig.savePath + "/tweet/";

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserService userService;

    @Autowired
    StarMapper starMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    TweetLikeMapper tweetLikeMapper;

    @Autowired
    TweetStarMapper tweetStarMapper;

    @Autowired
    TweetTagMapper tweetTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    SearchLogService searchLogService;

    @Autowired
    ImageService imageService;

    public Map<String, Object> adminTweetGet(AdminTweetGetBo adminTweetGetBo) {
        TweetExample example = new TweetExample();
        example.createCriteria()
                .andDeletedEqualTo(false)
                .andIsHelpEqualTo(false)
                .andCensoredEqualTo(CensorStatusEnum.UNREVIEWED.getCode());

        List<Tweet> tweetList = tweetMapper.selectByExample(example);

        List<AdminTweetGetVo> voList;

        if (tweetList.isEmpty()) {
            voList = new ArrayList<>();
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getTime));

            Map<Integer, UserInfo> userInfoMap = userService.getUserInfoByIdList(
                    tweetList.stream().map(Tweet::getUserId).distinct().toList());

            voList = tweetList
                    .stream()
                    .map(e -> {
                        AdminTweetGetVo vo = new AdminTweetGetVo();
                        BeanUtils.copyProperties(e, vo);
                        UserInfo userInfo = userInfoMap.get(e.getUserId());
                        vo.setUsername(userInfo.getUsername());
                        return vo;
                    }).toList();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = adminTweetGetBo.getPage() * adminTweetGetBo.getPageNum();
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + adminTweetGetBo.getPageNum(), voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> adminTweetGetContent(AdminTweetContentBo adminTweetContentBo) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(adminTweetContentBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("Tweet ID Does Not Exist");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(tweet.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("username", userInfo.getUsername());
        map.put("title", tweet.getTitle());
        map.put("content", tweet.getContent());
        map.put("images", tweet.getImages());
        map.put("time", tweet.getTime());
        map.put("views", tweet.getViews());
        map.put("viewsWeekly", tweet.getViewsWeekly());
        map.put("likes", tweet.getLikes());
        map.put("stars", tweet.getStars());
        map.put("isHelp", tweet.getIsHelp());
        map.put("censored", tweet.getCensored());
        map.put("solved", tweet.getSolved());
        map.put("published", tweet.getPublished());
        map.put("deleted", tweet.getDeleted());

        if (tweet.getImages() == null) {
            map.put("images", null);
        } else {
            List<String> images = Arrays.asList(tweet.getImages().split(";"));
            map.put("images", images);
        }

        return map;
    }

    public void adminTweetCensor(TweetCensorBo tweetCensorBo) {
        Integer tweetId = tweetCensorBo.getTweetId();
        checkIdExists(tweetId);
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        Message message;
        if (tweetCensorBo.getOperate() == 0) {
            tweet.setCensored(CensorStatusEnum.PASS.getCode());
            message = Message.builder()
            .userId(tweet.getUserId())
            .content("您的贴子：“" + tweet.getTitle() +"”已通过")
            .build();
        } else {
            tweet.setCensored(CensorStatusEnum.REJECT.getCode());
            message = Message.builder()
            .userId(tweet.getUserId())
            .content("您的贴子：“" + tweet.getTitle() +"”未能通过，理由如下：\n" + tweetCensorBo.getReason())
            .build();
        }
        tweetMapper.updateByPrimaryKeySelective(tweet);
        messageMapper.insertSelective(message);
    }

    public void checkIdExists(Integer tweetId) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        if (tweet == null) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
    }

    public TweetContentVo getTweetContent(TweetContentBo tweetContentBo) {
        Tweet tweet = getTweetById(tweetContentBo.getTweetId());
        UserInfo tweetAuthorInfo = userService.getUserInfoById(tweet.getUserId());
        checkTweetValid(tweetContentBo.getUserId(), tweet);

        TweetContentVo tweetContentVo = new TweetContentVo();
        BeanUtils.copyProperties(tweet, tweetContentVo);

        if (tweet.getImages() == null) {
            tweetContentVo.setImages(null);
        } else {
            List<String> images = Arrays.asList(tweet.getImages().split(";"));
            tweetContentVo.setImages(images);
        }

        tweetContentVo.setUsername(tweetAuthorInfo.getUsername());
        tweetContentVo.setAvatar(tweetAuthorInfo.getAvatar());

        TweetLikeExample likeExample = new TweetLikeExample();
        likeExample.createCriteria().andUserIdEqualTo(tweetContentBo.getUserId()).andTweetIdEqualTo(tweet.getId());
        tweetContentVo.setHasLiked(Objects.nonNull(tweetLikeMapper.selectOneByExample(likeExample)));

        TweetStarExample starExample = new TweetStarExample();
        starExample.createCriteria().andUserIdEqualTo(tweetContentBo.getUserId()).andTweetIdEqualTo(tweet.getId());
        tweetContentVo.setHasStarred(Objects.nonNull(tweetStarMapper.selectOneByExample(starExample)));

        tweetContentVo.setComments(commentService.getCommentVoListByTweetId(tweetContentBo.getTweetId()).size());

        TweetTagExample tweetTagExample = new TweetTagExample();
        tweetTagExample.createCriteria().andTweetIdEqualTo(tweetContentBo.getTweetId());
        List<TweetTagKey> tweetTagKeyList = tweetTagMapper.selectByExample(tweetTagExample);
        List<Integer> tagIdList = tweetTagKeyList.stream().map(TweetTagKey::getTagId).toList();

        if (tagIdList.isEmpty()) {
            tweetContentVo.setTags(new ArrayList<>());
        } else {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andIdIn(tagIdList);
            List<Tag> tagList = tagMapper.selectByExample(tagExample);
            List<String> tagNameList = tagList.stream().map(Tag::getContent).toList();

            tweetContentVo.setTags(tagNameList);
        }

        tweetContentVo.setMaxHeightImage(imageService.imagesMaxHeight(tweet.getImages()));
        return tweetContentVo;
    }

    /**
     * 通过id获取帖子，id不存在直接报错
     */
    public Tweet getTweetById(Integer id) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(id);
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("Tweet Id Does Not Exist");
        }
        return tweet;
    }

    /**
     * 校验帖子是否过审、被删除、发布
     */
    private void checkTweetValid(Integer userId, Tweet tweet) {
        if (!Objects.equals(tweet.getCensored(), CensorStatusEnum.PASS.getCode())
                && !Objects.equals(tweet.getUserId(), userId)) {
            throw new RuntimeException("Tweet Is Not Censored");
        }
        if (tweet.getDeleted()) {
            throw new RuntimeException("Tweet Has Been Deleted");
        }
        if (!tweet.getPublished()) {
            throw new RuntimeException("Tweet Has Not Been Published");
        }
    }

    public Map<String, Object> getTweets(GetTweetsBo getTweetsBo) {
        int pageSize = 8;

        TweetExample example = new TweetExample();
        example.createCriteria()
            .andPublishedEqualTo(true)
            .andCensoredEqualTo(CensorStatusEnum.PASS.getCode())
            .andDeletedEqualTo(false)
            .andIsHelpEqualTo(false)
            .andTitleLike("%" + getTweetsBo.getMatch() + "%");

        searchLogService.insertSearchLog(getTweetsBo.getMatch(), false);

        List<Tweet> tweetList = tweetMapper.selectByExample(example);
        if(getTweetsBo.getTag() != null && !getTweetsBo.getTag().equals("")) {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andContentEqualTo(getTweetsBo.getTag());
            Tag tag = tagMapper.selectOneByExample(tagExample);
            if(tag == null) {
                tweetList = new ArrayList<>();
            } else {
                List<Integer> tweetIdList = new ArrayList<>();
                List<Tweet> tweetList2 = new ArrayList<>();
                TweetTagExample tweetTagExample = new TweetTagExample();
                tweetTagExample.createCriteria().andTagIdEqualTo(tag.getId());
                List<TweetTagKey> tweetTagKeyList = tweetTagMapper.selectByExample(tweetTagExample);
                for(TweetTagKey tweetTagKey:tweetTagKeyList) {
                    tweetIdList.add(tweetTagKey.getTweetId());
                }
                for(Tweet tweet:tweetList) {
                    if(tweetIdList.contains(tweet.getId())) {
                        tweetList2.add(tweet);
                    }
                }
                tweetList = tweetList2;
            }
        }

        sortTweetList(tweetList, getTweetsBo.getType());

        tweetList.forEach(e-> e.setComments(commentService.getCommentVoListByTweetId(e.getId()).size()));

        List<MainTweetVo> mainTweetVos = tweetList.stream()
                .map(e -> transTweetToMainVo(e, getTweetsBo.getUserId()))
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();

        int start = getTweetsBo.getCommentpage() * pageSize;
        if (start >= mainTweetVos.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, mainTweetVos.size());
            map.put("tweets", mainTweetVos.subList(start, end));
        }
        return map;
    }

    private MainTweetVo transTweetToMainVo(Tweet e, Integer searchUserId) {
        MainTweetVo mainTweetVo = new MainTweetVo();
        BeanUtils.copyProperties(e,mainTweetVo);
        UserInfo userInfo = userService.getUserInfoById(e.getUserId());
        mainTweetVo.setUsername(userInfo.getUsername());
        mainTweetVo.setAvatar(userInfo.getAvatar());
        mainTweetVo.setIsLike(isUserLikeTweet(searchUserId, e.getId()));
        return mainTweetVo;
    }

    public boolean isUserLikeTweet(Integer userId, Integer tweetId) {
        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria().andTweetIdEqualTo(tweetId).andUserIdEqualTo(userId);
        return Objects.nonNull(tweetLikeMapper.selectOneByExample(example));
    }

    public Boolean tweetLike(Integer userId, Integer tweetId) {
        userService.getUserInfoById(userId);
        Tweet tweet = getTweetById(tweetId);
        checkTweetValid(0, tweet);

        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andTweetIdEqualTo(tweetId);
        TweetLikeKey tweetLike = tweetLikeMapper.selectOneByExample(example);
        if (Objects.isNull(tweetLike)) {
            TweetLikeKey insertTweetLike = TweetLikeKey.builder()
                    .userId(userId)
                    .tweetId(tweetId)
                    .build();
            tweetLikeMapper.insertSelective(insertTweetLike);
            tweet.setLikes(tweet.getLikes() + 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return true;
        } else {
            tweetLikeMapper.deleteByPrimaryKey(tweetLike);
            tweet.setLikes(tweet.getLikes() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return false;
        }
    }

    public Boolean tweetStar(Integer userId, Integer tweetId) {
        userService.getUserInfoById(userId);
        Tweet tweet = getTweetById(tweetId);
        checkTweetValid(0, tweet);

        TweetStarExample example = new TweetStarExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andTweetIdEqualTo(tweetId);
        TweetStarKey tweetStar = tweetStarMapper.selectOneByExample(example);
        if (Objects.isNull(tweetStar)) {
            TweetStarKey insertTweetStar = TweetStarKey.builder()
                    .userId(userId)
                    .tweetId(tweetId)
                    .build();
            tweetStarMapper.insertSelective(insertTweetStar);
            tweet.setStars(tweet.getStars() + 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return true;
        } else {
            tweetStarMapper.deleteByPrimaryKey(tweetStar);
            tweet.setStars(tweet.getStars() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return false;
        }
    }

    private List<Integer> getTweetIdListWithTag(String tagStr) {
        TagExample tagExample = new TagExample();
        tagExample.createCriteria().andContentEqualTo(tagStr);
        Tag tag = tagMapper.selectOneByExample(tagExample);
        if(Objects.isNull(tag)) {
            return new ArrayList<>();
        }

        TweetTagExample tweetTagExample = new TweetTagExample();
        tweetTagExample.createCriteria().andTagIdEqualTo(tag.getId());
        return tweetTagMapper.selectByExample(tweetTagExample).stream().map(e->e.getTweetId()).distinct().collect(Collectors.toList());
    }

    private List<Integer> getStarTweetIdList(Integer userId) {
        TweetStarExample tweetStarExample = new TweetStarExample();
        tweetStarExample.createCriteria().andUserIdEqualTo(userId);
        List<TweetStarKey> tweetStarList = tweetStarMapper.selectByExample(tweetStarExample);
        return tweetStarList.stream().map(TweetStarKey::getTweetId).distinct().collect(Collectors.toList());
    }

    private List<Tweet> getTweetListById(List<Integer> tweetIdList, String context, Integer type) {
        if(tweetIdList.isEmpty()) {
            return new ArrayList<>();
        }
        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andIdIn(tweetIdList)
                .andTitleLike("%" + context + "%")
                .andIsHelpEqualTo(type != 0);

        return tweetMapper.selectByExample(tweetExample);
    }

    private UserStarTweetVo transTweetToStarTweetVo(Tweet e, Integer searchUserId) {
        UserStarTweetVo vo = new UserStarTweetVo();
        BeanUtils.copyProperties(e, vo);
        UserInfo tweetOwner = userService.getUserInfoById(e.getUserId());
        vo.setAvatar(tweetOwner.getAvatar());
        vo.setUsername(tweetOwner.getUsername());
        vo.setIsLike(isUserLikeTweet(searchUserId, e.getId()));
        vo.setSolved(e.getSolved());
        return vo;
    }

    public Map<String, Object> starTweet(UserStarTweetBo userStarTweetBo) {
        userService.getUserInfoById(userStarTweetBo.getUserId());

        List<Integer> starTweetIdList = getStarTweetIdList(userStarTweetBo.getUserId());
        if(!(Objects.isNull(userStarTweetBo.getTag()) || userStarTweetBo.getTag().isEmpty())) {
            List<Integer> tweetIdListWithTag = getTweetIdListWithTag(userStarTweetBo.getTag());
            starTweetIdList.retainAll(tweetIdListWithTag);
        }

        List<Tweet> tweetList = getTweetListById(starTweetIdList, userStarTweetBo.getContext(), userStarTweetBo.getType());
        sortTweetList(tweetList, "时间");

        List<UserStarTweetVo> voList = tweetList.stream()
                .map(e->transTweetToStarTweetVo(e, userStarTweetBo.getUserId())).collect(Collectors.toList());

        return buildReturnMap(voList,userStarTweetBo.getPage(), 8, "tweets");
    }

    private Map<String, Object> buildReturnMap(List<?> list, Integer pageNum, Integer pageSize, String objectName) {
        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", list.size());
        int start = pageNum * pageSize;
        if (start >= list.size()) {
            map.put(objectName, null);
        } else {
            int end = Math.min(start + pageSize, list.size());
            map.put(objectName, list.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> selfTweet(UserSelfTweetBo userSelfTweetBo) {
        int pageSize = 8;

        UserInfo userInfo = userService.getUserInfoById(userSelfTweetBo.getUserId());

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andUserIdEqualTo(userSelfTweetBo.getUserId())
                .andIsHelpEqualTo(false)
                .andTitleLike("%" + userSelfTweetBo.getContext() + "%");

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);

        if(userSelfTweetBo.getTag() != null && !userSelfTweetBo.getTag().equals("")) {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andContentEqualTo(userSelfTweetBo.getTag());
            Tag tag = tagMapper.selectOneByExample(tagExample);
            if(tag == null) {
                tweetList = new ArrayList<>();
            } else {
                List<Integer> tweetIdList = new ArrayList<>();
                List<Tweet> tweetList2 = new ArrayList<>();
                TweetTagExample tweetTagExample = new TweetTagExample();
                tweetTagExample.createCriteria().andTagIdEqualTo(tag.getId());
                List<TweetTagKey> tweetTagKeyList = tweetTagMapper.selectByExample(tweetTagExample);
                for(TweetTagKey tweetTagKey:tweetTagKeyList) {
                    tweetIdList.add(tweetTagKey.getTweetId());
                }
                for(Tweet tweet:tweetList) {
                    if(tweetIdList.contains(tweet.getId())) {
                        tweetList2.add(tweet);
                    }
                }
                tweetList = tweetList2;
            }
        }

        tweetList.sort(Comparator.comparing(Tweet::getTime));

        List<UserSelfTweetVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserSelfTweetVo vo = new UserSelfTweetVo();
                    BeanUtils.copyProperties(e, vo);
                    vo.setIsLike(isUserLikeTweet(userInfo.getId(), e.getId()));
                    vo.setAvatar(userInfo.getAvatar());
                    vo.setUsername(userInfo.getUsername());
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = userSelfTweetBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> selfHelp(UserSelfHelpBo userSelfHelpBo) {
        int pageSize = userSelfHelpBo.getPageNum();

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userSelfHelpBo.getUserId());
        if (sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andUserIdEqualTo(userSelfHelpBo.getUserId())
                .andIsHelpEqualTo(true)
                .andTitleLike("%" + userSelfHelpBo.getContext() + "%");

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);

        if(userSelfHelpBo.getTag() != null && !userSelfHelpBo.getTag().equals("")) {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andContentEqualTo(userSelfHelpBo.getTag());
            Tag tag = tagMapper.selectOneByExample(tagExample);
            if(tag == null) {
                tweetList = new ArrayList<>();
            } else {
                List<Integer> tweetIdList = new ArrayList<>();
                List<Tweet> tweetList2 = new ArrayList<>();
                TweetTagExample tweetTagExample = new TweetTagExample();
                tweetTagExample.createCriteria().andTagIdEqualTo(tag.getId());
                List<TweetTagKey> tweetTagKeyList = tweetTagMapper.selectByExample(tweetTagExample);
                for(TweetTagKey tweetTagKey:tweetTagKeyList) {
                    tweetIdList.add(tweetTagKey.getTweetId());
                }
                for(Tweet tweet:tweetList) {
                    if(tweetIdList.contains(tweet.getId())) {
                        tweetList2.add(tweet);
                    }
                }
                tweetList = tweetList2;
            }
        }

        tweetList.sort(Comparator.comparing(Tweet::getTime));

        List<UserSelfHelpVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserSelfHelpVo vo = new UserSelfHelpVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = userSelfHelpBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> helpGet(UserHelpGetBo userHelpGetBo) {
        int pageSize = userHelpGetBo.getPageNum();

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andIsHelpEqualTo(true)
                .andCensoredEqualTo(CensorStatusEnum.PASS.getCode())
                .andTitleLike("%" + userHelpGetBo.getContext() + "%");

        searchLogService.insertSearchLog(userHelpGetBo.getContext(), true);

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);
        if(userHelpGetBo.getTag() != null && !userHelpGetBo.getTag().equals("")) {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andContentEqualTo(userHelpGetBo.getTag());
            Tag tag = tagMapper.selectOneByExample(tagExample);
            if(tag == null) {
                tweetList = new ArrayList<>();
            } else {
                List<Integer> tweetIdList = new ArrayList<>();
                List<Tweet> tweetList2 = new ArrayList<>();
                TweetTagExample tweetTagExample = new TweetTagExample();
                tweetTagExample.createCriteria().andTagIdEqualTo(tag.getId());
                List<TweetTagKey> tweetTagKeyList = tweetTagMapper.selectByExample(tweetTagExample);
                for(TweetTagKey tweetTagKey:tweetTagKeyList) {
                    tweetIdList.add(tweetTagKey.getTweetId());
                }
                for(Tweet tweet:tweetList) {
                    if(tweetIdList.contains(tweet.getId())) {
                        tweetList2.add(tweet);
                    }
                }
                tweetList = tweetList2;
            }
        }
        sortTweetList(tweetList, userHelpGetBo.getType());

        List<UserHelpGetVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserHelpGetVo vo = new UserHelpGetVo();
                    BeanUtils.copyProperties(e, vo);
                    UserInfo userInfo = userInfoMapper.selectByPrimaryKey(e.getUserId());
                    vo.setUsername(userInfo.getUsername());
                    vo.setAvatar(userInfo.getAvatar());
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = userHelpGetBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    private void sortTweetList(List<Tweet> tweetList, String sortedBy) {
        if (sortedBy.equals("时间")) {
            tweetList.sort((o1,o2)-> o2.getTime().compareTo(o1.getTime()));
        } else if (sortedBy.equals("热度")) {
            tweetList.sort((o1,o2)->(getTweetHot(o2)-getTweetHot(o1)));
        }
    }

    public int getTweetHot(Tweet e) {
        int a = commentService.getCommentVoListByTweetId(e.getId()).size();
        return e.getLikes() + e.getStars() + commentService.getCommentVoListByTweetId(e.getId()).size();
    }

    public void tweetCreate(TweetCreateBo tweetCreateBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetCreateBo.getUserId());
        if (sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        List<String> imageUrlList = tweetCreateBo.getImages();
        Integer listLength = tweetCreateBo.getImages().size();

        Tweet tweet = Tweet.builder()
                .userId(tweetCreateBo.getUserId())
                .title(tweetCreateBo.getTitle())
                .content(tweetCreateBo.getContent())
                .published(true)
                .build();
        tweetMapper.insertSelective(tweet);

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria().andUserIdEqualTo(tweetCreateBo.getUserId());
        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);
        tweetList.sort(Comparator.comparing(Tweet::getTime));

        tweet = tweetList.get(tweetList.size() - 1);

        List<String> tagList = tweetCreateBo.getTags();
        for(String tagString:tagList) {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andContentEqualTo(tagString);
            Tag tag = tagMapper.selectOneByExample(tagExample);
            if(tag == null) {
                Tag tag2Insert = Tag.builder()
                    .content(tagString)
                    .build();
                tagMapper.insertSelective(tag2Insert);
                tagExample = new TagExample();
                tagExample.createCriteria().andContentEqualTo(tagString);
                tag = tagMapper.selectOneByExample(tagExample);
            }
            TweetTagKey tweetTagKey2Insert = TweetTagKey.builder()
                .tweetId(tweet.getId())
                .tagId(tag.getId())
                .build();
            tweetTagMapper.insert(tweetTagKey2Insert);
        }

        Integer id = tweet.getId();

        if (!imageUrlList.isEmpty()) {
            String images = "";
            for (int i = 0; i < listLength - 1; i++) {
                tweetCreateSaveImage(imageUrlList.get(i), tweet, i);
                String newAvatarFront = PICTURE_SAVE_PATH_FRONT + id + "_" + i + ".png";
                images += newAvatarFront;
                images += ";";
            }
            tweetCreateSaveImage(imageUrlList.get(listLength - 1), tweet, listLength - 1);
            images += PICTURE_SAVE_PATH_FRONT + id + "_" + (listLength - 1) + ".png";
            tweet.setImages(images);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    private void tweetCreateSaveImage(String url, Tweet tweet, int num) {
        String imagePath = PICTURE_SAVE_PATH + tweet.getId() + "_" + num + ".png";
        try {
            Files.move(Paths.get(url), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteTweet(Integer userId, Integer tweetId) {
        userService.getUserInfoById(userId);
        Tweet tweet = getTweetById(tweetId);

        if(!Objects.equals(tweet.getUserId(), userId)) {
            throw new RuntimeException("不能删除他人帖子");
        }

        tweet.setDeleted(true);
        tweetMapper.updateByPrimaryKeySelective(tweet);
    }
}
