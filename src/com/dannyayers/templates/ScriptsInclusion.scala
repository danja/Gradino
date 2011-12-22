/**
 * 
 */
package com.dannyayers.templates

/**
 * @author danny
 *
 */

  object ScriptsInclusion {
	var content = """
<!-- button scripts -->
<script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
<script type="text/javascript">
  window.___gcfg = {lang: 'en-GB'};
  (function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/plusone.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>
"""
}