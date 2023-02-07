package com.mews.mews_backend.domain.article.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import com.mews.mews_backend.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //아무런 값 없을 때 객체 생성 막음
@Entity
@Table(name = "like")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Integer id; // 좋아요 id

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="article_id")
    private Article article;

}
