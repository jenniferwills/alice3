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
package org.alice.ide.gallerybrowser;

class FolderOperation extends edu.cmu.cs.dennisc.croquet.ActionOperation {
	private static java.util.Map<edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode, FolderOperation> map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public static FolderOperation getInstance( edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode node ) {
		FolderOperation rv = map.get( node );
		if( rv != null ) {
			//pass
		} else {
			rv = new FolderOperation( node );
			map.put(node, rv);
		}
		return rv;
	}
	private edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode node;
	private FolderOperation( edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode node ) {
		super( org.alice.ide.IDE.IDE_GROUP, java.util.UUID.fromString( "7ed82e57-4804-4aa5-b5e1-8274fc6ddfd1" ) );
		this.node = node;
	}
	
	@Override
	protected void perform(edu.cmu.cs.dennisc.croquet.ActionOperationContext context) {
	}
}

/**
* @author Dennis Cosgrove
*/
public abstract class ThumbnailsPane extends edu.cmu.cs.dennisc.croquet.LineAxisPanel {
	class ThumbnailSnapshotListCellRenderer extends org.alice.ide.swing.SnapshotListCellRenderer {
		private javax.swing.Icon folderIcon;

		public void setFolderIcon( javax.swing.Icon folderIcon ) {
			this.folderIcon = folderIcon;
		}
		
		private java.net.URL getIconResource( javax.swing.tree.TreeNode treeNode ) {
			if (treeNode instanceof edu.cmu.cs.dennisc.zip.ZipTreeNode) {
				edu.cmu.cs.dennisc.zip.ZipTreeNode zipTreeNode = (edu.cmu.cs.dennisc.zip.ZipTreeNode) treeNode;
				if (zipTreeNode instanceof edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode) {
					edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode directoryZipTreeNode = (edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode) zipTreeNode;
					zipTreeNode = directoryZipTreeNode.getChildNamed( "directoryThumbnail.png" );
				}
				if( zipTreeNode != null ) {
					return org.alice.stageide.gallerybrowser.ResourceManager.getLargeIconResource(zipTreeNode);
				}
			}
			return null;
		}
		@Override
		protected javax.swing.JLabel updateLabel( javax.swing.JLabel rv, Object value ) {
			javax.swing.tree.TreeNode file = (javax.swing.tree.TreeNode)value;
			if( file != null ) {
				String text = ThumbnailsPane.this.getTextFor( file );
				java.net.URL iconResource = this.getIconResource( file );
				rv.setText( text );
				javax.swing.Icon icon;
				if( iconResource != null ) {
					icon = new javax.swing.ImageIcon( iconResource );
				} else {
					icon = null;
				}
				if( file.isLeaf() ) {
					//pass
				} else {
					if( this.folderIcon != null ) {
						if( icon != null ) {
							icon = new edu.cmu.cs.dennisc.javax.swing.icons.CompositeIcon( this.folderIcon, icon );
						} else {
							icon = this.folderIcon;
						}
					}
				}
				rv.setIcon( icon );
			}
			return rv;
		}
	}
	
	private edu.cmu.cs.dennisc.croquet.ListSelectionState<javax.swing.tree.TreeNode> itemSelection = new edu.cmu.cs.dennisc.croquet.ListSelectionState<javax.swing.tree.TreeNode>( org.alice.ide.IDE.IDE_GROUP, java.util.UUID.fromString( "1814e4cc-1463-4191-bd85-72b61893d1e5" ) ) {
		@Override
		protected void encodeValue(edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder, javax.swing.tree.TreeNode value) {
			throw new RuntimeException("todo");
		}
		@Override
		protected javax.swing.tree.TreeNode decodeValue(edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder) {
			throw new RuntimeException("todo");
		}
	};
	private ThumbnailSnapshotListCellRenderer thumbnailSnapshotListCellRenderer = new ThumbnailSnapshotListCellRenderer();

	public ThumbnailsPane() {
		final edu.cmu.cs.dennisc.croquet.List<javax.swing.tree.TreeNode> list = itemSelection.createList();
		list.setCellRenderer( this.thumbnailSnapshotListCellRenderer );
		list.setLayoutOrientation( edu.cmu.cs.dennisc.croquet.List.LayoutOrientation.HORIZONTAL_WRAP );
		list.setVisibleRowCount( 1 );
		itemSelection.addValueObserver( new edu.cmu.cs.dennisc.croquet.ListSelectionState.ValueObserver<javax.swing.tree.TreeNode>() {
			public void changed(final javax.swing.tree.TreeNode nextValue) {
				if( nextValue != null ) {
//					itemSelection.setValue( null );
					ThumbnailsPane.this.handleFileActivation( nextValue );
//					ThumbnailsPane.this.handleFileActivation( nextValue );
//					javax.swing.SwingUtilities.invokeLater( new Runnable() {
//						public void run() {
//							list.revalidateAndRepaint();
//						}
//					} );
				}
			}
		} );
		list.addKeyListener( new java.awt.event.KeyListener() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				if( e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE ) {
					ThumbnailsPane.this.handleBackSpaceKey();
				}
			}
			public void keyReleased(java.awt.event.KeyEvent e) {
			}
			public void keyTyped(java.awt.event.KeyEvent e) {
			}
		} );
		
		this.addComponent( new edu.cmu.cs.dennisc.croquet.ScrollPane( list ) );
	}

	protected abstract String getTextFor( javax.swing.tree.TreeNode file );
	protected abstract void handleFileActivation( javax.swing.tree.TreeNode file );
	protected abstract void handleBackSpaceKey();
	public void setFolderIcon( javax.swing.Icon folderIcon ) {
		this.thumbnailSnapshotListCellRenderer.setFolderIcon( folderIcon );
	}
	
//	/*package private*/ static java.io.File[] listPackages( java.io.File directory ) {
//		return edu.cmu.cs.dennisc.java.io.FileUtilities.listDirectories( directory );
//	}
//	/*package private*/ static java.io.File[] listClasses( java.io.File directory ) {
//		return edu.cmu.cs.dennisc.java.io.FileUtilities.listFiles( directory, new java.io.FileFilter() {
//			public boolean accept( java.io.File file ) {
//				String lcFilename = file.getName().toLowerCase();
//				return file.isFile() && lcFilename.equals( "directorythumbnail.png" ) == false;
//			}
//		} );
//	}
	
	/*package private*/ static javax.swing.tree.TreeNode[] getSortedChildren( javax.swing.tree.TreeNode directory ) {
		if( directory != null ) {
			java.util.ArrayList< edu.cmu.cs.dennisc.zip.ZipTreeNode > list = edu.cmu.cs.dennisc.java.util.Collections.newArrayList();
			list.ensureCapacity( directory.getChildCount() );

			java.util.Enumeration< javax.swing.tree.TreeNode > e = directory.children();
			while( e.hasMoreElements() ) {
				javax.swing.tree.TreeNode treeNode = e.nextElement();
				if (treeNode instanceof edu.cmu.cs.dennisc.zip.ZipTreeNode) {
					edu.cmu.cs.dennisc.zip.ZipTreeNode zipTreeNode = (edu.cmu.cs.dennisc.zip.ZipTreeNode)treeNode;
					if( "directoryThumbnail.png".equals( zipTreeNode.getName() ) ) {
						//pass
					} else {
						list.add( zipTreeNode );
					}
				}
			}
			edu.cmu.cs.dennisc.zip.ZipTreeNode[] array = new edu.cmu.cs.dennisc.zip.ZipTreeNode[ list.size() ];
			list.toArray( array );
			java.util.Arrays.sort( array );
			return array;
		} else {
			return new edu.cmu.cs.dennisc.zip.ZipTreeNode[ 0 ];
		}
	}
	
	public void setDirectory( javax.swing.tree.TreeNode directory ) {
		this.itemSelection.setListData( -1, getSortedChildren( directory ) );
	}
}

//import org.alice.stageide.gallerybrowser.ResourceManager;
//class SingleOrDoubleClickListUI extends javax.swing.plaf.basic.BasicListUI {
//	@Override
//	protected javax.swing.event.MouseInputListener createMouseInputListener() {
//		return new javax.swing.event.MouseInputListener() {
//			private long tPrevious = System.currentTimeMillis();
//			public void mouseClicked( java.awt.event.MouseEvent e ) {
//			}
//			public void mouseEntered( java.awt.event.MouseEvent e ) {
//			}
//			public void mouseExited( java.awt.event.MouseEvent e ) {
//			}
//			public void mousePressed( java.awt.event.MouseEvent e ) {
//				long tCurrent = e.getWhen();
//				long tDelta = tCurrent - tPrevious;
//				if( tDelta > 400 ) {
//					int row = SingleOrDoubleClickListUI.this.locationToIndex( list, e.getPoint() );
//	                list.setValueIsAdjusting( true );
//	                list.setSelectionInterval(row, row);
//					tPrevious = tCurrent;
//				}
//			}
//			public void mouseReleased( java.awt.event.MouseEvent e ) {
//                list.setValueIsAdjusting( false );
//			}
//			public void mouseMoved( java.awt.event.MouseEvent e ) {
//			}
//			public void mouseDragged( java.awt.event.MouseEvent e ) {
//			}
//		};
//	}
//}
//
//class SingleOrDoubleClickList<E> extends edu.cmu.cs.dennisc.croquet.List<E> {
//	@Override
//	protected javax.swing.JList createAwtComponent() {
//		return new javax.swing.JList() {
//			@Override
//			public void updateUI() {
//				this.setUI( new SingleOrDoubleClickListUI() );
//			}
//		};
//	}
//}
///**
// * @author Dennis Cosgrove
// */
//public abstract class ThumbnailsPane extends edu.cmu.cs.dennisc.croquet.LineAxisPanel {
//	class ThumbnailSnapshotListCellRenderer extends org.alice.ide.swing.SnapshotListCellRenderer {
//		private javax.swing.Icon folderIcon;
//
//		public void setFolderIcon( javax.swing.Icon folderIcon ) {
//			this.folderIcon = folderIcon;
//		}
//		
//		private java.net.URL getIconResource( javax.swing.tree.TreeNode treeNode ) {
//			if (treeNode instanceof edu.cmu.cs.dennisc.zip.ZipTreeNode) {
//				edu.cmu.cs.dennisc.zip.ZipTreeNode zipTreeNode = (edu.cmu.cs.dennisc.zip.ZipTreeNode) treeNode;
//				if (zipTreeNode instanceof edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode) {
//					edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode directoryZipTreeNode = (edu.cmu.cs.dennisc.zip.DirectoryZipTreeNode) zipTreeNode;
//					zipTreeNode = directoryZipTreeNode.getChildNamed( "directoryThumbnail.png" );
//				}
//				if( zipTreeNode != null ) {
//					return ResourceManager.getLargeIconResource(zipTreeNode);
//				}
//			}
//			return null;
//		}
//		@Override
//		protected javax.swing.JLabel updateLabel( javax.swing.JLabel rv, Object value ) {
//			javax.swing.tree.TreeNode file = (javax.swing.tree.TreeNode)value;
//			if( file != null ) {
//				String text = ThumbnailsPane.this.getTextFor( file );
//				java.net.URL iconResource = this.getIconResource( file );
//				rv.setText( text );
//				javax.swing.Icon icon;
//				if( iconResource != null ) {
//					icon = new javax.swing.ImageIcon( iconResource );
//				} else {
//					icon = null;
//				}
//				if( file.isLeaf() ) {
//					//pass
//				} else {
//					if( this.folderIcon != null ) {
//						if( icon != null ) {
//							icon = new edu.cmu.cs.dennisc.javax.swing.icons.CompositeIcon( this.folderIcon, icon );
//						} else {
//							icon = this.folderIcon;
//						}
//					}
//				}
//				rv.setIcon( icon );
//			}
//			return rv;
//		}
//	}
//	
//	private edu.cmu.cs.dennisc.croquet.ListSelectionState<javax.swing.tree.TreeNode> itemSelection = new edu.cmu.cs.dennisc.croquet.ListSelectionState<javax.swing.tree.TreeNode>( org.alice.ide.IDE.IDE_GROUP, java.util.UUID.fromString( "1814e4cc-1463-4191-bd85-72b61893d1e5" ) ) {
//		@Override
//		protected void encodeValue(edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder, javax.swing.tree.TreeNode value) {
//			throw new RuntimeException("todo");
//		}
//		@Override
//		protected javax.swing.tree.TreeNode decodeValue(edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder) {
//			throw new RuntimeException("todo");
//		}
//	};
//	private ThumbnailSnapshotListCellRenderer thumbnailSnapshotListCellRenderer = new ThumbnailSnapshotListCellRenderer();
//
//	public ThumbnailsPane() {
//		final edu.cmu.cs.dennisc.croquet.List<javax.swing.tree.TreeNode> list = itemSelection.createList();
//		list.setCellRenderer( this.thumbnailSnapshotListCellRenderer );
//		list.setLayoutOrientation( edu.cmu.cs.dennisc.croquet.List.LayoutOrientation.HORIZONTAL_WRAP );
//		list.setVisibleRowCount( 1 );
//		itemSelection.addValueObserver( new edu.cmu.cs.dennisc.croquet.ListSelectionState.ValueObserver<javax.swing.tree.TreeNode>() {
//			public void changed(final javax.swing.tree.TreeNode nextValue) {
//				if( nextValue != null ) {
////					itemSelection.setValue( null );
//					ThumbnailsPane.this.handleFileActivation( nextValue );
////					ThumbnailsPane.this.handleFileActivation( nextValue );
////					javax.swing.SwingUtilities.invokeLater( new Runnable() {
////						public void run() {
////							list.revalidateAndRepaint();
////						}
////					} );
//				}
//			}
//		} );
//		list.addKeyListener( new java.awt.event.KeyListener() {
//			public void keyPressed(java.awt.event.KeyEvent e) {
//				if( e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE ) {
//					ThumbnailsPane.this.handleBackSpaceKey();
//				}
//			}
//			public void keyReleased(java.awt.event.KeyEvent e) {
//			}
//			public void keyTyped(java.awt.event.KeyEvent e) {
//			}
//		} );
//		
//		this.addComponent( new edu.cmu.cs.dennisc.croquet.ScrollPane( list ) );
//	}
//
//	protected abstract String getTextFor( javax.swing.tree.TreeNode file );
//	protected abstract void handleFileActivation( javax.swing.tree.TreeNode file );
//	protected abstract void handleBackSpaceKey();
//	public void setFolderIcon( javax.swing.Icon folderIcon ) {
//		this.thumbnailSnapshotListCellRenderer.setFolderIcon( folderIcon );
//	}
//	
////	/*package private*/ static java.io.File[] listPackages( java.io.File directory ) {
////		return edu.cmu.cs.dennisc.java.io.FileUtilities.listDirectories( directory );
////	}
////	/*package private*/ static java.io.File[] listClasses( java.io.File directory ) {
////		return edu.cmu.cs.dennisc.java.io.FileUtilities.listFiles( directory, new java.io.FileFilter() {
////			public boolean accept( java.io.File file ) {
////				String lcFilename = file.getName().toLowerCase();
////				return file.isFile() && lcFilename.equals( "directorythumbnail.png" ) == false;
////			}
////		} );
////	}
//	
//	/*package private*/ static javax.swing.tree.TreeNode[] getSortedChildren( javax.swing.tree.TreeNode directory ) {
//		if( directory != null ) {
//			java.util.ArrayList< edu.cmu.cs.dennisc.zip.ZipTreeNode > list = edu.cmu.cs.dennisc.java.util.Collections.newArrayList();
//			list.ensureCapacity( directory.getChildCount() );
//
//			java.util.Enumeration< javax.swing.tree.TreeNode > e = directory.children();
//			while( e.hasMoreElements() ) {
//				javax.swing.tree.TreeNode treeNode = e.nextElement();
//				if (treeNode instanceof edu.cmu.cs.dennisc.zip.ZipTreeNode) {
//					edu.cmu.cs.dennisc.zip.ZipTreeNode zipTreeNode = (edu.cmu.cs.dennisc.zip.ZipTreeNode)treeNode;
//					if( "directoryThumbnail.png".equals( zipTreeNode.getName() ) ) {
//						//pass
//					} else {
//						list.add( zipTreeNode );
//					}
//				}
//			}
//			edu.cmu.cs.dennisc.zip.ZipTreeNode[] array = new edu.cmu.cs.dennisc.zip.ZipTreeNode[ list.size() ];
//			list.toArray( array );
//			java.util.Arrays.sort( array );
//			return array;
//		} else {
//			return new edu.cmu.cs.dennisc.zip.ZipTreeNode[ 0 ];
//		}
//	}
//	
//	public void setDirectory( javax.swing.tree.TreeNode directory ) {
//		this.itemSelection.setListData( -1, getSortedChildren( directory ) );
//	}
//}
