/**
 * 
 */
package com.dannyayers.templates

/**
 * @author danny
 *
 */

  object TtrackerInclusion {
	var content = """
<!-- Ttracker -->
<script type="text/javascript">
document.write(unescape("%3Cscript src='http://hyperdata.org/ttracker/js/tt.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type='text/javascript'>
	var tracker = new Tracker();
	var url = tracker.getEscapedUrl();
	var datetime = tracker.getEscapedDateTime();
	
	var form = "%3Cform name='ttracker' action='http://hyperdata.org/ttracker/php/tracker.php' method='post'%3E"
    			+ "%3Cinput type='hidden' name='url' value='"+url+"' /%3E" 
    			+ "%3Cinput type='hidden' name='datetime' value='"+datetime+"' /%3E" 
			+ "%3C/form%3E";
	document.write(unescape(form));
	document.ttracker.submit();
</script>
<!-- End Ttracker --> 
"""
}