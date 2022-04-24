package com.hydro.common.csv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Helper csv builder class for building a csv download.
 * 
 * @author Sam Butler
 * @since March 18, 2022
 */
@Service
public class CSVBuilder<T> {

    private Map<String, String> csvParams;

    private List<T> data;

    private CSVBuilder(List<T> d) {
        this.data = d;
        this.csvParams = new LinkedHashMap<String, String>();
    }

    /**
     * Method that takes in a list generic object to be downloaded. This will be the
     * data used to parse the object into a csv.
     * 
     * @param <T> The type of the object.
     * @param d   The data contained in the object.
     * @return {@link CSVBuilder} with the set data and parms.
     */
    public static <T> CSVBuilder<T> download(List<T> d) {
        return new CSVBuilder<T>(d);
    }

    /**
     * This will add a column entry to the params which will be used to create the
     * csv and what data should be used.
     * 
     * @param label The label of the column.
     * @param field What the field name in the object is.
     * @return
     */
    public CSVBuilder<T> withColumn(String label, String field) {
        this.csvParams.put(field, label);
        return this;
    }

    /**
     * Builds out the csv with the given file name using the data and csv params
     * given.
     * 
     * @param filename The name of the file it should be downloaded as.
     * @return The resource of the downloaded csv.
     */
    public ResponseEntity<Resource> build(String filename) {
        InputStreamResource file = new InputStreamResource(generateCSV());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    /**
     * Private method that will do all the dirty work of populating the csv and
     * downloading it through the API.
     * 
     * @param listData The data to be included in the csv report.
     * @param params   The params to use as columns.
     * @return The resource of the generated csv.
     */
    private ByteArrayInputStream generateCSV() {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            csvPrinter.printRecord(getColumnHeaders());
            for (T d : this.data) {
                List<String> data = convertToMap(d).values().stream().map(e -> e == null ? "" : e).map(Object::toString)
                        .collect(Collectors.toList());
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

    /**
     * Will convert the given generic object into a linked hash map so that it can
     * be pushed into the csv.
     * 
     * @param d The data to convert.
     * @return The converted data.
     */
    private Map<String, Object> convertToMap(T d) {
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

        Field[] allFields = d.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            if (!this.csvParams.containsKey(field.getName())) {
                continue;
            }

            try {
                dataMap.put(field.getName(), field.get(d));
            } catch (Exception e) {
                dataMap.put(field.getName(), null);
            }
        }
        return dataMap;
    }

    /**
     * Gets the header columns based on the passed in parameters.
     * 
     * @return The list of the csv headers.
     */
    private List<String> getColumnHeaders() {
        return new ArrayList<String>(this.csvParams.values());
    }
}
