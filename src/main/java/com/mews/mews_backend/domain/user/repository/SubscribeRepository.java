package com.mews.mews_backend.domain.user.repository;

import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.user.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribeRepository  extends JpaRepository<Subscribe, Integer> {

}
