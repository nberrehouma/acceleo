<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
%>

<%script type="GenPackage" name="fileName"%>
src-gen/<%testPackage.replaceAll("\.", "/")%>/unit/Abstract<%prefix.toU1Case()%>Test.java

<%script type="GenPackage" name="abstractTestClass" file="<%fileName%>"%>
package <%testPackage()%>.unit;

import java.util.Calendar;

import junit.framework.TestCase;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class defines utility methods and will be used as the superclass of all {@link TestCase} for
 * "class-related" tests.
 *
 * @generated
 */
@SuppressWarnings("nls")
public class Abstract<%prefix.toU1Case()%>Test extends TestCase {
	/**
	 * Will hold <code>true</code> if the mock adapter has been notified of a changes.
	 *
	 * @generated
	 */
	protected boolean notified;
	
	/**
	 * This will return a boolean that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>true</code> if the default is <code>false</code>, <code>false</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>true</code> if the default is <code>false</code>, <code>false</code> otherwise.
	 * @generated
	 */
	protected boolean getBooleanDistinctFromDefault(EStructuralFeature feature) {
		return !((Boolean)feature.getDefaultValue()).booleanValue();
	}

	/**
	 * This will return an int that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>-1</code> if the default is <code>0</code>, <code>0</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>-1</code> if the default is <code>0</code>, <code>0</code> otherwise.
	 * @generated
	 */
	protected int getIntDistinctFromDefault(EStructuralFeature feature) {
		final int defaultValue = ((Integer)feature.getDefaultValue()).intValue();
		return defaultValue == 0 ? -1 : 0;
	}

	/**
	 * This will return a byte that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>-1</code> if the default is <code>0</code>, <code>0</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>-1</code> if the default is <code>0</code>, <code>0</code> otherwise.
	 * @generated
	 */
	protected byte getByteDistinctFromDefault(EStructuralFeature feature) {
		final byte defaultValue = ((Byte)feature.getDefaultValue()).byteValue();
		return defaultValue == 0 ? (byte)-1 : 0;
	}

	/**
	 * This will return a char that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>'b'</code> if the default is <code>'a'</code>, <code>'a'</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>'b'</code> if the default is <code>'a'</code>, <code>'a'</code> otherwise.
	 * @generated
	 */
	protected char getCharDistinctFromDefault(EStructuralFeature feature) {
		final char defaultValue = ((Character)feature.getDefaultValue()).charValue();
		return defaultValue == 'a' ? 'b' : 'a';
	}

	/**
	 * This will return a double that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>-1d</code> if the default is <code>0d</code>, <code>0d</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>-1d</code> if the default is <code>0d</code>, <code>0d</code> otherwise.
	 * @generated
	 */
	protected double getDoubleDistinctFromDefault(EStructuralFeature feature) {
		final double defaultValue = ((Double)feature.getDefaultValue()).doubleValue();
		return defaultValue == 0d ? -1d : 0d;
	}

	/**
	 * This will return a float that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>-1f</code> if the default is <code>0f</code>, <code>0f</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>-1f</code> if the default is <code>0f</code>, <code>0f</code> otherwise.
	 * @generated
	 */
	protected float getFloatDistinctFromDefault(EStructuralFeature feature) {
		final float defaultValue = ((Float)feature.getDefaultValue()).floatValue();
		return defaultValue == 0f ? -1f : 0f;
	}

	/**
	 * This will return a long that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>-1L</code> if the default is <code>0L</code>, <code>0L</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>-1L</code> if the default is <code>0L</code>, <code>0L</code> otherwise.
	 * @generated
	 */
	protected long getLongDistinctFromDefault(EStructuralFeature feature) {
		final long defaultValue = ((Long)feature.getDefaultValue()).longValue();
		return defaultValue == 0L ? -1L : 0L;
	}

	/**
	 * This will return a short that is distinct from the given <code>feature</code>'s default value.
	 * Namely, <code>-1</code> if the default is <code>0</code>, <code>0</code> otherwise.
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return <code>-1</code> if the default is <code>0</code>, <code>0</code> otherwise.
	 * @generated
	 */
	protected short getShortDistinctFromDefault(EStructuralFeature feature) {
		final short defaultValue = ((Short)feature.getDefaultValue()).shortValue();
		return defaultValue == 0 ? (short)-1 : 0;
	}

	/**
	 * This will return an {@link Object} that is distinct from the given <code>feature</code>'s default
	 * value, yet having the same type.
	 * <table>
	 * <tr>
	 * <td>Type</td>
	 * <td>Returned value</td>
	 * </tr>
	 * <tr>
	 * <td>EBoolean_OBJECT</td>
	 * <td>{@link Boolean#FALSE} if default is {@link Boolean#TRUE}, {@link Boolean#TRUE} otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>EINTEGER_OBJECT</td>
	 * <td><code>-1</code> if default is <code>0</code>, <code>0</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>EBYTE_OBJECT</td>
	 * <td><code>-1</code> if default is <code>0</code>, <code>0</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>ECHARACTER_OBJECT</td>
	 * <td><code>'b'</code> if default is <code>'a'</code>, <code>'a'</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>EDOUBLE_OBJECT</td>
	 * <td><code>-1d</code> if default is <code>0d</code>, <code>0d</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>EFLOAT_OBJECT</td>
	 * <td><code>-1f</code> if default is <code>0f</code>, <code>0f</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>ELONG_OBJECT</td>
	 * <td><code>-1L</code> if default is <code>0L</code>, <code>0L</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>ESHORT_OBJECT</td>
	 * <td><code>-1</code> if default is <code>0</code>, <code>0</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>ESTRING</td>
	 * <td><code>"notdefault"</code> if default is <code>""</code>, <code>""</code> otherwise</td>
	 * </tr>
	 * <tr>
	 * <td>EDATE</td>
	 * <td>Returns the current date</td>
	 * </tr>
	 * <tr>
	 * <td>EJAVA_OBJECT</td>
	 * <td>Returns a new Object</td>
	 * </tr>
	 * </table>
	 * 
	 * @param feature
	 *            Feature which default value is to be considered.
	 * @return An {@link Object} that is distinct from the given <code>feature</code>'s default value.
	 * @generated
	 */
	protected Object getValueDistinctFromDefault(EStructuralFeature feature) {
		final Object defaultValue = feature.getDefaultValue();
		if (feature.getEType() == EcorePackage.Literals.EBOOLEAN_OBJECT) {
			return defaultValue == Boolean.TRUE ? Boolean.FALSE : Boolean.TRUE;
		} else if (feature.getEType() == EcorePackage.Literals.EINTEGER_OBJECT) {
			return defaultValue == Integer.valueOf(0) ? Integer.valueOf(-1) : Integer.valueOf(0);
		} else if (feature.getEType() == EcorePackage.Literals.EBYTE_OBJECT) {
			return defaultValue == Byte.valueOf("0") ? Byte.valueOf("-1") : Byte.valueOf("0");
		} else if (feature.getEType() == EcorePackage.Literals.ECHARACTER_OBJECT) {
			return defaultValue == Character.valueOf('a') ? Character.valueOf('b') : Character.valueOf('a');
		} else if (feature.getEType() == EcorePackage.Literals.EDOUBLE_OBJECT) {
			return defaultValue == Double.valueOf(0) ? Double.valueOf(-1) : Double.valueOf(0);
		} else if (feature.getEType() == EcorePackage.Literals.EFLOAT_OBJECT) {
			return defaultValue == Float.valueOf(0) ? Float.valueOf(-1) : Float.valueOf(0);
		} else if (feature.getEType() == EcorePackage.Literals.ELONG_OBJECT) {
			return defaultValue == Long.valueOf(0) ? Long.valueOf(-1) : Long.valueOf(0);
		} else if (feature.getEType() == EcorePackage.Literals.ESHORT_OBJECT) {
			return defaultValue == Short.valueOf((short)0) ? Short.valueOf((short)-1) : Short.valueOf((short)0);
		} else if (feature.getEType() == EcorePackage.Literals.ESTRING) {
			return "".equals(defaultValue) ? "notdefault" : "";
		} else if (feature.getEType() == EcorePackage.Literals.EDATE) {
			return Calendar.getInstance().getTime();
		} else if (feature.getEType() == EcorePackage.Literals.EJAVA_OBJECT) {
			return new Object();
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * This basic implementation of an {@link org.eclipse.emf.common.notify.Adapter} will simply switch a
	 * boolean to true when it is notified of a change.
	 * 
	 * @generated
	 */
	protected class MockEAdapter extends AdapterImpl {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 * @generated
		 */
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			notified = true;
		}
	}
}