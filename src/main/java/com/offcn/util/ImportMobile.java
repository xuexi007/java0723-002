package com.offcn.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.dao.MobileDao;

public class ImportMobile {

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");

		MobileDao mdao = context.getBean(MobileDao.class);
		
		Workbook workbook = WorkbookFactory.create(new File("d:\\chart\\Mobile.xls"));
	
	for(int i=1;i<6;i++){
		Sheet sheet = workbook.getSheet("Sheet"+i);
		ThreadImportExcel t = new ThreadImportExcel(sheet,mdao);
		t.start();
	}
	}

}
