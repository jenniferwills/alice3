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

package edu.cmu.cs.dennisc.codec;

/**
 * @author Dennis Cosgrove
 */
public class BufferUtilities {
	private BufferUtilities() {
	}
	
	private static class Header {
		private final int limit;
		private final int bitsPerPrimitive;
		private final boolean isDirect;
		private final boolean isReadOnly;
		private final boolean isNativeRequired;
		public Header( java.nio.Buffer buffer, int bitsPerPrimitive, boolean isDirect, boolean isNativeRequired ) {
			this.limit = buffer.limit();
			this.bitsPerPrimitive = bitsPerPrimitive;
			this.isReadOnly = buffer.isReadOnly();
			this.isDirect = isDirect;
			this.isNativeRequired = isNativeRequired;
		}
		public Header( BinaryDecoder decoder ) {
			this.limit = decoder.decodeInt();
			this.bitsPerPrimitive = decoder.decodeInt();
			this.isReadOnly = decoder.decodeBoolean();
			this.isDirect = decoder.decodeBoolean();
			this.isNativeRequired = decoder.decodeBoolean();
		}
		public void encode( BinaryEncoder encoder ) {
			encoder.encode( this.limit );
			encoder.encode( this.bitsPerPrimitive );
			encoder.encode( this.isReadOnly );
			encoder.encode( this.isDirect );
			encoder.encode( this.isNativeRequired );
		}
		public java.nio.ByteBuffer createByteBuffer() {
			java.nio.ByteBuffer rv;
			final int N = this.limit * (this.bitsPerPrimitive/8);
			if( this.isDirect ) {
				rv = java.nio.ByteBuffer.allocateDirect( N );
			} else {
				rv = java.nio.ByteBuffer.allocate( N );
			}
			if( this.isNativeOrderChangeNecessary( rv ) ) {
				rv.order( java.nio.ByteOrder.nativeOrder() );
			}
			return rv;
		}
		public boolean isNativeOrderChangeNecessary( java.nio.ByteBuffer buffer ) {
			return this.isNativeRequired && buffer.order().equals( java.nio.ByteOrder.nativeOrder() ) == false;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append( "limit=" );
			sb.append( this.limit );
			sb.append( ";bitsPerPrimitive=" );
			sb.append( this.bitsPerPrimitive );
			return sb.toString();
		}
	}

	private static void encodeHeader( BinaryEncoder encoder, java.nio.Buffer buffer, int bitsPerPrimitive, boolean isDirect, boolean isNativeRequired ) {
		Header header = new Header( buffer, bitsPerPrimitive, isDirect, isNativeRequired );
		header.encode( encoder );
	}
	public static void encode( BinaryEncoder encoder, java.nio.IntBuffer buffer, boolean isNativeRequired ) {
		encodeHeader( encoder, buffer, Integer.SIZE, buffer.isDirect(), isNativeRequired );
		while( buffer.hasRemaining() ) {
			encoder.encode( buffer.get() );
		}
		encoder.flush();
	}
	public static void encode( BinaryEncoder encoder, java.nio.FloatBuffer buffer, boolean isNativeRequired ) {
		encodeHeader( encoder, buffer, Float.SIZE, buffer.isDirect(), isNativeRequired );
		while( buffer.hasRemaining() ) {
			encoder.encode( buffer.get() );
		}
		encoder.flush();
	}
	public static void encode( BinaryEncoder encoder, java.nio.DoubleBuffer buffer, boolean isNativeRequired ) {
		encodeHeader( encoder, buffer, Double.SIZE, buffer.isDirect(), isNativeRequired );
		while( buffer.hasRemaining() ) {
			encoder.encode( buffer.get() );
		}
		encoder.flush();
	}
	
	public static java.nio.IntBuffer decodeIntBuffer( BinaryDecoder decoder, boolean isNativeRequired ) {
		Header header = new Header( decoder );
		java.nio.ByteBuffer byteBuffer = header.createByteBuffer();
		java.nio.IntBuffer rv = byteBuffer.asIntBuffer();
		while( rv.hasRemaining() ) {
			rv.put( decoder.decodeInt() );
		}
		rv.rewind();
		if( header.isReadOnly ) {
			rv = rv.asReadOnlyBuffer();
		}
		return rv;
	}
	public static java.nio.DoubleBuffer decodeDoubleBuffer( BinaryDecoder decoder, boolean isNativeRequired ) {
		Header header = new Header( decoder );
		java.nio.ByteBuffer byteBuffer = header.createByteBuffer();
		java.nio.DoubleBuffer rv = byteBuffer.asDoubleBuffer();
		while( rv.hasRemaining() ) {
			rv.put( decoder.decodeDouble() );
		}
		rv.rewind();
		if( header.isReadOnly ) {
			rv = rv.asReadOnlyBuffer();
		}
		return rv;
	}
	public static java.nio.FloatBuffer decodeFloatBuffer( BinaryDecoder decoder, boolean isNativeRequired ) {
		Header header = new Header( decoder );
		java.nio.ByteBuffer byteBuffer = header.createByteBuffer();
		java.nio.FloatBuffer rv = byteBuffer.asFloatBuffer();
		while( rv.hasRemaining() ) {
			rv.put( decoder.decodeFloat() );
		}
		rv.rewind();
		if( header.isReadOnly ) {
			rv = rv.asReadOnlyBuffer();
		}
		return rv;
	}
}
