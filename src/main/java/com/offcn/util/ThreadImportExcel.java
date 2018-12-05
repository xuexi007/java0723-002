package com.offcn.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.offcn.dao.MobileDao;
import com.offcn.po.Mobile;

public class ThreadImportExcel extends Thread{
	private Sheet sheet;
	private MobileDao mdao;
	public ThreadImportExcel(Sheet sheet,MobileDao mdao){
		this.sheet=sheet;
		this.mdao=mdao;
	}

	@Override
	public void run(){
		List<Mobile> list=new ArrayList<Mobile>();
		int numrow = sheet.getPhysicalNumberOfRows();
		for(int i=1;i<numrow;i++){
			Row row = sheet.getRow(i);
			String number=row.getCell(1).getStringCellValue();
			String area=row.getCell(2).getStringCellValue();
			String mobiletype=row.getCell(3).getStringCellValue();
			String areacode=row.getCell(4).getStringCellValue();
			String postcode=row.getCell(5).getStringCellValue();
			
			Mobile m = new Mobile();
			m.setNumber(number);
			m.setArea(area);
			m.setAreacode(areacode);
			m.setMobiletype(mobiletype);
			m.setPostcode(postcode);
			
			list.add(m);
			
			//判断list集合的数量，等于1000条保存一次数据
			if(list.size()==1000){
				mdao.saves(list);
				//清空list
				list.clear();
			}
		}
		
		//判断list是够存在余数
		if(list.size()>0){
			
			mdao.saves(list);
			list.clear();
		}
	}
}
