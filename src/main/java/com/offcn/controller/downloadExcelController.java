package com.offcn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.offcn.po.Stu;
import com.offcn.service.StuService;

@Controller
public class downloadExcelController {
	@Autowired
	StuService service;

	@RequestMapping("/download")
	public void downloadExcel(HttpServletRequest request,HttpServletResponse response, Model model){
		//1、获取服务器全部的学生信息
		List<Stu> list = service.getAllStu();
		//2、确定excel的名字
		String fileName="down.xlsx";
		String path=request.getServletContext().getRealPath("down");
		File f1 = new File(path);
		if(!f1.exists()){
			f1.mkdir();
		}
		
		File f2 = new File(path+"\\"+fileName);
		
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("学生成绩表");
	int rownum=0;
	
	for(Stu stu:list){
		XSSFRow row = sheet.createRow(rownum);
		row.createCell(0).setCellValue(stu.getId());
		row.createCell(1).setCellValue(stu.getName());
		row.createCell(2).setCellValue(stu.getScore());
		row.createCell(3).setCellValue(stu.getPhone());
		
		rownum++;
	}
	
	//保存工作簿到磁盘
	try {
		workbook.write(new FileOutputStream(f2));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	//设置响应头(通知浏览器，服务器要返回的文件格式)
	response.setContentType("application/x-xls;charset=GBK");
	//设置浏览器下载提示
	try {
		response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
	
		//设置下载文件长度
		response.setContentLength((int)f2.length());
		ServletOutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(f2);
		
		int len=-1;
		byte buf[]=new byte[4096];
		
		while((len=in.read(buf))!=-1){
			out.write(buf, 0, len);
		}
		
		out.close();
		in.close();
	
	} catch ( IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
