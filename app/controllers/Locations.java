package controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import models.Location;
import models.Measurement;
import play.mvc.Controller;
import serializers.Serializers;

public class Locations extends Controller {

	protected static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}

	public static void index() {
		renderJSON(Serializers.locationSerializer.serialize(Location.all().fetch()));
	}

	public static void show(String slug) {
		renderJSON(Serializers.locationSerializer.serialize(Location.find("bySlug", slug).first()));
	}

	public static void xls() throws IOException, WriteException {
		response.contentType = "application/vnd.ms-excel";
		final String dateFormatString = "yyyy-MM-dd HH:mm:ss";
		DateFormat customDateFormat = new DateFormat(dateFormatString);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatString);
		File tempFile = File.createTempFile("measurements-export-" + simpleDateFormat.format(new Date()), ".xls");
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		List<Location> locations = Location.all().fetch();
		WritableCellFormat percentFloatFormat = new WritableCellFormat(NumberFormats.PERCENT_FLOAT);
		WritableCellFormat dateFormat = new WritableCellFormat(customDateFormat);
		int i = 0;
		for (Location location : locations) {
			WritableSheet sheet = workbook.createSheet(location.name + " overview", i);
			i++;
			Map<Integer, Integer> totalsMap = location.getTotalsMap();
			Map<Integer, Float> percentages = location.getPercentages();
			int j = 0;
			int k = 0;
			List<Integer> keySet = asSortedList(totalsMap.keySet());

			sheet.addCell(new Label(k, 0, "Values"));

			sheet.addCell(new Label(k, 1, "Totals"));

			sheet.addCell(new Label(k, 2, "Percentages"));
			for (k++; k < 5; k++) {
				Label label = new Label(k, 0, "Value " + k);
				sheet.addCell(label);

				Number number = new Number(k, 1, totalsMap.get(k) != null ? totalsMap.get(k) : 0);
				sheet.addCell(number);
				Number percentage = new Number(k, 2, percentages.get(k) != null ? (percentages.get(k) / 100) : 0,
						percentFloatFormat);
				sheet.addCell(percentage);

			}
		}

		for (Location location : locations) {
			WritableSheet sheet = workbook.createSheet(location.name + " values", i);
			i++;
			sheet.addCell(new Label(0, 0, "Value"));
			sheet.addCell(new Label(1, 0, "Date"));
			int k = 1;
			for (Measurement measurement : location.measurements) {
				sheet.addCell(new Number(0, k, measurement.value));
				DateTime dateCell = new DateTime(1, k, measurement.createdAt, dateFormat);

				sheet.addCell(dateCell);
				k++;
			}
		}
		workbook.write();
		workbook.close();
		renderBinary(tempFile);
	}
}
