package com.sparta.spring_projectclone.service;

import com.sparta.spring_projectclone.dto.requestDto.PostRequestDto;
import com.sparta.spring_projectclone.dto.responseDto.CommentResponseDto;
import com.sparta.spring_projectclone.dto.responseDto.PostResponseDto;
import com.sparta.spring_projectclone.model.Comment;
import com.sparta.spring_projectclone.model.Post;
import com.sparta.spring_projectclone.repository.CommentRepository;
import com.sparta.spring_projectclone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //전체 포스트
    public List<PostResponseDto> getPost() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .content(post.getContent())
                    .avgReviewPoint(post.getAvgReviewPoint())
                    .totalComment(post.getTotalComment())
                    .category(post.getCategory())
                    .loveCount(post.getLoveCount())
                    .price(post.getPrice())
                    .build();
            postList.add(postResponseDto);
        }
        return postList;
    }

    //포스트 상세 페이지
    public PostResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .userId(comment.getPost().getUser().getId())
                    .userImgUrl(comment.getPost().getUser().getUserImgUrl())
                    .comment(comment.getComment())
                    .reviewPoint(comment.getReviewPoint())
                    .build();
            commentList.add(commentResponseDto);
        }

        return PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .imgUrl(post.getImgUrl())
                .content(post.getContent())
                .avgReviewPoint(post.getAvgReviewPoint())
                .totalComment(post.getTotalComment())
                .category(post.getCategory())
                .loveCount(post.getLoveCount())
                .price(post.getPrice())
                .comments(commentList)
                .build();
    }

    //게시글 작성
    public void savePost(PostRequestDto requestDto) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .imgUrl(requestDto.getImgUrl())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .price(requestDto.getPrice())
                .build();
        postRepository.save(post);
    }

    //게시글 수정
    public void updatePost(Long postId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Long userId = post.getUser().getId();
        post.update(requestDto);
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        postRepository.deleteById(postId);
    }
}
