package edu.fa.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "Course_name", unique = false)
	private String name;

//	Không muốn đưa thuộc tính này vào column trong database
//	@Transient
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Transient
//	@Embedded
	private Syllabus syllabus;

//	mặc định fetch của nó là LAZY
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Syllabus> syllabuses;



	public List<Syllabus> getSyllabuses() {
		return syllabuses;
	}

	public void setSyllabuses(List<Syllabus> syllabuses) {
		this.syllabuses = syllabuses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public Course() {
		super();
	}

	public Course(String name, Date createdDate, Syllabus syllabus) {
		super();
		this.name = name;
		this.createdDate = createdDate;
		this.syllabus = syllabus;
	}

	public Course(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Course(String name) {
		this.name = name;
	}

	public Course(String name, Date createdDate) {
		super();
		this.name = name;
		this.createdDate = createdDate;
	}

	public Course(String name, Date createdDate, Syllabus syllabus, List<Syllabus> syllabuses) {
		super();
		this.name = name;
		this.createdDate = createdDate;
		this.syllabus = syllabus;
		this.syllabuses = syllabuses;
	}

	@Override
	public String toString() {
		return name;
	}
}
