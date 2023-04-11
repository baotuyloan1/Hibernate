package edu.fa;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConnectionUtil {
	private static SessionFactory sessionFactory = null;

	public static SessionFactory getSessionFactory() {
//		Chỉ có 1 sessionFactory được tạo ra duy nhất 
		/*-	Singleton*/
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			
//			hàm này là load file hibernate.cfg.xml vào
//			hàm này mặc định không tham số sẽ gọi đến file hibernate.cfg.xml, khi có tham số thì nó gọi tới file config đó
			configuration.configure("hibernates.cfg.xml");
			ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
					.build();
			sessionFactory = configuration.buildSessionFactory(registry);
		}
		return sessionFactory;

	}
}
