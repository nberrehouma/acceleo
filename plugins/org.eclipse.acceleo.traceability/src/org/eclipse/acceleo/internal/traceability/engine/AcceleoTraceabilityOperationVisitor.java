/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * The purpose of this Utility class is to allow execution of the subset of Standard and non standard Acceleo
 * operations, as well as the small subset of OCL operations that impacts traceability.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <C>
 *            Will be either EClassifier for ecore or Classifier for UML.
 * @param <PM>
 *            Will be either EParameter for ecore or Parameter for UML.
 */
public final class AcceleoTraceabilityOperationVisitor<C, PM> {
	/** The evaluation visitor that spawned this operation visitor. */
	private AcceleoTraceabilityVisitor<EPackage, C, EOperation, EStructuralFeature, EEnumLiteral, PM, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> visitor;

	/**
	 * Instantiates an operation visitor given its parent evaluation visitor.
	 * 
	 * @param visitor
	 *            Our parent evaluation visitor.
	 */
	public AcceleoTraceabilityOperationVisitor(
			AcceleoTraceabilityVisitor<EPackage, C, EOperation, EStructuralFeature, EEnumLiteral, PM, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> visitor) {
		this.visitor = visitor;
	}

	/**
	 * Handles the "first" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param endIndex
	 *            Index at which the substring ends.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitFirstOperation(String source, int endIndex) {
		final String result;
		if (endIndex < 0 || endIndex > source.length()) {
			result = source;
		} else {
			result = visitSubstringOperation(source, 0, endIndex);
		}
		return result;
	}

	/**
	 * Handles the "index" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to find a given index.
	 * @param substring
	 *            The substring we seek within <em>source</em>.
	 * @return Index of <em>substring</em> within <em>source</em>, -1 if it wasn't contained. Traceability
	 *         information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Integer visitIndexOperation(String source, String substring) {
		int result = source.indexOf(substring);
		final int digitCount;
		if (result == -1) {
			digitCount = 2;
		} else {
			digitCount = 1 + (int)Math.log(result);
		}

		/*
		 * We need to remove all recorded traces before this call, retaining the one that gave the region
		 * containing our index.
		 */
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (text.getEndOffset() < result || text.getStartOffset() > result) {
					entry.getValue().remove(text);
				} else {
					text.setStartOffset(0);
					text.setEndOffset(digitCount);
				}
			}
		}
		trace.setOffset(digitCount);

		// Increment java index value by 1 for OCL
		result++;
		if (result == Integer.valueOf(0)) {
			result = Integer.valueOf(-1);
		}
		return Integer.valueOf(result);
	}

	/**
	 * Handles the "isAlphanum" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String tht is to be considered.
	 * @return <code>true</code> iff the string is composed of alphanumeric characters. Traceability
	 *         information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitIsAlphanumOperation(String source) {
		boolean result = true;
		final char[] chars = source.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				result = false;
				break;
			}
		}

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "isAlpha" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String tht is to be considered.
	 * @return <code>true</code> iff the string is composed of alphabetic characters. Traceability information
	 *         will have been changed directly within {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitIsAlphaOperation(String source) {
		boolean result = true;
		final char[] chars = source.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetter(c)) {
				result = false;
				break;
			}
		}

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "last" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param charCount
	 *            Number of characters to keep.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitLastOperation(String source, int charCount) {
		final String result;
		if (charCount < 0 || charCount > source.length()) {
			result = source;
		} else {
			result = visitSubstringOperation(source, source.length() - charCount, source.length());
		}
		return result;
	}

	/**
	 * Handles the "substitute" and "replace" standard operations directly from the traceability visitor so as
	 * to record accurate traceability information.
	 * 
	 * @param source
	 *            String within which we need to replace substrings.
	 * @param substring
	 *            The substring that is to be substituted.
	 * @param replacement
	 *            The String which will be inserted in <em>source</em> where <em>substring</em> was.
	 * @param substitutionTrace
	 *            Traceability information of the replacement.
	 * @param substituteAll
	 *            Indicates wheter we should substitute all occurences of the substring or only the first.
	 * @param useInvocationTrace
	 *            If <code>true</code>, {@link #invocationTraces} will be altered in place of the last
	 *            {@link AcceleoTraceabilityVisitor#recordedTraces}. Should only be <code>true</code> when
	 *            altering indentation.
	 * @return <em>source</em> with the first occurence of <em>substring</em> replaced by <em>replacement</em>
	 *         or <em>source</em> unchanged if it did not contain <em>substring</em>.
	 */
	public String visitReplaceOperation(String source, String substring, String replacement,
			ExpressionTrace<C> substitutionTrace, boolean substituteAll, boolean useInvocationTrace) {
		if (substring == null || replacement == null) {
			throw new NullPointerException();
		}

		Matcher sourceMatcher = Pattern.compile(substring).matcher(source);
		StringBuffer result = new StringBuffer();
		boolean hasMatch = sourceMatcher.find();
		// Note : despite its name, this could be negative
		int addedLength = 0;
		// FIXME This loop does _not_ take group references into account except "$0 at the start"
		boolean startsWithZeroGroupRef = replacement.startsWith("$0"); //$NON-NLS-1$
		while (hasMatch) {
			// If we've already changed the String size, take it into account
			int startIndex = sourceMatcher.start() + addedLength;
			int endIndex = sourceMatcher.end() + addedLength;
			if (startsWithZeroGroupRef) {
				startIndex = endIndex;
			}
			sourceMatcher.appendReplacement(result, replacement);
			int replacementLength = result.length() - startIndex;
			// We now remove from the replacementLength the length of the replaced substring
			replacementLength -= endIndex - startIndex;
			addedLength += replacementLength;

			// Note that we could be out of a file block's scope
			if (useInvocationTrace && visitor.getCurrentFiles().size() > 0) {
				// We need the starting index of these traces
				int offsetGap = -1;
				for (ExpressionTrace<C> trace : visitor.getInvocationTraces()) {
					for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
						for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
							if (offsetGap == -1 || text.getStartOffset() < offsetGap) {
								offsetGap = text.getStartOffset();
							}
						}
					}
				}
				startIndex += offsetGap;
				endIndex += offsetGap;
				for (ExpressionTrace<C> trace : visitor.getInvocationTraces()) {
					changeTraceabilityIndicesOfReplaceOperation(trace, startIndex, endIndex,
							replacementLength);
				}
				GeneratedFile generatedFile = visitor.getCurrentFiles().getLast();
				final int fileLength = generatedFile.getLength();
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : substitutionTrace.getTraces()
						.entrySet()) {
					for (GeneratedText text : entry.getValue()) {
						GeneratedText copy = (GeneratedText)EcoreUtil.copy(text);
						copy.setStartOffset(copy.getStartOffset() + startIndex);
						copy.setEndOffset(copy.getEndOffset() + startIndex);
						generatedFile.getGeneratedRegions().add(copy);
						for (ExpressionTrace<C> trace : visitor.getInvocationTraces()) {
							insertTextInTrace(trace, copy);
						}
					}
				}
				generatedFile.setLength(fileLength + replacementLength);
			} else {
				ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
				changeTraceabilityIndicesOfReplaceOperation(trace, startIndex, endIndex, replacementLength);
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : substitutionTrace.getTraces()
						.entrySet()) {
					Set<GeneratedText> existingTraces = trace.getTraces().get(entry.getKey());
					if (existingTraces == null) {
						existingTraces = new LinkedHashSet<GeneratedText>();
						trace.getTraces().put(entry.getKey(), existingTraces);
					}
					for (GeneratedText text : entry.getValue()) {
						GeneratedText copy = (GeneratedText)EcoreUtil.copy(text);
						copy.setStartOffset(copy.getStartOffset() + startIndex);
						copy.setEndOffset(copy.getEndOffset() + startIndex);
						existingTraces.add(copy);
					}
				}
			}

			if (!substituteAll) {
				// Do once
				break;
			}
			hasMatch = sourceMatcher.find();
		}
		sourceMatcher.appendTail(result);
		return result.toString();
	}

	/**
	 * Takes care of the "sep" non standard operations from the traceability visitor so as to track
	 * traceability information for the added separators.
	 * 
	 * @param source
	 *            Collection in which we need to add the separators.
	 * @param separator
	 *            Separator that is to be added in-between elements.
	 * @return The source collection, with <em>separator</em> inserted in-between each couple of elements from
	 *         the <em>source</em>.
	 */
	public Collection<Object> visitSepOperation(Collection<Object> source, String separator) {
		final Collection<Object> temp = new ArrayList<Object>(source.size() << 1);
		final Iterator<?> sourceIterator = source.iterator();
		while (sourceIterator.hasNext()) {
			temp.add(sourceIterator.next());
			if (sourceIterator.hasNext()) {
				temp.add(separator);
			}
		}
		return temp;
	}

	/**
	 * Handles the "substring" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param startIndex
	 *            Index at which the substring starts.
	 * @param endIndex
	 *            Index at which the substring ends.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitSubstringOperation(String source, int startIndex, int endIndex) {
		changeTraceabilityIndicesSubstringReturn(startIndex, endIndex);

		return source.substring(startIndex, endIndex);
	}

	/**
	 * Handles the "trim" non-standard operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be trimmed.
	 * @return The trimmed String. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitTrimOperation(String source) {
		return visitTrimOperation(source, 0);
	}

	/**
	 * This will be used to trim the protected area markers.
	 * 
	 * @param source
	 *            String that is to be trimmed.
	 * @param gap
	 *            The marker might begin at an index superior to 0.
	 * @return The trimmed String. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitTrimOperation(String source, int gap) {
		int start = 0;
		int end = source.length();
		char[] chars = source.toCharArray();
		while (start < end && chars[start] <= ' ') {
			start++;
		}
		while (start < end && chars[end - 1] <= ' ') {
			end--;
		}

		start += gap;
		end += gap;
		changeTraceabilityIndicesSubstringReturn(start, end);

		return source.trim();
	}

	/**
	 * isAlpha, isAlphanum and possibily other traceability impacting operations share the same "basic"
	 * behavior of altering indices to reflect a boolean return. This behavior is externalized here.
	 * 
	 * @param result
	 *            Boolean result of the operation. We'll only leave a four-characters long legion if
	 *            <code>true</code>, five-characters long if <code>false</code>.
	 */
	private void changeTraceabilityIndicesBooleanReturn(boolean result) {
		// We'll keep only the very last trace and alter its indices
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		Map.Entry<InputElement, Set<GeneratedText>> lastEntry = null;
		GeneratedText lastRegion = null;
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (lastRegion == null) {
					lastRegion = text;
					lastEntry = entry;
				} else if (text.getEndOffset() > lastRegion.getEndOffset()) {
					// lastEntry cannot be null once we get here
					assert lastEntry != null;
					lastEntry.getValue().remove(lastRegion);
					lastRegion = text;
					lastEntry = entry;
				} else {
					entry.getValue().remove(text);
				}
			}
		}
		final int length;
		if (result) {
			length = 4;
		} else {
			length = 5;
		}
		if (lastRegion != null) {
			lastRegion.setStartOffset(0);
			lastRegion.setStartOffset(length);
		}
		trace.setOffset(length);
	}

	/**
	 * This will be in charge of altering traceability indices of the given <em>trace</em> to cope with a
	 * substitute or replace operation. That means changing all traces which offsets overlap with the range
	 * <em>[startIndex, endIndex]</em> so that the given <em>substitutionTrace</em> can be used in this range
	 * ; for a total length of <em>replacementLength</em>.
	 * 
	 * @param trace
	 *            The trace which offsets are to be altered.
	 * @param startIndex
	 *            Starting index of the range where a substring has been replaced.
	 * @param endIndex
	 *            Ending index of the range where a substring has been replaced.
	 * @param replacementLength
	 *            Total length of the replaced substring. This is not always equal to the length of the range
	 *            <em>[startIndex, endIndex]</em> as we <u>can</u> replace a small substring by a large one,
	 *            or the opposite ; which also mean that <em>replacementLength</em> <u>can</u> be negative.
	 */
	@SuppressWarnings("unchecked")
	private void changeTraceabilityIndicesOfReplaceOperation(ExpressionTrace<C> trace, int startIndex,
			int endIndex, int replacementLength) {
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (text.getEndOffset() < startIndex) {
					continue;
				}
				/*
				 * This can be one of five cases : 1) the text ends with a replaced substring, 2) the text
				 * starts with a replaced substring, 3) the text contains a replaced substring, 4) the text is
				 * contained within a replaced substring or 5) The text starts after the replacement
				 */
				if (text.getStartOffset() < startIndex && text.getEndOffset() == endIndex) {
					text.setEndOffset(startIndex);
				} else if (text.getStartOffset() == startIndex && text.getEndOffset() > endIndex) {
					text.setStartOffset(startIndex + replacementLength);
					text.setEndOffset(text.getEndOffset() + replacementLength);
				} else if (text.getStartOffset() < startIndex && text.getEndOffset() > endIndex) {
					// This instance of a GeneratedText is split in two by the substring
					GeneratedText endSubstring = (GeneratedText)EcoreUtil.copy(text);
					endSubstring.setStartOffset(endIndex + replacementLength);
					endSubstring.setEndOffset(text.getEndOffset() + replacementLength);
					text.setEndOffset(startIndex);
					if (text.eContainer() != null) {
						// We know this is a list
						((List<EObject>)text.eContainer().eGet(text.eContainingFeature())).add(endSubstring);
					}
					entry.getValue().add(endSubstring);
				} else if (text.getStartOffset() >= startIndex && text.getEndOffset() <= endIndex) {
					if (text.eContainer() != null) {
						EcoreUtil.remove(text);
					}
					entry.getValue().remove(text);
				} else {
					text.setStartOffset(text.getStartOffset() + replacementLength);
					text.setEndOffset(text.getEndOffset() + replacementLength);
				}
			}
		}
	}

	/**
	 * Substring, trim and possibily other traceability impacting operations share the same "basic" behavior
	 * of altering start and end indices without changing "inside". This behavior is externalized here.
	 * 
	 * @param startIndex
	 *            Index at which the substring starts.
	 * @param endIndex
	 *            Index at which the substring ends.
	 */
	private void changeTraceabilityIndicesSubstringReturn(int startIndex, int endIndex) {
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (text.getEndOffset() <= startIndex || text.getStartOffset() >= endIndex) {
					entry.getValue().remove(text);
				} else {
					/*
					 * We have four cases : either 1) the region overlaps with the start index, 2) it overlaps
					 * with the end index, 3) it overlaps with both or 4) it overlaps with none.
					 */
					if (text.getStartOffset() < startIndex && text.getEndOffset() > endIndex) {
						text.setStartOffset(0);
						text.setEndOffset(endIndex - startIndex);
					} else if (text.getStartOffset() < startIndex) {
						text.setStartOffset(0);
						text.setEndOffset(text.getEndOffset() - startIndex);
					} else if (text.getEndOffset() > endIndex) {
						text.setStartOffset(text.getStartOffset() - startIndex);
						text.setEndOffset(endIndex - startIndex);
					} else {
						text.setStartOffset(text.getStartOffset() - startIndex);
						text.setEndOffset(text.getEndOffset() - startIndex);
					}
				}
			}
		}
	}

	/**
	 * This will be used to insert the given <code>text</code> in the given <code>trace</code> iff the indices
	 * of this text correspond to one of the trace's texts start or end index.
	 * 
	 * @param trace
	 *            The trace in which we should insert <code>text</code>.
	 * @param text
	 *            The text that is to be inserted if it matches the trace.
	 */
	private void insertTextInTrace(ExpressionTrace<C> trace, GeneratedText text) {
		boolean insert = false;
		final Iterator<Set<GeneratedText>> setIterator = trace.getTraces().values().iterator();
		while (setIterator.hasNext() && !insert) {
			Set<GeneratedText> candidate = setIterator.next();
			final Iterator<GeneratedText> iterator = candidate.iterator();
			while (iterator.hasNext() && !insert) {
				GeneratedText next = iterator.next();
				if (next.getStartOffset() == text.getEndOffset()
						|| next.getEndOffset() == text.getStartOffset()) {
					insert = true;
				}
			}
			if (insert) {
				candidate.add(text);
			}
		}
	}
}