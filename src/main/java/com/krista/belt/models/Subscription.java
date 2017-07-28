package com.krista.belt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "subscriptions")
public class Subscription {

		@Id
		@GeneratedValue
		private Long id;
		
		@Column
		@Size(min = 2, max = 45)
		private String name;
		
		@Column
		private float cost;
		
		@Column
		private boolean isAvailable;
		
		@Column
		@ColumnDefault("0")
		private int count;
	
		
		@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
		@JoinTable(
				name = "subscriptions_users",
				joinColumns = @JoinColumn(name = "subscription_id"),
				inverseJoinColumns = @JoinColumn(name = "user_id")
				)
		
		private List<User> users;
		
		
		public Subscription() {
			
		}
		
		public Subscription(String name, float cost, boolean isAvailable, int count) {
			this.name = name;
			this.cost = cost;
			this.isAvailable = true;
			this.count = count;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getCost() {
			return cost;
		}

		public void setCost(float cost) {
			this.cost = cost;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public boolean isAvailable() {
			return isAvailable;
		}

		public void setAvailable(boolean isAvailable) {
			this.isAvailable = isAvailable;
		}

	
	
}
