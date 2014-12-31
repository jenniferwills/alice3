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
package org.lgna.debug.sg.croquet;

/**
 * @author Dennis Cosgrove
 */
public class DebugSgFrame extends org.lgna.croquet.FrameComposite<org.lgna.debug.sg.croquet.views.DebugSgFrameView> {
	public DebugSgFrame() {
		super( java.util.UUID.fromString( "8d282704-d6b6-4455-bd1d-80b6a529a19d" ) );
		this.refreshOperation.setName( "refresh" );
	}

	public org.lgna.croquet.Operation getRefreshOperation() {
		return this.refreshOperation;
	}

	public javax.swing.tree.TreeModel getTreeModel() {
		return this.treeModel.get();
	}

	@Override
	protected org.lgna.debug.sg.croquet.views.DebugSgFrameView createView() {
		return new org.lgna.debug.sg.croquet.views.DebugSgFrameView( this );
	}

	private final org.lgna.croquet.Operation refreshOperation = this.createActionOperation( "refreshOperation", new Action() {
		@Override
		public org.lgna.croquet.edits.Edit perform( org.lgna.croquet.history.CompletionStep<?> step, org.lgna.croquet.AbstractComposite.InternalActionOperation source ) throws org.lgna.croquet.CancelException {
			getView().expandAllRows();
			return null;
		}
	} );

	private final edu.cmu.cs.dennisc.pattern.Lazy<javax.swing.tree.TreeModel> treeModel = new edu.cmu.cs.dennisc.pattern.Lazy<javax.swing.tree.TreeModel>() {
		@Override
		protected javax.swing.tree.TreeModel create() {
			org.lgna.project.virtualmachine.UserInstance sceneUserInstance = org.alice.stageide.sceneeditor.StorytellingSceneEditor.getInstance().getActiveSceneInstance();
			org.lgna.story.SScene scene = sceneUserInstance.getJavaInstance( org.lgna.story.SScene.class );
			org.lgna.story.implementation.SceneImp sceneImp = org.lgna.story.EmployeesOnly.getImplementation( scene );
			final edu.cmu.cs.dennisc.scenegraph.Scene sgScene = sceneImp.getSgComposite();
			return new edu.cmu.cs.dennisc.javax.swing.models.AbstractTreeModel<edu.cmu.cs.dennisc.scenegraph.Component>() {
				@Override
				public edu.cmu.cs.dennisc.scenegraph.Component getRoot() {
					return sgScene;
				}

				@Override
				public edu.cmu.cs.dennisc.scenegraph.Component getChild( Object parent, int index ) {
					return ( (edu.cmu.cs.dennisc.scenegraph.Composite)parent ).getComponentAt( index );
				}

				@Override
				public int getChildCount( Object parent ) {
					return ( (edu.cmu.cs.dennisc.scenegraph.Composite)parent ).getComponentCount();
				}

				@Override
				public int getIndexOfChild( Object parent, Object child ) {
					return ( (edu.cmu.cs.dennisc.scenegraph.Composite)parent ).getIndexOfComponent( (edu.cmu.cs.dennisc.scenegraph.Component)child );
				}

				@Override
				public boolean isLeaf( Object parent ) {
					return ( parent instanceof edu.cmu.cs.dennisc.scenegraph.Composite ) == false;
				}

				@Override
				public javax.swing.tree.TreePath getTreePath( edu.cmu.cs.dennisc.scenegraph.Component e ) {
					return null;
				}
			};
		}
	};
}
