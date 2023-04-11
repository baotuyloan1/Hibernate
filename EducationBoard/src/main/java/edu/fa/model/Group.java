package edu.fa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@NamedQueries({ @NamedQuery(name = "findGroupByName", query = "from Group where name like :name") })
@Entity
@Table(name = "Groups")
// usage mục đích sử dụng trong transaction
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;

	@ManyToMany
	private Set<Fresher> freshers = new HashSet<>();

	public Group() {
		System.out.println("created a group");
	}

	public Group(String name, Set<Fresher> freshers) {
		super();
		System.out.println("created a group with cón");
		this.name = name;
		this.freshers = freshers;
	}

	public Set<Fresher> getFreshers() {
		return freshers;
	}

	public void setFreshers(Set<Fresher> freshers) {
		this.freshers = freshers;
	}

//
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

	public Group(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return id + " " + name;
	}

}
