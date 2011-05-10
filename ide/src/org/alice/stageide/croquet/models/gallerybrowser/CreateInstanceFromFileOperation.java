/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
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
 */
package org.alice.stageide.croquet.models.gallerybrowser;

/**
 * @author Dennis Cosgrove
 */
abstract class CreateInstanceFromFileOperation extends AbstractGalleryDeclareFieldOperation {
	public CreateInstanceFromFileOperation( java.util.UUID individualId ) {
		super( individualId );
	}

	protected abstract java.io.File getInitialDirectory();

	private void showMessageDialog( java.io.File file, boolean isValidZip ) {
		StringBuffer sb = new StringBuffer();
		sb.append( "Unable to create instance from file " );
		sb.append( edu.cmu.cs.dennisc.java.io.FileUtilities.getCanonicalPathIfPossible( file ) );
		sb.append( ".\n\n" );
		sb.append( getIDE().getApplicationName() );
		sb.append( " is able to create instances from class files saved by " );
		sb.append( getIDE().getApplicationName() );
		sb.append( ".\n\nLook for files with an " );
		sb.append( edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION );
		sb.append( " extension." );
		this.getIDE().showMessageDialog( sb.toString(), "Cannot read file", edu.cmu.cs.dennisc.croquet.MessageType.ERROR );
	}
	
	@Override
	protected org.alice.ide.declarationpanes.CreateFieldFromGalleryPane prologue( org.lgna.croquet.steps.InputDialogOperationStep context ) {
		org.alice.ide.declarationpanes.CreateFieldFromGalleryPane rv = null;
		java.io.File directory = this.getInitialDirectory();
		java.io.File file = this.getIDE().showOpenFileDialog( directory, null, edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION, false );
		if( file != null ) {
			String lcFilename = file.getName().toLowerCase();
			if( lcFilename.endsWith( ".a2c" ) ) {
				this.getIDE().showMessageDialog( "Alice3 does not load Alice2 characters", "Cannot read file", edu.cmu.cs.dennisc.croquet.MessageType.ERROR );
			} else if( lcFilename.endsWith( edu.cmu.cs.dennisc.alice.project.ProjectUtilities.PROJECT_EXTENSION.toLowerCase() ) ) {
				this.getIDE().showMessageDialog( file.getAbsolutePath() + " appears to be a project file and not a class file.\n\nLook for files with an " + edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION + " extension.",
						"Incorrect File Type", edu.cmu.cs.dennisc.croquet.MessageType.INFORMATION );
			} else {
				boolean isWorthyOfException = lcFilename.endsWith( edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION.toLowerCase() );
				java.util.zip.ZipFile zipFile;
				try {
					zipFile = new java.util.zip.ZipFile( file );
				} catch( java.io.IOException ioe ) {
					if( isWorthyOfException ) {
						throw new RuntimeException( file.getAbsolutePath(), ioe );
					} else {
						this.showMessageDialog( file, false );
						zipFile = null;
					}
				}
				if( zipFile != null ) {
					edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?> type;
					try {
						edu.cmu.cs.dennisc.pattern.Tuple2< ? extends edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?>, java.util.Set< org.alice.virtualmachine.Resource > > tuple = edu.cmu.cs.dennisc.alice.project.ProjectUtilities.readType( zipFile );
						type = tuple.getA();
						edu.cmu.cs.dennisc.print.PrintUtilities.println( "TODO: add in resources" );
					} catch( java.io.IOException ioe ) {
						if( isWorthyOfException ) {
							throw new RuntimeException( file.getAbsolutePath(), ioe );
						} else {
							this.showMessageDialog( file, true );
							type = null;
						}
					}
					if( type != null ) {
						rv = new org.alice.ide.declarationpanes.CreateFieldFromGalleryPane( this.getOwnerType(), type );
					}
				}
			}
		}
		return rv;
	}
	@Override
	protected edu.cmu.cs.dennisc.pattern.Tuple2< edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice, java.lang.Object > createFieldAndInstance( org.lgna.croquet.steps.InputDialogOperationStep context ) {
		org.alice.ide.declarationpanes.CreateFieldFromGalleryPane createFieldPane = context.getMainPanel();
		//todo: dialog title: "Create New Instance"
		edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice field = createFieldPane.getInputValue();
		if( field != null ) {
			Object instanceInJava = createFieldPane.createInstanceInJava();
			return edu.cmu.cs.dennisc.pattern.Tuple2.createInstance( field, instanceInJava );
		} else {
			return null;
		}
	}
//	@Override
//	protected edu.cmu.cs.dennisc.pattern.Tuple2< edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice, java.lang.Object > createFieldAndInstance( edu.cmu.cs.dennisc.croquet.Context context, java.awt.event.ActionEvent e, edu.cmu.cs.dennisc.croquet.AbstractButton< ? > button, edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice ownerType ) {
//		java.io.File directory = this.getInitialDirectory();
//		java.io.File file = edu.cmu.cs.dennisc.java.awt.FileDialogUtilities.showOpenFileDialog( this.getIDE().getJFrame(), directory, null, edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION, false );
//
//		if( file != null ) {
//			String lcFilename = file.getName().toLowerCase();
//			if( lcFilename.endsWith( ".a2c" ) ) {
//				this.getIDE().showMessageDialog( "Alice3 does not load Alice2 characters", "Cannot read file", edu.cmu.cs.dennisc.croquet.MessageType.ERROR );
//			} else if( lcFilename.endsWith( edu.cmu.cs.dennisc.alice.project.ProjectUtilities.PROJECT_EXTENSION.toLowerCase() ) ) {
//				this.getIDE().showMessageDialog( file.getAbsolutePath() + " appears to be a project file and not a class file.\n\nLook for files with an " + edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION + " extension.",
//						"Incorrect File Type", edu.cmu.cs.dennisc.croquet.MessageType.INFORMATION );
//			} else {
//				boolean isWorthyOfException = lcFilename.endsWith( edu.cmu.cs.dennisc.alice.project.ProjectUtilities.TYPE_EXTENSION.toLowerCase() );
//				java.util.zip.ZipFile zipFile;
//				try {
//					zipFile = new java.util.zip.ZipFile( file );
//				} catch( java.io.IOException ioe ) {
//					if( isWorthyOfException ) {
//						throw new RuntimeException( file.getAbsolutePath(), ioe );
//					} else {
//						this.showMessageDialog( file, false );
//						zipFile = null;
//					}
//				}
//				if( zipFile != null ) {
//					edu.cmu.cs.dennisc.alice.ast.AbstractType type;
//					try {
//						edu.cmu.cs.dennisc.pattern.Tuple2< edu.cmu.cs.dennisc.alice.ast.AbstractType, java.util.Set< org.alice.virtualmachine.Resource > > tuple = edu.cmu.cs.dennisc.alice.project.ProjectUtilities.readType( zipFile );
//						type = tuple.getA();
//
//						edu.cmu.cs.dennisc.print.PrintUtilities.println( "TODO: add in resources" );
//					} catch( java.io.IOException ioe ) {
//						if( isWorthyOfException ) {
//							throw new RuntimeException( file.getAbsolutePath(), ioe );
//						} else {
//							this.showMessageDialog( file, true );
//							type = null;
//						}
//					}
//					if( type != null ) {
//						org.alice.ide.declarationpanes.CreateFieldFromGalleryPane createFieldPane = new org.alice.ide.declarationpanes.CreateFieldFromGalleryPane( ownerType, type );
//						edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice field = createFieldPane.showInJDialog( button, "Create New Instance" );
//						if( field != null ) {
//							Object instanceInJava = createFieldPane.createInstanceInJava();
//							return new edu.cmu.cs.dennisc.pattern.Tuple2< edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice, Object >( field, instanceInJava );
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
}
