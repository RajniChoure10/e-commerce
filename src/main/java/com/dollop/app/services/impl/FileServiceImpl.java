package com.dollop.app.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.dtos.BadApiRequestException;
import com.dollop.app.services.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	
	List<String> images= new ArrayList<>();
	
	private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFilename = file.getOriginalFilename();
		logger.info("file name:{}",originalFilename);
		String filename=UUID.randomUUID().toString();
		
		//dyanamic extension
		String  extension=originalFilename.substring(originalFilename.lastIndexOf("."));
		
		//create filename and extension = fullpath
		String fileNameWithExtension= filename+extension;
		String fullPathWithFileName=path+fileNameWithExtension;
		logger.info("full image path:{}",fullPathWithFileName);
		
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg"))
		{
			logger.info("which extension is coming:{}",fullPathWithFileName);
			File folder = new File(path);
			if(!folder.exists())
			{
				folder.mkdirs();//create folder
			}
			Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));
		}
		else
			throw new BadApiRequestException("file with this "+extension +"extension is not found");
		return fileNameWithExtension;
	}

	@Override
	public InputStream getResource( String path,String name) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String fullPath = path+name;
		System.out.println(fullPath);
		InputStream inputStream= new FileInputStream(fullPath);
		return inputStream;
	}

}
