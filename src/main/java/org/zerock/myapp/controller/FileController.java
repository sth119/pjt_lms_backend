package org.zerock.myapp.controller;

import java.util.Optional;

import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.myapp.entity.Course;
import org.zerock.myapp.entity.Upfile;
import org.zerock.myapp.persistence.UpFileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Controller
@RequestMapping(value="/files")
public class FileController {
	@Autowired UpFileRepository repo;
	
//	@GetMapping(value = "/instructor/{fileId}")
//	public ModelAndView instructorImg(@PathVariable Integer fileId) {
//		Optional<Upfile> optionalFile = repo.findByEnabledAndFileId(true, fileId);
//		if (optionalFile.isPresent()) {
//			Upfile file = optionalFile.get();
//			return file;
//		};
//		return setFileResource(file);
//	}
	

	
//	public ModelAndView setFileResource(Upfile file) {
//		if (new File(file.getPath()).exists()) { 
//			ModelAndView mv = new ModelAndView(fileResourceView);
//			mv.addObject("file", file);
//			return mv;
//		}
//		else {
//			throw new ResourceNotFoundException();
//		}
//	}
}