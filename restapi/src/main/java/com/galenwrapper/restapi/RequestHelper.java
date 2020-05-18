package com.galenwrapper.restapi;

import java.util.HashMap;
import java.util.Properties;

class RequestHelper {

  Properties getPropertiesObject(HashMap<String, String> properties) {
    Properties props = new Properties();
    if (properties != null) for (String key : properties.keySet()) {
      props.setProperty(key, properties.get(key));
    }
    return props;
  }

  com.galenframework.speclang2.pagespec.SectionFilter getSectionFilterObject(
    com.galenwrapper.restapi.SectionFilter sectionFilter
  ) {
    com.galenframework.speclang2.pagespec.SectionFilter filter = new com.galenframework.speclang2.pagespec.SectionFilter();
    filter.setExcludedTags((sectionFilter.getExcludedTags()));
    filter.setIncludedTags(sectionFilter.getExcludedTags());
    filter.setSectionName(sectionFilter.getSectionName());
    return filter;
  }
}
