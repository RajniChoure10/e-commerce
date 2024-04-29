package com.dollop.app.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.entities.User;
import com.dollop.app.exception.DuplicateEntryException;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.helper.MyHelper;
import com.dollop.app.repositories.UserRepository;
import com.dollop.app.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${user.profile.image.path}")
	private String imagePath;

//	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto createUser(UserDto userdto) {

		String userId = UUID.randomUUID().toString();
		userdto.setUserId(userId);

		if(userRepository.existsByName(userdto.getName()))
		{
			throw new DuplicateEntryException("user name ->"+userdto.getName()+ "already exist");
		}
		if(userRepository.existsByEmail(userdto.getEmail()))
		{
			throw new DuplicateEntryException("user email->"+userdto.getEmail()+"already exists");
		}
		if(userRepository.existsByPassword(userdto.getPassword()))
		{
			throw new DuplicateEntryException("user Password->"+userdto.getPassword()+"already exists");
		}
		// UserDto to user entity
		User user = dtoToEntity(userdto);
		User saveduser = userRepository.save(user);

		// user entity to dto
		UserDto newDto = entityToDto(saveduser);
		return newDto;
	}

	private UserDto entityToDto(User savedUser) {
		return modelMapper.map(savedUser, UserDto.class);}

	private User dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);}

	public UserDto updateUser(UserDto userDto, String userId) {
		/*
		 * userdto.setUserId(userId);
           if(userdto.getUserId()==null || !userRepository.existsById(userdto.getUserId())) 
           { 
            String message = "User not found"; 
           } 
           else 
           { 
              User user=dtoToEntity(userdto);
              User saveduser = userRepository.save(user);
		      UserDto newDto=entityToDto(saveduser); return newDto; 
		    }
		 *  return userdto;
		 */
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User  not found for given user id"));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		user.setMultipleImages(userDto.getMultipleImages());
		User updatedUser = userRepository.save(user);
		UserDto updatedDto = modelMapper.map(updatedUser, UserDto.class);
		return updatedDto;
	}

	public void deleteUser(String userId) throws IOException {
		/*
		 * UserDto userDto = getOneUser(userId); 
		 * User u = modelMapper.map(userDto,User.class); userRepository.delete(u);
		 */
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User  not found for given user id"));
		String fullPath = imagePath + user.getImageName();

		Path path = Paths.get(fullPath);
		Files.delete(path);
		userRepository.delete(user);

	}

	/*
	 * public List<UserDto> getAllUsers()
	 *  { List<User>
	 * users=userRepository.findAll(); 
	 * List<UserDto> userDto=users.stream()
	 * .map((user)->modelMapper.map(user,UserDto.class))
	 * .collect(Collectors.toList()); return userDto; }
	 */

	public UserDto getOneUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User  not found for given user id"));
		return modelMapper.map(user, UserDto.class);
	}

	public List<UserDto> searchUser(String keyword) {
		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> userDto = users.stream().map((user) -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDto;

	}

	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? 
				(Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		// System.out.println(sort);

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> page = userRepository.findAll(pageable);
		PageableResponse<UserDto> response = MyHelper.getPageableResponse(page, UserDto.class);
		return response;
	}

}
