package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Measurement extends Model {
	
	@ManyToOne
    public Location location;
	
	public Date createdAt;
	
	@Column(nullable = false)
	public Integer value;
	
	public String toString(){
		return value != null ? value.toString() : "null [" + id.toString() + "]";
	}
	
	@PrePersist
	public void createDate(){
		createdAt = new Date();
	}
}
