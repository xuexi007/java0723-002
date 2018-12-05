package com.offcn.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offcn.dao.MobileDao;
import com.offcn.po.Mobile;
import com.offcn.service.MobileService;
@Service("mobileServiceImpl")
public class MobileServiceImpl implements MobileService {

	@Autowired
	MobileDao dao;
	@Override
	public Mobile search(String number) {
		if(number.length()>7){
			
			number=number.substring(0,7);
		}
		return dao.search(number);
	}

}
