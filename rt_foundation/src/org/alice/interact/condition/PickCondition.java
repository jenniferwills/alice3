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
package org.alice.interact.condition;

import org.alice.interact.InputState;
import org.alice.interact.PickHint;


/**
 * @author David Culyba
 */
public class PickCondition {
	
	
	
	protected boolean isNot = false;
	protected PickHint pickHint; 
	protected PickCondition nextCondition = null;
	
	public PickCondition( PickHint pickHint )
	{
		this.pickHint = pickHint;
	}
	
	public PickCondition( PickHint pickHint, PickCondition previousCondition )
	{
		this( pickHint );
		previousCondition.setNextCondition( this );
	}
	
	public void setNextCondition( PickCondition nextCondition )
	{
		this.nextCondition = nextCondition;
	}
	
	public static PickHint getPickType( edu.cmu.cs.dennisc.lookingglass.PickResult pickObject )
	{
		boolean isNull = pickObject == null || pickObject.getGeometry() == null || pickObject.getVisual() == null;
		if (isNull)
		{
			return PickHint.NOTHING;
		}
		else
		{
			return getPickType( pickObject.getVisual() );
		}
	}
	
	public static PickHint getPickType( edu.cmu.cs.dennisc.scenegraph.Component pickedObject )
	{
		if (pickedObject == null)
		{
			return PickHint.NOTHING;
		}
		else
		{
			Object bonusData = pickedObject.getBonusDataFor( PickHint.PICK_HINT_KEY );
			if (bonusData instanceof PickHint)
			{
				return (PickHint)bonusData;
			}
			else
			{
				return getPickType(pickedObject.getParent());
			}
		}
	}
	
	public boolean evaluateObject( InputState input )
	{
		boolean result = false;
		if (input.getClickHandle() != null)
		{
			if (input.getClickHandle().isPickable())
			{
				result = this.pickHint.intersects( input.getClickHandle().getPickHint() );
			}
			else
			{
				result = false;
			}
		}
		else
		{
			result = this.pickHint.intersects( getPickType( input.getClickPickResult() ) );
		}
		if (isNot)
		{
			result = !result;
		}
		return result;
	}
	
	public boolean evaluateObject(edu.cmu.cs.dennisc.lookingglass.PickResult pickObject)
	{
		boolean result = this.pickHint.intersects( getPickType( pickObject ) );
		if (isNot)
		{
			result = !result;
		}
		return result;
	}
	
	public boolean evalutateChain(edu.cmu.cs.dennisc.lookingglass.PickResult pickObject)
	{
		if ( this.nextCondition == null )
		{
			return this.evaluateObject( pickObject );
		}
		else
		{
			return this.evaluateObject( pickObject ) && this.nextCondition.evalutateChain( pickObject ) ;
		}
	}
	
	public boolean evalutateChain(InputState input)
	{
		if ( this.nextCondition == null )
		{
			return this.evaluateObject( input );
		}
		else
		{
			return this.evaluateObject( input ) && this.nextCondition.evalutateChain( input ) ;
		}
	}
	
}
