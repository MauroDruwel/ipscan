package net.azib.ipscan.exporters;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gson.Gson;

/**
 * JSON Exporter
 * 
 * Exports data as JSON objects, one per line.
 * Requires Gson library for JSON serialization.
 * 
 * @author Mauro Druwel
 */
public class JSONExporter extends AbstractExporter {

    private String[] fetcherNames;
    private final Gson gson = new Gson();

    public JSONExporter() {}

    @Override
    public String getId() {
        return "exporter.json";
    }

    @Override
    public String getFilenameExtension() {
        return "json";
    }

    @Override
    public void setFetchers(String[] fetcherNames) throws IOException {
        this.fetcherNames = fetcherNames;
        // No header line for JSON output
    }

    @Override
    public void nextAddressResults(Object[] results) throws IOException {
        if (fetcherNames == null || fetcherNames.length != results.length) {
            throw new IOException("Fetcher names and results length mismatch.");
        }

        Map<String, Object> jsonMap = new LinkedHashMap<>();
        for (int i = 0; i < fetcherNames.length; i++) {
            jsonMap.put(fetcherNames[i], results[i]);
        }
        String jsonLine = gson.toJson(jsonMap);
        output.write(jsonLine);
        output.write(System.lineSeparator());
    }
}
