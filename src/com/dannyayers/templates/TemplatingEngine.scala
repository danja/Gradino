/* trivial for now - to act as placeholder */
package com.dannyayers.templates

object TemplatingEngine { 
	def applyTemplate(queryTemplate : String, key : String, uri : String) = {
		queryTemplate.replaceAll("%"+key+"%", "<"+uri+">")
	}
}