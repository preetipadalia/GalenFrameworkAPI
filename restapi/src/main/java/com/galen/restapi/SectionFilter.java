package com.galen.restapi;

import java.util.List;

public class SectionFilter {
  private List<String> includedTags;
  private List<String> excludedTags;
  private String sectionName;

  public SectionFilter(List<String> includedTags, List<String> excludedTags) {
    this.setIncludedTags(includedTags);
    this.setExcludedTags(excludedTags);
  }

  public SectionFilter() {}

  public List<String> getIncludedTags() {
    return includedTags;
  }

  public void setIncludedTags(List<String> includedTags) {
    this.includedTags = includedTags;
  }

  public List<String> getExcludedTags() {
    return excludedTags;
  }

  public void setExcludedTags(List<String> excludedTags) {
    this.excludedTags = excludedTags;
  }

  public SectionFilter withSectionName(String sectionName) {
    this.sectionName = sectionName;
    return this;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }
}
