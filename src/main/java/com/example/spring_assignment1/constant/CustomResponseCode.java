package com.example.spring_assignment1.constant;

import org.springframework.http.HttpStatus;

public enum CustomResponseCode {
    SUCCESS(HttpStatus.OK, "요청 정상 처리"),
    CREATED(HttpStatus.CREATED, "리소스 생성 완료"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 정상 처리, 콘텐츠 없음"),
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "권한 없음"),

    // 회원 관련 에러
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복 이메일."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복 닉네임."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),

    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),

    // 댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    // 공통
    PROFILE_IMAGE_MISSING(HttpStatus.BAD_REQUEST, "이미지 등록이 필요합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    CustomResponseCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getMessage() { return message; }

}
