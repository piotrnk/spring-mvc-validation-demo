package com.github.kolorobot.domain;

import java.util.*;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean hasUser(String username) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByName", User.class).setParameter("name", username);
		List<User> result = query.setMaxResults(1).getResultList();
		return !result.isEmpty();
	}

	@Override
	public void save(User entity) {
		entityManager.persist(entity);
	}

	@Override
	public void remove(User entity) {
		entityManager.remove(entity);
	}

	@Override
	public User findById(long id) {
		User entity = entityManager.find(User.class, id);
		return entity;
	}

	@Override
	public Collection<User> findAll() {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findAll", User.class);
		List<User> result = query.getResultList();
		return Collections.unmodifiableCollection(result);
	}
}
