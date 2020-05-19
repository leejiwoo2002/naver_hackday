package com.hackday.sns_timeline.content.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	public String mkDir(String filePath, MultipartFile file){
		String saveName="";

		if(!file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			saveName = uuid.toString();

			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd");
			Date today = new Date();
			String folderName = dateFormat.format(today);

			String extension = "."+ FilenameUtils.getExtension(file.getOriginalFilename());

			saveName+=extension; // 확장자 추가

			File directory = new File(filePath+"/"+folderName);

			if (!directory.exists()) {
				try{
					directory.mkdir(); //폴더 생성합니다.
				}
				catch(Exception e){
					e.getStackTrace();
				}
			}

			File saveFile = new File(filePath+"/"+folderName, saveName); // 저장할 폴더 이름, 저장할 파일 이름
			try {
				file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return saveName;

	}

}
