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
package org.alice.stageide.personresource.views.renderers;

/**
 * @author Dennis Cosgrove
 */
public class HairListCellRenderer extends IngredientListCellRenderer<org.alice.stageide.personresource.data.HairHatStyle> {
	private static class SingletonHolder {
		private static HairListCellRenderer instance = new HairListCellRenderer();
	}

	public static HairListCellRenderer getInstance() {
		return SingletonHolder.instance;
	}

	private org.alice.stageide.personresource.data.HairColorName lastCommonHairColorName;
	private org.alice.stageide.personresource.data.HairColorName hairColorName;

	private HairListCellRenderer() {
	}

	public org.alice.stageide.personresource.data.HairColorName getHairColorName() {
		return this.hairColorName;
	}

	public void setHairColorName( org.alice.stageide.personresource.data.HairColorName hairColorName ) {
		if( edu.cmu.cs.dennisc.equivalence.EquivalenceUtilities.areEquivalent( this.hairColorName, hairColorName ) ) {
			//pass
		} else {
			if( this.hairColorName != null ) {
				if( org.alice.stageide.personresource.data.HairUtilities.isCommonHairColorName( this.hairColorName ) ) {
					this.lastCommonHairColorName = this.hairColorName;
				}
			}
			this.hairColorName = hairColorName;
		}
	}

	@Override
	protected Object getValue( org.alice.stageide.personresource.data.HairHatStyle value ) {
		if( value != null ) {
			if( this.hairColorName != null ) {
				Object rv = value.getHair( this.hairColorName );
				if( rv != null ) {
					//pass
				} else {
					if( this.lastCommonHairColorName != null ) {
						rv = value.getHair( this.lastCommonHairColorName );
					}
					if( rv != null ) {
						//pass
					} else {
						java.util.List<org.alice.stageide.personresource.data.HairColorNameHairCombo> hairColorNameHairCombos = value.getHairColorNameHairCombos();
						if( hairColorNameHairCombos.size() > 0 ) {
							org.alice.stageide.personresource.data.HairColorNameHairCombo hairColorNameHairCombo = hairColorNameHairCombos.get( 0 );
							if( hairColorNameHairCombo != null ) {
								rv = hairColorNameHairCombo.getHair();
							}
						}
					}
				}
				return rv;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	protected String getSubPath() {
		return "hair_pictures";
	}
}
