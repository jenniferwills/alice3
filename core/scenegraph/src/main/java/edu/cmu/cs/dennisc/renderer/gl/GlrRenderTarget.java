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

package edu.cmu.cs.dennisc.renderer.gl;

/**
 * @author Dennis Cosgrove
 */
abstract class GlrRenderTarget extends edu.cmu.cs.dennisc.pattern.DefaultReleasable implements edu.cmu.cs.dennisc.renderer.RenderTarget {
	public GlrRenderTarget( GlrRenderer lookingGlassFactory ) {
		glrRenderer = lookingGlassFactory;
	}

	/*package-private*/GLEventAdapter getGlEventAdapter() {
		return m_glEventAdapter;
	}

	@Override
	public edu.cmu.cs.dennisc.renderer.RenderFactory getRenderFactory() {
		return glrRenderer;
	}

	@Override
	public edu.cmu.cs.dennisc.renderer.SynchronousPicker getSynchronousPicker() {
		return this.synchronousPicker;
	}

	@Override
	public edu.cmu.cs.dennisc.renderer.SynchronousImageCapturer getSynchronousImageCapturer() {
		return this.synchronousImageCapturer;
	}

	@Override
	public edu.cmu.cs.dennisc.renderer.AsynchronousPicker getAsynchronousPicker() {
		return this.glrAsynchronousPicker;
	}

	@Override
	public edu.cmu.cs.dennisc.renderer.AsynchronousImageCapturer getAsynchronousImageCapturer() {
		return this.glrAsynchronousImageCapturer;
	}

	/* package-private */void fireInitialized( edu.cmu.cs.dennisc.renderer.event.RenderTargetInitializeEvent e ) {
		synchronized( m_lookingGlassListeners ) {
			for( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener lookingGlassListener : m_lookingGlassListeners ) {
				lookingGlassListener.initialized( e );
			}
		}
	}

	/* package-private */void fireCleared( edu.cmu.cs.dennisc.renderer.event.RenderTargetRenderEvent e ) {
		synchronized( m_lookingGlassListeners ) {
			for( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener lookingGlassListener : m_lookingGlassListeners ) {
				lookingGlassListener.cleared( e );
			}
		}
	}

	/* package-private */void fireRendered( edu.cmu.cs.dennisc.renderer.event.RenderTargetRenderEvent e ) {
		synchronized( m_lookingGlassListeners ) {
			for( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener lookingGlassListener : m_lookingGlassListeners ) {
				lookingGlassListener.rendered( e );
			}
		}
	}

	/* package-private */void fireResized( edu.cmu.cs.dennisc.renderer.event.RenderTargetResizeEvent e ) {
		synchronized( m_lookingGlassListeners ) {
			for( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener lookingGlassListener : m_lookingGlassListeners ) {
				lookingGlassListener.resized( e );
			}
		}
	}

	/* package-private */void fireDisplayChanged( edu.cmu.cs.dennisc.renderer.event.RenderTargetDisplayChangeEvent e ) {
		synchronized( m_lookingGlassListeners ) {
			for( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener lookingGlassListener : m_lookingGlassListeners ) {
				lookingGlassListener.displayChanged( e );
			}
		}
	}

	@Override
	public String getDescription() {
		return m_description;
	}

	@Override
	public void setDescription( String description ) {
		m_description = description;
	}

	@Override
	public void addSgCamera( edu.cmu.cs.dennisc.scenegraph.AbstractCamera sgCamera ) {
		assert sgCamera != null : this;
		this.sgCameras.add( sgCamera );
		if( m_glEventAdapter.isListening() ) {
			//pass
		} else {
			javax.media.opengl.GLAutoDrawable glAutoDrawable = this.getGLAutoDrawable();
			m_glEventAdapter.startListening( glAutoDrawable );
		}
	}

	@Override
	public void removeSgCamera( edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera ) {
		assert camera != null;
		this.sgCameras.remove( camera );
		if( m_glEventAdapter.isListening() ) {
			if( this.sgCameras.isEmpty() ) {
				m_glEventAdapter.stopListening( getGLAutoDrawable() );
			}
		}
	}

	@Override
	public void clearSgCameras() {
		if( this.sgCameras.size() > 0 ) {
			this.sgCameras.clear();
		}
		if( m_glEventAdapter.isListening() ) {
			m_glEventAdapter.stopListening( getGLAutoDrawable() );
		}
	}

	@Override
	public int getSgCameraCount() {
		return this.sgCameras.size();
	}

	@Override
	public edu.cmu.cs.dennisc.scenegraph.AbstractCamera getSgCameraAt( int index ) {
		return this.sgCameras.get( index );
	}

	@Override
	public java.util.List<edu.cmu.cs.dennisc.scenegraph.AbstractCamera> getSgCameras() {
		return java.util.Collections.unmodifiableList( this.sgCameras );
	}

	/*package-private*/java.util.List<edu.cmu.cs.dennisc.scenegraph.AbstractCamera> accessSgCameras() {
		return this.sgCameras;
	}

	@Override
	public edu.cmu.cs.dennisc.scenegraph.AbstractCamera getCameraAtPixel( int xPixel, int yPixel ) {
		java.util.ListIterator<edu.cmu.cs.dennisc.scenegraph.AbstractCamera> iterator = this.sgCameras.listIterator( this.sgCameras.size() );
		while( iterator.hasPrevious() ) {
			edu.cmu.cs.dennisc.scenegraph.AbstractCamera sgCamera = iterator.previous();
			synchronized( s_actualViewportBufferForReuse ) {
				getActualViewport( s_actualViewportBufferForReuse, sgCamera );
				if( s_actualViewportBufferForReuse.contains( xPixel, yPixel ) ) {
					return sgCamera;
				}
			}
		}
		return null;
	}

	@Override
	public void addRenderTargetListener( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener listener ) {
		synchronized( m_lookingGlassListeners ) {
			m_lookingGlassListeners.add( listener );
		}
	}

	@Override
	public void removeRenderTargetListener( edu.cmu.cs.dennisc.renderer.event.RenderTargetListener listener ) {
		synchronized( m_lookingGlassListeners ) {
			m_lookingGlassListeners.remove( listener );
		}
	}

	@Override
	public java.util.List<edu.cmu.cs.dennisc.renderer.event.RenderTargetListener> getRenderTargetListeners() {
		return java.util.Collections.unmodifiableList( m_lookingGlassListeners );
	}

	protected abstract java.awt.Dimension getSurfaceSize( java.awt.Dimension rv );

	@Override
	public final java.awt.Dimension getSurfaceSize() {
		return getSurfaceSize( new java.awt.Dimension() );
	}

	@Override
	public final int getSurfaceWidth() {
		synchronized( s_sizeBufferForReuse ) {
			getSurfaceSize( s_sizeBufferForReuse );
			return s_sizeBufferForReuse.width;
		}
	}

	@Override
	public final int getSurfaceHeight() {
		synchronized( s_sizeBufferForReuse ) {
			getSurfaceSize( s_sizeBufferForReuse );
			return s_sizeBufferForReuse.height;
		}
	}

	@Override
	public java.awt.Rectangle getSpecifiedViewport( edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera ) {
		edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter = AdapterFactory.getAdapterFor( camera );
		return cameraAdapter.getSpecifiedViewport();
	}

	@Override
	public void setSpecifiedViewport( edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera, java.awt.Rectangle viewport ) {
		edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter = AdapterFactory.getAdapterFor( camera );
		cameraAdapter.setSpecifiedViewport( viewport );
	}

	public java.awt.Rectangle getActualViewport( java.awt.Rectangle rv, edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter ) {
		java.awt.Dimension surfaceSize = this.getSurfaceSize();
		return cameraAdapter.getActualViewport( rv, surfaceSize.width, surfaceSize.height );
	}

	@Override
	public java.awt.Rectangle getActualViewport( java.awt.Rectangle rv, edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera ) {
		return getActualViewport( rv, AdapterFactory.getAdapterFor( camera ) );
	}

	@Override
	public final java.awt.Rectangle getActualViewport( edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera ) {
		return getActualViewport( new java.awt.Rectangle(), camera );
	}

	@Override
	public edu.cmu.cs.dennisc.math.Matrix4x4 getActualProjectionMatrix( edu.cmu.cs.dennisc.math.Matrix4x4 rv, edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera ) {
		synchronized( s_actualViewportBufferForReuse ) {
			edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter = AdapterFactory.getAdapterFor( camera );
			getActualViewport( s_actualViewportBufferForReuse, cameraAdapter );
			return cameraAdapter.getActualProjectionMatrix( rv, s_actualViewportBufferForReuse );
		}
	}

	@Override
	public final edu.cmu.cs.dennisc.math.Matrix4x4 getActualProjectionMatrix( edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera ) {
		return getActualProjectionMatrix( new edu.cmu.cs.dennisc.math.Matrix4x4(), camera );
	}

	private edu.cmu.cs.dennisc.math.ClippedZPlane getActualPicturePlane( edu.cmu.cs.dennisc.math.ClippedZPlane rv, edu.cmu.cs.dennisc.scenegraph.OrthographicCamera orthographicCamera ) {
		synchronized( s_actualViewportBufferForReuse ) {
			edu.cmu.cs.dennisc.renderer.gl.imp.adapters.OrthographicCameraAdapter orthographicCameraAdapter = AdapterFactory.getAdapterFor( orthographicCamera );
			getActualViewport( s_actualViewportBufferForReuse, orthographicCameraAdapter );
			return orthographicCameraAdapter.getActualPicturePlane( rv, s_actualViewportBufferForReuse );
		}
	}

	@Override
	public final edu.cmu.cs.dennisc.math.ClippedZPlane getActualPicturePlane( edu.cmu.cs.dennisc.scenegraph.OrthographicCamera orthographicCamera ) {
		return getActualPicturePlane( new edu.cmu.cs.dennisc.math.ClippedZPlane(), orthographicCamera );
	}

	private edu.cmu.cs.dennisc.math.ClippedZPlane getActualPicturePlane( edu.cmu.cs.dennisc.math.ClippedZPlane rv, edu.cmu.cs.dennisc.scenegraph.FrustumPerspectiveCamera frustumPerspectiveCamera ) {
		synchronized( s_actualViewportBufferForReuse ) {
			edu.cmu.cs.dennisc.renderer.gl.imp.adapters.FrustumPerspectiveCameraAdapter frustumPerspectiveCameraAdapter = AdapterFactory.getAdapterFor( frustumPerspectiveCamera );
			getActualViewport( s_actualViewportBufferForReuse, frustumPerspectiveCameraAdapter );
			return frustumPerspectiveCameraAdapter.getActualPicturePlane( rv, s_actualViewportBufferForReuse );
		}
	}

	@Override
	public final edu.cmu.cs.dennisc.math.ClippedZPlane getActualPicturePlane( edu.cmu.cs.dennisc.scenegraph.FrustumPerspectiveCamera frustumPerspectiveCamera ) {
		return getActualPicturePlane( new edu.cmu.cs.dennisc.math.ClippedZPlane(), frustumPerspectiveCamera );
	}

	@Override
	public edu.cmu.cs.dennisc.math.Angle getActualHorizontalViewingAngle( edu.cmu.cs.dennisc.scenegraph.SymmetricPerspectiveCamera symmetricPerspectiveCamera ) {
		synchronized( s_actualViewportBufferForReuse ) {
			edu.cmu.cs.dennisc.renderer.gl.imp.adapters.SymmetricPerspectiveCameraAdapter symmetricPerspectiveCameraAdapter = AdapterFactory.getAdapterFor( symmetricPerspectiveCamera );
			getActualViewport( s_actualViewportBufferForReuse, symmetricPerspectiveCameraAdapter );
			return symmetricPerspectiveCameraAdapter.getActualHorizontalViewingAngle( s_actualViewportBufferForReuse );
		}
	}

	@Override
	public edu.cmu.cs.dennisc.math.Angle getActualVerticalViewingAngle( edu.cmu.cs.dennisc.scenegraph.SymmetricPerspectiveCamera symmetricPerspectiveCamera ) {
		synchronized( s_actualViewportBufferForReuse ) {
			edu.cmu.cs.dennisc.renderer.gl.imp.adapters.SymmetricPerspectiveCameraAdapter symmetricPerspectiveCameraAdapter = AdapterFactory.getAdapterFor( symmetricPerspectiveCamera );
			getActualViewport( s_actualViewportBufferForReuse, symmetricPerspectiveCameraAdapter );
			return symmetricPerspectiveCameraAdapter.getActualVerticalViewingAngle( s_actualViewportBufferForReuse );
		}
	}

	private edu.cmu.cs.dennisc.math.Ray getRayAtPixel( edu.cmu.cs.dennisc.math.Ray rv, int xPixel, int yPixel, edu.cmu.cs.dennisc.scenegraph.AbstractCamera sgCamera ) {
		if( sgCamera != null ) {
			synchronized( s_actualViewportBufferForReuse ) {
				edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter = AdapterFactory.getAdapterFor( sgCamera );
				getActualViewport( s_actualViewportBufferForReuse, cameraAdapter );
				//			double halfWidth = s_actualViewportBufferForReuse.width / 2.0;
				//			double halfHeight = s_actualViewportBufferForReuse.height / 2.0;
				//			double xInPlane = (xPixel + 0.5 - halfWidth) / halfWidth;
				//			double yInPlane = -(yPixel + 0.5 - halfHeight) / halfHeight;
				cameraAdapter.getRayAtPixel( rv, xPixel, yPixel, s_actualViewportBufferForReuse );
			}
		} else {
			rv.setNaN();
		}
		//		java.awt.Rectangle viewport = getActualViewport( camera );
		//		double halfWidth = viewport.width / 2.0;
		//		double halfHeight = viewport.height / 2.0;
		//		double x = (xPixel + 0.5 - halfWidth) / halfWidth;
		//		double y = -(yPixel + 0.5 - halfHeight) / halfHeight;
		//
		//		edu.cmu.cs.dennisc.math.Matrix4d inverseProjection = getActualProjectionMatrix( camera );
		//		inverseProjection.invert();
		//
		//		edu.cmu.cs.dennisc.math.Point3d origin = new edu.cmu.cs.dennisc.math.Point3d(
		//				inverseProjection.backward.x / inverseProjection.backward.w,
		//				inverseProjection.backward.y / inverseProjection.backward.w,
		//				inverseProjection.backward.z / inverseProjection.backward.w
		//		);
		//
		//		edu.cmu.cs.dennisc.math.Vector4d qs = new edu.cmu.cs.dennisc.math.Vector4d( x, y, 0, 1 );
		//		edu.cmu.cs.dennisc.math.Vector4d qw = edu.cmu.cs.dennisc.math.LinearAlgebra.multiply( qs, inverseProjection );
		//
		//		edu.cmu.cs.dennisc.math.Vector3d direction = new edu.cmu.cs.dennisc.math.Vector3d(
		//				qw.x * inverseProjection.backward.w - qw.w * inverseProjection.backward.x,
		//				qw.y * inverseProjection.backward.w - qw.w * inverseProjection.backward.y,
		//				qw.z * inverseProjection.backward.w - qw.w * inverseProjection.backward.z
		//		);
		//		direction.normalize();
		//
		//		rv.setOrigin( origin );
		//		rv.setDirection( direction );
		//		return rv;
		return rv;
	}

	@Override
	public final edu.cmu.cs.dennisc.math.Ray getRayAtPixel( int xPixel, int yPixel, edu.cmu.cs.dennisc.scenegraph.AbstractCamera sgCamera ) {
		return getRayAtPixel( new edu.cmu.cs.dennisc.math.Ray(), xPixel, yPixel, sgCamera );
	}

	private edu.cmu.cs.dennisc.math.Ray getRayAtPixel( edu.cmu.cs.dennisc.math.Ray rv, int xPixel, int yPixel ) {
		return getRayAtPixel( rv, xPixel, yPixel, getCameraAtPixel( xPixel, yPixel ) );
	}

	@Override
	public final edu.cmu.cs.dennisc.math.Ray getRayAtPixel( int xPixel, int yPixel ) {
		return getRayAtPixel( new edu.cmu.cs.dennisc.math.Ray(), xPixel, yPixel );
	}

	@Override
	public boolean isLetterboxedAsOpposedToDistorted( edu.cmu.cs.dennisc.scenegraph.AbstractCamera sgCamera ) {
		edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter = AdapterFactory.getAdapterFor( sgCamera );
		return cameraAdapter.isLetterboxedAsOpposedToDistorted();
	}

	@Override
	public void setLetterboxedAsOpposedToDistorted( edu.cmu.cs.dennisc.scenegraph.AbstractCamera sgCamera, boolean isLetterboxedAsOpposedToDistorted ) {
		edu.cmu.cs.dennisc.renderer.gl.imp.adapters.AbstractCameraAdapter<? extends edu.cmu.cs.dennisc.scenegraph.AbstractCamera> cameraAdapter = AdapterFactory.getAdapterFor( sgCamera );
		cameraAdapter.setIsLetterboxedAsOpposedToDistorted( isLetterboxedAsOpposedToDistorted );
	}

	public double[] getActualPlane( double[] rv, java.awt.Dimension size, edu.cmu.cs.dennisc.scenegraph.OrthographicCamera orthographicCamera ) {
		throw new RuntimeException( "todo" );
		//		OrthographicCameraAdapter orthographicCameraAdapter = ElementAdapter.getAdapterFor( orthographicCamera );
		//		return orthographicCameraAdapter.getActualPlane( rv, size );
	}

	public double[] getActualPlane( double[] rv, java.awt.Dimension size, edu.cmu.cs.dennisc.scenegraph.FrustumPerspectiveCamera perspectiveCamera ) {
		throw new RuntimeException( "todo" );
		//		PerspectiveCameraAdapter perspectiveCameraAdapter = ElementAdapter.getAdapterFor( perspectiveCamera );
		//		return perspectiveCameraAdapter.getActualPlane( rv, size );
	}

	@Override
	public boolean isRenderingEnabled() {
		return m_isRenderingEnabled;
	}

	protected abstract void repaintIfAppropriate();

	@Override
	public void setRenderingEnabled( boolean isRenderingEnabled ) {
		if( m_isRenderingEnabled != isRenderingEnabled ) {
			m_isRenderingEnabled = isRenderingEnabled;
			this.repaintIfAppropriate();
			//			//todo
			//			if( m_isRenderingEnabled ) {
			//				if( m_glEventAdapter.isListening() ) {
			//					//pass
			//				} else {
			//					m_glEventAdapter.startListening( getGLAutoDrawable() );
			//				}
			//			} else {
			//				if( m_glEventAdapter.isListening() ) {
			//					m_glEventAdapter.stopListening( getGLAutoDrawable() );
			//				} else {
			//					//pass
			//				}
			//			}
		}
	}

	@Override
	public void forgetAllCachedItems() {
		if( m_glEventAdapter != null ) {
			m_glEventAdapter.forgetAllCachedItems();
		}
	}

	@Override
	public void clearUnusedTextures() {
		if( m_glEventAdapter != null ) {
			m_glEventAdapter.clearUnusedTextures();
		}
	}

	private final GlrRenderer glrRenderer;
	private final java.util.List<edu.cmu.cs.dennisc.scenegraph.AbstractCamera> sgCameras = edu.cmu.cs.dennisc.java.util.Lists.newCopyOnWriteArrayList();

	private GLEventAdapter m_glEventAdapter = new GLEventAdapter( this );

	private String m_description = new String();
	private java.util.Vector<edu.cmu.cs.dennisc.renderer.event.RenderTargetListener> m_lookingGlassListeners = new java.util.Vector<edu.cmu.cs.dennisc.renderer.event.RenderTargetListener>();

	protected abstract javax.media.opengl.GLAutoDrawable getGLAutoDrawable();

	private boolean m_isRenderingEnabled = true;

	private final SynchronousPicker synchronousPicker = new SynchronousPicker( this );
	private final SynchronousImageCapturer synchronousImageCapturer = new SynchronousImageCapturer( this );

	private final GlrAsynchronousPicker glrAsynchronousPicker = new GlrAsynchronousPicker( this );
	private final GlrAsynchronousImageCapturer glrAsynchronousImageCapturer = new GlrAsynchronousImageCapturer( this );

	//
	private static java.awt.Rectangle s_actualViewportBufferForReuse = new java.awt.Rectangle();
	private static java.awt.Dimension s_sizeBufferForReuse = new java.awt.Dimension();
}
