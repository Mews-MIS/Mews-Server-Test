package com.mews.mews_backend.domain.user.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //아무런 값 없을 때 객체 생성 막음
@Entity
@Table(name = "subscribe")
public class Subscribe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscribe_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="editor_id")
    private Editor editor;

}
