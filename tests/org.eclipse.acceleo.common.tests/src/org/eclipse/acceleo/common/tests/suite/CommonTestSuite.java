/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.tests.suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.common.tests.unit.utils.AcceleoCommonPluginTest;
import org.eclipse.acceleo.common.tests.unit.utils.MessagesTest;

/**
 * This suite will launch all the tests defined for the Acceleo common plugin.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CommonTestSuite extends TestCase {
	/**
	 * Launches the test with the given arguments.
	 * 
	 * @param args
	 *            Arguments of the testCase.
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * Creates the {@link junit.framework.TestSuite TestSuite} for all the test.
	 * 
	 * @return The test suite containing all the tests
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("Acceleo common test suite"); //$NON-NLS-1$

		suite.addTestSuite(MessagesTest.class);
		suite.addTestSuite(AcceleoCommonPluginTest.class);

		return suite;
	}
}