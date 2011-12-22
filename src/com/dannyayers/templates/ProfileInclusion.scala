/**
 * 
 */
package com.dannyayers.templates

/**
 * @author danny
 *
 */

  object ProfileInclusion {
	var content = """
<section itemscope itemtype="http://schema.org/Person"> 
    <img itemprop="photo" class="me" width="48" height="48"
         src="/icons/danny.jpg"
         alt="Danny Ayers" /> 
 
    <h4>Contact</h4> 
    <ul> 
<li><span itemprop="name">Danny Ayers</span></li>
<li>(<span itemprop="nickname">danja</span>)</li>
<li><a href="mailto:danny.ayers@gmail.com" itemprop="url">email</a></li>
<li><a href="http://danny.ayers.name/index.rdf"
           itemprop="url"><img src="/icons/foaf.gif" alt="FOAF Profile" width="26" height="14" /> FOAF Profile</a></li>
</ul>
<h4>Nearby</h4>
<ul>
    <li><a href="/about.html" itemprop="url">About</a></li>
    <li><a href="/feed"
           itemprop="url"><img src="/icons/rss.png" alt="Feed" width="24" height="24" /> Feed</a></li>
    <li><a href="http://hyperdata.org"
           itemprop="url">planet danja</a> (me aggregated)</li>
    <li><a href="http://danny.ayers.name"
           itemprop="url">danny.ayers.name</a> (resume etc)</li>
<li><a href="/" itemprop="url">Raw</a> (blog)</li>
    <li><a href="http://hyperdata.org"
           itemprop="url">Hyperdata</a> (tech/projects)</li>
    <li><a href="http://hyperdata.org/xmlns/"
           itemprop="url">Vocabs</a></li>
    <li><a href="http://spikeandwave.com/"
           itemprop="url">Spike and Wave</a> (music)</li>
</ul>
<h4>Archives</h4>
<ul>
    <li><a href="/archive/older.html" itemprop="url">pre-2004</a><em>(dates messed up!)</em></li>
    <li><a href="/archive/2004.html" itemprop="url">2004</a></li>
    <li><a href="/archive/2005.html" itemprop="url">2005</a></li>
    <li><a href="/archive/2006.html" itemprop="url">2006</a></li>
    <li><a href="/archive/2007.html" itemprop="url">2007</a></li>
    <li><a href="/archive/2008.html" itemprop="url">2008</a></li>
    <li><a href="/archive/2009.html" itemprop="url">2009</a></li>
    <li><a href="/archive/2010.html" itemprop="url">2010</a></li>
    <li><a href="/archive/2011.html" itemprop="url">2011</a></li>
</ul>
<h4>Elsewhere</h4>
<ul>
    <li><a href="https://plus.google.com/112609322932428633493?rel=author"
           itemprop="url" rel="author"><img src="/icons/plus.png" alt="Google Plus" width="24" height="24" /> Google Plus</a></li>
    <li><a href="http://twitter.com/danja"
           itemprop="url"><img src="/icons/twitter.png" alt="Twitter" width="24" height="24" /> Twitter</a></li>
    <li><a href="http://www.facebook.com/danny.ayers"
           itemprop="url"><img src="/icons/fb.png" alt="Facebook" width="24" height="24" /> Facebook</a></li>
    <li><a href="http://www.linkedin.com/in/danja"
           itemprop="url"><img src="/icons/li.png" alt="LinkedIn" width="24" height="24" /> LinkedIn</a></li>
    <li><a href="http://flickr.com/danja"
           itemprop="url"><img src="/icons/flickr.png" alt="Flickr" width="24" height="24" /> Flickr</a> (photos)</li>
    <li><a href="http://www.youtube.com/user/djayers"
           itemprop="url"><img src="/icons/youtube.png" alt="YouTube" width="24" height="24" /> YouTube</a> (videos)</li>
    <li><a href="http://www.slideshare.net/danja/"
           itemprop="url"><img src="/icons/ss.png" alt="slideshare" width="24" height="24" /> slideshare</a> (presentations)</li>
    <li><a href="http://del.icio.us/danja"
           itemprop="url"><img src="/icons/delicious.png" alt="del.icio.us" width="24" height="24" /> del.icio.us</a> (links)</li>
    <li><a href="http://www.goodreads.com/user/show/551528-danny-ayers"
           itemprop="url"><img src="/icons/gr.png" alt="GoodReads" width="24" height="24" /> GoodReads</a> (books)</li>
    <li><a href="https://github.com/danja"
           itemprop="url">github</a> (code)</li>
    <li><a href="http://knowledgeforge.net/person/danja/"
           itemprop="url">KnowledgeForge</a> (code)</li>
    <li><a href="http://www.w3.org/wiki/DannyAyers"
           itemprop="url">W3C Wiki</a> (bits)</li>
    </ul> 
<form action="https://www.paypal.com/cgi-bin/webscr" method="post"><input type="hidden" name="cmd" value="_s-xclick"><input type="hidden" name="hosted_button_id" value="HX3P7DNMUR7CW"><input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!"><img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1"></form>
  </section>    
"""
}