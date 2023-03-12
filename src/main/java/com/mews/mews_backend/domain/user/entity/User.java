package com.mews.mews_backend.domain.user.entity;

import com.mews.mews_backend.api.user.dto.GoogleUser;
import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "imgurl", columnDefinition = "TEXT", nullable = false)
    private String imgUrl;

    @Column(name = "introduction",columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "bookmark_count")
    private int bookmarkCount;

    @Column(name = "subscribe_count")
    private int subscribeCount;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "is_open")
    private boolean isOpen; //true:공개, false:비공개

    @Column(name = "social")
    private String social; //pwd로 사용할 google 값

    @Column(name = "status")
    private String status; //ACTIVE, DELETED(탈퇴회원)

    public void updateStatus(){
        this.status = "DELETED";
    }
    //프로필 편집 부분
    public void changeName(String name) {this.userName = name;}

    public void changeImg(String img) {this.imgUrl = img;}

    public void changeIntroduction(String introduction) {this.introduction= introduction;}

    public void changeIsOpen(boolean open) {this.isOpen = open;}

    //북마크 추가 및 취소
    public void upBookmark(){this.bookmarkCount++;}

    public void downBookmark(){this.bookmarkCount--;}

    //좋아요 추가 및 취소
    public void upLike(){this.likeCount++;}
    public void downLike(){this.likeCount--;}

    public static User createUser (UserDto.register register, PasswordEncoder passwordEncoder) {

        return User.builder()
                .userName(register.getUserName())
                .userEmail(register.getUserEmail())
                .imgUrl(register.getImgUrl())
                .introduction("")
                .likeCount(0)
                .bookmarkCount(0)
                .subscribeCount(0)
                .userType(UserType.ROLE_USER)
                .isOpen(true)
                .social(passwordEncoder.encode("google"))
                .status("ACTIVE")
                .build();
    }
    public User(String userEmail, String userName, Set<GrantedAuthority> singleton) {
    }
}

