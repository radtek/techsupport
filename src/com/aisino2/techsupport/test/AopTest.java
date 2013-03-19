package com.aisino2.techsupport.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;

public class AopTest {
	private ApplicationContext context;
	
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext(new String[] {
				"/config/spring/applicationContext.xml",
				"/config/spring/techsupport-common.xml",
				"/config/spring/sysadmin-*.xml"});
	}
	
	@Test
	public void testAop(){
		BindArg arg = (BindArg) context.getBean("bindArg");
		SupportTicket st = new SupportTicket();
		arg.invoke(st);
		BindArg2 arg2 = (BindArg2) context.getBean("bindArg2");
		arg2.invoke("1", st);
		BindArg3 arg3 = (BindArg3) context.getBean("bindArg3");
		arg3.invoke("2", st, null);
	}
	
	@Test
	public void testSet() {
		Set<String> to = new HashSet<String>();
		to.add("usa");
		to.add("chn");
		to.add("jpn");
		System.out.println(to.toString());
		
	}
}

class BindArg{
	public void invoke(SupportTicket st){
		st.setId(1);
		throw new RuntimeException("test exception");
	}
}

class BindArg2{
	public void invoke(String taskId,SupportTicket st){
		st.setId(2);
	}
}

class BindArg3{
	public void invoke(String taskId,SupportTicket st,Tracking track){
		st.setId(3);
	}
}

class BindTest{
	public void testBind(SupportTicket st){
		System.out.println("testBind 的 st.getId() == "+st.getId());
	}
	public void testBind2(String taskId,SupportTicket st){
		System.out.println("testBind 的 st.getId() == "+st.getId());
		System.out.println("testBind 的 taskId == "+taskId);
	}
	public void testBind3(String taskId,SupportTicket st,Tracking track){
		System.out.println("testBind 的 st.getId() == "+st.getId());
		System.out.println("testBind 的 taskId == "+taskId);
	}
}