/* LanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules.en;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.Language;
import org.languagetool.rules.Example;
import org.languagetool.rules.WordRepeatBeginningRule;
import org.languagetool.tools.StringTools;

import java.util.*;

/**
 * Adds a list English adverbs to {@link WordRepeatBeginningRule}.
 * 
 * @author Markus Brenneis
 */
public class EnglishWordRepeatBeginningRule extends WordRepeatBeginningRule {
  
  public EnglishWordRepeatBeginningRule(ResourceBundle messages, Language language) {
    super(messages, language);
    addExamplePair(Example.wrong("Moreover, the street is almost entirely residential. <marker>Moreover</marker>, it was named after a poet."),
                   Example.fixed("Moreover, the street is almost entirely residential. <marker>It</marker> was named after a poet."));
  }
  
  @Override
  public String getId() {
    return "ENGLISH_WORD_REPEAT_BEGINNING_RULE";
  }
  
  private static final Set<String> ADVERBS = new HashSet<>();
  static {
    ADVERBS.add("Additionally");
    ADVERBS.add("Besides");
    ADVERBS.add("Furthermore");
    ADVERBS.add("Moreover");
  }

  @Override
  public boolean isException(String token) {
    return super.isException(token) || token.equals("The") || token.equals("A") || token.equals("An");
  }

  @Override
  protected boolean isAdverb(AnalyzedTokenReadings token) {
    return ADVERBS.contains(token.getToken());
  }

  @Override
  protected List<String> getSuggestions(AnalyzedTokenReadings token) {
    if (token.hasPosTag("PRP")) {
      String tok = token.getToken();
      String adaptedToken = tok.equals("I") ? tok : tok.toLowerCase();
      return Arrays.asList(
              "Furthermore, " + adaptedToken,
              "Likewise, " + adaptedToken,
              "Not only that, but " + adaptedToken);
    }
    return Collections.emptyList();
  }

}
