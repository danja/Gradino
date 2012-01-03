#!/usr/bin/python

# http://www.pythonware.com/products/pil/
 
from PIL import Image
import os

path = "images2/preferred/"
thumb_path = "images2/thumbs/"
dirList=os.listdir(path)
gallery = ""
inline = ""

for filename in dirList:
#   print "FILE="+filename
   ext = filename[len(filename)-4:]
#   print "EXT="+ext
   if ext != ".jpg" and ext != ".png" and ext != ".gif": continue
#   print "HERE="+filename[len(filename)-4:]
   label = filename[0:(len(filename) - 4)].title() # .title does the upper/lower case thing
   img = Image.open(path+filename)
 #  width, height = img.size // not needed
   thumb = Image.open(thumb_path+filename)
   thumb_width, thumb_height = thumb.size
   gallery = gallery + """
<li>
   <br />""" + label + """
   <br />
   <a href="images/preferred/"""+filename+"""" rel="lightbox" title=\""""+label+"""">
      <img alt=\""""+label+"""" src="images/thumbs/"""+filename+"""" 
           width=\""""+str(thumb_width)+"""\" height=\""""+str(thumb_height)+"""\" />
   </a>
</li>
"""
   inline = inline + """
<p>
   <a href="images/preferred/"""+filename+"""" rel="lightbox" title=\""""+label+"""">
      <img alt=\""""+label+"""" src="images/thumbs/"""+filename+"""" 
           width=\""""+str(thumb_width)+"""" height=\""""+str(thumb_height)+"""" />
   </a>
</p>"""
print gallery
print inline





