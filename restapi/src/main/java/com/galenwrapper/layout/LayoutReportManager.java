package com.galenwrapper.layout;

import com.galenframework.reports.model.LayoutReport;
import java.util.HashMap;
import java.util.UUID;

public class LayoutReportManager {
  public static HashMap<String, LayoutReport> layoutReportsMap = new HashMap<>();

  public static String UpdateLayoutReportMap(LayoutReport rep) {
    String key = generateString();
    layoutReportsMap.put(key, rep);
    return key;
  }

  public static String generateString() {
    String uuid = UUID.randomUUID().toString();
    return uuid;
  }
}
