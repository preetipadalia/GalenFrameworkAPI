package com.galenwrapper.restapi;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SectionFilter {
  private List<String> IncludedTags;
  private List<String> ExcludedTags;
  private String SectionName;
}
