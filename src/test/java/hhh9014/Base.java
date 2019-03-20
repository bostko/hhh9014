package hhh9014;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Base {

	private Integer id;
	
	public Base(Integer id) {
		this.id = id;
	}

	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
    public boolean equals(final Object obj) {
        // id is never null in the test case
        return this == obj
        		|| (obj instanceof Base && id.equals(((Base) obj).getId()));
    }

    @Override
    public int hashCode() {
    	// id is never null in the test case
        return id.hashCode();
    }
}
