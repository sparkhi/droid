/**
 * Copyright (c) 2016-19, The National Archives <pronom@nationalarchives.gsi.gov.uk>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following
 * conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of the The National Archives nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/*
 * The National Archives 2005-2006.  All rights reserved.
 * See Licence.txt for full licence details.
 *
 * Developed by:
 * Tessella Support Services plc
 * 3 Vineyard Chambers
 * Abingdon, OX14 3PX
 * United Kingdom
 * http://www.tessella.com
 *
 * Tessella/NPD/4305
 * PRONOM 4
 *
 * SAXModelBuilder.java
 *
 * $Id: SAXModelBuilder.java,v 1.7 2006/03/13 15:15:29 linb Exp $
 *
 * $Log: SAXModelBuilder.java,v $
 * Revision 1.7  2006/03/13 15:15:29  linb
 * Changed copyright holder from Crown Copyright to The National Archives.
 * Added reference to licence.txt
 * Changed dates to 2005-2006
 *
 * Revision 1.6  2006/02/09 15:31:23  linb
 * Updates to javadoc and code following the code review
 *
 * Revision 1.5  2006/01/31 16:47:30  linb
 * Added log messages that were missing due to the log keyword being added too late
 *
 * Revision 1.4  2006/01/31 16:21:20  linb
 * Removed the dollars from the log lines generated by the previous message,
 * so as not to cause problems with subsequent commits
 *
 * Revision 1.3  2006/01/31 16:19:07  linb
 * Added Log and Id tags to these files
 *
 * Revision 1.2  2006/01/31 16:11:37  linb
 * Add support for XML namespaces to:
 * 1) The reading of the config file, spec file and file-list file
 * 2) The writing of the config file and file-list file
 * - The namespaces still need to be set to their proper URIs (currently set to example.com...)
 * - Can still read in files without namespaces*
 *
 */
package uk.gov.nationalarchives.droid.core.signature.xml;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.gov.nationalarchives.droid.core.signature.FileFormat;
import uk.gov.nationalarchives.droid.core.signature.FileFormatCollection;
import uk.gov.nationalarchives.droid.core.signature.FileFormatHit;
import uk.gov.nationalarchives.droid.core.signature.droid6.*;

/**
 * reads and parses data from an XML file.
 *
 * @version 4.0.0
 */
public class SAXModelBuilder extends DefaultHandler {

    private static final String ADD = "add";
    private static final String SET = "set";

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private Stack<Object> stack = new Stack<Object>();
    private SimpleElement element;

    private String mySignaturePackage = FFSignatureFile.class.getPackage().getName();
    private String myFormatPackage = FileFormat.class.getPackage().getName();
    
    private String namespace = "";
    private boolean useNamespace;
    private boolean allowGlobalNamespace = true;

    /**
     * 
     * @param theSignaturePackage The signature package to use.
     */
    public void setSignaturePackage(String theSignaturePackage) {
        mySignaturePackage = theSignaturePackage;
    }

    /**
     * Set up XML namespace handling.
     * 
     * <p>If <code>allowGlobalNamespace</code> is set to <code>true</code>, elements
     * that do not have a namespace specified are parsed; attributes that don't
     * have a namespace specified are parsed.  If it is <code>false</code>, for
     * it to be parsed, an element must have a namespace specifed (by default or
     * with a prefix); an attribute must have a namespace specified with a prefix.</p>
     *
     * @param nspace            the XML namespace to use
     * @param globalNamespace allow the parser to recognise elements/ attributes that aren't in any namespace
     */
    public void setupNamespace(String nspace, boolean globalNamespace) {
        if (nspace == null) {
            throw new IllegalArgumentException("Namespace cannot be null");
        }

        this.namespace = nspace;
        this.useNamespace = true;
        this.allowGlobalNamespace = globalNamespace;

    }

    /**
     * Handle names in a namespace-aware fashion.
     * <p/>
     * <p>If an element/ attribute is in a namespace, qname is not required to be set.
     * We must, therefore, use the localname if the namespace is set, and qname if it isn't.
     *
     * @param nspace the namespace uri
     * @param localname the local part of the name
     * @param qname     a qualified name
     * @return the local part or the qualified name, as appropriate
     */
    private String handleNameNS(String nspace, String localname, String qname) {
        String result = null;
        if (this.useNamespace && this.namespace.equals(nspace)) {
            // Name is in the specified namespace
            result = localname;
        } else if (this.allowGlobalNamespace && "".equals(nspace)) {
            // Name is in the global namespace
            result = qname;
        }
        return result;
    }

    /**
     * @param nspace the namespace uri
     * @param localname the local part of the name
     * @param qname     a qualified name
     * @param atts The attributes of the element.
     */
    @Override
    public void startElement(String nspace, String localname, String qname, Attributes atts) {
        String elementName = handleNameNS(nspace, localname, qname);
        if (elementName == null) {
            return;
        }
        final SimpleElement elem;
        switch (elementName) {
            case "FileFormat": elem = new FileFormat(); break;
            case "FileFormatHit": elem = new FileFormatHit(); break;
            case "FileFormatCollection": elem = new FileFormatCollection(); break;
            case "ByteSequence": elem = new ByteSequence(); break;
            case "FFSignatureFile": elem = new FFSignatureFile(); break;
            case "InternalSignature": elem = new InternalSignature(); break;
            case "InternalSignatureCollection": elem = new InternalSignatureCollection(); break;
            case "LeftFragment": elem = new LeftFragment(); break;
            case "RightFragment": elem = new RightFragment(); break;
            case "Shift": elem = new Shift(); break;
            case "SubSequence": elem = new SubSequence(); break;
            default: elem = new SimpleElement();
        }

        for (int i = 0; i < atts.getLength(); i++) {
            String attributeName = handleNameNS(atts.getURI(i), atts.getLocalName(i), atts.getQName(i));
            if (attributeName == null) {
                continue;
            }
            elem.setAttributeValue(attributeName, atts.getValue(i));
        }
        stack.push(elem);
    }

    /**
     * @param nspace the namespace uri
     * @param localname the local part of the name
     * @param qname     a qualified name 
     * @throws SAXException if a problem occurs.
     */
    @Override
    public void endElement(String nspace, String localname, String qname) throws SAXException {
        String elementName = handleNameNS(nspace, localname, qname);
        if (elementName == null) {
            return;
        }
        element = (SimpleElement) stack.pop();
        element.completeElementContent();
        if (!stack.empty()) {
            setProperty(elementName, stack.peek(), element);
        }
    }

    @Override
    public void characters(char[] ch, int start, int len) {
        if (!stack.empty()) { // Ignore character data if we don't have an element to put it in.
            String text = new String(ch, start, len);
            ((SimpleElement) (stack.peek())).setText(text);
        }
    }

    private void setProperty(String name, Object target, Object value)  {
        switch (target.getClass().getSimpleName()) {
            case "SubSequence": setSubSequenceProperty((SubSequence) target, name, value); break;
            case "ByteSequence": setByteSequenceProperty((ByteSequence) target, name, value); break;
            case "InternalSignature": setInternalSignatureProperty((InternalSignature) target, name, value); break;
            case "FFSignatureFile": setFFSignatureFileProperty((FFSignatureFile) target, name, value); break;
            case "InternalSignatureCollection": setInternalSignatureCollectionProperty((InternalSignatureCollection) target, name, value); break;
            case "FileFormat": setFileFormatProperty((FileFormat) target, name, value); break;
            case "FileFormatCollection": setFileFormatCollection((FileFormatCollection) target, name, value); break;
            default: log.warn("Unknown target object: " + target.toString());
        }
    }

    private void setFileFormatCollection(FileFormatCollection target, String name, Object value) {
        switch (name) {
            case "FileFormat": target.addFileFormat((FileFormat) value); break;
            default: logUnknownProperty(name, target);
        }
    }

    private void setFileFormatProperty(FileFormat target, String name, Object value) {
        String valueText = (value instanceof SimpleElement)? ((SimpleElement) value).getText().trim() : "";
        switch (name) {
            case "Extension": target.setExtension(valueText); break;
            case "InternalSignatureID": target.setInternalSignatureID(valueText); break;
            case "HasPriorityOverFileFormatID": target.setHasPriorityOverFileFormatID(valueText); break;
            default: logUnknownProperty(name, target);
        }
    }

    private void setInternalSignatureCollectionProperty(InternalSignatureCollection target, String name, Object value) {
        switch (name) {
            case "InternalSignature": target.addInternalSignature((InternalSignature) value); break;
            default: logUnknownProperty(name, target);
        }
    }

    private void setFFSignatureFileProperty(FFSignatureFile target, String name, Object value) {
        switch (name) {
            case "InternalSignatureCollection": target.setInternalSignatureCollection((InternalSignatureCollection) value); break;
            case "FileFormatCollection": target.setFileFormatCollection((FileFormatCollection) value); break;
            default: logUnknownProperty(name, target);
        }
    }

    private void setInternalSignatureProperty(InternalSignature target, String name, Object value) {
        switch (name) {
            case "ByteSequence": target.addByteSequence((ByteSequence) value); break;
            default: logUnknownProperty(name, target);
        }
    }

    private void setByteSequenceProperty(ByteSequence target, String name, Object value) {
        switch(name) {
            case "SubSequence": target.addSubSequence(((SubSequence) value)); break;
            default: logUnknownProperty(name, target);
        }
    }

    private void setSubSequenceProperty(SubSequence target, String name, Object value) {
        switch(name) {
            case "LeftFragment": target.addLeftFragment((LeftFragment) value); break;
            case "RightFragment": target.addRightFragment((RightFragment) value); break;
            case "Sequence": target.setSequence(((SimpleElement) value).getText().trim()); break;
            case "Shift": case "DefaultShift": break; // Ignore Shift and DefaultShift - they are deprecated.
            default: logUnknownProperty(name, target);
        }
    }

    private void logUnknownProperty(String propertyName, Object target) {
        log.warn("Unknown property " + propertyName + " requested for " + target.getClass().getSimpleName());
    }

    /**
     * 
     * @return The element.
     */
    public SimpleElement getModel() {
        return element;
    }
    
    /**
     * Displays a special warning for unknown XML elements when reading
     * XML files.
     *
     * @param unknownElement   The name of the element which was not recognised
     * @param containerElement The name of the element which contains the unrecognised element
     */
    public void unknownElementWarning(String unknownElement, String containerElement) {
        String warning = "WARNING: Unknown XML element " + unknownElement + " found under " + containerElement + " ";
        log.trace(warning);
    }    

}
