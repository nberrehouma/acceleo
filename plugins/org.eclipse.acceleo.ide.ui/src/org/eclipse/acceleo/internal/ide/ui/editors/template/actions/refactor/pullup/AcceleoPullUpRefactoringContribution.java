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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import java.util.Map;

import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;

/**
 * The refactoring contribution.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpRefactoringContribution extends RefactoringContribution {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.RefactoringContribution#createDescriptor(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.util.Map, int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public RefactoringDescriptor createDescriptor(String id, String project, String description,
			String comment, Map arguments, int flags) throws IllegalArgumentException {
		return new AcceleoPullUpDescriptor(project, description, comment, arguments);
	}

}