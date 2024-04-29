package com.dollop.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.dtos.ApiResponseMessage;
import com.dollop.app.dtos.ImageResponse;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.services.IFileService;
import com.dollop.app.services.IUserService;
import com.dollop.app.services.impl.FileServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private IFileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	
	
	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	//create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Validated @RequestBody UserDto userdto)
	{
		return new ResponseEntity<>(service.createUser(userdto),HttpStatus.CREATED);
		
	}
	
	/*
	 * @PutMapping("{id}") 
	 * public ResponseEntity<UserDto> editUser(@RequestBody
	 *   UserDto userdto,@PathVariable ("id") String UserId)
	 *  {
	 *   return new ResponseEntity<>(service.updateUser(userdto, UserId),HttpStatus.OK);
	 * 
	 * }
	 * 
	 * 
	 * @GetMapping("{id}") public ResponseEntity<UserDto> getUser(@PathVariable
	 * ("id") String userId) { UserDto u = service.getOneUser(userId);
	 * ResponseEntity<UserDto> ud = new ResponseEntity<UserDto>(u, HttpStatus.OK);
	 * return ud; }
	 */
	
	/*
	 * @DeleteMapping("{id}") public ResponseEntity<UserDto> removeUser(@RequestBody
	 * UserDto userDto,@PathVariable ("id") String userId)
	 * 
	 * { ; service.deleteUser(userId); String msg = "deleted successfully";
	 * System.out.println(msg); return new ResponseEntity<UserDto>(HttpStatus.OK) ;
	 * 
	 * }
	 */
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(
			@Validated
			@PathVariable ("userId") String userId,
			@RequestBody UserDto userDto)
	{ 
		UserDto updateUserDto = service.updateUser(userDto,userId);
	    return ResponseEntity.ok(updateUserDto);
		
  	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(
			@PathVariable String userId) throws IOException
	{
		service.deleteUser(userId);
		ApiResponseMessage response =ApiResponseMessage.builder()
				.message("user is deleted")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	/*
	 * @GetMapping public ResponseEntity<List<UserDto>> getAllUsers() { return
	 * ResponseEntity.ok(service.getAllUsers()); }
	 * 
	 */
	
	@GetMapping("/allusers")
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam (value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam (value = "pageSize",defaultValue ="2",required = false)int pageSize,
			@RequestParam (value = "sortBy",defaultValue ="name",required = false)String sortBy,
			@RequestParam (value = "sortDir",defaultValue ="asc",required = false)String sortDir
			)
	{
		return new  ResponseEntity<PageableResponse<UserDto>>
		(service.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(
			@PathVariable String userId)
	{
		return ResponseEntity.ok(service.getOneUser(userId));
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUser(
			@PathVariable String keyword)
	{
		return ResponseEntity.ok(service.searchUser(keyword));
	}
	
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(
			@RequestParam("imageName") MultipartFile image,
			@PathVariable String userId) throws IOException
	{
		String imageName = fileService.uploadFile(image,imageUploadPath);
		UserDto user = service.getOneUser(userId);
		user.setImageName(imageName);
		UserDto userDto = service.updateUser(user, userId);
		ImageResponse imageResponse = ImageResponse.builder()
				.imageName(imageName).message("image is uploaded")
				.success(true)
				.status(HttpStatus.CREATED).build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	//output stream se read n get krlenege
	@GetMapping("/image/{userId}")
	public void serverUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException
	{
		UserDto user = service.getOneUser(userId);
		InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
		
	}
	
	@PostMapping("/imagessss/{userId}")
	public ResponseEntity<ImageResponse> uploadMultipleImage(
			@RequestParam("userImage") List<MultipartFile> images, 
			@PathVariable String userId) throws IOException {
	    List<String> imageNames = new ArrayList<>();

	    for (MultipartFile image : images) {
	        String uploadedFileNames = fileService.uploadFile(image, imageUploadPath);
	        imageNames.add(uploadedFileNames);
	        
	    }
	    System.err.println(imageNames);
	    UserDto user = service.getOneUser(userId);
	    user.setMultipleImages(imageNames) ;
	    UserDto userDto = service.updateUser(user, userId);

	    ImageResponse imageResponse = ImageResponse.builder()
	            .multipleImages(imageNames)
	            .message("Images are uploaded successfully")
	            .success(true)
	            .status(HttpStatus.CREATED)
	            .build();

	    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
	}
}
