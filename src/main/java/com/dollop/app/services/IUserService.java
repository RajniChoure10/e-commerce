package com.dollop.app.services;

import java.io.IOException;
import java.util.List;

import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;

/***
 * @author Rajni
 * 
 */
public interface IUserService 
{
/***
 * 
 * @param userdto
 * @return
 */
	//create...
	  public UserDto createUser(UserDto userdto);
	 
	 //update
	  UserDto updateUser(UserDto userdto,String userId);
	  
	 //delete
	  void deleteUser(String userId) throws IOException;
	  
	 //get all users
	 //List<UserDto> getAllUsers();
	  
	 //get one user
	  UserDto getOneUser(String userId);
	  
	 //search user
	  List<UserDto> searchUser(String keyword);
      
	  //pagination and sort to all users
	  public PageableResponse<UserDto> getAllUsers(
			  int pageNumber, int pageSize, String sortBy, String sortDir);
	  
	  
     	

	
	 
	
}
