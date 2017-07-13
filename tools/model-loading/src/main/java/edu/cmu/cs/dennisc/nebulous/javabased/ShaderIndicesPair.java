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

package edu.cmu.cs.dennisc.nebulous.javabased;

import java.io.InputStream;
import java.io.OutputStream;

public class ShaderIndicesPair 
{
	private Shader shader;
	int[] indices;
	boolean isInNeedOfIndexAdjustment;
	
	public ShaderIndicesPair()
	{
		this.shader = null;
		this.indices = new int[0];
		this.isInNeedOfIndexAdjustment = true;
	}
	
	public void read(InputStream iStream, int nVersion)
	{
		Element element = Element.construct(iStream, nVersion);
		assert element instanceof Shader;
		this.shader = (Shader)element;
		
		try
		{
			if (nVersion < 3)
			{
				short[] shorts =  Utilities.readShortArray(iStream);
				this.indices = new int[shorts.length];
				for (int i=0; i<this.indices.length; i++)
				{
					this.indices[i] = shorts[i];
				}
			}
			else
			{
				this.indices = Utilities.readIntArray(iStream);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void write(OutputStream oStream)
	{
		try
		{
			this.shader.writeWithoutVersion(oStream);
			Utilities.writeIntArray(oStream, this.indices);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int[] getIndices()
	{
		return this.indices;
	}
	
	public int getIndexCount()
	{
		return this.indices.length;
	}
	
	public Shader getShader() 
	{
		return this.shader;
	}
	
	public void setShader( Shader shader ) 
	{
		this.shader = shader;
	}
	
	public boolean needsAdjustment()
	{
	    return this.isInNeedOfIndexAdjustment;
	}
	
//	public void addIndex(int index)
//    {
//	    if (index*3 > Short.MAX_VALUE)
//	    {
//	        throw new IllegalArgumentException("Exceeded max value of an adjusted index of "+Short.MAX_VALUE);
//	    }
//        this.addIndex((short)index);
//    }
	
	public void addIndex(int index)
	{
		assert this.isInNeedOfIndexAdjustment;
		this.indices = Utilities.addIntToArray(this.indices, index);
//		System.out.println("added "+index);
	}
	
	public int getTriangleCount()
	{
		return this.indices.length / 9;
	}
	
	public void adjustIndicesIfNecessary() 
	{
		if( this.isInNeedOfIndexAdjustment ) 
		{
		    //Adjust the indices to account for the fact that vertices are 3 floats, normals are 3 floats and UVs are 2 floats
			for( int i=0; i<this.indices.length; ) {
			    this.indices[i] = (this.indices[i]*2);
				i++;
				this.indices[i] = (this.indices[i]*3);
				i++;
				this.indices[i] = (this.indices[i]*3);
				i++;
			}
			this.isInNeedOfIndexAdjustment = false;
		}
	}
	
	public int getTextureCoordinateIndex( int triangleIndex, int vertexIndex )
	{
		int nRV = this.indices[ triangleIndex*9 + vertexIndex*3 ];
		if( this.isInNeedOfIndexAdjustment ) {
			//pass
		} else {
			nRV /= 2;
		}
		return nRV;
	}
	
	public int getNormalIndex( int triangleIndex, int vertexIndex )
	{
		int nRV = this.indices[ triangleIndex*9 + vertexIndex*3 + 1 ];
		if( this.isInNeedOfIndexAdjustment ) {
			//pass
		} else {
			nRV /= 3;
		}
		return nRV;
	}
	
	public int getVertexIndex( int triangleIndex, int vertexIndex )
	{
		int nRV = this.indices[ triangleIndex*9 + vertexIndex*3 + 2 ];
		if( this.isInNeedOfIndexAdjustment ) {
			//pass
		} else {
			nRV /= 3;
		}
		return nRV;
	}
	
	
	
}
