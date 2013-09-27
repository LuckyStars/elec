//package com.nbcedu.function.booksite;
//
//import static org.junit.Assert.*;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.nbcedu.function.booksite.biz.BookSiteBiz;
//import com.nbcedu.function.booksite.biz.SiteBiz;
//import com.nbcedu.function.booksite.model.BookSite;
//import com.nbcedu.function.booksite.model.Site;
//
//public class BookSiteTest {
//	private BookSiteBiz bookSiteBiz;
//	private SiteBiz siteBiz;
//	private Site site;
//	private ApplicationContext ac;
//	@Before
//	public void setUp() throws Exception {
//		ac = new ClassPathXmlApplicationContext(new String[]{"com/nbcedu/function/booksite/config/spring-conf/bizContext.xml",
//				"com/nbcedu/function/booksite/config/spring-conf/daoContext.xml","applicationContext.xml"});
//		bookSiteBiz = (BookSiteBiz) ac.getBean("bookSiteBiz");
//		siteBiz = (SiteBiz) ac.getBean("siteBiz");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testAddBookSite() throws ParseException {
//		
//		for (int i = 0; i < 3; i++) {
//			BookSite bookSite = new BookSite();
//			bookSite.setBookSite_beginTime(new Date());
//			bookSite.setBookSite_endTime(new Date());
//			bookSite.setSite(siteBiz.findSiteById("75942c2149e811e0a30502004c4f4f50"));
//			bookSite.setBookSite_explain("测试数据 " + i);
//			bookSiteBiz.addBookSite(bookSite);
//			System.out.println(bookSite.getSite().getSite_id());
//			assertNotNull(bookSite.getSite().getSite_id());
//		}
////		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
////		Date date = null;
////		date = df.parse(df.format(new Date()));
////		bookSite.setBookSite_beginTime(date);
////		bookSite.setBookSite_endTime(date);
////		bookSite.setBookSite_explain("发生的防守打法");
////		bookSite.setSite(siteBiz.findSiteById("75942c2149e811e0a30502004c4f4f50"));
////		System.out.println("添加开始");
////		bookSiteBiz.addBookSite(bookSite);
////		System.out.println("添加结束");
////		bookSite.getBookSite_beginTime();
////		bookSite.getBookSite_endTime();
////		bookSite.getSite().getSite_id();
//	}
//
//}
