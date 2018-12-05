package com.offcn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.dao.MobileDao;
import com.offcn.po.Mobile;

public class TestDao {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");

		/*StuDao dao = context.getBean(StuDao.class);
		
		Stu s = new Stu();
		s.setName("猛哥");
		s.setPhone("188888888888");
		s.setScore(99.89F);
		
		dao.save(s);
		
		List<Stu> list = dao.getAllStu();
		for (Stu stu : list) {
			System.out.println(stu);
		}*/
		
		MobileDao mdao = context.getBean(MobileDao.class);
		
		Mobile m = mdao.search("1300000");
		
		System.out.println(m);
		/*List<Mobile> list=new ArrayList<Mobile>();
		
		Mobile m1 = new Mobile();
		m1.setNumber("1300000");
		m1.setMobiletype("中国联通GSM");
		m1.setArea("山东济南");
		m1.setAreacode("0531");
		m1.setPostcode("500091");
		
		Mobile m2 = new Mobile();
		m2.setNumber("1800000");
		m2.setMobiletype("中国移动GSM");
		m2.setArea("山东日照");
		m2.setAreacode("0538");
		m2.setPostcode("580091");
		
		list.add(m1);
		list.add(m2);
		
		mdao.saves(list);*/
		
		
	}

}
