/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package org.alice.ide.warningpane;

public class WarningPane extends edu.cmu.cs.dennisc.croquet.swing.PageAxisPane {
	public WarningPane( boolean isSolicited ) {
		javax.swing.JLabel label = edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel(  new javax.swing.ImageIcon( this.getClass().getResource( "images/toxic.png" ) ) );

		StringBuffer sb = new StringBuffer();
		sb.append( "<html><body>" );
		sb.append( "<h1>WARNING: Alice3 is not for the faint of heart.</h1>" );
		sb.append( "<font size=\"+1\">" );
		sb.append( "Alice3 is currently under development.  We are working very hard to make this dialog box obsolete.<br>" );
		sb.append( "Thank you for your patience.<br>" );
		sb.append( "We welcome your feedback.<br>" );
		sb.append( "</font>" );
		sb.append( "</body></html>" );
		
		javax.swing.JEditorPane editorPane = new javax.swing.JEditorPane( "text/html", sb.toString() );
		editorPane.setEditable( false );
		editorPane.setOpaque( false );
		
		label.setAlignmentX( 0.0f );
		editorPane.setAlignmentX( 0.0f );
		this.add( label );
		this.add( editorPane );
		this.add( javax.swing.Box.createVerticalStrut( 8 ) );
		
		class FurtherInfoPane extends edu.cmu.cs.dennisc.croquet.swing.RowsSpringPane {
			public FurtherInfoPane() {
				super( 8, 4 );
				this.setBorder( javax.swing.BorderFactory.createEmptyBorder( 0, 16, 0, 0 ) );
			}
			private java.awt.Component createLabel( String text ) {
				javax.swing.JLabel rv = edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( text );
				rv.setHorizontalAlignment( javax.swing.SwingConstants.TRAILING );
				return rv;
			}
			@Override
			protected java.util.List< java.awt.Component[] > addComponentRows( java.util.List< java.awt.Component[] > rv ) {
				rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( createLabel( "updates:" ), new edu.cmu.cs.dennisc.swing.Hyperlink( "http://kenai.com/projects/alice/downloads" ) ) );
				rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( createLabel( "blog:" ), new edu.cmu.cs.dennisc.swing.Hyperlink( "http://blog.alice.org/" ) ) );
				rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( createLabel( "community:" ), new edu.cmu.cs.dennisc.swing.Hyperlink( "http://www.alice.org/community/" ) ) );
				rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( createLabel( "bug reports:" ), new edu.cmu.cs.dennisc.swing.Hyperlink( "http://bugs.alice.org:8080/" ) ) );
				return rv;
			}
		}
		this.add( new FurtherInfoPane() );
		
		if( isSolicited ) {
			//pass
		} else {
			this.add( javax.swing.Box.createVerticalStrut( 8 ) );
			this.add( new javax.swing.JCheckBox( "show this warning at start up ") );
		}
	}
	
	public static void main( String[] args ) {
		WarningPane warningPane = new WarningPane( false );
		javax.swing.JOptionPane.showMessageDialog( null, warningPane, "Alice3 is currently under development", javax.swing.JOptionPane.WARNING_MESSAGE );
	}
}
