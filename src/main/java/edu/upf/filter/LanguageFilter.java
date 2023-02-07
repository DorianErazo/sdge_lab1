package edu.upf.filter;

public interface LanguageFilter {

  /**
   * Process
   * @param language
   * @return
   */
  void filterLanguage(String language, boolean append_file) throws Exception; // added the boolean to control if we need append the file
}
