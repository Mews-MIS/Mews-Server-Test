package com.mews.mews_backend.domain.user.repository;

import com.mews.mews_backend.domain.user.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

}
