package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import play.db.jpa.Model;
import play.templates.JavaExtensions;

@Entity
public class Location extends Model {
	public String name;

	public String slug;

	@OneToMany(mappedBy = "location")
	@Cascade({CascadeType.DELETE})
	public List<Measurement> measurements = new ArrayList<Measurement>();

	public double lat;
	public double lng;
	// 52.070498,4.3007
	public int getNumberOfMeasurements() {
		return measurements.size();
	}

	@PrePersist
	@PreUpdate
	public void createSlug() {
		slug = JavaExtensions.slugify(name);
	}

	public String toString() {
		return name != null ? name : id.toString();
	}

	public Map<Integer, Integer> getTotalsMap() {
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

	public Map<Integer, Float> getPercentages() {
		Map<Integer, Integer> totals = getTotalsMap();

		Map<Integer, Float> percentages = new HashMap<Integer, Float>();

		for (Integer value : totals.keySet()) {
			Integer total = totals.get(value);
			percentages.put(value, ((float) total / (float) measurements.size()) * 100f);
		}

		return percentages;
	}
}
