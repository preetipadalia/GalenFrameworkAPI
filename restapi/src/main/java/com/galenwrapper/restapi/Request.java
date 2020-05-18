package com.galenwrapper.restapi;

import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {
  private String SessionId;
  private String Url;
  private String SpecPath;
  private List<String> IncludedTags;
  private String ReportPath;
  private SectionFilter SectionFilter;
  private String TestTitle;
  private String ReportTitle;
  private HashMap<String, String> Properties;
  private String BrowserType;
  private String DriverPath;
  public int BrowserHeight;
  public int BrowserWidth;
}
