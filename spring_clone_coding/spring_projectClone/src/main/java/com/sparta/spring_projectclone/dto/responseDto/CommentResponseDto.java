package com.sparta.spring_projectclone.dto.responseDto;


import com.sparta.spring_projectclone.model.Comment;
import lombok.Builder;

public class CommentResponseDto {
    private Long id;
    private String comment;
    private int reviewPoint;
    private String image;

    @Builder
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.reviewPoint = comment.getReviewPoint();
        this.image = comment.getImage();

    }
}
