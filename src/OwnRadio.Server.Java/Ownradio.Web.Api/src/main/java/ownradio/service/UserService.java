package ownradio.service;

import ownradio.domain.User;
import ownradio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User getById(String id) {
		return userRepository.findOne(id);
	}

	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}
}
