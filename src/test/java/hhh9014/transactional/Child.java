package hhh9014.transactional;

import hhh9014.BaseChild;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Child extends BaseChild implements Serializable {

	private static final long serialVersionUID = 1L;

	private Parent parent;

	public Child() {
		this(null);
	}
	
	public Child(Integer id) {
        super(id);
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}
