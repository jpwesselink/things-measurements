package models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Measurement extends Model {
	
	@ManyToOne
	@Index(name = "location_index")
	
    public Location location;
	@Index(name = "created_at_index")
	@Required
	public Date createdAt = new Date();
	
	@Column(nullable = false)
	@Required
	public Integer value;
	
	@Required
	public double lat;
	
	@Required
	public double lng;
	
	public String toString(){
		return (location != null?location.slug:"no location") + " " + (value != null ? value.toString() : "null [" + id.toString() + "]");
	}
	
	
	@PrePersist
	public void createDate(){
		createdAt = new Date();
	}
	
	public static Map<Integer, Integer> getTotalsMap() {
		List<Measurement> measurements = Measurement.all().fetch();
		Map<Integer, Integer> totals = new HashMap<Integer, Integer>();
		for (Measurement m : measurements) {
			if (totals.get(m.value) == null) {
				totals.put(m.value, 0);
			}
			Integer total = totals.get(m.value);

			totals.put(m.value, ++total);
		}

		return totals;
	}

	public static Map<Integer, Float> getPercentages() {
		List<Measurement> measurements = Measurement.all().fetch();
		Map<Integer, Integer> totals = new HashMap<Integer, Integer>();
		for (Measurement m : measurements) {
			if (totals.get(m.value) == null) {
				totals.put(m.value, 0);
			}
			Integer total = totals.get(m.value);

			totals.put(m.value, ++total);
		}

		Map<Integer, Float> percentages = new HashMap<Integer, Float>();

		for (Integer value : totals.keySet()) {
			Integer total = totals.get(value);
			percentages.put(value, ((float) total / (float) measurements.size()) * 100f);
		}

		return percentages;
	}
}
