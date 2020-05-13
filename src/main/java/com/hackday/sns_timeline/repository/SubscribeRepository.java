package com.hackday.sns_timeline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.sns_timeline.domain.entity.Subscribe;
import com.hackday.sns_timeline.domain.entity.SubscribePK;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, SubscribePK> {
}
