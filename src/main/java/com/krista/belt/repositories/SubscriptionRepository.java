package com.krista.belt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.krista.belt.models.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
	List<Subscription> findAll();
	Subscription findByName(String name);
}
