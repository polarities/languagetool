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
package org.languagetool.rules.pt;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.language.Portuguese;
import org.languagetool.rules.AbstractSimpleReplaceRule2;
import org.languagetool.rules.Categories;
import org.languagetool.rules.Example;
import org.languagetool.rules.ITSIssueType;
import org.languagetool.tools.Tools;

import java.util.*;

import java.net.URL;

/**
 * A rule that matches words which should not be used and suggests correct ones instead. 
 * Romanian implementations. Loads the list of words from
 * <code>/ro/replace.txt</code>.
 *
 * @author Tiago F. Santos (localized from romanian)
 * @since 3.6
 */
public class PortugueseBarbarismsRule extends AbstractSimpleReplaceRule2 {

  public static final String PT_BARBARISMS_REPLACE = "PT_BARBARISMS_REPLACE";

  private static final Locale PT_LOCALE = new Locale("pt");  // locale used on case-conversion

  private final String path;

  @Override
  public List<String> getFileNames() {
    return Collections.singletonList(path);
  }

  public PortugueseBarbarismsRule(ResourceBundle messages, String path) {
    super(messages, new Portuguese());
    this.path = Objects.requireNonNull(path);
    setCategory(Categories.STYLE.getCategory(messages));
    setLocQualityIssueType(ITSIssueType.LocaleViolation);
    useSubRuleSpecificIds();
    addExamplePair(Example.wrong("<marker>curriculum vitae</marker>"),
                   Example.fixed("<marker>currículo</marker>"));
  }

  @Override
  public String getId() {
    return PT_BARBARISMS_REPLACE;
  }

  @Override
  public String getDescription() {
    return "Palavras de origem estrangeira evitáveis: $match";
  }

  @Override
  public String getShort() {
    return "Estrangeirismo";
  }

  @Override
  public String getMessage() {
    return "\"$match\" é um estrangeirismo. É preferível dizer $suggestions.";
  }

  @Override
  public String getSuggestionsSeparator() {
    return " ou ";
  }

  @Override
  public URL getUrl() {
    return Tools.getUrl("https://pt.wikipedia.org/wiki/Estrangeirismo");
  }

  @Override
  public Locale getLocale() {
    return PT_LOCALE;
  }
  
  @Override
  protected boolean isTokenException(AnalyzedTokenReadings atr) {
    // proper nouns tagged in multiwords are exceptions
    return atr.hasPosTagStartingWith("NP") || atr.isImmunized();
  }

}
