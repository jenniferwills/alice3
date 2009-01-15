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
package edu.cmu.cs.dennisc.ui.lookingglass;

import edu.cmu.cs.dennisc.ui.scenegraph.SetPointOfViewAction;


//todo: allow specification of a reference frame other than absolute
/**
 * @author Dennis Cosgrove
 */
public class ModelManipulationDragAdapter extends edu.cmu.cs.dennisc.ui.lookingglass.OnscreenLookingGlassDragAdapter {
	private edu.cmu.cs.dennisc.scenegraph.AbstractCamera m_sgCamera = null;
	private edu.cmu.cs.dennisc.scenegraph.Transformable m_sgDragAcceptor = null;
	private edu.cmu.cs.dennisc.math.Plane m_planeInAbsolute = new edu.cmu.cs.dennisc.math.Plane();
	private edu.cmu.cs.dennisc.math.Point3 m_xyzInAbsoluteAtPress = null;
	private edu.cmu.cs.dennisc.math.Point3 m_xyzInDragAcceptorAtPress = null;
	private edu.cmu.cs.dennisc.math.Vector3 m_offset = null;

	private edu.cmu.cs.dennisc.math.AffineMatrix4x4 m_undoPOV;
	
	public ModelManipulationDragAdapter() {
		setModifierMask( java.awt.event.MouseEvent.BUTTON1_MASK );
	}

	protected edu.cmu.cs.dennisc.lookingglass.PickObserver getPickObserver() {
		return null;
	}
	
	protected void updateTranslation( edu.cmu.cs.dennisc.scenegraph.Transformable sgDragAcceptor, edu.cmu.cs.dennisc.math.Tuple3 xyz, edu.cmu.cs.dennisc.scenegraph.ReferenceFrame asSeenBy ) {
		if( sgDragAcceptor != null ) {
			sgDragAcceptor.setTranslationOnly( xyz, asSeenBy );
		}
	}
	
	protected edu.cmu.cs.dennisc.scenegraph.Transformable lookupDragAcceptor( edu.cmu.cs.dennisc.scenegraph.Visual sgVisual ) {
		edu.cmu.cs.dennisc.scenegraph.Composite sgParent = sgVisual.getParent();
		if( sgParent instanceof edu.cmu.cs.dennisc.scenegraph.Transformable ) {
			return (edu.cmu.cs.dennisc.scenegraph.Transformable)sgParent;
		} else {
			return null;
		}
	}
	protected edu.cmu.cs.dennisc.scenegraph.Transformable getDragAcceptor() {
		return m_sgDragAcceptor;
	}
	
	private edu.cmu.cs.dennisc.math.Point3 getPointInPlane( edu.cmu.cs.dennisc.math.Plane plane, int xPixel, int yPixel ) {
		edu.cmu.cs.dennisc.math.Ray ray = getOnscreenLookingGlass().getRayAtPixel( xPixel, yPixel, m_sgCamera );
		edu.cmu.cs.dennisc.math.AffineMatrix4x4 m = m_sgCamera.getAbsoluteTransformation();
		ray.transform( m );
		double t = plane.intersect( ray );
		return ray.getPointAlong( t );
	}
	

	private double yDelta = 0.0;
	@Override
	protected void handleMousePress( java.awt.Point current, edu.cmu.cs.dennisc.ui.DragStyle dragStyle, boolean isOriginalAsOpposedToStyleChange ) {
		if( isOriginalAsOpposedToStyleChange ) {
			edu.cmu.cs.dennisc.lookingglass.PickResult pickResult = getOnscreenLookingGlass().pickFrontMost( current.x, current.y, edu.cmu.cs.dennisc.lookingglass.LookingGlass.SUB_ELEMENT_IS_NOT_REQUIRED, getPickObserver() );
			m_sgCamera = (edu.cmu.cs.dennisc.scenegraph.AbstractCamera)pickResult.getSource();
			edu.cmu.cs.dennisc.scenegraph.Visual sgVisual = pickResult.getVisual();
			if( sgVisual != null ) {
				m_sgDragAcceptor = lookupDragAcceptor( sgVisual );
				if( m_sgDragAcceptor != null ) {
					m_undoPOV = m_sgDragAcceptor.getTransformation( edu.cmu.cs.dennisc.scenegraph.AsSeenBy.SCENE );
					m_xyzInAbsoluteAtPress = edu.cmu.cs.dennisc.scenegraph.util.TransformationUtilities.transformToAbsolute_New( pickResult.getPositionInSource(), m_sgCamera );
				}
			}
			this.yDelta = 0.0;
		} else {
			if( m_sgDragAcceptor != null ) {
				edu.cmu.cs.dennisc.math.Ray ray = getOnscreenLookingGlass().getRayAtPixel( current.x, current.y, m_sgCamera );
				ray.transform( m_sgCamera.getAbsoluteTransformation() );
				double t = m_planeInAbsolute.intersect( ray );
				m_xyzInAbsoluteAtPress = ray.getPointAlong( t );
				//m_xyzInAbsoluteAtPress.y += this.yDelta;
			}
		}
		
		if( m_sgDragAcceptor != null ) {
			edu.cmu.cs.dennisc.math.AffineMatrix4x4 m = m_sgDragAcceptor.getAbsoluteTransformation();
			m_offset = edu.cmu.cs.dennisc.math.Vector3.createSubtraction( m_xyzInAbsoluteAtPress, m.translation );
			m_xyzInDragAcceptorAtPress = m_sgDragAcceptor.transformTo_New( m_xyzInAbsoluteAtPress, m_sgDragAcceptor.getRoot()/*todo: edu.cmu.cs.dennisc.scenegraph.AsSeenBy.SCENE*/ );
			if( dragStyle.isShiftDown() ) {
				edu.cmu.cs.dennisc.math.AffineMatrix4x4 cameraAbsolute = m_sgCamera.getAbsoluteTransformation();
				edu.cmu.cs.dennisc.math.Vector3 axis = edu.cmu.cs.dennisc.math.Vector3.createSubtraction( cameraAbsolute.translation, m_xyzInAbsoluteAtPress );
				axis.normalize();
				m_planeInAbsolute.set( m_xyzInAbsoluteAtPress, axis );
			} else {
				m_planeInAbsolute.set( m_xyzInAbsoluteAtPress, edu.cmu.cs.dennisc.math.Vector3.accessPositiveYAxis() );
			}
		} else {
			m_planeInAbsolute.setNaN();
			m_xyzInAbsoluteAtPress = edu.cmu.cs.dennisc.math.Point3.createNaN();
			m_xyzInDragAcceptorAtPress = null;
		}
	}
	@Override
	protected void handleMouseDrag( java.awt.Point current, int xDeltaSince0, int yDeltaSince0, int xDeltaSincePrevious, int yDeltaSincePrevious, edu.cmu.cs.dennisc.ui.DragStyle dragStyle ) {
		if( m_sgDragAcceptor == null || m_planeInAbsolute.isNaN() || m_xyzInDragAcceptorAtPress.isNaN() ) {
			//pass
		} else {
			if( dragStyle.isControlDown() ) {
//				if( false ) {
//					final int THRESHOLD = 25;
//					if( (yDeltaSince0 * yDeltaSince0 + xDeltaSince0 * xDeltaSince0) > THRESHOLD ) {
//						edu.cmu.cs.dennisc.math.Vector3d dir = edu.cmu.cs.dennisc.math.LinearAlgebra.subtract( xyzInAbsolutePlane, m_xyzInAbsoluteAtPress );
//						double yaw = Math.atan2( dir.x, dir.z );
//						//y += Math.PI / 2;
//						m_sgDragAcceptor.setAxesOnly( edu.cmu.cs.dennisc.math.LinearAlgebra.newRotationAboutYAxisMatrix3d( yaw, edu.cmu.cs.dennisc.math.UnitOfAngle.RADIANS ), edu.cmu.cs.dennisc.scenegraph.ReferenceFrame.AsSeenBy.SCENE );
//					} else {
//						//						System.out.println( "too close" );
//					}
//				} else {
					//double yaw0 = Math.atan2( m_undoPOV.right.z, m_undoPOV.backward.z );
					m_sgDragAcceptor.applyRotationAboutYAxis( new edu.cmu.cs.dennisc.math.AngleInRadians( xDeltaSincePrevious * 0.01 ) );
//				}
			} else {
				if( dragStyle.isShiftDown() ) {
					if( m_sgDragAcceptor != null ) {
						edu.cmu.cs.dennisc.math.Point3 t = m_sgDragAcceptor.getAbsoluteTransformation().translation;
						final edu.cmu.cs.dennisc.math.Point3 xyzInAbsolutePlane = getPointInPlane( m_planeInAbsolute, current.x, current.y );
						
						this.yDelta += xyzInAbsolutePlane.y - t.y;

						xyzInAbsolutePlane.subtract( m_offset );
						xyzInAbsolutePlane.x = t.x;
						xyzInAbsolutePlane.z = t.z;
						
						
						getOnscreenLookingGlass().getLookingGlassFactory().invokeLater( new Runnable() {
							public void run() {
								updateTranslation( m_sgDragAcceptor, xyzInAbsolutePlane, edu.cmu.cs.dennisc.scenegraph.AsSeenBy.SCENE );
							}
						} );
					}
				} else {
					final edu.cmu.cs.dennisc.math.Point3 xyzInAbsolutePlane = getPointInPlane( m_planeInAbsolute, current.x, current.y );
					xyzInAbsolutePlane.subtract( m_offset );
					getOnscreenLookingGlass().getLookingGlassFactory().invokeLater( new Runnable() {
						public void run() {
							updateTranslation( m_sgDragAcceptor, xyzInAbsolutePlane, edu.cmu.cs.dennisc.scenegraph.AsSeenBy.SCENE );
						}
					} );
				}
			}
		}
	}
	@Override
	protected java.awt.Point handleMouseRelease( java.awt.Point rv, edu.cmu.cs.dennisc.ui.DragStyle dragStyle, boolean isOriginalAsOpposedToStyleChange ) {
		if( m_sgCamera != null && m_sgDragAcceptor != null ) {
//			if( dragStyle.isControlDown() ) {
//				java.awt.Point p = m_sgDragAcceptor.transformToAWT_New( m_xyzInDragAcceptorAtPress, getOnscreenLookingGlass(), m_sgCamera );
//				warpCursor( p );
//				showCursor();
//				rv.setLocation( p );
//			}
		}
		if( isOriginalAsOpposedToStyleChange ) {
			if( m_sgDragAcceptor != null ) {
				edu.cmu.cs.dennisc.math.AffineMatrix4x4 redoPOV = m_sgDragAcceptor.getTransformation( edu.cmu.cs.dennisc.scenegraph.AsSeenBy.SCENE );
				if( getUndoRedoManager() != null ) {
					getUndoRedoManager().pushAlreadyRunActionOntoUndoStack( new SetPointOfViewAction( getAnimator(), m_sgDragAcceptor, edu.cmu.cs.dennisc.scenegraph.AsSeenBy.SCENE, m_undoPOV, redoPOV ) );
				}
			}
			m_sgDragAcceptor = null;
			m_planeInAbsolute.setNaN();
		}
		return rv;
	}
}
