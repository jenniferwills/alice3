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

package edu.cmu.cs.dennisc.alice.virtualmachine;

import edu.cmu.cs.dennisc.alice.ast.*;

/**
 * @author Dennis Cosgrove
 */
public class InstanceInAlice {
	public static InstanceInAlice createInstance( VirtualMachine vm, ConstructorDeclaredInAlice constructor, Object[] arguments, boolean isBodyExcutionDesired ) {
		return new InstanceInAlice( vm, constructor, arguments, isBodyExcutionDesired );
	}
	
	private final Object nextInstance;
	private final AbstractTypeDeclaredInAlice<?> type;
	private final java.util.Map< FieldDeclaredInAlice, Object > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public InstanceInAlice( VirtualMachine vm, ConstructorDeclaredInAlice constructor, Object[] arguments, boolean isBodyExcutionDesired ) {
		ConstructorBlockStatement constructorBlockStatement = constructor.body.getValue();
		ConstructorInvocationStatement constructorInvocationStatement = constructorBlockStatement.constructorInvocationStatement.getValue();
		this.type = constructor.getDeclaringType();
		AbstractConstructor nextConstructor = constructorInvocationStatement.contructor.getValue();
		Object[] nextArguments = vm.evaluateArguments( nextConstructor.getParameters(), constructorInvocationStatement.arguments );
		if( nextConstructor.isDeclaredInAlice() ) {
			
			java.util.Map<edu.cmu.cs.dennisc.alice.ast.AbstractParameter,Object> map = new java.util.HashMap< edu.cmu.cs.dennisc.alice.ast.AbstractParameter, Object >();
			for( int i=0; i<arguments.length; i++ ) {
				map.put( constructor.parameters.get( i ), arguments[ i ] );
			}
			edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice type = (edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice)constructor.getDeclaringType();
			vm.pushConstructorFrame( type, map );
			try {
				this.nextInstance = new InstanceInAlice( vm, (ConstructorDeclaredInAlice)nextConstructor, nextArguments, isBodyExcutionDesired );
				vm.setConstructorFrameInstanceInAlice( this );
				for( AbstractField field : this.type.getDeclaredFields() ) {
					assert field instanceof FieldDeclaredInAlice;
					FieldDeclaredInAlice fieldDeclaredInAlice = (FieldDeclaredInAlice)field;
					set( fieldDeclaredInAlice, vm.evaluate( fieldDeclaredInAlice.initializer.getValue() ) );
				}
				if( isBodyExcutionDesired ) {
					try {
						vm.executeBlockStatement( constructorBlockStatement );
					} catch( ReturnException re ) {
						throw new RuntimeException( re );
					}
				}
			} finally {
				vm.popFrame();
			}
		} else {
			ConstructorDeclaredInJava nextConstructorDeclaredInJava = (ConstructorDeclaredInJava)nextConstructor;
			ConstructorReflectionProxy constructorReflectionProxy =  nextConstructorDeclaredInJava.getConstructorReflectionProxy();
			java.lang.reflect.Constructor<?> cnstrctr = constructorReflectionProxy.getReification();
			assert cnstrctr != null : constructorReflectionProxy.getDeclaringClassReflectionProxy().getName();
			this.nextInstance = edu.cmu.cs.dennisc.java.lang.reflect.ReflectionUtilities.newInstance( cnstrctr, nextArguments );
		}
	}
//	public void initialize( VirtualMachine vm, ConstructorDeclaredInAlice constructor, Object[] arguments ) {
//		this.type = constructor.getDeclaringType();
//		this.map = new java.util.HashMap< FieldDeclaredInAlice, Object >();
//		assert this.type != null;
//		assert this.type instanceof TypeDeclaredInAlice;
//
//		AbstractType<?,?,?> t = this.type;
//		while( t instanceof TypeDeclaredInAlice ) {
//			for( AbstractField field : t.getDeclaredFields() ) {
//				assert field instanceof FieldDeclaredInAlice;
//				FieldDeclaredInAlice fieldDeclaredInAlice = (FieldDeclaredInAlice)field;
//				set( fieldDeclaredInAlice, vm.evaluate( fieldDeclaredInAlice.initializer.getValue() ) );
//			}
//			t = t.getSuperType();
//		}
//		assert t instanceof TypeDeclaredInJava;
//		TypeDeclaredInJava typeDeclaredInJava = (TypeDeclaredInJava)t;
//
//		
////		ConstructorBlockStatement constructorBlockStatement = constructor.body.getValue();
////		ConstructorInvocationStatement invocationStatement = constructorBlockStatement.constructorInvocationStatement.getValue();
////		if( invocationStatement != null ) {
////			Object[] arguments2 = vm.evaluateArguments( null, invocationStatement.arguments );
////		}
//
//		ClassReflectionProxy classReflectionProxy = typeDeclaredInJava.getClassReflectionProxy();
//		assert classReflectionProxy != null;
//		Class<?> cls = classReflectionProxy.getReification();
//		assert cls != null : classReflectionProxy.getName();
//		this.instanceInJava = edu.cmu.cs.dennisc.java.lang.reflect.ReflectionUtilities.newInstance( cls );
//	}
	public AbstractTypeDeclaredInAlice<?> getType() {
		return this.type;
	}
	public Object getInstanceInJava() {
		if( this.nextInstance instanceof InstanceInAlice ) {
			return ((InstanceInAlice)this.nextInstance).nextInstance;
		} else {
			return this.nextInstance;
		}
	}
	public <E> E getInstanceInJava( Class<E> cls ) {
		return edu.cmu.cs.dennisc.java.lang.ClassUtilities.getInstance( this.nextInstance, cls );
	}
	public Object get( FieldDeclaredInAlice field ) {
		return this.map.get( field );
	}
	public void set( FieldDeclaredInAlice field, Object value ) {
		this.map.put( field, value );
	}

	@Override
	public String toString() {
		if( this.nextInstance != null ) {
			return this.nextInstance.toString();
		} else {
			return null;
		}
	}
}
