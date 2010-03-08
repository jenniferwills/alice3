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
package org.alice.apis.moveandturn.gallery.textbookdemos.newstufftemp;
	
public class MaleBalletDancer extends org.alice.apis.moveandturn.gallery.GalleryModel {
	public MaleBalletDancer() {
		super( "Textbook Demos/New Stuff (Temp)/MaleBalletDancer" );
	}
	public enum Part {
		LeftThigh_LeftKnee_LeftCalf_LeftFoot( "Left_thigh", "Left_knee", "Left_calf", "Left_foot" ),
		LeftThigh_LeftKnee_LeftCalf( "Left_thigh", "Left_knee", "Left_calf" ),
		LeftThigh_LeftKnee( "Left_thigh", "Left_knee" ),
		LeftThigh( "Left_thigh" ),
		RightThigh_RightKnee_RightCalf_RightFoot( "Right_thigh", "Right_knee", "Right_calf", "Right_foot" ),
		RightThigh_RightKnee_RightCalf( "Right_thigh", "Right_knee", "Right_calf" ),
		RightThigh_RightKnee( "Right_thigh", "Right_knee" ),
		RightThigh( "Right_thigh" ),
		Torso_RightArm_RightForarm_RightHand( "Torso", "Right_Arm", "Right_forarm", "Right_Hand" ),
		Torso_RightArm_RightForarm( "Torso", "Right_Arm", "Right_forarm" ),
		Torso_RightArm( "Torso", "Right_Arm" ),
		Torso_LeftArm_LeftForarm_LeftHand( "Torso", "Left_arm", "Left_forarm", "Left_hand" ),
		Torso_LeftArm_LeftForarm( "Torso", "Left_arm", "Left_forarm" ),
		Torso_LeftArm( "Torso", "Left_arm" ),
		Torso_Neck_Head( "Torso", "neck", "head" ),
		Torso_Neck( "Torso", "neck" ),
		Torso( "Torso" );
		private String[] m_path;
		Part( String... path ) {
			m_path = path;
		}
		public String[] getPath() {
			return m_path;
		}
	}
	public org.alice.apis.moveandturn.Model getPart( Part part ) {
		return getDescendant( org.alice.apis.moveandturn.Model.class, part.getPath() );
	}

}
