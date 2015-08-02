package com.gravity.goose.outputformatters

import com.gravity.goose.Paragraph
import com.gravity.goose.utils.Logging
import org.apache.commons.lang.StringEscapeUtils
import org.jsoup.nodes.Element

/**
 * Created by venkat on 01/08/15.
 */
case object ParagraphOutputFormatter extends OutputFormatter with Logging {

    def getParagraphs(topNode: Element) = topNode match {
        case null => Vector()
        case node => {
            getFormattedElement(node)
            (Vector[Paragraph]() /: node.children().toArray(Array[Element]()))((acc, node) => {
                acc :+ Paragraph(getXpathForElement(node), StringEscapeUtils.unescapeHtml(node.text).trim)
            })
        }
    }

    def getIndexOfChild(parent: Element, child: Element) = {
        (((parent children ()).toArray(Array[Element]()) filter
            ((ch) => (ch tagName ()) == (child tagName ()))) indexOf (child)) + 1
    }

    def getXpathForElement(elem: Element) = {
        def xpathHelper(e: Element, xpath: String): String = e match {
            case null => xpath
            case el: Element => {
                if(el hasAttr ("id")) {
                    "//" + (el tagName) + "[@id='" + (el attr ("id")) + "']" + xpath
                }
                else xpathHelper((el parent), "/" + (el tagName) + "[" + getIndexOfChild((el parent), el) + "]" + xpath)
            }
        }
        xpathHelper(elem, "")
    }
    
}
