package com.example.demo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import utility.database.Mysql;
import utility.mqtt.ClientMQTT;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
class Greeting {

	private final long id;
	private final String content;
	private final String content2;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
		this.content2 = "hello world";
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	public String getContent2(){
		return content2;
	}

}
class Good {
	private Integer id;
	private String name;
	@JsonIgnore
	private Double price;
	@JsonFormat(pattern = "yy-MM-dd")
	private Date dealDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
}
class readproperties{
	//方法二
	public void readProperty2() {
		Properties properties = new Properties();
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("file/code.properties");
			//遍历取值
			Set<Object> objects = properties.keySet();
			for (Object object : objects) {
				System.out.println(new String(properties.getProperty((String) object).getBytes("iso-8859-1"), "gbk"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readProperty(){
		Properties properties = new Properties();
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("file/code.properties");
			System.out.println(properties.getProperty("warshipType.1"));
			System.out.println(new String(properties.getProperty(("warshipType.1") ).getBytes(StandardCharsets.UTF_8)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
@Component
@PropertySource({"classpath:file/code.properties"})
class getmessage{
	@Value("${wx.id}")
	public  String wxid;
	public String get(){
		this.wxid=wxid;
		return this.wxid;
	}
}
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController

public class DemoApplication {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		ClientMQTT client = new ClientMQTT();
		client.start();
		readproperties read =new readproperties();
		read.readProperty();
		Mysql db=new Mysql();
		db.text2();
		getmessage g =new getmessage();
        System.out.println(g.wxid);
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping(value = {"/"})
	public String index(Model model){
		return "<h1>hello world</h1>";
	}
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	@GetMapping(path = "/GET")
	@ResponseBody
	public Good getGood(){
		Good good = new Good();
		good.setId(1);
		good.setName("MacBook Pro 2019");
		good.setPrice(16999.99);
		good.setDealDate(new Date());
		return good;
	}
}

