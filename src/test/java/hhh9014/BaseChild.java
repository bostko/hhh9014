package hhh9014;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseChild extends Base {

	private int index;

    public BaseChild(Integer id) {
        super(id);
    }
	
	@Column(name="idx", nullable = false)
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
