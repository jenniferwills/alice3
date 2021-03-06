/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may
 *    "Alice" appear in their name, without prior written permission of
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package edu.cmu.cs.dennisc.javax.swing.filechooser;

import edu.cmu.cs.dennisc.java.io.FileUtilities;
import edu.cmu.cs.dennisc.java.util.Lists;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Dennis Cosgrove
 */
public class ExtensionFileFilter extends FileFilter {
  private final String description;
  private final boolean areDirectoriesAccepted;
  private final Collection<String> extensions;

  public ExtensionFileFilter(String name, boolean areDirectoriesAccepted, String... extensions) {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    if (extensions.length > 0) {
      sb.append(" (");
      String prefix = "";
      for (String extenstion : extensions) {
        sb.append(prefix);
        sb.append("*.");
        sb.append(extenstion);
        prefix = ", ";
      }
      sb.append(")");
    }
    this.description = sb.toString();
    this.areDirectoriesAccepted = areDirectoriesAccepted;
    this.extensions = Lists.newArrayList(extensions);
  }

  @Override
  public boolean accept(File file) {
    if (file.isDirectory()) {
      return this.areDirectoriesAccepted;
    } else {
      String extension = FileUtilities.getExtension(file);
      if (extension != null) {
        return this.extensions.contains(extension.toLowerCase(Locale.ENGLISH));
      } else {
        return false;
      }
    }
  }

  @Override
  public String getDescription() {
    return this.description;
  }
}
