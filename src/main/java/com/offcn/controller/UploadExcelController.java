package com.offcn.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.offcn.po.Stu;
import com.offcn.service.StuService;

@Controller
public class UploadExcelController {

	@Autowired
	StuService service;
	@RequestMapping("/testupload")
	public String uploadExcel(@RequestParam("file1")MultipartFile file,HttpServletRequest request,Model model){
		//1、获取服务器路径
		String path=request.getServletContext().getRealPath("upload");
		
		//2、创建File对象，指向上传目录
		File f1 = new File(path);
		//判断上传目录是否存在
		if(!f1.exists()){
			//创建上传目录
			f1.mkdir();
		}
		
		//3、获取上传文件名称
		String filename = file.getOriginalFilename();
		//4、保存文件的对象
		File f2 = new File(path+"\\"+filename);
		//5、把上传文件保存到指定位置
		try {
			file.transferTo(f2);
			readExcel(f2);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "upload-ok";
	}
	
	
	public void readExcel(File f1){
		Workbook workbook=null;
		try {
			workbook = WorkbookFactory.create(f1);
			
			Sheet sheet = workbook.getSheet("成绩");
			int numrow = sheet.getPhysicalNumberOfRows();
			for(int i=1;i<numrow;i++){
				Row row = sheet.getRow(i);
				String name=row.getCell(1).getStringCellValue();
				float score=(float) row.getCell(2).getNumericCellValue();
				long phone=(long) row.getCell(3).getNumericCellValue();
				
				Stu stu = new Stu();
				stu.setName(name);
				stu.setPhone(phone+"");
				stu.setScore(score);
				System.out.println(stu);
				service.save(stu);
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
