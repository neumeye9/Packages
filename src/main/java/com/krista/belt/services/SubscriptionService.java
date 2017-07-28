package com.krista.belt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krista.belt.models.Subscription;
import com.krista.belt.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {
	
private SubscriptionRepository subscriptionRepository;
	
	public SubscriptionService(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}
	
	public void createSubscription(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}
	
	public List<Subscription> allSubscriptions(){
		return subscriptionRepository.findAll();
	}
	
	public Subscription findByName(String name) {
		return subscriptionRepository.findByName(name);
	}
	
	public Subscription getById(Long id) {
		return subscriptionRepository.findOne(id);
	}
	
	public void update(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}
	
	public void destroy(Long id) {
		subscriptionRepository.delete(id);
	}

}
