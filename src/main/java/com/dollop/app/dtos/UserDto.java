package com.dollop.app.dtos;

import java.util.List;

import com.dollop.app.validate.ImageNameValid;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

	private String userId;

	@NotNull
	@NotBlank(message = "user first name required")
	@Size(min = 3, max = 30, message = "invalid firstName!!")
	private String name;

	@Column(unique = true, length = 25)
	@NotBlank(message = "Email is required")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
	private String email;

	@NotBlank(message = "password required")
	@Column(unique = true)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and minimum length of 8 characters")
	private String password;

	@Size(min = 4, max = 6, message = "Invalid Gender !!")
	private String gender;

	@NotBlank(message = "Write something about you!!")
	private String about;

	@ImageNameValid(message = "invaliddd....image")
	private String imageName;

	private List<String> multipleImages;
}
