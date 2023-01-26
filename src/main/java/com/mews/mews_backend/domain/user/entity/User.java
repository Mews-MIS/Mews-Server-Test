package com.mews.mews_backend.domain.user.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private int id;

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
    public User(String userEmail, String userName, Set<GrantedAuthority> singleton) {
    }
}

