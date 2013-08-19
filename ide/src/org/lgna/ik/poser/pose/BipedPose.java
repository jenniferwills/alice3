/**
 * Copyright (c) 2006-2012, Carnegie Mellon University. All rights reserved.
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
package org.lgna.ik.poser.pose;

import java.util.ArrayList;
import java.util.List;

import org.lgna.ik.poser.pose.builder.BipedPoseBuilder;
import org.lgna.ik.poser.pose.builder.PoseBuilder;
import org.lgna.story.ImplementationAccessor;
import org.lgna.story.SBiped;
import org.lgna.story.implementation.JointImp;
import org.lgna.story.resources.BipedResource;
import org.lgna.story.resources.JointId;

import edu.cmu.cs.dennisc.java.util.Collections;

/**
 * @author Matt May
 */
public class BipedPose extends Pose<SBiped> {

	public BipedPose( JointKey... pairs ) {
		super( SBiped.class, pairs );
	}

	@Override
	public PoseBuilder getBuilder() {
		return new BipedPoseBuilder();
	}

	@Override
	public JointId[] getDefaultJoints() {
		ArrayList<JointId> rv = Collections.newArrayList();
		JointId[] roots = BipedResource.JOINT_ID_ROOTS;
		for( JointId id : roots ) {
			rv.addAll( tunnel( id ) );
		}
		return rv.toArray( new JointId[ 0 ] );
	}

	private ArrayList<JointId> tunnel( JointId id ) {
		ArrayList<JointId> rv = Collections.newArrayList( id );
		for( JointId child : id.getChildren( BipedResource.class ) ) {
			rv.addAll( tunnel( child ) );
		}
		return rv;
	}

	public static BipedPose createPoseFromBiped( SBiped model, JointId[] arr ) {
		List<JointKey> list = Collections.newArrayList();
		for( JointId id : arr ) {
			JointImp implementation = ImplementationAccessor.getImplementation( model.getJoint( id ) );
			list.add( new JointKey( implementation.getLocalOrientation(), id ) );
		}
		BipedPoseBuilder builder = new BipedPoseBuilder();
		for( JointKey key : list ) {
			builder.addCustom( key.getOrientation(), key.getJointId() );
		}
		return builder.build();
	}

	public static BipedPose createPoseFromBiped( SBiped model ) {
		return createPoseFromBiped( model, new BipedPose().getDefaultJoints() );
	}
}
