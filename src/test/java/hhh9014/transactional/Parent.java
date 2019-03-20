package hhh9014.transactional;

import hhh9014.Base;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class Parent extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Child> children = new ArrayList<>();

	public Parent() {
		this(null);
	}
	
	public Parent(Integer id) {
		super(id);
	}

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@OrderColumn(name = "idx")
	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public Parent add(Child child) {
		child.setParent(this);
		getChildren().add(child);
		return this;
	}
}
