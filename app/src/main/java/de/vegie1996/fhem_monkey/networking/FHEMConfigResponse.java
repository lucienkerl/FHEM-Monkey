package de.vegie1996.fhem_monkey.networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lucienkerl on 22/01/16.
 */
public class FHEMConfigResponse {
    String arg;
    List<FHEMDevice> results;
    int totalResultsReturned;

    public void fillFromJSON(JSONObject object) throws JSONException {
        arg = object.getString("Arg");
        totalResultsReturned = object.getInt("totalResultsReturned");
        JSONArray resultsArray = object.getJSONArray("Results");
        List<FHEMDevice> fhemDevicesList = new ArrayList<>();
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject jsonObject = resultsArray.getJSONObject(i);
            FHEMDevice device = new FHEMDevice();
            device.fillFromJSON(jsonObject);
            fhemDevicesList.add(device);
        }
        results = fhemDevicesList;
    }

    public String getArg() {
        return arg;
    }

    public List<FHEMDevice> getResults() {
        return results;
    }

    public int getTotalResultsReturned() {
        return totalResultsReturned;
    }

    public class FHEMDevice {
        String name;
        String possibleSets;
        String possibleAttrs;
        HashMap<String, String> internals;
        HashMap<String, ReadingsItem> readings;
        HashMap<String, String> attributes;

        public void fillFromJSON(JSONObject object) throws JSONException {
            name = object.getString("Name");
            possibleSets = object.getString("PossibleSets");
            possibleAttrs = object.getString("PossibleAttrs");

            JSONObject internalsJSONObject = object.getJSONObject("Internals");
            HashMap<String, String> internalHashMap = new HashMap<>();
            Iterator<String> keysIterator = internalsJSONObject.keys();
            while (keysIterator.hasNext()) {
                String keyStr = keysIterator.next();
                String valueStr = internalsJSONObject.getString(keyStr);
                internalHashMap.put(keyStr, valueStr);
            }
            internals = internalHashMap;

            JSONObject readingsJSONObject = object.getJSONObject("Readings");
            HashMap<String, ReadingsItem> readingsItemHashMap = new HashMap<>();
            Iterator<String> readingsKeysIterator = readingsJSONObject.keys();
            while (readingsKeysIterator.hasNext()) {
                String keyStr = readingsKeysIterator.next();
                JSONObject valueStr = readingsJSONObject.getJSONObject(keyStr);
                ReadingsItem readingsItem = new ReadingsItem();
                readingsItem.fillFromJSON(valueStr);
                readingsItemHashMap.put(keyStr, readingsItem);
            }
            readings = readingsItemHashMap;

            JSONObject attributesJSONObject = object.getJSONObject("Attributes");
            HashMap<String, String> attributesHashMap = new HashMap<>();
            Iterator<String> attrIterator = attributesJSONObject.keys();
            while (attrIterator.hasNext()) {
                String keyStr = attrIterator.next();
                String valueStr = attributesJSONObject.getString(keyStr);
                attributesHashMap.put(keyStr, valueStr);
            }
            attributes = attributesHashMap;
        }

        public String getName() {
            return name;
        }

        public String getPossivleSets() {
            return possibleSets;
        }

        public String getPossibleAttrs() {
            return possibleAttrs;
        }

        public HashMap<String, String> getInternals() {
            return internals;
        }

        public HashMap<String, ReadingsItem> getReadings() {
            return readings;
        }

        public HashMap<String, String> getAttributes() {
            return attributes;
        }
    }

    public class ReadingsItem {
        String value;
        String time;

        public void fillFromJSON(JSONObject object) throws JSONException {
            value = object.getString("Value");
            time = object.getString("Time");
        }

        public String getValue() {
            return value;
        }

        public String getTime() {
            return time;
        }
    }
}
