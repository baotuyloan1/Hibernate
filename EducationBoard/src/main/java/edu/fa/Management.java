package edu.fa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import edu.fa.model.Address;
import edu.fa.model.Course;
import edu.fa.model.Fresher;
import edu.fa.model.Group;
import edu.fa.model.Syllabus;

public class Management {

	public static void main(String[] args) {
//		createCourseSyllabuses();
//		getCourseSyllabuses(1);
//		createFresherAndCourse();
//		createFresherAndAddress();
//		createFresherAndGroup();
		createGroup();
//		getGroup();
//		queryGroupUsingHQL();
//		updateGroupUsingHQL();
//		deleteGroupUsingHQL();
//		useCriteria();
//		useNamedQuery();
//		showFirstLevel();
		showSecondLevel();
		ConnectionUtil.getSessionFactory().close();

	}
	

	private static void showSecondLevel() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Group group1 = (Group) session.get(Group.class, 1);
			System.out.println(group1);
			session.getTransaction().commit();
			session.close();
			
			session = sessionFactory.openSession();
			session.beginTransaction();
			group1 = null;
			group1 = (Group) session.get(Group.class, 1);
			System.out.println(group1);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void showFirstLevel() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Group group1 = (Group) session.get(Group.class, 1);
			System.out.println(group1);
			group1 = null;
			group1 = (Group) session.get(Group.class, 1);
			System.out.println(group1);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void useNamedQuery() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query namedQuery = session.getNamedQuery(Constants.GROUP_BY_NAME);
			namedQuery.setParameter("name", "JavaScript Group");
			System.out.println(namedQuery.list());

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

//	Criteria dùng cho reading là chủ yếu
	private static void useCriteria() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria groupCriteria = session.createCriteria(Group.class);

//			Logical Conditions
			SimpleExpression eq = Restrictions.eq("id", 1);
			SimpleExpression like = Restrictions.like("name", "Java%");
			LogicalExpression le = Restrictions.or(eq, like);
			groupCriteria.add(le);
			System.out.println("Logical:" + groupCriteria.list());

//			Or conditions
			Criteria orCondition = groupCriteria
					.add(Restrictions.or(Restrictions.eq("id", 1), Restrictions.like("name", "Java%")));
			System.out.println("Or condition: " + orCondition.list());

//			Viết kiểu này sẽ là and giữa 2 điều kiện
			groupCriteria.add(Restrictions.eq("id", 1));
			groupCriteria.add(Restrictions.like("name", "Java%"));
			System.out.println(groupCriteria.list());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void deleteGroupUsingHQL() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String queryStr = "DELETE from Group WHERE id = :id";
			Query query = session.createQuery(queryStr);
//			vi tri 0, id 1
			query.setInteger("id", 1);
//			query.setString("name", "Funny Java Group");
			int resultNumber = query.executeUpdate();
			System.out.println(resultNumber);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void updateGroupUsingHQL() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String queryStr = "UPDATE Group set name = :name WHERE id = :id";
			Query query = session.createQuery(queryStr);
//			vi tri 0, id 1
			query.setInteger("id", 1);
			query.setString("name", "Funny Java Group");
			int resultNumber = query.executeUpdate();
			System.out.println(resultNumber);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void queryGroupUsingHQL() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String queryStr = "FROM Group WHERE id = ? and name like ?";
			Query query = session.createQuery(queryStr);
//			vi tri 0, id 1
			query.setInteger(0, 2);
			query.setString(1, "Java%");
			List<Group> groups = query.list();
			System.out.println(groups);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void queryGroupUsingHQLNamedQuery() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String queryStr = "SELECT name FROM Group WHERE name like :name and id = :id";
			Query query = session.createQuery(queryStr);
			query.setParameter("id", 2);
			query.setParameter("name", "Java%");
			List<Group> groups = query.list();
			System.out.println(groups);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void getGroup() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Group javaGroup = (Group) session.get(Group.class, 1);
			javaGroup.setName("New Java Group");
//			session.update(javaGroup);
			session.delete(javaGroup);
			session.getTransaction().commit();
			System.out.println(javaGroup);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void createGroup() {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Group javaGroup = new Group("Java Group");
			Group jsGroup = new Group("JavaScript Group");
			session.save(jsGroup);
			session.save(javaGroup);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void createFresherAndGroup() {
		Fresher fresher1 = new Fresher();
		Fresher fresher2 = new Fresher();
		Group group1 = new Group();
		Group group2 = new Group();
		Set<Fresher> freshers = new HashSet<>();
		freshers.add(fresher1);
		freshers.add(fresher2);
		Set<Group> groups = new HashSet<>();
		groups.add(group1);
		groups.add(group2);
		fresher1.setName("Fresher 1");
		fresher2.setName("Fresher 2");
		fresher1.setGroups(groups);
		fresher2.setGroups(groups);

		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(fresher1);
			session.save(fresher2);
			session.save(group1);
			session.save(group2);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void createFresherAndCourse() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course("Java"));
		courses.add(new Course("Hibernate"));
		Fresher fresher = new Fresher("BaoKey", courses);
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

		try {
			Session session = sessionFactory.openSession();
//			start session
			session.beginTransaction();

			session.save(fresher);
//			end session
			for (Course course : courses) {
				session.save(course);
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void createFresherAndAddress() {
		Address address = new Address("Duy Tan", "Cau Giay");
		Fresher fresher = new Fresher("BaoKey", address);
		address.setFresher(fresher);
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

		try {
			Session session = sessionFactory.openSession();

//			start session
			session.beginTransaction();
			session.save(address);
			session.save(fresher);

//			end session
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void getCourseSyllabuses(int id) {
		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
		try {
			Session session = sessionFactory.openSession();

//			start session
			session.beginTransaction();

			Course course = (Course) session.get(Course.class, id);
			System.out.println(course.getName());
			session.getTransaction().commit();
			session.close();
			System.out.println(course.getSyllabuses());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void createCourseSyllabuses() {
		List<Syllabus> syllabuses = new ArrayList<>();

		Syllabus syllabus = new Syllabus("Hibernate Content", 30);

		Syllabus offlineSyllabus = new Syllabus("Hibernate Content offline", 40);

		syllabuses.add(syllabus);
		syllabuses.add(offlineSyllabus);
		Course course = new Course("Hibernate", new Date(), syllabus, syllabuses);

		SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

		try {
			Session session = sessionFactory.openSession();

//			save 
//			start session
			session.beginTransaction();
			session.save(course);

//			end session
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
