package com.gravity.goose

/**
 * Created by venkat on 01/08/15.
 */
case class Paragraph (xpath: String, text: String) {
    override def toString = (xpath, text).toString
}
